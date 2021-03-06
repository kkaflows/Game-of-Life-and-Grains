package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;

import java.util.*;

public class GameLogic {


    int sizeOfCell;
    int rows, columns;
    int canvasHeight, canvasWidth;
    int grainsCount;
    public String choice;
    Boolean period = true;

    RadioButton periodRadioButton;

    Canvas canvas;

    public GraphicsContext graphicsContext;

    public Board board;
    public Drawing drawing;

    public GameLogic(int sizeOfCell, int canvasHeight, int canvasWidth, Canvas canvas, RadioButton period) {
        graphicsContext = canvas.getGraphicsContext2D();
        this.sizeOfCell = sizeOfCell;
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.rows = canvasHeight / sizeOfCell;
        this.columns = canvasWidth / sizeOfCell;

        board = new Board(rows, columns);
        drawing = new Drawing(graphicsContext, canvasHeight, canvasWidth, sizeOfCell);
        periodRadioButton = period;

    }

    public GameLogic(int sizeOfCell, int canvasHeight, int canvasWidth, Canvas canvas, int grainsCount, String choice, RadioButton period) {
        graphicsContext = canvas.getGraphicsContext2D();
        this.sizeOfCell = sizeOfCell;
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.rows = canvasHeight / sizeOfCell;
        this.columns = canvasWidth / sizeOfCell;
        this.grainsCount = grainsCount;
        this.choice = choice;

        board = new Board(rows, columns);
        drawing = new Drawing(graphicsContext, canvasHeight, canvasWidth, sizeOfCell, grainsCount);
        periodRadioButton = period;

//        randomGrains();

    }

    public void fillBoardStateString(int i, int j, String text) {
        board.board[i][j].setCellStateString(text);
    }

    public void fillBoardStateBoolean(int i, int j) {
        board.board[i][j].setCellStateBoolean(true);
    }

    public void fillBoardStateInt(int i, int j, int value) {
        board.board[i][j].setCellStateInt(value);
    }


    public Board calculateIterationGameOfLife() {
        Board newBoard = new Board(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                HashMap hashMap = new HashMap();
                hashMap.put("true", 0);
                hashMap.put("false", 0);
                int startX = i - 1;
                int startY = j - 1;
                int endX = i + 1;
                int endY = j + 1;

                int tmpX, tmpY;
                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        tmpX = x;
                        tmpY = y;
                        period = periodRadioButton.isSelected();
                        if (period) {
                            if (x == -1) tmpX = columns - 1;
                            if (x == columns) tmpX = 0;
                            if (y == -1) tmpY = rows - 1;
                            if (y == rows) tmpY = 0;
                        } else {
                            if (x == -1) tmpX = 0;
                            if (x == columns) tmpX = columns;
                            if (y == -1) tmpY = 0;
                            if (y == rows) tmpY = rows;
                        }

                        if (board.board[tmpX][tmpY].getCellStateBoolean() == true) {
                            int tmpValue = (int) hashMap.get("true");
                            hashMap.put("true", tmpValue + 1);
                        } else {
                            int tmpValue = (int) hashMap.get("false");
                            hashMap.put("false", tmpValue + 1);
                        }

                    }
                }


                boolean stateOfCell = board.board[i][j].cellStateBoolean;

                if (stateOfCell == true) {
                    int tmpValue = (int) hashMap.get("true");
                    hashMap.put("true", tmpValue - 1);
                } else {
                    int tmpValue = (int) hashMap.get("false");
                    hashMap.put("false", tmpValue - 1);
                }

                int trueValue = (int) hashMap.get("true");
                if (stateOfCell == true) {

                    if (trueValue < 2) {
                        newBoard.board[i][j].cellStateBoolean = false;
                    } else if (trueValue == 2 || trueValue == 3) {
                        newBoard.board[i][j].cellStateBoolean = true;
                    } else if (trueValue > 3) {
                        newBoard.board[i][j].cellStateBoolean = false;
                    }
                } else {
                    if (trueValue == 3) {
                        newBoard.board[i][j].cellStateBoolean = true;
                    } else {
                        newBoard.board[i][j].cellStateBoolean = false;
                    }
                }


            }
        }

        return newBoard;

    }


    public Board calculateIterationGrains() {
        Board newBoard = new Board(rows, columns);
        Random random = new Random();
//        newBoard = board;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                if (board.board[i][j].cellStateString != "") {
                    newBoard.board[i][j].cellStateString = board.board[i][j].cellStateString;
                    switch (choice) {

                        case "Grains - Moore": {
                            mooreSurroundingFill(board, newBoard, i, j);
                            break;
                        }
                        case "Grains - Von Neumann": {
                            vonNeumannSurrounding(board, newBoard, i, j);
                            break;
                        }
                        case "Grains - Hexagonal left": {
                            hexagonalLeftSurrounding(board, newBoard, i, j);
                            break;
                        }
                        case "Grains - Hexagonal right": {
                            hexagonalRightSurrounding(board, newBoard, i, j);
                            break;
                        }
                        case "Grains - Hexagonal random": {
                            hexagonalRandomSurrounding(board, newBoard, i, j);
                            break;
                        }

                        case "Grains - Pentagonal up": {
                            pentagonalUpSurrounding(board, newBoard, i, j);
                            break;
                        }
                        case "Grains - Pentagonal down": {
                            pentagonalDownSurrounding(board, newBoard, i, j);
                            break;
                        }
                        case "Grains - Pentagonal left": {
                            pentagonalLeftSurrounding(board, newBoard, i, j);
                            break;
                        }
                        case "Grains - Pentagonal right": {
                            pentagonalRightSurrounding(board, newBoard, i, j);
                            break;
                        }
                        case "Grains - Pentagonal random": {
                            pentagonalRandomSurrounding(board, newBoard, i, j);
                            break;
                        }

                        default: {
                            vonNeumannSurrounding(board, newBoard, i, j);
                            break;
                        }
                    }


                }

            }
        }


        return newBoard;
    }

    private void pentagonalRandomSurrounding(Board board, Board newBoard, int i, int j) {
        Random random = new Random();
        int choice = random.nextInt(4);
        switch (choice) {
            case 0: {
                pentagonalUpSurrounding(board, newBoard, i, j);
                break;
            }
            case 1: {
                pentagonalDownSurrounding(board, newBoard, i, j);
                break;
            }
            case 2: {
                pentagonalLeftSurrounding(board, newBoard, i, j);
                break;
            }
            case 3: {
                pentagonalRightSurrounding(board, newBoard, i, j);
                break;
            }
        }
    }

    private void pentagonalRightSurrounding(Board board, Board newBoard, int i, int j) {

        int startX = i - 1;
        int startY = j - 1;
        int endX = i + 1;
        int endY = j + 1;

        String grainName = board.board[i][j].cellStateString;

        int tmpX, tmpY;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (((x == i - 1) && (y == j - 1)) || (x == i - 1) && (y == j) || (x == i - 1) && (y == j + 1)) {
                } else {
                    fillNewBoard(board, newBoard, grainName, x, y);
                }
            }
        }
    }

    private void fillNewBoard(Board board, Board newBoard, String grainName, int x, int y) {
        int tmpX;
        int tmpY;
        tmpX = x;
        tmpY = y;
        period = periodRadioButton.isSelected();
//        System.out.println("BC period = " + period);
        if (period) {
            if (x == -1) tmpX = columns - 1;
            if (x == columns) tmpX = 0;
            if (y == -1) tmpY = rows - 1;
            if (y == rows) tmpY = 0;
        } else {
            if (x == -1) tmpX = 0;
            if (x == columns) tmpX = columns - 1;
            if (y == -1) tmpY = 0;
            if (y == rows) tmpY = rows - 1;
        }
        if (board.board[tmpX][tmpY].cellStateString == "" && newBoard.board[tmpX][tmpY].cellStateString == "")

            newBoard.board[tmpX][tmpY].cellStateString = grainName;
    }

    private void pentagonalLeftSurrounding(Board board, Board newBoard, int i, int j) {


        int startX = i - 1;
        int startY = j - 1;
        int endX = i + 1;
        int endY = j + 1;

        String grainName = board.board[i][j].cellStateString;

        int tmpX, tmpY;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (((x == i + 1) && (y == j - 1)) || (x == i + 1) && (y == j) || (x == i + 1) && (y == j + 1)) {
                } else {
                    fillNewBoard(board, newBoard, grainName, x, y);
                }
            }
        }
    }

    private void hexagonalRandomSurrounding(Board board, Board newBoard, int i, int j) {
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        if (randomNumber % 2 == 1) {
            hexagonalLeftSurrounding(board, newBoard, i, j);
        } else {
            hexagonalRightSurrounding(board, newBoard, i, j);
        }
    }

    private void mooreSurroundingFill(Board board, Board newBoard, int i, int j) {
        int startX = i - 1;
        int startY = j - 1;
        int endX = i + 1;
        int endY = j + 1;

        String grainName = board.board[i][j].cellStateString;

        int tmpX, tmpY;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                fillNewBoard(board, newBoard, grainName, x, y);

            }
        }

    }

    private void pentagonalDownSurrounding(Board board, Board newBoard, int i, int j) {


        int startX = i - 1;
        int startY = j - 1;
        int endX = i + 1;
        int endY = j + 1;

        String grainName = board.board[i][j].cellStateString;

        int tmpX, tmpY;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (((x == i - 1) && (y == j - 1)) || (x == i) && (y == j - 1) || (x == i + 1) && (y == j - 1)) {
                } else {
                    fillNewBoard(board, newBoard, grainName, x, y);
                }
            }
        }

    }


    private void pentagonalUpSurrounding(Board board, Board newBoard, int i, int j) {

        int startX = i - 1;
        int startY = j - 1;
        int endX = i + 1;
        int endY = j + 1;

        String grainName = board.board[i][j].cellStateString;

        int tmpX, tmpY;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (((x == i - 1) && (y == j + 1)) || (x == i) && (y == j + 1) || (x == i + 1) && (y == j + 1)) {
                } else {
                    fillNewBoard(board, newBoard, grainName, x, y);
                }
            }
        }

    }


    private void hexagonalRightSurrounding(Board board, Board newBoard, int i, int j) {
        int startX = i - 1;
        int startY = j - 1;
        int endX = i + 1;
        int endY = j + 1;

        String grainName = board.board[i][j].cellStateString;

        int tmpX, tmpY;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (((x == i - 1) && (y == j - 1)) || (x == i + 1) && (y == j + 1)) {
                } else {
                    fillNewBoard(board, newBoard, grainName, x, y);
                }
            }
        }

    }

    private void hexagonalLeftSurrounding(Board board, Board newBoard, int i, int j) {
        int startX = i - 1;
        int startY = j - 1;
        int endX = i + 1;
        int endY = j + 1;

        String grainName = board.board[i][j].cellStateString;

        int tmpX, tmpY;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (((x == i - 1) && (y == j + 1)) || (x == i + 1) && (y == j - 1)) {
                } else {
                    fillNewBoard(board, newBoard, grainName, x, y);
                }
            }
        }
    }

    private void vonNeumannSurrounding(Board board, Board newBoard, int i, int j) {

        int leftX = i;
        int leftY = j - 1;
        int upX = i - 1;
        int upY = j;
        int rightX = i;
        int rightY = j + 1;
        int downX = i + 1;
        int downY = j;
        String grainName = board.board[i][j].cellStateString;
        period = periodRadioButton.isSelected();
        if (period) {
            if (leftY == -1) leftY = rows - 1;
            if (leftY == rows) leftY = 0;
            if (upX == -1) upX = columns - 1;
            if (upX == columns) upX = 0;
            if (rightY == rows) rightY = 0;
            if (rightY == -1) rightY = rows - 1;
            if (downX == -1) downX = columns - 1;
            if (downX == columns) downX = 0;

        } else {
            if (leftY == -1) leftY = 0;
            if (leftY == rows) leftY = rows - 1;
            if (upX == -1) upX = 0;
            if (upX == columns) upX = columns - 1;
            if (rightY == rows) rightY = rows - 1;
            if (rightY == -1) rightY = 0;
            if (downX == -1) downX = 0;
            if (downX == columns) downX = columns - 1;
        }

        String grainNameTmp = board.board[leftX][leftY].cellStateString;
        if (grainNameTmp == "") {
            newBoard.board[leftX][leftY].cellStateString = grainName;
        }
        grainNameTmp = board.board[upX][upY].cellStateString;
        if (grainNameTmp == "") {
            newBoard.board[upX][upY].cellStateString = grainName;
        }
        grainNameTmp = board.board[rightX][rightY].cellStateString;
        if (grainNameTmp == "") {
            newBoard.board[rightX][rightY].cellStateString = grainName;
        }

        grainNameTmp = board.board[downX][downY].cellStateString;
        if (grainNameTmp == "") {
            newBoard.board[downX][downY].cellStateString = grainName;
        }

    }

    public void randomGrains() {
        String grainName = "grain";
        Random random = new Random();
        System.out.println("rows = " + rows);
        System.out.println("columns = " + columns);
        for (int i = 1; i <= grainsCount; i++) {
            int x = Math.abs(random.nextInt() % (rows - 2) + 1);
            int y = Math.abs(random.nextInt() % (columns - 2) + 1);
            System.out.println("x = " + x + "y = " + y);
            board.board[x][y].cellStateString = grainName + i;
            System.out.println(board.board[x][y].cellStateString);

        }
        drawing.drawBoardString(board);
    }

    public void randomGrainsRadius(int radius) {
        String grainName = "grain";
        Random random = new Random();
        System.out.println("rows = " + rows);
        System.out.println("columns = " + columns);

        Boolean aboveRadius = false;

        int[][] grainCoordinates = new int[grainsCount][2];
        int x = Math.abs(random.nextInt() % (rows - 2) + 1);
        int y = Math.abs(random.nextInt() % (columns - 2) + 1);
        board.board[x][y].cellStateString = grainName + 1;
        grainCoordinates[0][0] = x;
        grainCoordinates[0][1] = y;

        System.out.println("x = " + grainCoordinates[0][0] + " y = " + grainCoordinates[0][1]);

        for (int i = 0; i < grainsCount - 1; i++) {
            x = Math.abs(random.nextInt() % (rows - 2) + 1);
            y = Math.abs(random.nextInt() % (columns - 2) + 1);
            System.out.println("x = " + x + " y = " + y);
            if (board.board[x][y].cellStateString == "") {
                aboveRadius = checkRadius(radius, grainCoordinates, x, y, i);
                System.out.println("aboveRadius = " + aboveRadius);
                if (aboveRadius) {
                    board.board[x][y].cellStateString = grainName + (i + 2);
                    grainCoordinates[i + 1][0] = x;
                    grainCoordinates[i + 1][1] = y;
                    System.out.println("inside aboveRadius");
                } else {
                    i--;
                }
            } else {
                i--;
            }
        }


        drawing.drawBoardString(board);
    }


    public void randomGrainsEvenlySpaced() {


        int product = rows * columns;
        int distance = product / grainsCount;
        int k = 0;
        int grainNumber = 1;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (k % distance == 0 && k < product) {
                    board.board[i][j].cellStateString = "grain" + grainNumber;
                    grainNumber++;
                    System.out.println("grainNumber = " + grainNumber);
                }
                k++;
            }
        }

        drawing.drawBoardString(board);


    }

    private Boolean checkRadius(int radius, int[][] grainCoordinates, int x, int y, int i) {
        Boolean aboveRadius = false;
        for (int j = 0; j <= i; j++) {
            double distance = (Math.pow(x - grainCoordinates[j][0], 2) + Math.pow(y - grainCoordinates[j][1], 2));
            distance = Math.sqrt(distance);
            System.out.println("distance = " + distance);
            if (distance > radius) {
                aboveRadius = true;
            } else {
                aboveRadius = false;
                return aboveRadius;
            }
        }
        return aboveRadius;
    }


    public Board monteCarloWholeBoard() {
        Board newBoard = new Board(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                int energyCenter = calculateEnergy(i, j);
                System.out.println("energyCenter = " + energyCenter);
                if (energyCenter >= 5) {
                    System.out.println("DO NOTHING");
                    newBoard.board[i][j].cellStateString = board.board[i][j].cellStateString;
                } else {
                    List maxSurroundings = getListOfSurroundingsOfCell(i, j);

                    newBoard.board[i][j].cellStateString = (String) maxSurroundings.get(0);
                }
            }
        }
        return newBoard;
    }

    public void monteCarloRandomShots() {
        int shots = 100;
        Random random = new Random();

        for (int k = 0; k < shots; k++) {
            int i = random.nextInt(rows);
            int j = random.nextInt(columns);
            int energyCenter = calculateEnergy(i, j);
            System.out.println("energyCenter = " + energyCenter);
            if (energyCenter >= 5) {
                System.out.println("DO NOTHING");
            } else {
                List maxSurroundings = getListOfSurroundingsOfCell(i, j);
                board.board[i][j].cellStateString = (String) maxSurroundings.get(0);
            }
            drawing.drawBoardString(board);
        }
    }

    private List getListOfSurroundingsOfCell(int i, int j) {
        HashMap hashMap = new HashMap();

        for (int startX = i - 1; startX <= i + 1; startX++) {
            for (int startY = j - 1; startY <= j + 1; startY++) {
                int tmpX = startX;
                int tmpY = startY;
                if (period) {
                    if (tmpX == -1) tmpX = columns - 1;
                    if (tmpX == columns) tmpX = 0;
                    if (tmpY == -1) tmpY = rows - 1;
                    if (tmpY == rows) tmpY = 0;
                } else {
                    if (tmpX == -1) tmpX = 0;
                    if (tmpX == columns) tmpX = columns - 1;
                    if (tmpY == -1) tmpY = 0;
                    if (tmpY == rows) tmpY = rows - 1;
                }
                int valueFromHashMap;

                String cellStateString = board.board[tmpX][tmpY].cellStateString;
                if (hashMap.get(cellStateString) != null) {
                    valueFromHashMap = (int) hashMap.get(cellStateString);
                    hashMap.put(cellStateString, valueFromHashMap + 1);
                } else {
                    hashMap.put(cellStateString, 1);
                }
            }

        }
        int valueFromHashMap = (int) hashMap.get(board.board[i][j].cellStateString);
        hashMap.put(board.board[i][j].cellStateString, valueFromHashMap - 1);

        List maxSurroundings = getHighestVoteList(hashMap);
        Collections.shuffle(maxSurroundings);
        System.out.println("Print maxList-------------------------------------");
        System.out.println(maxSurroundings);
        System.out.println(maxSurroundings.size());
        System.out.println("end maxList -------------------------------------");
        System.out.println();
        return maxSurroundings;
    }

    private int calculateEnergy(int x, int y) {
        int sameCellCount = 0;
        for (int startX = x - 1; startX <= x + 1; startX++) {
            for (int startY = y - 1; startY <= y + 1; startY++) {

                int tmpX = startX;
                int tmpY = startY;
                if (period) {
                    if (tmpX == -1) tmpX = columns - 1;
                    if (tmpX == columns) tmpX = 0;
                    if (tmpY == -1) tmpY = rows - 1;
                    if (tmpY == rows) tmpY = 0;
                } else {
                    if (tmpX == -1) tmpX = 0;
                    if (tmpX == columns) tmpX = columns - 1;
                    if (tmpY == -1) tmpY = 0;
                    if (tmpY == rows) tmpY = rows - 1;
                }

                if (board.board[tmpX][tmpY].cellStateString == board.board[x][y].cellStateString) {
                    sameCellCount++;
                }
                System.out.println("x = " + tmpX + "y = " + tmpY);

            }
        }
        sameCellCount--;
        return sameCellCount;

    }

    public void randomMonteCarloGrains(){
        List<String> grainsList = new ArrayList<>();
        List<String> grainsListCopy = new ArrayList<>();
        for (int i = 1; i <= grainsCount; i++) {
            grainsList.add("grain"+i);
        }
        grainsListCopy = grainsList;
        Collections.shuffle(grainsListCopy);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(grainsList.size()>0){
                    board.board[i][j].cellStateString = grainsList.get(0);
                    grainsListCopy.remove(0);
                    System.out.println(grainsList.size());
                }else{
                    for (int k = 1;k < grainsCount; k++) {
                        grainsList.add("grain"+k);
                    }
                    Collections.shuffle(grainsList);
                    board.board[i][j].cellStateString = grainsList.get(0);
                    grainsListCopy.remove(0);
                    System.out.println(grainsList.size());
                }
            }
        }
        drawing.drawBoardString(board);
    }


    public String getHighestVote(HashMap<String, Integer> map) {
        String highestMap = "";
        int highestVote = -1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > highestVote) {
                highestMap = entry.getKey();
                highestVote = entry.getValue();
            }
        }
        return highestMap;
    }

    public List<String> getHighestVoteList(HashMap<String, Integer> map) {
        String highestMap = "";
        int highestVote = -1;
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > highestVote) {
                highestMap = entry.getKey();
                highestVote = entry.getValue();
            }
        }
        if (highestVote > -1) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() == highestVote) {
                    highestMap = entry.getKey();
                    list.add(highestMap);
                }
            }
        } else {
            list.add("");
        }

        return list;
    }
}
