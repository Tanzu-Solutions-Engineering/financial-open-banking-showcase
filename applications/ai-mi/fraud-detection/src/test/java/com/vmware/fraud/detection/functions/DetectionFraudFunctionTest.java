package com.vmware.fraud.detection.functions;

import com.vmware.fraud.detection.ai.FeaturesRequest;
import com.vmware.fraud.detection.ai.PredictionScore;
import com.vmware.fraud.detection.ai.tribuo.PredictionResponse;
import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.model.ModelClient;
import showcase.financial.banking.transactions.domain.Transaction;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetectionFraudFunctionTest {

    private DetectionFraudFunction subject;

    @Mock
    private ModelClient<FeaturesRequest, PredictionResponse> modelClient;

    @Mock
    private Converter<Transaction,FeaturesRequest> converter;

    @Mock
    private FeaturesRequest featureRequest;

    private Transaction transaction = JavaBeanGeneratorCreator.of(Transaction.class).create();
    private Double score = 232.232;
    private PredictionScore expectedScore = new PredictionScore(score);
    private List<Double> instructions = List.of(transaction.amount(),
            Integer.valueOf(transaction.device()).doubleValue(),
            Integer.valueOf(transaction.location()).doubleValue(),
            Integer.valueOf(transaction.merchant()).doubleValue());

    @BeforeEach
    void setUp() {
        subject = new DetectionFraudFunction(modelClient,converter);
    }

    @Test
    void isFraud() {
        var expectedPrediction = new PredictionResponse(expectedScore);
        when(modelClient.call(any())).thenReturn(expectedPrediction);
        when(converter.convert(any())).thenReturn(featureRequest);

        var actual = subject.apply(transaction);
        assertThat(actual).isNotNull();
        assertThat(actual.getOutput()).isEqualTo(expectedScore.getOutput());
    }
}