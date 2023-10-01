import java.util.concurrent.*;
public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(7);
        Agent agent1 = new Agent("Agent1", 100);
        Agent agent2 = new Agent("Agent2", 100);
        Agent agent3 = new Agent("Agent3", 100);
        Agent agent4 = new Agent("Agent4", 100);
        Agent agent5 = new Agent("Agent5", 100);
        Miner miner1 = new Miner("Miner1", 100);
        Miner miner2 = new Miner("Miner2", 100);
        Bitcoin.nodes.put("Agent1", agent1);
        Bitcoin.nodes.put("Agent2", agent2);
        Bitcoin.nodes.put("Agent3", agent3);
        Bitcoin.nodes.put("Agent4", agent4);
        Bitcoin.nodes.put("Agent5", agent5);
        Bitcoin.nodes.put("Miner1", miner1);
        Bitcoin.nodes.put("Miner2", miner2);
        Bitcoin.blockchains.put("Agent1", new Blockchain());
        Bitcoin.blockchains.put("Agent2", new Blockchain());
        Bitcoin.blockchains.put("Agent3", new Blockchain());
        Bitcoin.blockchains.put("Agent4", new Blockchain());
        Bitcoin.blockchains.put("Agent5", new Blockchain());
        Bitcoin.blockchains.put("Miner1", new Blockchain());
        Bitcoin.blockchains.put("Miner2", new Blockchain());
        executor.execute(agent1);
        executor.execute(agent2);
        executor.execute(agent3);
        executor.execute(agent4);
        executor.execute(agent5);
        executor.execute(miner1);
        executor.execute(miner2);

        while (true) {
            try {
                Thread.sleep(1000); // Reduce sleep time to print balances more frequently
                updateOverallBalance();
                System.out.println("\n");
                System.out.println("Overall Balance: " + Bitcoin.overallBalance);
                System.out.println("Node Balances:");
                for (Node node : Bitcoin.nodes.values()) {
                    System.out.println(node.getName() + " balance: " + node.getBalance());

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateOverallBalance() {
        int balance = 0;
        for (Node node : Bitcoin.nodes.values()) {
            balance += node.getBalance();
        }
        Bitcoin.overallBalance = balance;
    }
}



