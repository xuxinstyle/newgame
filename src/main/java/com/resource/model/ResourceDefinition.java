package com.resource.model;


/**
 * @Author：xuxin
 * @Date: 2019/6/4 10:31
 */
public class ResourceDefinition {

    private static final String FILE_PATH="src/main/resources/resource/";

    /** 资源类 */
    private final Class<?> clz;

    /** 资源路径*/
    private final String location;

    public static ResourceDefinition valueOf(Object bean){
        ResourceDefinition resourceDefinition = new ResourceDefinition(bean.getClass());
        return resourceDefinition;
    }

    public ResourceDefinition(Class<?> clz) {
        this.clz = clz;
        location = FILE_PATH + clz.getSimpleName()+".xlsx";
    }

    public Class<?> getClz() {
        return clz;
    }

    public String getLocation() {
        return location;
    }
}
