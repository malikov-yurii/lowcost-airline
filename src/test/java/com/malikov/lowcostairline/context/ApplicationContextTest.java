package com.malikov.lowcostairline.context;

import com.malikov.lowcostairline.matchers.DataSourceMatcher;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yurii Malikov
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContextInstantiation() throws Exception {
        LowcostAirlineContext.initialize(new XMLApplicationContext("context/mockApplicationContext.xml"));
        IApplicationContext context = LowcostAirlineContext.getSingleton();
        PoolProperties poolProperties = (PoolProperties) context.getBean("poolProperties");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        Assert.assertTrue(DataSourceMatcher.compare(dataSource, new DataSource(poolProperties)));
    }

}