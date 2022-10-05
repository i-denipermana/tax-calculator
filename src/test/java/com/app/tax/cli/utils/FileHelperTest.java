package com.app.tax.cli.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelperTest extends TestCase {

    public void testIfFileTypeIsNotAllowedShouldReturnFalse() {
        String[] allowedExt = {"csv"};
        FileHelper fh = new FileHelper(allowedExt);
        assertFalse(fh.isFiletypeAllowed("abc.zip"));
    }

    public void testIfFilenameDontHaveAnyExtensionShouldReturnFalse() {
        FileHelper fh = new FileHelper();
        assertFalse(fh.isFiletypeAllowed("abc"));
    }

    public void testIfFileTypeIsAllowedShouldReturnTrue() {
        FileHelper fh = new FileHelper();
        assertTrue(fh.isFiletypeAllowed("abc.csv"));
    }

    public void testIfFileExistsShouldReturnTrue() throws IOException {
        File file = File.createTempFile("test", ".csv", new File("."));
        FileHelper fh = new FileHelper();
        Path path = Paths.get(file.getPath());
        file.deleteOnExit();
        assertTrue(fh.exists(path));
    }

    public void testIfFileNotExistsShouldReturnFalse() {
        FileHelper fh = new FileHelper();
        assertFalse(fh.exists(Path.of("test.csv")));
    }
}