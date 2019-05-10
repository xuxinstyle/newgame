package com.socket.dispatcher.core;

import com.socket.core.TSession;
import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 20:43
 */
public class EnhanceUtil {
    private static final ClassPool classPool = ClassPool.getDefault();
    private static AtomicInteger index = new AtomicInteger();

    public static IHandlerInvoke createHandlerDefintion(HandlerDefintion defintion) throws Exception{
        Object bean = defintion.getBean();
        Method method = defintion.getMethod();
        String methodName = method.getName();
        Class<?> clz = bean.getClass();
        CtClass enhancedClz = buildCtClass(IHandlerInvoke.class);
        CtField field = new CtField(classPool.get(clz.getCanonicalName()),
                "bean", enhancedClz);
        field.setModifiers(Modifier.PRIVATE);
        enhancedClz.addField(field);
        CtConstructor constructor = new CtConstructor(classPool.get(new String[]{clz.getCanonicalName()}),
                enhancedClz);
        constructor.setBody("{this.bean = $1;}");
        constructor.setModifiers(Modifier.PUBLIC);
        enhancedClz.addConstructor(constructor);
        CtMethod ctMethod = new CtMethod(
                classPool.get(Object.class.getCanonicalName()),
                "invoke",
                classPool.get(new String[]{TSession.class.getCanonicalName(), int.class.getCanonicalName(),
                        Object.class.getCanonicalName()}), enhancedClz);
        ctMethod.setModifiers(Modifier.PUBLIC+Modifier.FINAL);
        boolean noReturn = method.getReturnType().equals(void.class);

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if(noReturn){
            switch (defintion.getIndextype()){
                case INDEX:
                    sb.append("bean." + methodName + "($1,$2("+defintion.getClz().getCanonicalName()+")$3);");
                    break;
                case NOINDEX:
                    sb.append("bean." + methodName + "($1,("+defintion.getClz().getCanonicalName()+")$3);");
                    break;
            }
            sb.append("return null");
        }else{
            switch (defintion.getIndextype()){
                case INDEX:
                    sb.append("return bean." + methodName + "($1,$2("+defintion.getClz().getCanonicalName()+")$3);");
                    break;
                case NOINDEX:
                    sb.append("return bean." + methodName + "($1,("+defintion.getClz().getCanonicalName()+")$3);");
                    break;
            }
        }
        sb.append("}");
        ctMethod.setBody(sb.toString());
        enhancedClz.addMethod(ctMethod);

        Class<?> rClz = enhancedClz.toClass();
        Constructor<?> con = rClz.getConstructor(clz);
        IHandlerInvoke result = (IHandlerInvoke) con.newInstance(bean);
        return result;
    }
    private static CtClass buildCtClass(Class<?> clz) throws Exception{
        CtClass result = classPool.makeClass(clz.getSimpleName() + "Enhance" + index.incrementAndGet());
        result.addInterface(classPool.get(clz.getCanonicalName()));
        return result;
    }
}
