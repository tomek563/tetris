package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.dataModel.Game;
import sample.dataModel.TFigure;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        Game game = new Game(scene, pane);
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.setHeight(700);
        primaryStage.setWidth(500);
        primaryStage.show();
        game.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
