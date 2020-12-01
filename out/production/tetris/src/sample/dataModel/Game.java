package sample.dataModel;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Game {
    private final Scene scene;
    private final Pane pane;
    protected final static int FIELD_SIZE = 40;
    private final FigureFactory figureFactory;
    private final CompositeFigure composite;
    private final BoardGame boardGame;
    private Figure currentFigure;
    private boolean running = true;

    public Game(Scene scene, Pane pane) {
        this.scene = scene;
        this.pane = pane;
        this.figureFactory = new FigureFactory();
        this.currentFigure = figureFactory.getRandomFigure();
        this.boardGame = new BoardGame();
        this.composite = new CompositeFigure();
    }

    public void startGame() {
        pane.getChildren().add(composite);
        setCurrentFigure(currentFigure);
        startTimer();
        setUpKeyEvents();
    }

    private void setCurrentFigure(Figure currentFigure) {
        this.currentFigure = currentFigure;
        pane.getChildren().add(currentFigure);
    }

    private void startTimer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (now - lastTime > 1_000_000_000) {
                    lastTime = now;
                    if (running) {
                        updateGame();
                    }
                }

            }
        };
        animationTimer.start();
    }

    private void setUpKeyEvents() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                currentFigure.move(keyEvent.getCode(), composite);
            }
        });
    }

    private void updateGame() {
        printBoard();
        currentFigure.move(KeyCode.DOWN, composite);
        handleBottomCollision();
        composite.handleCompletingRow();
        checkToFinishGame();
    }

    private void printBoard() {
        boardGame.print(currentFigure, composite);
    }

    private void handleBottomCollision() {
        if (currentFigure.isSingleFieldOnHorizontalBorder() || currentFigure.isCurrentFigureOnBorderWith(composite)) {
            composite.addToComposite(currentFigure);
            pane.getChildren().remove(currentFigure);
            createNewFigure();
        }
    }

    private void createNewFigure() {
        Figure newRandomFigure = figureFactory.getRandomFigure();
        setCurrentFigure(newRandomFigure);
    }

    private void checkToFinishGame() {
        if (isTheEndOfAGame()) {
            System.out.println("Koniec gry");
            running = false;
        }
    }

    private boolean isTheEndOfAGame() {
        for (SingleField singleField : composite.getCompositeSingleFields()) {
            if (singleField.getGridY() == 1) {
                return true;
            }
        }
        return false;
    }

}



