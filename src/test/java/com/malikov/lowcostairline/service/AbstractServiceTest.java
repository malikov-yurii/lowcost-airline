package com.malikov.lowcostairline.service;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Yurii Malikov
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {
//    "classpath:db/initTestDB.sql",
        "classpath:db/scripts/populateTestDb.sql"},
        config = @SqlConfig(encoding = "UTF-8"))
//@ActiveProfiles({Profiles.ACTIVE_DB})
public abstract class AbstractServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractServiceTest.class);

}
