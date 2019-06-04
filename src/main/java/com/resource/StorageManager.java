package com.resource;

import com.game.scence.resource.TestResource;
import com.resource.anno.Resource;
import com.resource.other.ResourceDefinition;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 10:30
 */
@Component
public class StorageManager implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(StorageManager.class);
    // 静态类容器
    private Map<Class<?>, ResourceDefinition> definitionMap = new ConcurrentHashMap<>();
    // 静态资源存储容器
    private static Map<Class<?>, Storage<?,?>> storageMap = new ConcurrentHashMap<>();
    // csv读取的缓存流容器
    private static Map<String, InputStream> caches = new ConcurrentHashMap<>();

    public static void init(){
        System.out.println("进入init");
        if(logger.isDebugEnabled()){
            logger.debug("进入init");
        }
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clz = bean.getClass();
        if(clz.isAnnotationPresent(Resource.class)){
            ResourceDefinition def = ResourceDefinition.valueOf(bean);
            registResourceDefintion(def.getClz(), def);
            registResourceCacah(def.getLocation());
            readXlsx(def,caches);
        }
        logger.info(definitionMap.toString());
        logger.info(caches.toString());
        return bean;
    }

    private void addStorageMap() {

    }

    public void readXlsx(ResourceDefinition def, Map<String, InputStream> caches){
        try {
            String location = def.getLocation();
            InputStream inputStream = caches.get(location);
            File excel = new File(location);
            if (excel.isFile() && excel.exists()) { //判断文件是否存在
                String[] split = excel.getName().split("\\.");
                // .是特殊字符，需要转义！！！！！
                Workbook wb;
                // 根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    //FileInputStream fis = new FileInputStream(excel);
                    // 文件流对象
                    wb = new HSSFWorkbook(inputStream);
                } else if ("xlsx".equals(split[1])) {
                    //FileInputStream fis = new FileInputStream(excel);
                    wb = new XSSFWorkbook(inputStream);
                } else {
                    logger.error("文件类型错误!");
                    return;
                } //开始解析
                Sheet sheet = wb.getSheetAt(0);
                //读取sheet 0
                int firstRowIndex = sheet.getFirstRowNum() + 1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();

                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                    //遍历行
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
                        putStorage(def, resourceData);
                    }
                }
            } else {
                logger.error("找不到指定的文件");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Storage<?, ?> getStorage(Class clz){
        return storageMap.get(clz);
    }
    // 根据主键和clz获取Resource对象
    public <T> T getResource(Object key, Class<T> clz){
        Storage<?, ?> storage = getStorage(clz);
        return (T)storage.getData().getValues().get(key);
    }
    private void putStorage(ResourceDefinition def, List<String> resourceData) {
        Class<?> clz = def.getClz();
        // <主键， Object>
        Map<String,Object> map = new HashMap<>();
        Object parse = parse(clz, resourceData);
        map.put(resourceData.get(0), parse);
        StorageData<String,Object> data = new StorageData<>();
        data.setValues(map);
        Storage<String,Object> storage = new Storage<>();
        storage.setData(data);
        storageMap.put(def.getClz(), storage);
    }

    private Object parse(Class<?> clz, List<String> resourceData) {
        try {
            int length = clz.getDeclaredFields().length;
            if (resourceData.size() != length) {
                logger.error("静态资源表" + TestResource.class.getName() + "加载失败");
                return null;
            }
            TestResource testResource = new TestResource();
            Field[] declaredFields = TestResource.class.getDeclaredFields();
            for (int i = 0; i < length; i++) {
                if(logger.isDebugEnabled()){
                    logger.debug(testResource.toString()+"resource:"+i+":"+resourceData.get(i));
                }
                declaredFields[i].set(testResource, resourceData.get(i));
            }
            return testResource;
        } catch (Exception e) {
            logger.error("静态资源属性和List中的类型不匹配");
            e.printStackTrace();
            return null;
        }

    }

    private void registResourceCacah(String loaction) {
        try {
            File file = new File(loaction);
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);
            caches.put(loaction,inputStream);
        }catch (IOException e){
            logger.error("注册资源缓存失败");
            e.printStackTrace();
        }
    }
    private void registResourceDefintion(Class<?> clz, ResourceDefinition def) {
        if(!definitionMap.containsKey(clz)) {
            definitionMap.put(clz, def);
        }
    }

    public Map<Class<?>, ResourceDefinition> getDefinitionMap() {
        return definitionMap;
    }

    public void setDefinitionMap(Map<Class<?>, ResourceDefinition> definitionMap) {
        this.definitionMap = definitionMap;
    }

    public static Map<Class<?>, Storage<?, ?>> getStorageMap() {
        return storageMap;
    }
    public static Object getResource( Class<?> clz, String id){
        Object object = storageMap.get(clz).getData().getValues().get(id);
        if(object==null||!object.getClass().equals(clz)){
            logger.error("获取资源对象失败！"+"object的class："+object.getClass()+"clz的class:"+clz);
            return null;
        }
        return object;
    }

    public static void setStorageMap(Map<Class<?>, Storage<?, ?>> storageMap) {
        StorageManager.storageMap = storageMap;
    }

    public static Map<String, InputStream> getCaches() {
        return caches;
    }

    public static void setCaches(Map<String, InputStream> caches) {
        StorageManager.caches = caches;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
