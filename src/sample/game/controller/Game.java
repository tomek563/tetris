package sample.game.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.ViewFactory;
import sample.game.Player;
import sample.game.ScoresLabel;
import sample.game.ScoresManager;

public class Game extends Stage {
    protected final static int FIELD_SIZE = 40;
    public final static int WIDTH = 411;
    public final static int HEIGHT = 607;
    private final ViewFactory viewFactory;
    private final ScoresManager scoresManager;
    private final Pane gameComponents = new Pane();
    private final FigureFactory figureFactory;
    private final CompositeFigure composite;
    private final BoardGame boardGame;
    private final Scene scene;
    private Figure currentFigure;
    private boolean running = true;
    private final Player player;
    private final ScoresLabel scores;

    public Game(ViewFactory viewFactory, ScoresManager scoresManager) {
        this.viewFactory = viewFactory;
        this.scoresManager = scoresManager;
        setTitle("Tetris");
        BorderPane root = new BorderPane();
        scene = new Scene(root);
        setScene(scene);
        this.figureFactory = new FigureFactory();
        this.currentFigure = figureFactory.getRandomFigure();
        this.boardGame = new BoardGame();
        this.composite = new CompositeFigure();
        player = new Player();
        scores = new ScoresLabel(player);
        root.setCenter(gameComponents);
        VBox guiComponents = new VBox();
        root.setRight(guiComponents);
        BackgroundImage myBI = new BackgroundImage(new Image("tetris_background.jpg", WIDTH, HEIGHT, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        root.setPadding(new Insets(0, 0, 0, 5));
        setHeight(HEIGHT + 40);
        setWidth(WIDTH + 350);
        show();
        startGame();
    }

    public void startGame() {
        gameComponents.getChildren().add(composite);
        gameComponents.getChildren().add(scores);
        setCurrentFigure(currentFigure);
        startTimer();
        setUpKeyEvents();
    }

    private void setCurrentFigure(Figure currentFigure) {
        this.currentFigure = currentFigure;
        gameComponents.getChildren().add(currentFigure);
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
        int rows = composite.handleCompletingRow();
        player.addScore(rows);
        scores.updateScores();
        finishGame();
    }

    private void printBoard() {
        boardGame.print(currentFigure, composite);
    }

    private void handleBottomCollision() {
        if (currentFigure.isSingleFieldOnHorizontalBorder() || currentFigure.isCurrentFigureOnBorderWith(composite)) {
            composite.addToComposite(currentFigure);
            gameComponents.getChildren().remove(currentFigure);
            createNewFigure();
        }
    }

    private void createNewFigure() {
        Figure newRandomFigure = figureFactory.getRandomFigure();
        setCurrentFigure(newRandomFigure);
    }

    private void finishGame() {
        if (isTheEndOfAGame()) {
            readNameAndSave();
            running = false;
        }
    }

    private void readNameAndSave() {
        TextInputDialog window = new TextInputDialog();
        window.setTitle("Game ended!");
        window.setHeaderText("Game ended! You got: " + player.getScores() + " points!");
        window.setContentText("Enter your name:");
        Platform.runLater(window::show);
        window.setOnHidden(e -> {
                    savePlayer(window);
                    viewFactory.buildMenuView();
                    close();
                }
        );
    }

    private void savePlayer(TextInputDialog window) {
        String name = window.resultProperty().get();
        if (name == null || name.isEmpty()) return;
        player.setName(name);
        scoresManager.savePlayer(player);
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



