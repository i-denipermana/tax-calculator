package com.app.tax.cli.config;

public class AppConfig {
    private final String[] allowedExt;

    public AppConfig(String[] allowedExt) {
        this.allowedExt = allowedExt;
    }

    public AppConfig() {
        this.allowedExt = new String[]{"csv"};
    }

    public String[] getAllowedExt() {
        return allowedExt;
    }

}
