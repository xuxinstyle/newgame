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

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/9 23:00
 */
public class ReadXlsx {
    private static final Logger logger = LoggerFactory.getLogger(ReadXlsx.class);
    public static void readXlsx(ResourceDefinition def, Map<String, InputStream> caches){
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
                /* 读取sheet 0*/
                /**第一行是列名，所以不读*/
                int firstRowIndex = sheet.getFirstRowNum() + 2;
                int lastRowIndex = sheet.getLastRowNum();
                Map<String,Object> map = new HashMap<>();
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

                        Object parse = parse(def.getClz(), resourceData);
                        map.put(resourceData.get(0),parse);

                    }
                }
                StorageManager.putStorage(def, map);
            } else {
                logger.error("找不到指定的文件");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Object parse(Class<?> clz, List<String> resourceData) {
        try {
            int length = clz.getDeclaredFields().length;
            if (resourceData.size() != length) {
                logger.error("静态资源表" + clz.getSimpleName() + "加载失败");
                return null;
            }
            Object object = clz.newInstance();
            Field[] declaredFields = clz.getDeclaredFields();
            for (int i = 0; i < length; i++) {
                if(logger.isDebugEnabled()){
                    logger.debug(clz.toString()+"--resource:"+i+":"+resourceData.get(i));
                }
                declaredFields[i].set(object, resourceData.get(i));
                /*if(declaredFields[i].getType()==Integer.class){
                    declaredFields[i].set(object, Integer.parseInt(resourceData.get(i)));
                }
                if(declaredFields[i].getType().isArray()){

                }*/

            }
            return object;
        } catch (Exception e) {
            logger.error("静态资源属性和List中的类型不匹配");
            e.printStackTrace();
            return null;
        }

    }
}
