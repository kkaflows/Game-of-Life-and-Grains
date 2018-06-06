package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Random;

public class Drawing {

    private GraphicsContext graphicsContext;
    int canvasHeight, canvasWidth;
    int sizeOfCell;
    int grainCount;

    HashMap<String, Color> colorHashMap;


    public Drawing(GraphicsContext graphicsContext, int canvasHeight, int canvasWidth, int sizeOfCell) {
        this.graphicsContext = graphicsContext;
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.sizeOfCell = sizeOfCell;
    }

    public Drawing(GraphicsContext graphicsContext, int canvasHeight, int canvasWidth, int sizeOfCell, int grainCount) {
        this.graphicsContext = graphicsContext;
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.sizeOfCell = sizeOfCell;
        this.grainCount = grainCount;

        randomColors();
    }

    public void initDraw() {

        graphicsContext.setFill(Color.WHITE);
        for (int i = 0; i < canvasHeight; i++) {
            for (int j = 0; j < canvasWidth; j++) {
                graphicsContext.fillOval(i,j, 10,10);
            }
        }

        graphicsContext.setFill(Color.BLACK);
        for (int i = 0; i < canvasHeight; i += sizeOfCell) {
            graphicsContext.moveTo(i, 0);
            graphicsContext.lineTo(i, canvasHeight);
            graphicsContext.stroke();

        }
        for (int i = 0; i < canvasWidth; i += sizeOfCell) {
            graphicsContext.moveTo(i, 0);
            graphicsContext.lineTo(i, canvasWidth);
            graphicsContext.stroke();

        }



    }

    public void drawBoard(Board board) {


        for (int i = 0; i < canvasHeight/sizeOfCell; i++) {
            for (int j = 0; j < canvasWidth/sizeOfCell; j++) {
                if (board.board[i][j].getCellStateBoolean() == true) {
                    graphicsContext.setFill(Color.RED);
                    graphicsContext.fillRoundRect(i * sizeOfCell, j * sizeOfCell, sizeOfCell, sizeOfCell, sizeOfCell, sizeOfCell);
                }
                if (board.board[i][j].getCellStateBoolean() == false) {
                    graphicsContext.setFill(Color.WHITE);
                    graphicsContext.fillRoundRect(i * sizeOfCell, j * sizeOfCell, sizeOfCell, sizeOfCell, sizeOfCell, sizeOfCell);
                }

            }
        }

    }

    public void drawBoardString(Board board){
        for (int i = 0; i < canvasHeight/sizeOfCell; i++) {
            for (int j = 0; j < canvasWidth/sizeOfCell; j++) {
                String key = board.board[i][j].cellStateString;

                graphicsContext.setFill(colorHashMap.get(key));

//                graphicsContext.fillRoundRect(i * sizeOfCell, j * sizeOfCell, sizeOfCell, sizeOfCell, sizeOfCell, sizeOfCell);
                graphicsContext.fillRect(i * sizeOfCell, j * sizeOfCell,sizeOfCell, sizeOfCell);
            }
        }
    }

    public void randomColors(){
        Random random = new Random();
        colorHashMap = new HashMap<String, Color>();
        for (int i = 1; i <= grainCount; i++) {
            int r = Math.abs(random.nextInt()%255);
            int g = Math.abs(random.nextInt()%255);
            int b = Math.abs(random.nextInt()%255);
            Color color = Color.rgb(r,g,b);
            String name = "grain"+i;
            colorHashMap.put(name, color);
        }
        colorHashMap.put("", Color.WHITE);
    }

    public void addOnClickGrainColor(int grainCount){
        Random random = new Random();
        int r = Math.abs(random.nextInt()%255);
        int g = Math.abs(random.nextInt()%255);
        int b = Math.abs(random.nextInt()%255);
        Color color = Color.rgb(r,g,b);
        String name = "grain"+grainCount;
        colorHashMap.put(name, color);
    }

    public void clearBoard(){
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, canvasHeight, canvasWidth);

    }
}
