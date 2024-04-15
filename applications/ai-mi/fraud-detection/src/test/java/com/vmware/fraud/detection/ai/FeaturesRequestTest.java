package com.vmware.fraud.detection.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeaturesRequestTest {
    private Map<String,Double> expectedInstructions = Map.of(
            "min",Double.MIN_VALUE,
            "max",Double.MAX_VALUE);

    private FeaturesRequest subject;

    @BeforeEach
    void setUp() {
        subject = new FeaturesRequest(expectedInstructions);
    }

    @Test
    void getInstructions() {
        assertEquals(expectedInstructions, subject.getInstructions());
    }
}