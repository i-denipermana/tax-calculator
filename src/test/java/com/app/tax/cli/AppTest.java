package com.app.tax.cli;

import com.app.tax.cli.utils.FileHelper;
import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class AppTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private App app;
    private FileHelper fileHelper;


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
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

    @Test
    public void whenEverythingOkayShouldReturnTrue() {
        String args = "filename.csv";
        when(fileHelper.isFiletypeAllowed(any())).thenReturn(true);
        when(fileHelper.exists(any())).thenReturn(true);
        assertTrue(app.validateArguments(args));
    }

    @Test
    public void testCheckIfFileExtensionNotExistsShouldReturnFalse() {
        String args = "filename";
        assertFalse(app.validateArguments(args));
    }

    @Test
    public void testCheckIfFileExistsShouldReturnFalseIfNotExists() {
        assertFalse(app.validateArguments("file.csv"));
    }

    @Test
    @ExpectSystemExitWithStatus(0)
    public void testEverythingShouldWorkingAsExpected() throws IOException {
        File file = File.createTempFile("test", ".csv", new File("."));
        file.deleteOnExit();
        App.main(new String[]{"GST", "123", file.getName()});
        String captured = outputStreamCaptor.toString();
        assertTrue(captured.contains("has declared"));
    }

    @Test
    @ExpectSystemExitWithStatus(0)
    public void shouldContainFileNotExistMessage() throws IOException {
            App.main(new String[]{"GST", "123", "sbc.csv"});
            String captured = outputStreamCaptor.toString();

            assertTrue(captured.contains("not exists"));
    }
}
