import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Miner extends Node{


    public Miner(String name, int balance) {
        super(name, balance);
    }

    private Blockchain selectLongestBlockchain() {
        Blockchain longestBlockchain = null;
        int longestBlockchainLength = 0;
        for (Blockchain blockchain : Bitcoin.blockchains.values()) {
            if (blockchain.getTransactions().size() > longestBlockchainLength) {
                longestBlockchain = blockchain;
                longestBlockchainLength = blockchain.getTransactions().size();
            }
        }
        return longestBlockchain;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                // this part of the code it checks the longest blockchain and choose one
                Thread.sleep(1000); // Reduce sleep time to increase transaction processing frequency
                Blockchain blockchain = selectLongestBlockchain();
                synchronized (blockchain) { // synchronized to avoid other miner to modify the longest blockchain

                    ConcurrentLinkedQueue<Transaction> transactions = blockchain.getTransactions();
                    for (Transaction transaction : transactions) {
                        Node sender = Bitcoin.nodes.get(transaction.getSender());


                        if (sender.getBalance() >= transaction.getAmount()) {
                            // The transaction is valid
                            sender.deductFromBalance(transaction.getAmount()); // here we reduce the balance of the sender
                            Node receiver = Bitcoin.nodes.get(transaction.getReceiver());
                            receiver.addToBalance(transaction.getAmount()); // increase balance of the reiceiver

                            // Mining reward
                            int reward = 9; // Set reward amount
                            addToBalance(reward);
                            // Miner makes a transaction with a random agent
                            Node randomAgent = Bitcoin.nodes.get(new ArrayList<>(Bitcoin.nodes.keySet()).get(random.nextInt(Bitcoin.nodes.size())));
                            if (!randomAgent.getName().equals(name) && balance > 0) {
                                int maxAmount = Math.min(balance, 10); // Limit the transfer amount
                                int amount = random.nextInt(maxAmount) + 1;
                                deductFromBalance(amount);
                                Transaction minerTransaction = new Transaction(name, randomAgent.getName(), amount);
                                blockchain.addTransaction(minerTransaction);
                            }
                        }else {
                            // transaction in invalid
                            //System.out.println("Invalid transaction from " + sender.getName() + " to the agent " + Bitcoin.nodes.get(transaction.getReceiver()).getName() + "with amount" + transaction.getAmount());
                        }

                    }
                    transactions.clear(); // Clear processed transactions from the blockchain
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


