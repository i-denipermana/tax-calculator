package com.app.tax.cli;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.app.tax.cli.utils.FileHelper;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    private App app;
    private FileHelper fileHelper;

    @Before
    public void setUp() throws Exception {
        fileHelper = Mockito.mock(FileHelper.class);
        app = new App(fileHelper);
    }

    public void testValidateArgumentsLengthShouldReturnFalseIfLengthIsLessThan3() {
        String[] args = {"App"};
        assertFalse(app.validateArguments(args));
    }

    public void testIfFileIsNotExistsShouldReturnFalse() {
        String[] args = {"App", "Tax", "calculation.csv", "More", "Than", "3"};
        when(fileHelper.isFiletypeAllowed(any())).thenReturn(true);
        when(fileHelper.exists(any())).thenReturn(false);
        assertFalse(app.validateArguments(args));
    }

    public void testValidateArgumentsLengthShouldReturnTrueIfLengthIs3OrMore() {
        String[] args = {"App", "Tax", "calculation.csv", "More", "Than", "3"};
        when(fileHelper.isFiletypeAllowed(any())).thenReturn(true);
        when(fileHelper.exists(any())).thenReturn(true);
        assertTrue(app.validateArguments(args));
    }

    public void testCheckIfFileExtensionNotExistsShouldReturnFalse() {
        String[] args = {"GST", "123", "file"};
        assertFalse(app.validateArguments(args));
    }

    public void testCheckIfFileExistsShouldReturnFalseIfNotExists() {
        String[] args = {"GST", "123", "file.csv"};
        assertFalse(app.validateArguments(args));
    }
}
