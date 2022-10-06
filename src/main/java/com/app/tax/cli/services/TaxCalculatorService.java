package com.app.tax.cli.services;

import com.app.tax.cli.domain.entities.Transaction;
import com.app.tax.cli.domain.enums.TaxType;
import com.app.tax.cli.services.parser.RecordParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class TaxCalculatorService {
    private final RecordParser parser;

    public TaxCalculatorService(RecordParser parser) {
        this.parser = parser;
    }

    public double getTotalAmount(TaxType taxType, int userId, String file) throws IOException {
        String line;
        double taxAmount = 0.0;
        double transactionAmount = 0.0;

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            Transaction trx = buildTransaction(line);
            taxAmount += this.parser.getRecordTaxAmount(trx, userId, taxType);
            transactionAmount += this.parser.getAmount(trx, userId, taxType);
        }
        return transactionAmount + taxAmount;
    }

    private Transaction buildTransaction(String line) {
        Transaction transaction = new Transaction();
        String[] record = line.split(",");
        transaction.setAmount(Double.parseDouble(record[3].trim()));
        transaction.setCustomerId(Integer.parseInt(record[0].trim()));
        transaction.setTaxType(TaxType.valueOf(record[4].trim().toUpperCase()));
        transaction.setTimestamp(LocalDateTime.parse(record[2].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        return transaction;
    }

}
