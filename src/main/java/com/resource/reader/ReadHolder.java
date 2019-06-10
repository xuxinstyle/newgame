package com.resource.reader;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/10 11:51
 */
public class ReadHolder {

    private static final Logger logger = LoggerFactory.getLogger(ReadHolder.class);

    private static Map<Class<?> ,List<FieldInfo>> fieldInfoMap = new ConcurrentHashMap<>();

    public void read(ResourceDefinition def, Map<String, InputStream> caches){
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
                int firstIndex = sheet.getFirstRowNum();
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
                            holderName.add(cell.toString());
                        }
                    }
                    List<FieldInfo> fieldList = new ArrayList<>();
                    for(int i = 0;i<holderName.size();i++) {
                        Field declaredField = def.getClz().getDeclaredField(holderName.get(i));
                        if(declaredField==null){
                            if(logger.isDebugEnabled()){
                                logger.debug("类{}中没有字段{}",def.getClz().getSimpleName(),holderName.get(i));
                            }
                            continue;
                        }
                        fieldList.add(new FieldInfo(i,declaredField));
                    }
                    fieldInfoMap.put(def.getClz(), fieldList);
                }

            }
        }catch (Exception e){
            logger.error("读取资源[{}]的表头失败",def.getLocation());
        }
    }
}
