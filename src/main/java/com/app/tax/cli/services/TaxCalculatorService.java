package com.app.tax.cli.services;

import com.app.tax.cli.domain.entities.Transaction;
import com.app.tax.cli.domain.enums.TaxType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TaxCalculatorService {
    public TaxCalculatorService() {
    }

    public List<Transaction> readFromFile(TaxType taxType, String userId, String file) throws IOException {
        String line;
        List<Transaction> transactions = new ArrayList<>();

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Pattern pattern = Pattern.compile("(?=.*"+ taxType +")(?=.*"+ userId +")");
        while ((line = bufferedReader.readLine()) != null) {
            if(pattern.matcher(line).find()) {
                Transaction trx = new Transaction();
                String[] record = line.split(",");
                trx.setAmount(Double.parseDouble(record[3].trim()));
                trx.setCustomerId(Integer.parseInt(record[0].trim()));
                trx.setTaxType(TaxType.valueOf(record[4].trim().toUpperCase()));
                trx.setTimestamp(LocalDateTime.parse(record[2].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                transactions.add(trx);
            }
        }
        return transactions;
    }

    public double calculate(List<Transaction> transactions) {
        return transactions.stream().mapToDouble(t -> t.getAmount() + (t.getAmount() * 10/100)).sum();
    }

}
