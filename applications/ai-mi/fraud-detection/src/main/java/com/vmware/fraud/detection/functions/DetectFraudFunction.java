package com.vmware.fraud.detection.functions;

import com.vmware.fraud.detection.ai.FeaturesRequest;
import com.vmware.fraud.detection.ai.PredictionScore;
import com.vmware.fraud.detection.ai.tribuo.PredictionResponse;
import lombok.RequiredArgsConstructor;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.model.ModelClient;
import org.springframework.stereotype.Component;
import showcase.financial.banking.transactions.domain.Transaction;

import java.util.function.Function;

/**
 * @author gregory green
 */
@RequiredArgsConstructor
@Component
public class DetectFraudFunction implements Function<Transaction,PredictionScore> {

    private final ModelClient<FeaturesRequest, PredictionResponse> modelClient;

    private final Converter<Transaction,FeaturesRequest> converter;

    @Override
    public PredictionScore apply(Transaction transaction) {
        return modelClient.call(converter.convert(transaction))
                .getResult();
    }

}
