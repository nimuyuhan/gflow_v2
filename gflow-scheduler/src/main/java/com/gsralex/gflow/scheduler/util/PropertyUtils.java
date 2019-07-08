package com.gsralex.gflow.scheduler.util;

import java.util.Properties;

public class PropertyUtils {

    public static int getInt(Properties properties,String key,int defaultValue){
        return (int) properties.getOrDefault(key,defaultValue);
    }
}