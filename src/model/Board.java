package model;


public class Board {


    public Cell[][] board;
    int rows, columns;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        board = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new Cell();
            }
        }
        clearBoard();
//        System.out.println("cellState = "+board[0][0].cellStateBoolean);

    }

    public void clearBoard(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j].fillCell("", false, 0);
            }
        }
    }






}
