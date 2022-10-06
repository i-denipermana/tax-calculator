package com.app.tax.cli.services.parser;

import com.app.tax.cli.domain.entities.Transaction;
import com.app.tax.cli.domain.enums.TaxType;

public class SimpleRecordParser implements RecordParser {

    @Override
    public double getRecordTaxAmount(Transaction trx, int userId, TaxType taxType) {
        if (trx.getTaxType() == taxType && trx.getCustomerId() == userId) {
            return trx.getAmount() * 0.1;
        }
        return 0;
    }

    @Override
    public double getAmount(Transaction trx, int userId, TaxType taxType) {
        if(trx.getTaxType() == taxType && trx.getCustomerId() == userId) {
            return trx.getAmount();
        }
        return 0;
    }
}
