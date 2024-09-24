package showcase.financial.open.banking.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCard {
     private String card_type;
     private String brand;
     private short cvv;
     private long card_number;
     private String name_on_card;
     private short expiry_year;
     private short expiry_month;
}
