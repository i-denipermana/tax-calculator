package com.app.tax.cli.utils;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileHelperTest {

    @Test
    public void testIfFileTypeIsNotAllowedShouldReturnFalse() {
        String[] allowedExt = {"csv"};
        FileHelper fh = new FileHelper(allowedExt);
        assertFalse(fh.isFiletypeAllowed("abc.zip"));
    }

    @Test
    public void testIfFilenameDontHaveAnyExtensionShouldReturnFalse() {
        FileHelper fh = new FileHelper();
        assertFalse(fh.isFiletypeAllowed("abc"));
    }

    @Test
    public void testIfFileTypeIsAllowedShouldReturnTrue() {
        FileHelper fh = new FileHelper();
        assertTrue(fh.isFiletypeAllowed("abc.csv"));
    }

    @Test
    public void testIfFileExistsShouldReturnTrue() throws IOException {
        File file = File.createTempFile("test", ".csv", new File("."));
        FileHelper fh = new FileHelper();
        Path path = Paths.get(file.getPath());
        file.deleteOnExit();
        assertTrue(fh.exists(path));
    }

    @Test
    public void testIfFileNotExistsShouldReturnFalse() {
        FileHelper fh = new FileHelper();
        assertFalse(fh.exists(Path.of("test.csv")));
    }
}