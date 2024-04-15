package com.vmware.fraud.detection.ai;

import nyla.solutions.core.patterns.conversion.Converter;
import showcase.financial.banking.transactions.domain.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.valueOf;

public class TransactionToFeaturesRequestConverter implements Converter<Transaction,FeaturesRequest> {

    public static final String AMOUNT_KEY = "amount";
    public static final String DEVICE_KEY = "device";
    public static final String LOCATION_KEY = "location";
    public static final String MERCHANT_KEY = "merchant";

    @Override
    public FeaturesRequest convert(Transaction transaction) {

        Map<String,Double> instructions = Map.of(
                AMOUNT_KEY,transaction.amount(),
                DEVICE_KEY, valueOf(transaction.device()).doubleValue(),
                LOCATION_KEY, valueOf(transaction.location()).doubleValue(),
                MERCHANT_KEY,valueOf(transaction.merchant()).doubleValue());

        return new FeaturesRequest(instructions);
    }
}
