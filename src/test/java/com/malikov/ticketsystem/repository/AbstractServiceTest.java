package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.AbstractTest;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

/**
 * @author Yurii Malikov
 */
@Sql(scripts = {
//    "classpath:db/initTestDB.sql",
        "classpath:db/scripts/populateTestDb.sql"},
        config = @SqlConfig(encoding = "UTF-8")
)
public abstract class AbstractServiceTest extends AbstractTest{

    private static final Logger LOG = LoggerFactory.getLogger(AbstractServiceTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

}
