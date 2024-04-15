package showcase.financial.banking.transactions.domain;

public record Transaction(double amount,int device,int merchant, int location) {
}
