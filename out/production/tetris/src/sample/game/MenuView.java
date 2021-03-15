package sample.game;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuView extends Stage {
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 100;
    private final VBox root;
    private Button start;
    private Button scores;
    private Button exit;

    public MenuView() {
        setTitle("Tetris");
        root = new VBox(25);
        root.setPadding(new Insets(50));
        Scene scene = new Scene(root);
        setScene(scene);
        setResizable(false);
        initComponents();
        show();
    }

    private void initComponents() {
        start = new Button("Start");
        scores = new Button("Scores");
        exit = new Button("Exit");
        start.setPrefWidth(BUTTON_WIDTH);
        start.setPrefHeight(BUTTON_HEIGHT);
        scores.setPrefWidth(BUTTON_WIDTH);
        scores.setPrefHeight(BUTTON_HEIGHT);
        exit.setPrefWidth(BUTTON_WIDTH);
        exit.setPrefHeight(BUTTON_HEIGHT);
        start.setFont(new Font("Verdana", 20));
        scores.setFont(new Font("Verdana", 20));
        exit.setFont(new Font("Verdana", 20));
        root.getChildren().addAll(start, scores, exit);
    }

    public void addStartAction(EventHandler event) {
        start.setOnAction(event);
    }

    public void addScoresAction(EventHandler event) {
        scores.setOnAction(event);
    }

    public void addExitAction(EventHandler event) {
        exit.setOnAction(event);
    }
}
