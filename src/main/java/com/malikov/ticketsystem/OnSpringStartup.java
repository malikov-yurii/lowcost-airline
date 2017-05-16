package com.malikov.ticketsystem;

import org.springframework.stereotype.Component;

/**
 * @author Yurii Malikov
 */
@Component
public class OnSpringStartup {

    // TODO: 5/15/2017 is it ok or are there better way to set default server timezone? Is it best practice to set default timezone to UtC on sql-server and applicationserver?????
    //@EventListener(ContextRefreshedEvent.class)
    //public void contextRefreshedEvent() {
    //    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    //}

}
