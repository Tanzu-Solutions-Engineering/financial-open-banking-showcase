package com.vmware.fraud.detection.dataSource;

import nyla.solutions.core.util.Digits;
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
import org.tribuo.common.libsvm.SVMParameters;
import org.tribuo.impl.ArrayExample;
import org.tribuo.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.tribuo.anomaly.AnomalyFactory.ANOMALOUS_EVENT;
import static org.tribuo.anomaly.AnomalyFactory.EXPECTED_EVENT;

class TransactionDataSourceTest {

//    private int numSamples = 2000;
    private Digits digits = new Digits();
    private long seed = Trainer.DEFAULT_SEED;
    private Random rng = new Random(seed);
    private double fractionAnomalous;

    private double[] expectedMeans = new double[]{1.0, 2.0, 1.0, 2.0, 5.0};
    private double[] expectedVariances = new double[]{1.0, 0.5, 0.25, 1.0,  0.1};

    private double[] anomalousMeans = new double[]{-2.0, 2.0, -2.0, 2.0, -10.0};
    private double[] anomalousVariances = new double[]{1.0, 0.5, 0.25, 1.0, 0.1};

    private static final String[] allFeatureNames = new String[]{
            "amount","timestamp", "device", "merchant", "location"
    };
    private String[] featureNames = allFeatureNames;

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

            System.out.println("prediction:"+prediction+ "\n event:"+event);
        }

        Example<Event> test = constructFeatures();
        var prediction = model.predict(test);
        System.out.println("SCORE:"+prediction.getOutput().getScore());

        Example<Event> testFraud = constructFeaturesFraud();
        var predictionFraud = model.predict(testFraud);

        System.out.println("SCORE:"+predictionFraud.getOutput().getScore());

    }

    private Example<Event>   constructFeaturesFraud() {

        double currentTime = System.currentTimeMillis();
        List<Feature> features = new ArrayList<>();
        // "amount","timestamp", "device", "merchant", "location"
        features.add(new Feature("amount", digits.generateDouble(500D,1000D)));
        features.add(new Feature("timestamp", digits.generateDouble(currentTime-1000,currentTime)));
        features.add(new Feature("device", digits.generateInteger(1001,2000)));
        features.add(new Feature("merchant", digits.generateInteger(1,100)));
        features.add(new Feature("location", digits.generateInteger(1,200)));
        return new ArrayExample<>(EXPECTED_EVENT,features);
    }

    private Example<Event>   constructFeatures() {

        double currentTime = System.currentTimeMillis();
        List<Feature> features = new ArrayList<>();
        // "amount","timestamp", "device", "merchant", "location"
        features.add(new Feature("amount", digits.generateDouble(1D,100D)));
        features.add(new Feature("timestamp", digits.generateDouble(currentTime-1000,currentTime)));
        features.add(new Feature("device", digits.generateInteger(1,1000)));
        features.add(new Feature("merchant", digits.generateInteger(1,100)));
        features.add(new Feature("location", digits.generateInteger(1,200)));
        return new ArrayExample<>(EXPECTED_EVENT,features);
    }


    private List<Example<Event>> constructExamples(int numSamples,float fractionAnomalous, long seed) {

        List<Example<Event>> examples = new ArrayList<>(numSamples);
        for (int i = 0; i < numSamples; i++) {
            double draw = rng.nextDouble();
            if (draw < fractionAnomalous) {
                List<Feature> featureList = generateFeatures(rng, featureNames, anomalousMeans, anomalousVariances);
                examples.add(new ArrayExample<Event>(ANOMALOUS_EVENT,featureList));
//                examples.add(generateFraud());
            } else {
                List<Feature> featureList = generateFeatures(rng, featureNames, expectedMeans, expectedVariances);
                examples.add(new ArrayExample<Event>(EXPECTED_EVENT,featureList));
//                examples.add(generateExpected());
            }
        }
        return Collections.unmodifiableList(examples);
    }

    private Example<Event> generateExpected() {

        double currentTime = System.currentTimeMillis();
        List<Feature> features = new ArrayList<>();
        // "amount","timestamp", "device", "merchant", "location"
        features.add(new Feature("amount", digits.generateDouble(1D,100D)));
        features.add(new Feature("timestamp", digits.generateDouble(currentTime-1000,currentTime)));
        features.add(new Feature("device", digits.generateInteger(1,1000)));
        features.add(new Feature("merchant", digits.generateInteger(1,100)));
        features.add(new Feature("location", digits.generateInteger(1,200)));
        return new ArrayExample<>(EXPECTED_EVENT,features);
    }

    private Example<Event> generateFraud() {
        double currentTime = System.currentTimeMillis();
        List<Feature> features = new ArrayList<>();
        // "amount","timestamp", "device", "merchant", "location"
        features.add(new Feature("amount", digits.generateDouble(999D,1000D)));
        features.add(new Feature("timestamp", digits.generateDouble(currentTime-1000,currentTime)));
        features.add(new Feature("device", digits.generateInteger(1999,2000)));
        features.add(new Feature("merchant", digits.generateInteger(99,100)));
        features.add(new Feature("location", digits.generateInteger(199,200)));
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