package com.app.tax.cli;

import com.app.tax.cli.config.AppConfig;
import com.app.tax.cli.domain.entities.Transaction;
import com.app.tax.cli.domain.enums.TaxType;
import com.app.tax.cli.services.TaxCalculatorService;
import com.app.tax.cli.services.parser.SimpleRecordParser;
import com.app.tax.cli.utils.FileHelper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Command(name = "tcalc", mixinStandardHelpOptions = true, parameterListHeading = "")
public class App implements Runnable {

    @Parameters(index = "0", description = "Tax Type (GST, PAYROLL, COMPANY_TAX, LAND_TAX, CAPITOL_GAIN)")
    private String taxType;

    @Parameters(index = "1", description = "Your customer id")
    private int customerId;

    @Parameters(index = "2", description = "Path to filename")
    private String file;

    private FileHelper fileHelper;

    public App(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        App app = new App(new FileHelper(appConfig.getAllowedExt()));
        int exitCode = new CommandLine(app).execute(args);
        System.exit(exitCode);
    }


    public boolean validateArguments(String filename) {
        if (filename != null) {
            if (!fileHelper.isFiletypeAllowed(filename)) {
                System.out.println("Filetype is not allowed");
                return false;
            }
            Path path = Paths.get(filename);
            return fileHelper.exists(path);
        }
        return true;
    }

    @Override
    public void run() {
        if (validateArguments(file)) {

            TaxCalculatorService ts = new TaxCalculatorService(new SimpleRecordParser());
            double result = ts.getTotalAmount(TaxType.valueOf(taxType.toUpperCase()), customerId, file);
            System.out.printf("For tax %s, customer %s has declared $%s \n", taxType, customerId, result);

        }

    }
}
