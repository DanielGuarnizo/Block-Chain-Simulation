import java.util.concurrent.ConcurrentLinkedQueue;

public class Blockchain {
    private ConcurrentLinkedQueue<Transaction> transactions = new ConcurrentLinkedQueue<>();

    public ConcurrentLinkedQueue<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return String.valueOf(transactions.size());
    }
}
