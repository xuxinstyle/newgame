package com.resource.reader;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/9 23:00
 */
@Component
public class XlsxRead {
    private static final Logger logger = LoggerFactory.getLogger(XlsxRead.class);

    private final static TypeDescriptor SOURCE_TYPE = TypeDescriptor.valueOf(String.class);
    @Autowired
    private StorageManager storageManager;
    @Autowired
    private ConversionService conversionService;

    public void readXlsx(ResourceDefinition def, Map<String, InputStream> caches) {
        try {
            String location = def.getLocation();
            InputStream inputStream = caches.get(location);
            File excel = new File(location);
            /** 判断文件是否存在*/
            if (excel.isFile() && excel.exists()) {
                String[] split = excel.getName().split("\\.");
                /** .
                 * 是特殊字符，需要转义！！！！！
                 */
                Workbook wb;
                /**
                 * 根据文件后缀（xls/xlsx）进行判断
                 */
                if ("xls".equals(split[1])) {
                    /** 文件流对象*/
                    wb = new HSSFWorkbook(inputStream);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(inputStream);
                } else {
                    logger.error("文件类型错误!");
                    return;
                }

                /**开始解析*/
                Sheet sheet = wb.getSheetAt(0);
                /* 读取表头*/
                int firstIndex = sheet.getFirstRowNum() + 1;
                readHead(def, sheet, firstIndex);
                /**
                 * 读取表资源
                 */
                int firstRowIndex = sheet.getFirstRowNum() + 3;
                int lastRowIndex = sheet.getLastRowNum();
                Map<Object, Object> resourceMap = readResource(def, sheet, firstRowIndex, lastRowIndex);
                storageManager.putStorage(def, resourceMap);
                inputStream.close();
            } else {
                logger.error("找不到[{}]资源文件", location);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<Object, Object> readResource(ResourceDefinition def, Sheet sheet, int firstRowIndex, int lastRowIndex) {
        Map<Object, Object> map = new HashMap<>();
        for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
            /** 遍历行*/
            Row row = sheet.getRow(rIndex);
            if (row != null) {
                int firstCellIndex = row.getFirstCellNum();
                int lastCellIndex = row.getLastCellNum();
                List<String> resourceData = new ArrayList<>();
                for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                    //遍历列
                    Cell cell = row.getCell(cIndex);
                    if (cell != null) {
                        resourceData.add(cell.toString());
                    } else {
                        resourceData.add("");
                    }
                }
                /** 这里的获取资源id，必须放在表的第一列才能算是id*/
                Object index = getIndex(def.getClz(), resourceData);
                Object object = parse(def.getClz(), resourceData);
                doAnalyze(def.getClz(), object);
                map.put(index, object);
            }
        }
        return map;
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
                    logger.error("资源[{}]的方法[{}]失败",clz.getSimpleName(),method.getName());
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    logger.error("资源[{}]的方法[{}]是私有的",clz.getSimpleName(),method.getName());
                    e.printStackTrace();
                }
            }
        }
    }
    private void readHead(ResourceDefinition def, Sheet sheet, int firstIndex) {
        /** 遍历行*/
        Row row = sheet.getRow(firstIndex);
        if (row != null) {
            int firstCellIndex = row.getFirstCellNum();
            int lastCellIndex = row.getLastCellNum();
            List<String> holderName = new ArrayList<>();
            for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                //遍历列
                Cell cell = row.getCell(cIndex);
                if (cell != null) {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    holderName.add(cell.toString());
                }
            }
            List<FieldInfo> fieldList = new ArrayList<>();
            for (int i = 0; i < holderName.size(); i++) {
                try {
                    Field declaredField = def.getClz().getDeclaredField(holderName.get(i));
                    fieldList.add(new FieldInfo(i, declaredField));
                } catch (NoSuchFieldException e) {
                    logger.error("没有字段:[{}]",holderName.get(i));
                    continue;
                }
            }
            FieldManager.getFieldInfoMap().put(def.getClz(), fieldList);
        }
    }

    private Object getIndex(Class<?> clz, List<String> resourceData) {
        try {

            List<FieldInfo> fieldInfos = FieldManager.getFieldInfoMap().get(clz);
            for (FieldInfo fieldInfo : fieldInfos) {
                if ("id".equals(fieldInfo.getField().getName())) {
                    TypeDescriptor typeDescriptor = new TypeDescriptor(fieldInfo.getField());
                    String resource = resourceData.get(fieldInfo.getIndex());
                    String concat = resource;
                    if (fieldInfo.getField().getType().equals(int.class) || fieldInfo.getField().getType().equals(Integer.class)) {
                        concat = resource.replace(".0", "");
                    }
                    resource = concat;

                    Object value = conversionService.convert(resource, SOURCE_TYPE, typeDescriptor);
                    return value;
                }
            }

            FieldInfo fieldInfo = fieldInfos.get(0);
            TypeDescriptor typeDescriptor = new TypeDescriptor(fieldInfo.getField());
            String resource = resourceData.get(fieldInfo.getIndex());
            String concat = resource;
            if (fieldInfo.getField().getType().equals(int.class) || fieldInfo.getField().getType().equals(Integer.class)) {
                concat = resource.replace(".0", "");
            }
            resource = concat;
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
            String concat = context;
            if (field.getType().equals(int.class) || field.getType().equals(Integer.class) || field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                concat = context.replace(".0", "");
            }
            context = concat;
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