package com.app.tax.cli;

import com.app.tax.cli.utils.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Permission;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class AppTest {
    private SecurityManager m;
    private TestSecurityManager sm;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    private App app;
    private FileHelper fileHelper;


    @BeforeEach
    void setUp() {
        m = System.getSecurityManager();
        sm = new TestSecurityManager ();
        System.setSecurityManager(sm);
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
    public void testEverythingShouldWorkingAsExpected() throws IOException {
        File file = File.createTempFile("test", ".csv", new File("."));
        file.deleteOnExit();
        SecurityException ex = assertThrows(SecurityException.class, () -> App.main(new String[]{"GST", "123", file.getName()}));
        String captured = outputStreamCaptor.toString();
        assertEquals("0", ex.getMessage());
        assertTrue(captured.contains("has declared"));
    }

    @Test
    public void shouldContainFileNotExistMessage() throws IOException {
        SecurityException ex = assertThrows(SecurityException.class, () -> App.main(new String[]{"GST", "123", "sbc.csv"}));
        String captured = outputStreamCaptor.toString();
        assertEquals("0", ex.getMessage());
        assertTrue(captured.contains("not exists"));
    }



    class TestSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission permission) {
            if ("exitVM".equals(permission.getName()))
            {
                throw new SecurityException("System.exit attempted and blocked.");
            }
        }
        @Override
        public void checkExit(int status) {
            throw new SecurityException(Integer.toString(status));
        }
    }

}
