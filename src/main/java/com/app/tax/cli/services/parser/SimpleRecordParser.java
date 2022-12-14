package com.app.tax.cli.services.parser;

import com.app.tax.cli.domain.entities.Transaction;
import com.app.tax.cli.domain.enums.TaxType;

public class SimpleRecordParser implements RecordParser {

    @Override
    public double getRecordTaxAmount(Transaction trx, int userId, TaxType taxType) {
        if (isValid(trx, userId, taxType)) {
            return trx.getAmount() * 0.1;
        }

        return 0;
    }

    @Override
    public double getAmount(Transaction trx, int userId, TaxType taxType) {
        if (isValid(trx, userId, taxType)) {
            return trx.getAmount();
        }
        return 0;
    }

    private boolean isValid(Transaction trx, int userId, TaxType taxType) {
        return trx.getTaxType() == taxType && trx.getCustomerId() == userId;
    }
}
