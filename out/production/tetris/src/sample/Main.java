package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.game.ScoresManager;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ScoresManager scoresManager = new ScoresManager();
        ViewFactory factory = new ViewFactory(scoresManager);
        factory.buildMenuView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
