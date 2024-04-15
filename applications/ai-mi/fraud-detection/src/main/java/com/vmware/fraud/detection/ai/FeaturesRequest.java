package com.vmware.fraud.detection.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.model.ModelOptions;
import org.springframework.ai.model.ModelRequest;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class FeaturesRequest implements ModelRequest<Map<String,Double>> {

    private final Map<String,Double> instructions;

    @Override
    public Map<String,Double> getInstructions() {
        return instructions;
    }

    @Override
    public ModelOptions getOptions() {
       return new ModelOptions() {
           @Override
           public String toString() {
               return String.valueOf(instructions);
           }
       };
    }
}
