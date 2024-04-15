package showcase.financial.banking.ml.dataSource;

import com.oracle.labs.mlrg.olcut.provenance.impl.SkeletalConfiguredObjectProvenance;
import org.tribuo.provenance.ConfiguredDataSourceProvenance;

public class TransactionDataSourceProvenance extends SkeletalConfiguredObjectProvenance implements ConfiguredDataSourceProvenance {
    TransactionDataSourceProvenance(TransactionDataSource host) {
        super(host,
                "DataSource");
    }

}
