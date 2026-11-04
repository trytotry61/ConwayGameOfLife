
package com.conway.frontend;

import com.conway.backend.GameBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameView {

    private final BorderPane root;

    private final Canvas canvas;

    private final Label generationLabel;
    private final Label aliveLabel;
    private final Label statusLabel;

    private final Button startButton;
    private final Button pauseButton;
    private final Button stepButton;
    private final Button clearButton;
    private final Button randomButton;
    private final Button resizeButton;

    private final Spinner<Integer> widthSpinner;
    private final Spinner<Integer> heightSpinner;

    private final Slider speedSlider;
    private final Slider probabilitySlider;

    public GameView() {

        root = new BorderPane();
        root.setStyle("-fx-background-color: #111827;");

        canvas = new Canvas(850, 620);

        generationLabel = new Label("Поколение: 0");
        aliveLabel = new Label("Живых клеток: 0");
        statusLabel = new Label("Симуляция остановлена");

        startButton = new Button("▶ Старт");
        pauseButton = new Button("Ⅱ Пауза");
        stepButton = new Button("Шаг →");
        clearButton = new Button("Очистить");
        randomButton = new Button("Случайно");
        resizeButton = new Button("Применить");

        widthSpinner = new Spinner<>(3, 100, 30);
        heightSpinner = new Spinner<>(3, 100, 20);

        speedSlider = new Slider(1, 20, 5);
        probabilitySlider = new Slider(0, 100, 30);

        createTopPanel();
        createCenterPanel();
        createRightPanel();
        createBottomPanel();

        drawEmptyBoard();
    }

    private void createTopPanel() {

        Label title = new Label("CONWAY'S GAME OF LIFE");
        title.setStyle(
                "-fx-text-fill: #F9FAFB;" +
                        "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;"
        );

        Label subtitle = new Label("TOROIDAL CELLULAR AUTOMATON");
        subtitle.setStyle(
                "-fx-text-fill: #9CA3AF;" +
                        "-fx-font-size: 11px;"
        );

        VBox titleBox = new VBox(4, title, subtitle);

        generationLabel.setStyle(
                "-fx-text-fill: #F9FAFB;" +
                        "-fx-font-size: 14px;"
        );

        aliveLabel.setStyle(
                "-fx-text-fill: #4ADE80;" +
                        "-fx-font-size: 14px;"
        );

        VBox statistics = new VBox(7, generationLabel, aliveLabel);
        statistics.setAlignment(Pos.CENTER_RIGHT);

        HBox header = new HBox(titleBox, statistics);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        HBox.setHgrow(titleBox, Priority.ALWAYS);

        root.setTop(header);
    }

    private void createCenterPanel() {

        StackPane canvasContainer = new StackPane(canvas);

        canvasContainer.setStyle(
                "-fx-background-color: #1F2937;" +
                        "-fx-background-radius: 12px;"
        );

        canvasContainer.setPadding(new Insets(10));

        BorderPane.setMargin(canvasContainer, new Insets(0, 10, 10, 20));

        root.setCenter(canvasContainer);
    }

    private void createRightPanel() {

        VBox panel = new VBox(15);

        panel.setPadding(new Insets(20));
        panel.setPrefWidth(230);

        panel.setStyle(
                "-fx-background-color: #1F2937;" +
                        "-fx-background-radius: 12px;"
        );

        Label controlsTitle = createSectionTitle("УПРАВЛЕНИЕ");

        startButton.setMaxWidth(Double.MAX_VALUE);
        pauseButton.setMaxWidth(Double.MAX_VALUE);
        stepButton.setMaxWidth(Double.MAX_VALUE);

        styleButton(startButton, "#16A34A");
        styleButton(pauseButton, "#475569");
        styleButton(stepButton, "#2563EB");

        Label settingsTitle = createSectionTitle("НАСТРОЙКИ");

        Label widthLabel = createTextLabel("Ширина поля");
        Label heightLabel = createTextLabel("Высота поля");

        widthSpinner.setEditable(true);
        heightSpinner.setEditable(true);

        widthSpinner.setMaxWidth(Double.MAX_VALUE);
        heightSpinner.setMaxWidth(Double.MAX_VALUE);

        resizeButton.setMaxWidth(Double.MAX_VALUE);
        styleButton(resizeButton, "#475569");

        Label speedLabel = createTextLabel("Скорость симуляции");
        Label probabilityLabel = createTextLabel("Заполнение поля");

        configureSlider(speedSlider);
        configureSlider(probabilitySlider);

        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(5);

        probabilitySlider.setShowTickLabels(true);
        probabilitySlider.setMajorTickUnit(25);

        Label speedValue = createTextLabel("1–20 поколений/сек");
        Label probabilityValue = createTextLabel("Вероятность появления клетки");

        clearButton.setMaxWidth(Double.MAX_VALUE);
        randomButton.setMaxWidth(Double.MAX_VALUE);

        styleButton(clearButton, "#475569");
        styleButton(randomButton, "#475569");

        panel.getChildren().addAll(
                controlsTitle,
                startButton,
                pauseButton,
                stepButton,

                new Separator(),

                settingsTitle,
                widthLabel,
                widthSpinner,
                heightLabel,
                heightSpinner,
                resizeButton,

                new Separator(),

                speedLabel,
                speedSlider,
                speedValue,

                probabilityLabel,
                probabilitySlider,
                probabilityValue,

                new Separator(),

                randomButton,
                clearButton
        );

        BorderPane.setMargin(panel, new Insets(0, 20, 10, 0));

        root.setRight(panel);
    }

    private void createBottomPanel() {

        statusLabel.setStyle(
                "-fx-text-fill: #9CA3AF;" +
                        "-fx-font-size: 12px;"
        );

        HBox footer = new HBox(statusLabel);
        footer.setPadding(new Insets(10, 20, 15, 20));

        root.setBottom(footer);
    }

    private Label createSectionTitle(String text) {

        Label label = new Label(text);

        label.setStyle(
                "-fx-text-fill: #60A5FA;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;"
        );

        return label;
    }

    private Label createTextLabel(String text) {

        Label label = new Label(text);

        label.setStyle(
                "-fx-text-fill: #D1D5DB;" +
                        "-fx-font-size: 12px;"
        );

        return label;
    }

    private void styleButton(Button button, String color) {

        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-background-radius: 6px;" +
                        "-fx-padding: 10px;"
        );
    }

    private void configureSlider(Slider slider) {

        slider.setMaxWidth(Double.MAX_VALUE);
        slider.setBlockIncrement(1);
    }

    public void render(GameBoard board, int generation) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        gc.setFill(Color.web("#1F2937"));
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        int width = board.getWidth();
        int height = board.getHeight();

        double cellSize = Math.min(
                canvasWidth / width,
                canvasHeight / height
        );

        double offsetX = (canvasWidth - width * cellSize) / 2;
        double offsetY = (canvasHeight - height * cellSize) / 2;

        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {

                double x = offsetX + col * cellSize;
                double y = offsetY + row * cellSize;

                if (board.isAlive(row, col)) {
                    gc.setFill(Color.web("#4ADE80"));
                } else {
                    gc.setFill(Color.web("#374151"));
                }

                gc.fillRoundRect(
                        x + 1,
                        y + 1,
                        cellSize - 2,
                        cellSize - 2,
                        3,
                        3
                );
            }
        }

        generationLabel.setText("Поколение: " + generation);

        aliveLabel.setText(
                "Живых клеток: " + board.countAliveCells()
        );
    }

    public void drawEmptyBoard() {

        GameBoard emptyBoard = new GameBoard(30, 20);

        render(emptyBoard, 0);
    }

    public int[] getCellAt(
            double mouseX,
            double mouseY,
            GameBoard board
    ) {

        double cellSize = Math.min(
                canvas.getWidth() / board.getWidth(),
                canvas.getHeight() / board.getHeight()
        );

        double offsetX = (
                canvas.getWidth() - board.getWidth() * cellSize
        ) / 2;

        double offsetY = (
                canvas.getHeight() - board.getHeight() * cellSize
        ) / 2;

        int col = (int) ((mouseX - offsetX) / cellSize);
        int row = (int) ((mouseY - offsetY) / cellSize);

        if (row < 0 || row >= board.getHeight()
                || col < 0 || col >= board.getWidth()) {
            return null;
        }

        return new int[]{row, col};
    }

    public BorderPane getRoot() {
        return root;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public Button getStepButton() {
        return stepButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public Button getRandomButton() {
        return randomButton;
    }

    public Button getResizeButton() {
        return resizeButton;
    }

    public Spinner<Integer> getWidthSpinner() {
        return widthSpinner;
    }

    public Spinner<Integer> getHeightSpinner() {
        return heightSpinner;
    }

    public Slider getSpeedSlider() {
        return speedSlider;
    }

    public Slider getProbabilitySlider() {
        return probabilitySlider;
    }

    public void setStatus(String text) {
        statusLabel.setText(text);
    }
}
