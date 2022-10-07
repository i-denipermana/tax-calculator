package com.app.tax.cli;

import com.app.tax.cli.utils.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class AppTest {

    private App app;
    private FileHelper fileHelper;


    @BeforeEach
    void setUp() {
        fileHelper = Mockito.mock(FileHelper.class);
        app = new App(fileHelper);
    }


    @Test
    public void testValidateArgumentsLengthShouldReturnFalseIfLengthIsLessThan3() {
        String args = "FILENAME";
        assertFalse(app.validateArguments(args));
    }

    @Test
    public void testIfFileIsNotExistsShouldReturnFalse() {
        when(fileHelper.isFiletypeAllowed(any())).thenReturn(true);
        when(fileHelper.exists(any())).thenReturn(false);
        assertFalse(app.validateArguments("calculation.csv"));
    }

//    @Test
//    public void testValidateArgumentsLengthShouldReturnTrueIfLengthIs3OrMore() {
//        String[] args = {"App", "Tax", "calculation.csv", "More", "Than", "3"};
//        when(fileHelper.isFiletypeAllowed(any())).thenReturn(true);
//        when(fileHelper.exists(any())).thenReturn(true);
//        assertTrue(app.validateArguments(args));
//    }

    @Test
    public void testCheckIfFileExtensionNotExistsShouldReturnFalse() {
        String args = "filename";
        assertFalse(app.validateArguments(args));
    }

    @Test
    public void testCheckIfFileExistsShouldReturnFalseIfNotExists() {
        assertFalse(app.validateArguments("file.csv"));
    }
}
