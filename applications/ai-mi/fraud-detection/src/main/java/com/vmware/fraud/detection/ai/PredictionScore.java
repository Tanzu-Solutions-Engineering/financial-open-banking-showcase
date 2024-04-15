package com.vmware.fraud.detection.ai;

import org.springframework.ai.model.ModelResult;
import org.springframework.ai.model.ResultMetadata;

/**
 * @author gregory green
 */
public class PredictionScore implements ModelResult<Double> {
    private final Double score;

    public PredictionScore(Double score) {
        this.score = score;
    }

    @Override
    public Double getOutput() {
        return score;
    }

    @Override
    public ResultMetadata getMetadata() {
        return new ResultMetadata() {
            @Override
            public String toString() {
                return "score:"+score;
            }
        };
    }
}
