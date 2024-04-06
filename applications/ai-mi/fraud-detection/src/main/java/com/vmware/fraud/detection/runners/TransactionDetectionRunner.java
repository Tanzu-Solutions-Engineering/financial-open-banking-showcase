package com.vmware.fraud.detection.runners;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.tribuo.Dataset;
import org.tribuo.Trainer;
import org.tribuo.anomaly.evaluation.AnomalyEvaluator;
import org.tribuo.classification.Label;
import org.tribuo.classification.evaluation.LabelEvaluator;
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer;

//@Component
public class TransactionDetectionRunner implements ApplicationRunner {

    private final boolean outputRequired;
    private final Dataset<Label> dataSet;
    private final AnomalyEvaluator anomalyEvaluator;
    private final Dataset<Label> testDataSet;

    public TransactionDetectionRunner(boolean outputRequired,
                                      Dataset<Label> dataSet,
                                      AnomalyEvaluator anomalyEvaluator,
                                      Dataset<Label> testDataSet) {
        this.outputRequired = outputRequired;
        this.dataSet = dataSet;
        this.anomalyEvaluator = anomalyEvaluator;
        this.testDataSet = testDataSet;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Trainer<Label> trainer = new LogisticRegressionTrainer();
        var model = trainer.train(dataSet);


        var evaluator = new LabelEvaluator();
        var evaluation = evaluator.evaluate(model,testDataSet);
        System.out.println("FN="+evaluation.fn());

        System.out.println(trainer.toString());
    }
}
