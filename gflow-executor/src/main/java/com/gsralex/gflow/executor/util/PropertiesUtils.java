package com.gsralex.gflow.executor.util;

import java.util.Properties;

/**
 * @author gsralex
 * @version 2019-07-13
 */
public class PropertiesUtils {

    public static int getInt(Properties properties, String key, int defaultValue) {
        return (int) properties.getOrDefault(key, defaultValue);
    }

    public static String getString(Properties properties, String key) {
        return properties.getProperty(key);
    }
}
