package com.vmware.fraud.detection.ai.tribuo;

import com.vmware.fraud.detection.ai.FeaturesRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tribuo.Example;
import org.tribuo.Prediction;
import org.tribuo.anomaly.Event;
import org.tribuo.common.libsvm.LibSVMModel;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TribuoModelClientTest {
    @Mock
    private FeaturesRequest request;

    @Mock
    private PredictionResponse expected;

    @Mock
    private LibSVMModel<Event> model;

    @Mock
    private Supplier<LibSVMModel<Event>> modelSupplier;
    @Mock
    private Prediction<Event> response;


    @Mock
    private Event output;

    private Double expectedScore = 23.23;

    private TribuoModelClient subject;

    @BeforeEach
    void setUp() {
        subject = new TribuoModelClient(modelSupplier);
    }


    @Test
    void prediction() {

        when(modelSupplier.get()).thenReturn(model);
        when(model.predict((Example<Event>) any(Example.class))).thenReturn(response);

        when(response.getOutput()).thenReturn(output);
        when(output.getScore()).thenReturn(expectedScore);

        var actual = subject.call(request);

        assertEquals(expectedScore, actual.getResult().getOutput());
    }
}