package sample.game;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.game.controller.Game;

public class ScoresLabel extends Label {

    private final Player player;

    public ScoresLabel(Player player) {
        this.player = player;
        setTranslateX(Game.WIDTH + 100);
        setTranslateY(50);
        setFont(new Font("Verdana", 30));
        setTextFill(Color.CHOCOLATE);
    }


    public void updateScores() {
        setText("Scores: " + player.getScores());
    }
}
