package com.vmware.fraud.detection.ai.tribuo;

import com.vmware.fraud.detection.ai.PredictionScore;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.model.ModelResponse;
import org.springframework.ai.model.ResponseMetadata;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class PredictionResponse implements ModelResponse<PredictionScore> {

    private final PredictionScore  predictionScore;

    @Override
    public PredictionScore getResult() {
        return predictionScore;
    }

    @Override
    public List<PredictionScore> getResults() {
        return Collections.singletonList(predictionScore);
    }

    @Override
    public ResponseMetadata getMetadata() {
        return null;
    }
}
