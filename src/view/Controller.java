package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.GameLogic;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Controller {

    int canvasHeight, canvasWidth;
    int sizeOfCell;
    int iterationCount;
    int grainsCount;
    Thread thread;

    int i = 1;

    @FXML
    TextField iterationCountTextField;

    @FXML
    TextField grainsCountTextField;

    @FXML
    TextField radiusTextField;

    @FXML
    Canvas canvas;

    @FXML
    TextField sizeOfCellTextField;

    @FXML
    RadioButton gameOfLifeRadioButton;

    @FXML
    RadioButton grainsRadioButton;

    @FXML
    RadioButton periodRadioButton;

    @FXML
    ChoiceBox choiceBox;

    public GraphicsContext graphicsContext;

    GameLogic gameLogic;


    @FXML
    private void initialize() {
        System.out.println("initialize start");
        graphicsContext = canvas.getGraphicsContext2D();
        sizeOfCellTextField.setText("20");
        grainsCountTextField.setText("0");
        iterationCountTextField.setText("1");
        choiceBox.setItems(FXCollections.observableArrayList(
                "Grains - Moore",
                "Grains - Von Neumann",
                "Grains - Hexagonal left",
                "Grains - Hexagonal right",
                "Grains - Hexagonal random",
                "Grains - Pentagonal up",
                "Grains - Pentagonal down",
                "Grains - Pentagonal left",
                "Grains - Pentagonal right",
                "Grains - Pentagonal random"));


    }

    @FXML
    public void handleInitializeButton() {


        sizeOfCell = Integer.parseInt(sizeOfCellTextField.getText());
        canvasHeight = (int) canvas.getHeight();
        canvasWidth = (int) canvas.getWidth();
        if (grainsRadioButton.isSelected()) {
            String choice = (String) choiceBox.getValue();
            grainsCount = Integer.parseInt(grainsCountTextField.getText());
            gameLogic = new GameLogic(sizeOfCell, canvasHeight, canvasWidth, canvas, grainsCount, choice, periodRadioButton);
            gameLogic.drawing.clearBoard();
            gameLogic.drawing.drawBoardString(gameLogic.board);

        } else if (gameOfLifeRadioButton.isSelected()) {
            gameLogic = new GameLogic(sizeOfCell, canvasHeight, canvasWidth, canvas,periodRadioButton);
            gameLogic.drawing.clearBoard();
            gameLogic.drawing.drawBoard(gameLogic.board);

        }

        drawOnCanvas();

        System.out.println("initialisation ok");
        System.out.println("size of cell = " + sizeOfCell);
        System.out.println("canvasHeight = " + canvasHeight + " canvasWidth = " + canvasWidth);

    }

    @FXML
    void handleStartSimulationButton() throws InterruptedException {

        thread = new Thread(() -> {
            iterationCount = Integer.parseInt(iterationCountTextField.getText());
            System.out.println("iterationCount" + iterationCount);
            for (int i = 0; i < iterationCount; i++) {

                Platform.runLater(() -> startFunction());

                try {
                    thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end of iteration" + i);
            }
        });

        thread.start();

    }

    private void startFunction() {

        if (gameOfLifeRadioButton.isSelected()) {
            gameLogic.board = gameLogic.calculateIterationGameOfLife();
            gameLogic.drawing.drawBoard(gameLogic.board);
        }
        if (grainsRadioButton.isSelected()) {
            gameLogic.board = gameLogic.calculateIterationGrains();
            gameLogic.drawing.drawBoardString(gameLogic.board);
        }
    }


    public void drawOnCanvas() {
//        initDraw();
        i = 1;

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();

                int xPosition = (int) (x / sizeOfCell);
                int yPosition = (int) (y / sizeOfCell);

                gameLogic.fillBoardStateBoolean(xPosition, yPosition);
                gameLogic.fillBoardStateString(xPosition, yPosition, "grain" + i);
                gameLogic.fillBoardStateInt(xPosition, yPosition, 1);
                System.out.println("state of cell = " + xPosition + " " + yPosition + "  " + gameLogic.board.board[xPosition][yPosition].cellStateString);
                i++;
                gameLogic.drawing.drawBoard(gameLogic.board);
            }
        });
    }
    @FXML
    public void handleRandomGrains(){
        gameLogic.randomGrains();
    }
    @FXML
    public void handleRandomGrainsRadius(){
        int radius = Integer.parseInt(radiusTextField.getText());
        System.out.println("radius = "+radius);
        gameLogic.randomGrainsRadius(radius);
    }


}
