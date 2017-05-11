package com.malikov.ticketsystem.context;

/**
 * @author Yurii Malikov
 */
public interface IApplicationContext {
    Object getBean(String beanName);
    void initialize();
}
