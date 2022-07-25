package boardgame;

public class Board {

    private int rows;
    private int colums;
    private Piece[][] pieces;

    public Piece piece(int row, int column){  //method 1 just with row and column attribute

        return pieces[row][column];
    }

    public Piece piece(Position position){  //method 2 with Row and Column of Position

        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position){ //method 3 defines the Row and Column of Position
                                                            //with a new Piece, and a new Position to Piece

        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Board(int rows, int colums) {
        this.rows = rows;
        this.colums = colums;
        pieces = new Piece[rows][colums];  //constructor with pieces from Piece
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColums() {
        return colums;
    }

    public void setColums(int colums) {
        this.colums = colums;
    }
}