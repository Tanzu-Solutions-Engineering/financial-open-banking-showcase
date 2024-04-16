package com.vmware.fraud.detection.ai.tribuo;

import com.vmware.fraud.detection.ai.FeaturesRequest;
import com.vmware.fraud.detection.ai.PredictionScore;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.model.ModelClient;
import org.tribuo.Feature;
import org.tribuo.anomaly.Event;
import org.tribuo.common.libsvm.LibSVMModel;
import org.tribuo.impl.ArrayExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.tribuo.anomaly.AnomalyFactory.EXPECTED_EVENT;


@Setter
@Slf4j
public class TribuoModelClient implements ModelClient<FeaturesRequest,PredictionResponse> {

    private LibSVMModel <Event> model;

    @Override
    public PredictionResponse call(FeaturesRequest request) {

        if(model == null)
            return null;

        List<Feature> features = new ArrayList<>();
        for (Map.Entry<String,Double> entry : request.getInstructions().entrySet())
        {
            features.add(new Feature(entry.getKey(), entry.getValue()));
        }

        var event = new ArrayExample<>(EXPECTED_EVENT,features); //TODO this may always be expected

        var prediction = model.predict(event);
        log.info("prediction:  {}",prediction);

        return new PredictionResponse(new PredictionScore(prediction.getOutput().getScore()));
    }
}
