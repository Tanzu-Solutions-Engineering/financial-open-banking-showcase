package com.vmware.fraud.detection.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.model.ModelOptions;
import org.springframework.ai.model.ModelRequest;

import java.util.List;

@RequiredArgsConstructor
public class FeaturesRequest implements ModelRequest<List<Double>> {

    private final List<Double> instructions;

    @Override
    public List<Double> getInstructions() {
        return instructions;
    }

    @Override
    public ModelOptions getOptions() {
        return null;
    }
}
