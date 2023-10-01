public abstract class Node implements Runnable{
    protected String name; // they should be protected given that it will be extended by Miner and Agent class
    protected int balance;

    public Node(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }
    /*
    * the following methods should be synchronized given that may transaction of the same agent to another receiver can
    * occurs in a sequence, and then the Miner that validated the transaction should read and modify the balance field
    * many times and then we have to ensure thread safety to avoid race condition
    */
    public synchronized int getBalance() {
        return balance;
    }

    public synchronized void setBalance(int balance) {
        this.balance = balance;
    }

    public synchronized void addToBalance(int amount) {
        this.balance += amount;
    }

    public synchronized void deductFromBalance(int amount) {
        this.balance -= amount;
    }
}
