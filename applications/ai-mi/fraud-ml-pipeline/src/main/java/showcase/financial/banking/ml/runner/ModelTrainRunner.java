package showcase.financial.banking.ml.runner;

import lombok.RequiredArgsConstructor;
import nyla.solutions.core.io.IO;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.tribuo.Example;
import org.tribuo.Feature;
import org.tribuo.MutableDataset;
import org.tribuo.Trainer;
import org.tribuo.anomaly.Event;
import org.tribuo.anomaly.libsvm.LibSVMAnomalyTrainer;
import org.tribuo.anomaly.libsvm.SVMAnomalyType;
import org.tribuo.common.libsvm.KernelType;
import org.tribuo.common.libsvm.LibSVMModel;
import org.tribuo.common.libsvm.SVMParameters;
import org.tribuo.impl.ArrayExample;
import showcase.financial.banking.ml.dataSource.TransactionDataSource;

import java.util.*;

import static org.tribuo.anomaly.AnomalyFactory.ANOMALOUS_EVENT;
import static org.tribuo.anomaly.AnomalyFactory.EXPECTED_EVENT;

@Component
@RequiredArgsConstructor
public class ModelTrainRunner implements ApplicationRunner {

    private final Queue<byte[]> queue;
    private int numSamples = 2000;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var dataSource = new TransactionDataSource(constructExamples(2000,
                0.0f, 1L));

        var dataSet = new MutableDataset<>(dataSource);

        var params = new SVMParameters<>(new SVMAnomalyType(SVMAnomalyType.SVMMode.ONE_CLASS), KernelType.RBF);
        params.setGamma(1.0);
        params.setNu(0.1);
        var trainer = new LibSVMAnomalyTrainer(params);

        //Training is the same as other Tribuo prediction tasks, just call train and pass the training data.
        LibSVMModel<Event> model = trainer.train(dataSet);

        byte[] serializerModel = IO.serializeToBytes(model);

        this.queue.add(serializerModel);

    }

    private List<Example<Event>> constructExamples(int numSamples, float fractionAnomalous, long seed) {

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

}
