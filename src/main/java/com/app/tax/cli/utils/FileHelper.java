package com.app.tax.cli.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public class FileHelper {
    private final String[] allowedExt;

    public FileHelper(String[] allowedExt) {
        this.allowedExt = allowedExt;
    }

    public FileHelper() {
        this.allowedExt = new String[]{"csv", "tsv"};
    }

    public boolean isFiletypeAllowed(String filename) {
        Optional<String> fileExtension = Optional.of(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.toLowerCase().substring(filename.indexOf(".") + 1));
        if (fileExtension.isPresent()) {
            String ext = fileExtension.get();
            return Arrays.stream(allowedExt).anyMatch(e -> e.equalsIgnoreCase(ext));
        }
        System.out.println("Invalid file or file extension");
        return false;
    }

    public boolean exists(Path filepath) {
        if (!Files.exists(filepath) || Files.isDirectory(filepath)) {
            System.out.printf("The file %s not exists", filepath.getFileName());
            return false;
        }
        return true;
    }
}
