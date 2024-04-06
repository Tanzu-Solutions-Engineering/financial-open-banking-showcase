package com.vmware.fraud.detection.runners;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.tribuo.MutableDataset;
import org.tribuo.anomaly.evaluation.AnomalyEvaluator;
import org.tribuo.anomaly.example.GaussianAnomalyDataSource;
import org.tribuo.anomaly.libsvm.LibSVMAnomalyTrainer;
import org.tribuo.anomaly.libsvm.SVMAnomalyType;
import org.tribuo.common.libsvm.KernelType;
import org.tribuo.common.libsvm.SVMParameters;
import org.tribuo.util.Util;

@Component
public class ExampleRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var eval = new AnomalyEvaluator();

        var dataSource = new GaussianAnomalyDataSource(2000,/* number of examples */
                0.0f,/*fraction anomalous */
                1L/* RNG seed */);


        var dataSet = new MutableDataset<>(dataSource);

        System.out.println("*********"+dataSet);

        var testDataSet = new MutableDataset<>(new GaussianAnomalyDataSource(2000,0.2f,2L));


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

        var testEvaluation = eval.evaluate(model,testDataSet);
        System.out.println(testEvaluation.toString());
        System.out.println(testEvaluation.confusionString());
    }
}
