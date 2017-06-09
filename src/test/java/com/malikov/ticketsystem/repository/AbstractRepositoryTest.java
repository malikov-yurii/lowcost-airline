package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.AbstractTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

/**
 * @author Yurii Malikov
 */
@Sql(scripts = {
        "classpath:db/scripts/populateTestDb.sql"},
        config = @SqlConfig(encoding = "UTF-8")
)
public abstract class AbstractRepositoryTest extends AbstractTest{
}
