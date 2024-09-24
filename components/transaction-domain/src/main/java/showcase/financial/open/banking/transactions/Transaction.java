package showcase.financial.open.banking.transactions;

import lombok.Data;

@Data
public class Transaction {
     private TransactionCard card;
     private TransactionTo to;
     private TransactionValue value;
     private String description;
}
