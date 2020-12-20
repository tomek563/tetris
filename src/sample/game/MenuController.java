package sample.game;

import javafx.application.Platform;
import sample.ViewFactory;


public class MenuController {

    private final MenuView view;
    private final ViewFactory factory;

    public MenuController(MenuView view, ViewFactory factory) {
        this.view = view;
        this.factory = factory;
        initActions();
    }

    private void initActions() {
        view.addStartAction(e -> start());
        view.addScoresAction(e -> scores());
        view.addExitAction(e -> exit());
    }

    public void start() {
        factory.buildGameView();
        view.close();
    }

    public void scores() {
        factory.buildScoresView();
    }

    public void exit() {
        Platform.exit();
    }

}
