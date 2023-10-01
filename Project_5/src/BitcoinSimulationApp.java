import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BitcoinSimulationApp extends Application {

    private Map<String, Label> balanceLabels = new HashMap<>();
    private Label transactionRateLabel = new Label("Transaction Rate: 0 tx/s");
    private AtomicInteger transactionCount = new AtomicInteger(0);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bitcoin Simulation");

        VBox vbox = new VBox();
        for (Node node : Bitcoin.nodes.values()) {
            Label label = new Label(node.getName() + " balance: " + node.getBalance());
            balanceLabels.put(node.getName(), label);
            vbox.getChildren().add(label);
        }
        vbox.getChildren().add(transactionRateLabel);

        Scene scene = new Scene(vbox, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

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

        // Periodically update GUI with balances and transaction rate
        ScheduledExecutorService guiUpdater = Executors.newSingleThreadScheduledExecutor();
        guiUpdater.scheduleAtFixedRate(this::updateGUI, 0, 1, TimeUnit.SECONDS);
    }

    private void updateGUI() {
        Platform.runLater(() -> {
            for (Node node : Bitcoin.nodes.values()) {
                Label label = balanceLabels.get(node.getName());
                label.setText(node.getName() + " balance: " + node.getBalance());
            }
            int transactionsThisSecond = transactionCount.getAndSet(0);
            transactionRateLabel.setText("Transaction Rate: " + transactionsThisSecond + " tx/s");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

