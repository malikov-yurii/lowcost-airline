package com.malikov.lowcostairline.context;

/**
 * @author Yurii Malikov
 */
public interface IApplicationContext {
    Object getBean(String beanName);
    void initialize();
}
