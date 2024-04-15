package com.vmware.fraud.detection.ai;

import nyla.solutions.core.patterns.conversion.Converter;
import showcase.financial.banking.transactions.domain.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;

public class TransactionToFeaturesRequestConverter implements Converter<Transaction,FeaturesRequest> {
    public static final int AMOUNT_INDEX = 0;
    public static final int DEVICE_INDEX = 1;
    public static final int LOCATION_INDEX = 2;
    public static final int MERCHANT_INDEX = 3;

    @Override
    public FeaturesRequest convert(Transaction transaction) {

        List<Double> instructions = List.of(transaction.amount(),
                valueOf(transaction.device()).doubleValue(),
                valueOf(transaction.location()).doubleValue(),
                valueOf(transaction.merchant()).doubleValue());

        return new FeaturesRequest(instructions);
    }
}
