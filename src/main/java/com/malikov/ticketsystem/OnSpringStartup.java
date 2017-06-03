package com.malikov.ticketsystem;

import org.springframework.stereotype.Component;

/**
 * @author Yurii Malikov
 */
@Component
public class OnSpringStartup {

    // TODO: 5/15/2017 is it ok or are there better way dto set default server timezone? Is it best practice dto set default timezone dto UtC on sql-server and applicationserver?????
    //@EventListener(ContextRefreshedEvent.class)
    //public void contextRefreshedEvent() {
    //    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    //}

}
