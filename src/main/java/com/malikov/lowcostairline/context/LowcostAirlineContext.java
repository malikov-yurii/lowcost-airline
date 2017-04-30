package com.malikov.lowcostairline.context;

/**
 * @author Yurii Malikov
 */
public class LowcostAirlineContext {

    private static IApplicationContext contextSingleton;

    public LowcostAirlineContext() {
    }

    public static void initialize(IApplicationContext applicationContext) {
        contextSingleton = applicationContext;
        contextSingleton.initialize();
    }

    public static IApplicationContext getSingleton(){
        return contextSingleton;
    }

}
