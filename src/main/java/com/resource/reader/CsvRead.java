package com.resource.reader;

import com.csvreader.CsvReader;
import com.resource.anno.Analyze;
import com.resource.core.FieldManager;
import com.resource.core.StorageManager;
import com.resource.model.FieldInfo;
import com.resource.model.ResourceDefinition;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/9 23:00
 */
@Component
public class CsvRead {
    private static final Logger logger = LoggerFactory.getLogger(CsvRead.class);

    private static final Charset DEFAULT_CHARSET = Charset.forName("GB2312");

    private final static TypeDescriptor SOURCE_TYPE = TypeDescriptor.valueOf(String.class);
    @Autowired
    private StorageManager storageManager;
    @Autowired
    private ConversionService conversionService;

    public void readCsv(ResourceDefinition def, Map<String, InputStream> caches) {
        try {
            String location = def.getLocation();
            InputStream inputStream = caches.get(location);
            CsvReader csvReader = new CsvReader(inputStream, ',', DEFAULT_CHARSET);
            csvReader.readRecord();
            // 读表头
            csvReader.readHeaders();
            // 获取表头
            String[] headers = csvReader.getHeaders();
            List<FieldInfo> fieldList = new ArrayList<>();
            for(int i = 0;i<headers.length;i++) {
                try {
                    Field field = def.getClz().getDeclaredField(headers[i]);
                    fieldList.add(new FieldInfo(i,field));
                } catch (NoSuchFieldException e){
                    continue;
                }
            }
            csvReader.readRecord();
            FieldManager.getFieldInfoMap().put(def.getClz(), fieldList);
            Map<Object, Object> dataMap = new HashMap<>();
            while (csvReader.readRecord()) {
                List<String> resourceData = new ArrayList<>();
                for (int cIndex = 0; cIndex < headers.length; cIndex++) {
                    resourceData.add(csvReader.get(cIndex));
                }
                /** 这里的获取资源id，必须放在表的第一列才能算是id*/
                Object index = getIndex(def.getClz(), resourceData);
                Object object = parse(def.getClz(), resourceData);
                doAnalyze(def.getClz(), object);
                dataMap.put(index, object);
            }
            storageManager.putStorage(def, dataMap);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void doAnalyze(Class<?> clz, Object resource) {
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Analyze.class)) {
                Analyze analyze = field.getAnnotation(Analyze.class);
                Method method = null;
                try {
                    method = clz.getMethod(analyze.value());
                    method.invoke(resource);
                } catch (NoSuchMethodException e) {
                    logger.error("资源[{}]的解析方法[{}]不存在",clz.getSimpleName(),analyze.value());
                    e.printStackTrace();
                }catch (InvocationTargetException e) {
                    logger.error("解析资源[{}]的方法[{}]失败",clz.getSimpleName(),method.getName());
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    logger.error("资源[{}]的方法[{}]是私有的",clz.getSimpleName(),method.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private Object getIndex(Class<?> clz, List<String> resourceData) {
        try {

            List<FieldInfo> fieldInfos = FieldManager.getFieldInfoMap().get(clz);
            for (FieldInfo fieldInfo : fieldInfos) {
                if ("id".equals(fieldInfo.getField().getName())) {
                    TypeDescriptor typeDescriptor = new TypeDescriptor(fieldInfo.getField());
                    String resource = resourceData.get(fieldInfo.getIndex());
                    Object value = conversionService.convert(resource, SOURCE_TYPE, typeDescriptor);
                    return value;
                }
            }

            FieldInfo fieldInfo = fieldInfos.get(0);
            TypeDescriptor typeDescriptor = new TypeDescriptor(fieldInfo.getField());
            String resource = resourceData.get(fieldInfo.getIndex());
            Object value = conversionService.convert(resource, SOURCE_TYPE, typeDescriptor);
            return value;


        } catch (Exception e) {
            logger.error("资源[{}]没有字段名为id的属性", clz.getSimpleName());
            e.printStackTrace();

        }
        return null;
    }

    private Object parse(Class<?> clz, List<String> resourceData) {
        try {
            Object instance = clz.newInstance();
            Map<Class<?>, List<FieldInfo>> fieldInfoMap = FieldManager.getFieldInfoMap();
            List<FieldInfo> fieldInfos = fieldInfoMap.get(clz);
            for (FieldInfo fieldInfo : fieldInfos) {

                if (!inject(instance, fieldInfo.getField(), resourceData.get(fieldInfo.getIndex()))) {
                    logger.error("注入静态资源[{}]的属性[{}]错误", clz.getSimpleName(), fieldInfo.getField().getName());
                }
            }
            return instance;
        } catch (Exception e) {
            logger.error("解析静态资源{}错误", clz.getSimpleName());
            e.printStackTrace();
            return null;
        }

    }

    private boolean inject(Object instance, Field field, String context) {
        if (context == null || "null".equals(context) || "".equals(context)) {
            return true;
        }
        try {
            TypeDescriptor typeDescriptor = new TypeDescriptor(field);
            Object value = conversionService.convert(context, SOURCE_TYPE, typeDescriptor);
            field.set(instance, value);
            return true;
        } catch (ConverterNotFoundException e) {
            logger.error("静态资源[{}]属性[{}]的转换器不存在", instance.getClass().getSimpleName(), field.getName());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("属性[{}]注入失败", field);
            e.printStackTrace();
        }
        return false;
    }

}
