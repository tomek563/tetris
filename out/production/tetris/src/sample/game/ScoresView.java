package sample.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScoresView extends Stage {
    private final VBox root;
    private final ScoresManager scoresManager;

    public ScoresView(ScoresManager scoresManager) {
        this.scoresManager = scoresManager;
        root = new VBox(5);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        Scene scene = new Scene(root);
        initComponents();
        setTitle("Best scores: ");
        setScene(scene);
        setResizable(false);
        show();
    }

    private void initComponents() {
        ListView<Player> players = new ListView<>();
        players.getItems().addAll(scoresManager.getPlayers());
        root.getChildren().add(players);
    }
}
