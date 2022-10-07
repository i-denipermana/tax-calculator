package com.app.tax.cli.config;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AppConfigTest {

    @Test
    public void testGetAllowedExtShouldReturnCsv() {
        String[] expect = {"csv"};
        AppConfig config = new AppConfig(expect);

        assertEquals(expect[0], config.getAllowedExt()[0]);
    }

    @Test
    public void testSetAllowedExtShouldReturnCsv() {

        AppConfig config = new AppConfig();
        String[] expect = {"csv"};
        assertEquals(expect[0], config.getAllowedExt()[0]);
    }
}