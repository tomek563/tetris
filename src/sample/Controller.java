package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.dataModel.Game;

public class Controller {
    @FXML
    public Pane boardGame;

    @FXML
    private Pane grid;

    @FXML
    private Button nowaGra;


    @FXML
    public void startGame() {
    }

    @FXML
    public void initialize() {
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        };
        nowaGra.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

}
