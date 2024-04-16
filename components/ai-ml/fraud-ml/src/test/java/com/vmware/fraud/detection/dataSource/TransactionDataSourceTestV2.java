package com.vmware.fraud.detection.dataSource;

import nyla.solutions.core.io.IO;
import org.junit.jupiter.api.Test;
import org.tribuo.Example;
import org.tribuo.Feature;
import org.tribuo.MutableDataset;
import org.tribuo.Trainer;
import org.tribuo.anomaly.Event;
import org.tribuo.anomaly.evaluation.AnomalyEvaluator;
import org.tribuo.anomaly.libsvm.LibSVMAnomalyTrainer;
import org.tribuo.anomaly.libsvm.SVMAnomalyType;
import org.tribuo.common.libsvm.KernelType;
import org.tribuo.common.libsvm.LibSVMModel;
import org.tribuo.common.libsvm.SVMParameters;
import org.tribuo.impl.ArrayExample;
import org.tribuo.util.Util;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.tribuo.anomaly.AnomalyFactory.ANOMALOUS_EVENT;
import static org.tribuo.anomaly.AnomalyFactory.EXPECTED_EVENT;

class TransactionDataSourceTestV2 {

//    private int numSamples = 2000;
    private long seed = Trainer.DEFAULT_SEED;
    private Random rng = new Random(seed);
    private double fractionAnomalous;

    private double[] expectedMeans = new double[]{1.0, 2.0, 1.0, 2.0, 5.0};
    private double[] expectedVariances = new double[]{1.0, 0.5, 0.25, 1.0,  0.1};

    private double[] anomalousMeans = new double[]{-2.0, 2.0, -2.0, 2.0, -10.0};
    private double[] anomalousVariances = new double[]{1.0, 0.5, 0.25, 1.0, 0.1};

    private static final String[] allFeatureNames = new String[]{
            "amount", "device", "merchant", "location"
    };
    private String[] featureNames = allFeatureNames;
    private double amountExpectedMean = 100;
    private double amountExpectedVariances = 1.0;
    private double amountFraudMean = 1000;
    private double amountFraudVariances = 1.0;

    private double timestampExpectedMean = 2.0;
    private double timeExpectedVariances = 0.5;
    private double timestampFraudMean = 2.0;
    private double timeFraudVariances = 0.5;

    private double deviceExpectedMean = expectedMeans[2];;
    private double deviceExpectedVariances = expectedVariances[2];
    private double deviceFraudMean = anomalousMeans[2];
    private double deviceFraudVariances = anomalousVariances[2];

    private double merchantExpectedMean = expectedMeans[3];;
    private double merchantExpectedVariances = expectedVariances[3];

    private double locationExpectedMean = expectedMeans[4];;
    private double locationExpectedVariances = expectedVariances[4];





    private double merchantVarianceMean = anomalousMeans[3];
    private double merchantVarianceVariances = anomalousVariances[3];
    private double locationVarianceMean = anomalousMeans[4];
    private double locationVarianceVariances = anomalousVariances[4];


    @Test
    void build() {
        var subject = new TransactionDataSource(constructExamples(2000,
                0.0f, 1L));

        var dataSet = new MutableDataset<>(subject);

        var testDataSet = new MutableDataset<>(new TransactionDataSource(constructExamples(2000,
                0.2f, 2L)));


        var params = new SVMParameters<>(new SVMAnomalyType(SVMAnomalyType.SVMMode.ONE_CLASS), KernelType.RBF);
        params.setGamma(1.0);
        params.setNu(0.1);
        var trainer = new LibSVMAnomalyTrainer(params);



        //Training is the same as other Tribuo prediction tasks, just call train and pass the training data.
        var startTime = System.currentTimeMillis();
        var model = trainer.train(dataSet);

        var endTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("Training took " + Util.formatDuration(startTime,endTime));

        var eval = new AnomalyEvaluator();
        var testEvaluation = eval.evaluate(model,testDataSet);

        System.out.println(testEvaluation.toString());
        System.out.println(testEvaluation.confusionString());

        for(Example<Event> event: testDataSet)
        {
            var prediction = model.predict(event);
        }

        List<Feature> features = new ArrayList<>();
        features.add(new Feature("amount", 999.0));
        features.add(new Feature("device", -2.0));
        features.add(new Feature("merchant", 1.0));
        features.add(new Feature("location", -10.0));
        var event = new ArrayExample<>(EXPECTED_EVENT,features);

        System.out.println(event);
        var out = model.predict(event);

        System.out.println(out.getOutput().getScore());

        IO.serializeToFile(model, Paths.get("/tmp/model.zip").toFile());

        LibSVMModel<Event> outModel = (LibSVMModel<Event>) IO.deserialize( Paths.get("/tmp/model.zip").toFile());

        System.out.println(outModel.predict(event));


    }



    private List<Example<Event>> constructExamples(int numSamples,float fractionAnomalous, long seed) {

        List<Example<Event>> examples = new ArrayList<>(numSamples);
        for (int i = 0; i < numSamples; i++) {
            double draw = rng.nextDouble();
            if (draw < fractionAnomalous) {
                examples.add(generateFraud());
            } else {
                examples.add(generateExpected());
            }
        }
        return Collections.unmodifiableList(examples);
    }

    private Example<Event> generateExpected() {

        double currentTime = System.currentTimeMillis();
        List<Feature> features = new ArrayList<>();

        // "amount","timestamp", "device", "merchant", "location"
        features.add(new Feature("amount", generateValue(amountExpectedMean,amountExpectedVariances)));
        features.add(new Feature("device", generateValue(deviceExpectedMean,deviceExpectedVariances)));
        features.add(new Feature("merchant", generateValue(merchantExpectedMean,merchantExpectedVariances)));
        features.add(new Feature("location", generateValue(locationExpectedMean,locationExpectedVariances)));
        return new ArrayExample<>(EXPECTED_EVENT,features);
    }
    double generateValue(double mean, double variance)
    {
        return Math.round(rng.nextGaussian() * Math.sqrt(variance) + mean);
    }

    private Example<Event> generateFraud() {
        double currentTime = System.currentTimeMillis();
        List<Feature> features = new ArrayList<>();
        features.add(new Feature("amount", generateValue(amountFraudMean, amountFraudVariances)));
        features.add(new Feature("device", generateValue(deviceFraudMean, deviceFraudVariances)));
        features.add(new Feature("merchant", generateValue(merchantVarianceMean,merchantVarianceVariances)));
        features.add(new Feature("location", generateValue(locationVarianceMean,locationVarianceVariances)));
        return new ArrayExample<>(ANOMALOUS_EVENT,features);
    }

    private static List<Feature> generateFeatures(Random rng, String[] names, double[] means, double[] variances) {
        if ((names.length != means.length) || (names.length != variances.length)) {
            throw new IllegalArgumentException("Names, means and variances must be the same length");
        }

        List<Feature> features = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            double value = (rng.nextGaussian() * Math.sqrt(variances[i])) + means[i];
            features.add(new Feature(names[i], value));
        }

        return features;
    }
}