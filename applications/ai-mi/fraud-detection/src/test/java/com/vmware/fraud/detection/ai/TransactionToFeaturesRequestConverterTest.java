package com.vmware.fraud.detection.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import showcase.financial.banking.transactions.domain.Transaction;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionToFeaturesRequestConverterTest {

    private TransactionToFeaturesRequestConverter subject;
    private Transaction transaction;


    @BeforeEach
    void setUp() {
        transaction = new Transaction(99.2,1,2,3);
        subject = new TransactionToFeaturesRequestConverter();
    }

    @Test
    void convert() {

        var out = subject.convert(transaction);

        assertThat(out).isNotNull();
        var instructions = out.getInstructions();
        assertThat(instructions).isNotNull().isNotEmpty();

        assertThat(transaction.amount()).isEqualTo(instructions.get(TransactionToFeaturesRequestConverter.AMOUNT_KEY));
        assertThat(Integer.valueOf(transaction.device()).doubleValue()).isEqualTo(instructions.get(TransactionToFeaturesRequestConverter.DEVICE_KEY));
        assertThat(Integer.valueOf(transaction.location()).doubleValue()).isEqualTo(instructions.get(TransactionToFeaturesRequestConverter.LOCATION_KEY));
        assertThat(Integer.valueOf(transaction.merchant()).doubleValue()).isEqualTo(instructions.get(TransactionToFeaturesRequestConverter.MERCHANT_KEY));
    }
}