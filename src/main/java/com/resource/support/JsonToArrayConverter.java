package com.resource.support;

import com.socket.utils.JsonUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/6/28 11:12
 */
public class JsonToArrayConverter implements ConditionalGenericConverter {
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if(sourceType.getType()!=String.class){
            return false;
        }
        if(!targetType.getType().isArray()){
            return false;
        }
        return true;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class,Object[].class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        String content = (String) source;
        if(content == null||content.equals("")||content.equals("null")){
            return null;
        }
        if(targetType.getElementTypeDescriptor().isPrimitive()){
            return JsonUtils.string2Object(content,targetType.getType());
        }else{
            if(!content.startsWith("[")){
                content = "[" + content +"]";
            }
            return JsonUtils.string2Array(content,targetType.getElementTypeDescriptor().getType());
        }
    }
}
