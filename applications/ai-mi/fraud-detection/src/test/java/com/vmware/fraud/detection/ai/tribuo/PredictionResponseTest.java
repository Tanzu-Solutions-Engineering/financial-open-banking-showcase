package com.vmware.fraud.detection.ai.tribuo;

import com.vmware.fraud.detection.ai.PredictionScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

class PredictionResponseTest {

    private PredictionResponse subject;
    private Double expectedDouble = 44.45;
    private PredictionScore expected = new PredictionScore(expectedDouble);

    @BeforeEach
    void setUp() {
        subject = new PredictionResponse(expected);
    }

    @Test
    void getResult() {

        assertEquals(expected, subject.getResult());
    }

    @Test
    void getResults() {
    }
}