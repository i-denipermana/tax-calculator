package com.app.tax.cli.services.parser;


import com.app.tax.cli.domain.entities.Transaction;
import com.app.tax.cli.domain.enums.TaxType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRecordParserTest {

    @Test
    public void shouldReturnTrueOne() {
        SimpleRecordParser parser = new SimpleRecordParser();
        assertEquals(1, parser.getRecordTaxAmount(transaction(TaxType.GST, 10, 123), 123, TaxType.GST));
    }

    @Test
    public void shouldReturnTrueZero() {
        SimpleRecordParser parser = new SimpleRecordParser();
        assertEquals(0, parser.getRecordTaxAmount(transaction(TaxType.GST, 1000, 1), 123, TaxType.PAYROLL));
    }

    @Test
    public void shouldReturnTrueOneHundred() {
        SimpleRecordParser parser = new SimpleRecordParser();
        assertEquals(100, parser.getRecordTaxAmount(transaction(TaxType.PAYROLL, 1000, 123), 123, TaxType.PAYROLL));
    }

    private Transaction transaction(TaxType type,double amount, int customerId) {
        Transaction trx = new Transaction();
        trx.setTaxType(type);
        trx.setAmount(amount);
        trx.setCustomerId(customerId);
        trx.setInvoiceNumber("Invoice-123");
        return trx;
    }
}