package sample;

import sample.game.*;
import sample.game.controller.Game;

public class ViewFactory {

    private final ScoresManager scoresManager;

    public ViewFactory(ScoresManager scoresManager) {
        this.scoresManager = scoresManager;
    }

    public void buildGameView() {
        Game game = new Game(this, scoresManager);
    }

    public void buildMenuView() {
        MenuView menu = new MenuView();
        MenuController menuController = new MenuController(menu, this);
    }

    public void buildScoresView() {
        ScoresView scoresView = new ScoresView(scoresManager);
    }


}
