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
    /**<class对象，对应类的字段列表>*/
    private static Map<Class<?> ,List<FieldInfo>> fieldInfoMap = new ConcurrentHashMap<>();

    public static Map<Class<?>, List<FieldInfo>> getFieldInfoMap() {
        return fieldInfoMap;
    }

    public static void setFieldInfoMap(Map<Class<?>, List<FieldInfo>> fieldInfoMap) {
        ReadHolder.fieldInfoMap = fieldInfoMap;
    }

}
