package com.resource.reader;

import com.resource.core.StorageManager;
import com.resource.other.ResourceDefinition;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/9 23:00
 */
@Component
public class ReadXlsx {
    private static final Logger logger = LoggerFactory.getLogger(ReadXlsx.class);
    private final static TypeDescriptor SOURCE_TYPE = TypeDescriptor.valueOf(String.class);

    private static ConversionService conversionService = new DefaultConversionService();

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
                int firstIndex = sheet.getFirstRowNum()+1;
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
                    for(int i = 0;i<holderName.size();i++) {
                        try {
                            Field declaredField = def.getClz().getDeclaredField(holderName.get(i));
                            if(declaredField==null){
                                if(logger.isDebugEnabled()){
                                    logger.debug("类{}中没有字段{}",def.getClz().getSimpleName(),holderName.get(i));
                                }
                                continue;
                            }
                            fieldList.add(new FieldInfo(i,declaredField));
                        } catch (NoSuchFieldException e){
                            continue;
                        }
                    }
                    ReadHolder.getFieldInfoMap().put(def.getClz(), fieldList);
                }

                int firstRowIndex = sheet.getFirstRowNum() + 3;
                int lastRowIndex = sheet.getLastRowNum();
                Map<Object, Object> map = new HashMap<>();
                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                    /** 遍历行*/
                    row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        List<String> resourceData = new ArrayList<>();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                            //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                                resourceData.add(cell.toString());
                            }else {
                                resourceData.add("");
                            }
                        }
                        /** 这里的获取资源id，必须放在表的第一列才能算是id*/
                        Object index = getIndex(def.getClz(), resourceData);
                        Object object = parse(def.getClz(), resourceData);
                        map.put(index, object);

                    }
                }
                StorageManager.putStorage(def, map);
                inputStream.close();

            } else {
                logger.error("找不到[{}]资源文件", location);
                return ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getIndex(Class<?> clz, List<String> resourceData) {
        try {

            List<FieldInfo> fieldInfos = ReadHolder.getFieldInfoMap().get(clz);
            for(FieldInfo fieldInfo:fieldInfos){
                if("id".equals(fieldInfo.getField().getName())){
                    TypeDescriptor typeDescriptor = new TypeDescriptor(fieldInfo.getField());
                    String resource =resourceData.get(fieldInfo.getIndex());
                    String concat = resource;
                    if(fieldInfo.getField().getType().equals(int.class)||fieldInfo.getField().getType().equals(Integer.class)){
                        concat = resource.replace(".0", "");
                    }
                    resource = concat;
                    Object value = conversionService.convert(resource, SOURCE_TYPE, typeDescriptor);
                    return value;
                }
            }

            FieldInfo fieldInfo = fieldInfos.get(0);
            TypeDescriptor typeDescriptor = new TypeDescriptor(fieldInfo.getField());
            String resource =resourceData.get(fieldInfo.getIndex());
            String concat = resource;
            if(fieldInfo.getField().getType().equals(int.class)||fieldInfo.getField().getType().equals(Integer.class)){
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
            Map<Class<?>, List<FieldInfo>> fieldInfoMap = ReadHolder.getFieldInfoMap();
            List<FieldInfo> fieldInfos = fieldInfoMap.get(clz);
            for (FieldInfo fieldInfo : fieldInfos) {

                if (!inject(instance, fieldInfo.getField(), resourceData.get(fieldInfo.getIndex()))) {
                    logger.error("注入静态资源[{}]的属性[{}]错误", clz.getSimpleName(), fieldInfo.getField().getName());
                }
            }
            return instance;
        } catch (Exception e) {
            logger.error("解析静态资源{}错误",clz.getSimpleName());
            e.printStackTrace();
            return null;
        }

    }

    private boolean inject(Object instance, Field field, String context) {
        if(context==null||"null".equals(context)||"".equals(context)){
            return true;
        }
        try {
            TypeDescriptor typeDescriptor = new TypeDescriptor(field);
            String concat = context;
            if(field.getType().equals(int.class)||field.getType().equals(Integer.class)||field.getType().equals(long.class)||field.getType().equals(Long.class)){
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
