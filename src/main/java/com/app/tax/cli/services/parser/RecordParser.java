package com.app.tax.cli.services.parser;

import com.app.tax.cli.domain.entities.Transaction;
import com.app.tax.cli.domain.enums.TaxType;

public interface RecordParser {
    double getRecordTaxAmount(Transaction trx, int userId, TaxType taxType);
    double getAmount(Transaction trx, int userId, TaxType taxType);
}
