package com.app.tax.cli;


import com.app.tax.cli.utils.FileHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class AppTest {

    private App app;
    private FileHelper fileHelper;

    @Before
    public void setUp() throws Exception {
        fileHelper = Mockito.mock(FileHelper.class);
        app = new App(fileHelper);
    }

    @Test
    public void testValidateArgumentsLengthShouldReturnFalseIfLengthIsLessThan3() {
        String[] args = {"App"};
        assertFalse(app.validateArguments(args));
    }

    @Test
    public void testIfFileIsNotExistsShouldReturnFalse() {
        String[] args = {"App", "Tax", "calculation.csv", "More", "Than", "3"};
        when(fileHelper.isFiletypeAllowed(any())).thenReturn(true);
        when(fileHelper.exists(any())).thenReturn(false);
        assertFalse(app.validateArguments(args));
    }

    @Test
    public void testValidateArgumentsLengthShouldReturnTrueIfLengthIs3OrMore() {
        String[] args = {"App", "Tax", "calculation.csv", "More", "Than", "3"};
        when(fileHelper.isFiletypeAllowed(any())).thenReturn(true);
        when(fileHelper.exists(any())).thenReturn(true);
        assertTrue(app.validateArguments(args));
    }

    @Test
    public void testCheckIfFileExtensionNotExistsShouldReturnFalse() {
        String[] args = {"GST", "123", "file"};
        assertFalse(app.validateArguments(args));
    }

    @Test
    public void testCheckIfFileExistsShouldReturnFalseIfNotExists() {
        String[] args = {"GST", "123", "file.csv"};
        assertFalse(app.validateArguments(args));
    }
}
