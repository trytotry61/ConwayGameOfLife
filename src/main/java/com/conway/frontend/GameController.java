
package com.conway.frontend;

import com.conway.backend.GameEngine;
import com.conway.backend.GameBoard;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController {
    private final GameEngine game;
    private final GameView view;
    private Timeline timeline;
    private boolean running = false;
    public GameController(GameEngine game, GameView view) {
        this.game = game;
        this.view = view;
        initialize();
    }

    private void initialize() {

        updateView();
        view.getStartButton().setOnAction(event -> start());
        view.getPauseButton().setOnAction(event -> pause());
        view.getStepButton().setOnAction(event -> step());
        view.getClearButton().setOnAction(event -> clear());
        view.getRandomButton().setOnAction(event -> randomize());
        view.getResizeButton().setOnAction(event -> resize());
        view.getSpeedSlider().valueProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (running) {
                        restartTimeline();
                    }
                }
        );
        view.getCanvas().setOnMouseClicked(event -> {

            int[] cell = view.getCellAt(
                    event.getX(),
                    event.getY(),
                    game.getBoard()
            );

            if (cell != null) {

                game.getBoard().toggleCell(
                        cell[0],
                        cell[1]
                );

                updateView();
            }
        });
    }

    public void start() {

        if (running) {
            return;
        }

        running = true;

        createTimeline();

        timeline.play();

        view.setStatus("Симуляция запущена");
    }

    public void pause() {

        if (!running) {
            return;
        }

        running = false;

        if (timeline != null) {
            timeline.stop();
        }

        view.setStatus("Симуляция остановлена");
    }

    public void step() {

        if (running) {
            pause();
        }

        game.nextGeneration();

        updateView();

        view.setStatus("Выполнен следующий шаг");
    }

    public void clear() {

        pause();

        game.clearBoard();

        updateView();

        view.setStatus("Игровое поле очищено");
    }

    public void randomize() {

        pause();

        double probability =
                view.getProbabilitySlider().getValue() / 100.0;

        game.randomizeBoard(probability);

        updateView();

        view.setStatus("Создано случайное расположение клеток");
    }

    public void resize() {

        pause();

        try {

            int width = Integer.parseInt(
                    view.getWidthSpinner().getEditor().getText()
            );

            int height = Integer.parseInt(
                    view.getHeightSpinner().getEditor().getText()
            );

            if (width < 3 || width > 100
                    || height < 3 || height > 100) {

                view.setStatus(
                        "Размер поля должен быть от 3 до 100"
                );

                return;
            }

            game.resetBoard(width, height);

            updateView();

            view.setStatus(
                    "Размер поля изменён: " + width + " × " + height
            );

        } catch (NumberFormatException exception) {

            view.setStatus("Введите корректные размеры поля");
        }
    }

    private void createTimeline() {

        double speed = view.getSpeedSlider().getValue();

        double interval = 1000.0 / speed;

        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(interval),
                        event -> {

                            game.nextGeneration();

                            updateView();
                        }
                )
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void restartTimeline() {

        if (timeline != null) {
            timeline.stop();
        }

        createTimeline();

        timeline.play();
    }

    private void updateView() {

        view.render(
                game.getBoard(),
                game.getGeneration()
        );
    }

    public void shutdown() {

        pause();
    }
}
