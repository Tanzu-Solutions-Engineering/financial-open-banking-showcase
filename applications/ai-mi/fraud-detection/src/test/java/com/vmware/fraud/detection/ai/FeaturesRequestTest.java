package com.vmware.fraud.detection.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeaturesRequestTest {
    private List<Double> expectedInstructions = List.of(Double.MIN_VALUE,Double.MAX_VALUE);
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