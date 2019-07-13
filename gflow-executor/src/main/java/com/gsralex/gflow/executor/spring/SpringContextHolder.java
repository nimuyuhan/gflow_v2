package com.gsralex.gflow.executor.spring;

import org.springframework.context.ApplicationContext;

/**
 * @author gsralex
 * @version 2019-07-13
 */
public class SpringContextHolder {

    private ApplicationContext applicationContext;


    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(Class<T> clazz) {
        return this.applicationContext.getBean(clazz);
    }

    public boolean isSpring() {
        return this.applicationContext != null ? true : false;
    }
}
