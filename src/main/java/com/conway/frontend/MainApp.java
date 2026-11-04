
package com.conway.frontend;
import com.conway.backend.GameEngine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class MainApp extends Application {
    private GameController controller;
    @Override
    public void start(Stage stage) {
        GameEngine game = new GameEngine(30, 20);
        GameView view = new GameView();
        controller = new GameController(game, view);
        Scene scene = new Scene(
                view.getRoot(),
                1150,
                750
        );
        stage.setTitle("Conway's Game of Life — Torus");
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() {
        if (controller != null) {
            controller.shutdown();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
