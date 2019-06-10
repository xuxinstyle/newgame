package com.resource.reader;

import com.resource.StorageManager;
import com.resource.other.ResourceDefinition;
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
import org.springframework.util.ReflectionUtils;

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
public class ReadXlsx {
    private static final Logger logger = LoggerFactory.getLogger(ReadXlsx.class);

    @Autowired
    private ConversionService conversionService;

    private final static TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);

    public void readXlsx(ResourceDefinition def, Map<String, InputStream> caches){
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


                /**第一行是列名，所以不读*/
                int firstRowIndex = sheet.getFirstRowNum() + 2;
                int lastRowIndex = sheet.getLastRowNum();
                Map<Object,Object> map = new HashMap<>();
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
                            }
                        }
                        Object index = getIndex(def.getClz(), resourceData);
                        Object parse = parse(def.getClz(), resourceData);
                        map.put(index,parse);

                    }
                }
                StorageManager.putStorage(def, map);
            } else {
                logger.error("找不到[{}]资源文件",location);
                return ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getIndex(Class<?> clz, List<String> resourceData) {
        try {
            Field id = clz.getDeclaredField("id");
            TypeDescriptor typeDescriptor = new TypeDescriptor(id);
            Object value = conversionService.convert(resourceData.get(0), sourceType, typeDescriptor);
        } catch (NoSuchFieldException e) {
            logger.error("资源[{}]没有字段名为id的属性",clz.getSimpleName());
            e.printStackTrace();

        }
    }

    private Object parse(Class<?> clz, List<String> resourceData) {
        try {
            int length = clz.getDeclaredFields().length;
            if (resourceData.size() != length) {
                logger.error("静态资源表" + clz.getSimpleName() + "加载失败");
                return null;
            }
            Object instance = clz.newInstance();
            Field[] declaredFields = clz.getDeclaredFields();
            for (int i = 0; i < length; i++) {
                if(logger.isDebugEnabled()){
                    logger.debug(clz.toString()+"--resource:"+i+":"+resourceData.get(i));
                }
                if(!inject( instance,declaredFields[i], resourceData.get(i))){
                    break;
                }
            }
            return instance;
        } catch (Exception e) {
            logger.error("静态资源属性和List中的类型不匹配");
            e.printStackTrace();
            return null;
        }

    }

    private boolean inject(Object instance, Field field, String context) {
        try {
            TypeDescriptor typeDescriptor = new TypeDescriptor(field);
            Object value = conversionService.convert(context, sourceType, typeDescriptor);
            field.set(instance,value);
            return true;

        } catch (ConverterNotFoundException e){
            logger.error("静态资源[{}]属性[{}]的转换器不存在",instance.getClass().getSimpleName(), field.getName());
            e.printStackTrace();

        } catch(Exception e) {
            logger.error("属性[{}]注入失败",field);
            e.printStackTrace();

        }
        return false;
    }

}
