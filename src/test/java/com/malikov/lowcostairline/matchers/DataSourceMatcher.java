package com.malikov.lowcostairline.matchers;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * @author Yurii Malikov
 */
public class DataSourceMatcher {
    public static boolean compare(DataSource first, DataSource second) {
        return first.getDriverClassName().equals(second.getDriverClassName())
                && first.getUrl().equals(second.getUrl())
                && first.getUsername().equals(second.getUsername())
                && first.getPassword().equals(second.getPassword());
    }
}
