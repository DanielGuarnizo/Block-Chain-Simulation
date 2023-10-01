import java.util.ArrayList;
import java.util.Random;

public class Agent extends Node{
    public Agent(String name, int balance) {
        super(name, balance);
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(500); // Reduce sleep time to increase transaction frequency
                Node receiver = Bitcoin.nodes.get(new ArrayList<>(Bitcoin.nodes.keySet()).get(random.nextInt(Bitcoin.nodes.size())));
                /*this line get from the field in the bitcoin class a node, first of all taking all the keys of the hashlist
                * and convert in to an Arraylist that we will access to an index randombly choose
                * */
                if (!receiver.getName().equals(name) && balance > 0) { // check if is not the same Agent
                    synchronized (this) {
                        //int maxAmount = Math.min(balance, 10); // Limit the transfer amount
                        //int amount = random.nextInt(maxAmount) + 1; // generates a random number between maxAmount and 0
                        int amount = random.nextInt(balance) + 1;
                        Transaction transaction = new Transaction(name, receiver.getName(), amount);
                        Blockchain blockchain = Bitcoin.blockchains.get(name);
                        blockchain.addTransaction(transaction);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
