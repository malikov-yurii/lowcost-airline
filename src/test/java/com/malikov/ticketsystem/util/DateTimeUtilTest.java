package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.AbstractTest;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;

/**
 * @author Yurii Malikov
 */
public class DateTimeUtilTest extends AbstractTest{

    @Test
    public void testUtcToZoneIdTest(){
        LocalDateTime expected = DateTimeUtil.parseToLocalDateTime("2017-04-23 16:00");
        LocalDateTime actual = DateTimeUtil.utcToZoneId(DateTimeUtil.parseToLocalDateTime("2017-04-23 13:00"),
                ZoneId.of("Europe/Kiev"));

        assertEquals(expected, actual);
    }

    @Test
    public void testZoneIdToUtcTest(){
        LocalDateTime expected = DateTimeUtil.parseToLocalDateTime("2017-04-23 16:00");
        LocalDateTime actual = DateTimeUtil.utcToZoneId(DateTimeUtil.parseToLocalDateTime("2017-04-23 13:00"),
                        ZoneId.of("Europe/Kiev"));

        assertEquals(expected, actual);
    }
}
