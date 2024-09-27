package showcase.financial.open.banking.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
     private TransactionCard card;
     private TransactionTo to;
     private TransactionValue value;
     private String description;
}
