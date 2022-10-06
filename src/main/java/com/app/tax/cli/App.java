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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Command(name = "calc")
public class App implements Runnable {

    @Parameters(paramLabel = "<taxType> <userId> <filename>")
    private String[] parameters;
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


    public boolean validateArguments(String[] args) {
        if (args.length < 3) {
            return false;
        }
        if (isArgIndexValid(args, 2)) {
            String filename = args[2];
            if (!fileHelper.isFiletypeAllowed(filename)) {
                System.out.println("Filetype is not allowed");
                return false;
            }
            Path path = Paths.get(filename);
            return fileHelper.exists(path);
        }
        return true;
    }

    private boolean isArgIndexValid(String[] args, int index) {
        return index >= 0 && index < args.length;
    }

    @Override
    public void run() {
        if (parameters == null) {
            showHelp();
            System.exit(1);
        }
        if (validateArguments(parameters)) {
            String taxType = parameters[0];
            String userId = parameters[1];
            String file = parameters[2];
            try {
                TaxCalculatorService ts = new TaxCalculatorService(new SimpleRecordParser());

                double result = ts.getTotalAmount(TaxType.valueOf(taxType.toUpperCase()), Integer.parseInt(userId), file);
                System.out.printf("For tax %s, customer %s has declared $%s \n", parameters[0], parameters[1], result);
            } catch (IOException e) {
                System.out.printf(
                        "Error occurred while try to parse and calculate the tax for tax %s, customer %s \n",
                        taxType, userId);
                System.exit(1);
            }
        }

    }

    private void showHelp() {
        CommandLine.usage(this, System.out);
    }
}
