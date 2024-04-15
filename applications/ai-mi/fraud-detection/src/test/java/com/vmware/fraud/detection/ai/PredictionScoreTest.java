package com.vmware.fraud.detection.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PredictionScoreTest {

    private PredictionScore subject;
    private Double expected = 23.34;

    @BeforeEach
    void setUp() {
        subject = new PredictionScore(expected);
    }

    @Test
    void getOutput() {
        assertEquals(expected, subject.getOutput());
    }
}