package showcase.financial.banking.ml.dataSource;

import org.jetbrains.annotations.NotNull;
import org.tribuo.ConfigurableDataSource;
import org.tribuo.Example;
import org.tribuo.OutputFactory;
import org.tribuo.anomaly.AnomalyFactory;
import org.tribuo.anomaly.Event;
import org.tribuo.provenance.DataSourceProvenance;

import java.util.Iterator;
import java.util.List;

public class TransactionDataSource  implements ConfigurableDataSource<Event> {

    private final List<Example<Event>> examples;
    private static final AnomalyFactory factory = new AnomalyFactory();

    public TransactionDataSource(List<Example<Event>> examples) {
        this.examples = examples;
    }

    @Override
    public OutputFactory<Event> getOutputFactory() {
        return factory;
    }

    @Override
    public DataSourceProvenance getProvenance() {
        return new TransactionDataSourceProvenance(this);
    }


    @NotNull
    @Override
    public Iterator<Example<Event>> iterator() {
        return examples.iterator();
    }
}
