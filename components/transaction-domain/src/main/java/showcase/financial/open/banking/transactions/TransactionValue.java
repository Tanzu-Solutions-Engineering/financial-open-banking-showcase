package showcase.financial.open.banking.transactions;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionValue {
    private String currency;
    private BigDecimal amount;
}
