package model;

public class Cell {

    public String cellStateString;             //do opisu kolorow itd
    public Boolean cellStateBoolean;           //do gry w zycie
    public int cellStateInt;                   //wartosc liczbowa, na przyszlosc


    public Cell() {
        cellStateBoolean = false;
        cellStateInt = 0;
        cellStateString = "";
    }

    public void fillCell(String str, Boolean bool, int number) {
       cellStateString = str;
       cellStateInt = number;
       cellStateBoolean = bool;
    }


    public String getCellStateString() {
        return cellStateString;
    }

    public void setCellStateString(String cellStateString) {
        this.cellStateString = cellStateString;
    }

    public Boolean getCellStateBoolean() {
        return cellStateBoolean;
    }

    public void setCellStateBoolean(Boolean cellStateBoolean) {
        this.cellStateBoolean = cellStateBoolean;
    }

    public int getCellStateInt() {
        return cellStateInt;
    }

    public void setCellStateInt(int cellStateInt) {
        this.cellStateInt = cellStateInt;
    }
}
