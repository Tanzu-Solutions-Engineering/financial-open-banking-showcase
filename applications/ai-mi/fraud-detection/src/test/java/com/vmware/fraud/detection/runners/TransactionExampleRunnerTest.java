package com.vmware.fraud.detection.runners;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationArguments;
import org.tribuo.Dataset;
import org.tribuo.MutableDataset;
import org.tribuo.OutputFactory;
import org.tribuo.anomaly.AnomalyFactory;
import org.tribuo.anomaly.evaluation.AnomalyEvaluator;
import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.data.columnar.FieldProcessor;
import org.tribuo.data.columnar.ResponseProcessor;
import org.tribuo.data.columnar.RowProcessor;
import org.tribuo.data.columnar.processors.field.DoubleFieldProcessor;
import org.tribuo.data.columnar.processors.response.FieldResponseProcessor;
import org.tribuo.data.csv.CSVLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

class TransactionExampleRunnerTest {

    private RowProcessor rowProcessor;
    private boolean outputRequired;
    private Dataset<Label> dataSet;
    private Dataset<Label> testDataSource;
    private TransactionDetectionRunner subject;
    private AnomalyEvaluator eval;
    private ResponseProcessor responseProcessor;
    private Map<String, FieldProcessor> fieldProcessorMap;
    private String privateResponse = "Label";
    private OutputFactory outputFactory = new AnomalyFactory();
    private ApplicationArguments args;
    private URI dataSetCsvPath;
    private URI testSetCsvPath;

//    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        this.eval = new AnomalyEvaluator();
        responseProcessor = new FieldResponseProcessor(List.of("Label"),"Legitimate",outputFactory);
        fieldProcessorMap = Map.of("Amount",new DoubleFieldProcessor("Amount"),
                "Merchant",new DoubleFieldProcessor("Merchant"),
                "Category",new DoubleFieldProcessor("Category")
                );
        this.rowProcessor = new RowProcessor(responseProcessor,fieldProcessorMap);

        var irisHeaders = new String[]{"Amount","Merchant","Category","Label"};
        this.dataSet =
               new MutableDataset<>( new CSVLoader<Label>(new LabelFactory()).loadDataSource(Paths.get("/Users/Projects/VMware/Tanzu/Use-Cases/Vertical-Industries/VMware-Financial/dev/financial-open-banking-showcase/applications/ai-mi/fraud-detection/src/main/resources/data-set.csv"),
                        /* Output column   */ irisHeaders[3],
                        /* Column headers  */ irisHeaders));


        this.testDataSource =
                new MutableDataset<>( new CSVLoader<Label>(new LabelFactory()).loadDataSource(Paths.get("/Users/Projects/VMware/Tanzu/Use-Cases/Vertical-Industries/VMware-Financial/dev/financial-open-banking-showcase/applications/ai-mi/fraud-detection/src/main/resources/test-set.csv"),
                        /* Output column   */ irisHeaders[3],
                        /* Column headers  */ irisHeaders));

        this.subject = new TransactionDetectionRunner(outputRequired,dataSet,eval,testDataSource);
    }

//    @Test
    void run() throws Exception {
        subject.run(args);
    }
}