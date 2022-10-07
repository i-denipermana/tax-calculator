package com.app.tax.cli.services;


import com.app.tax.cli.domain.enums.TaxType;
import com.app.tax.cli.services.parser.RecordParser;
import com.app.tax.cli.services.parser.SimpleRecordParser;
import com.ginsberg.junit.exit.ExpectSystemExit;
import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxCalculatorServiceTest {

    @Test
    public void testShouldReturnZeroIfNotInParameter() throws IOException {
        RecordParser parser = new SimpleRecordParser();
        TaxCalculatorService ts = new TaxCalculatorService(parser);
        String content = "123, 2021/04/customer1-12212, 2021-04-29T13:15:54, 10.00, GST \n" +
                "123, 2021/04/customer1-12212, 2021-04-30T13:15:54, 20.00, PAYROLL";
        File f = createFile(content);

        double res = ts.getTotalAmount(TaxType.GST, 123, f.getName());
        assertEquals(11, res);
    }

    @Test
    public void testShouldCalculatePayrollTax() throws IOException {
        RecordParser parser = new SimpleRecordParser();
        TaxCalculatorService ts = new TaxCalculatorService(parser);
        String content = "123, 2021/04/customer1-12212, 2021-04-29T13:15:54, 20.00, PAYROLL \n" +
                "123, 2021/04/customer1-12212, 2021-04-30T13:15:54, 20.00, GST";
        File f = createFile(content);

        double res = ts.getTotalAmount(TaxType.GST, 123, f.getName());
        assertEquals(22, res);
    }

    @Test
    public void testShouldCalculateGSTTax() throws IOException {
        RecordParser parser = new SimpleRecordParser();
        TaxCalculatorService ts = new TaxCalculatorService(parser);
        String content = "123, 2021/04/customer1-12212, 2021-04-29T13:15:54, 10.00, GST \n" +
                "123, 2021/04/customer1-12212, 2021-04-30T13:15:54, 20.00, GST";
        File f = createFile(content);

        double res = ts.getTotalAmount(TaxType.GST, 123, f.getName());
        assertEquals(33, res);
    }

    @Test
    public void testShouldCalculateGSTTaxShouldReturnZeroIfNotContainTheSameUserId() throws IOException {
        RecordParser parser = new SimpleRecordParser();
        TaxCalculatorService ts = new TaxCalculatorService(parser);
        String content = "123, 2021/04/customer1-12212, 2021-04-29T13:15:54, 10.00, GST \n" +
                "123, 2021/04/customer1-12212, 2021-04-30T13:15:54, 20.00, GST";
        File f = createFile(content);

        double res = ts.getTotalAmount(TaxType.GST, 1234, f.getName());
        assertEquals(0, res);
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    public void testGetTotalAmountShouldHaveSystemExitIfThrowException() throws IOException {
        RecordParser parser = new SimpleRecordParser();
        TaxCalculatorService ts = new TaxCalculatorService(parser);
        String content = "123, 2021/04/customer1-12212, 2021-04-29T13:15:54, 10.00, GST \n" +
                "123, 2021/04/customer1-12212, 2021-04-30T13:15:54, 20.00, GST";
        File f = createFile(content, false);
        ts.getTotalAmount(TaxType.GST, 1234, f.getName());
    }


    private File createFile(String content, boolean readable) throws IOException {
        File tempFile = File.createTempFile("test", ".csv", new File("."));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write(content);
        writer.close();
        tempFile.setReadable(readable);
        tempFile.deleteOnExit();
        return tempFile;
    }

    private File createFile(String content) throws IOException {
        return createFile(content, true);
    }


}