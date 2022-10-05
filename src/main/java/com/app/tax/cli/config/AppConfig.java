package com.app.tax.cli.config;

public class AppConfig {
    private String[] allowedExt = {"csv", "tsv"};

    public String[] getAllowedExt() {
        return allowedExt;
    }

}
