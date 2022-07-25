package boardgame;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Piece piece(int row, int column){  //method 1 just with row and column attribute

        if (!positionExists(row, column)){  //verify if really have a piece to return
            throw new BoardException("Position not on the board");
        }

        return pieces[row][column];
    }

    public Piece piece(Position position){  //method 2 with Row and Column of Position

        if (!positionExists(position)){   //verify if really have a piece to return
            throw new BoardException("Position not on the board");
        }

        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position){ //method 3 defines the Row and Column of Position
                                                            //with a new Piece, and a new Position to Piece

        if (thereIsAPiece(position)){  //we cant put a piece if already there is a piece in this position
            throw new BoardException("There is already a piece on position " + position);
        }

        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    private boolean positionExists(int row, int column){
        return row >= 0 && row < rows && column >= 0 && column < columns;
        //this is the conditional to exists a position
        //this method needs to came first
    }

    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());
        //getRow and getColumns to test with the method above
    }

    public boolean thereIsAPiece(Position position){

        if (!positionExists(position)){  //verify if really have a piece to return
            throw new BoardException("Position not on the board");
        }

        return piece(position) != null;  //if in this position has piece not null, so there is a piece
    }

    public Board(int rows, int columns) {

        if (rows < 1 || columns < 1){
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
        }   //this is an exception

        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];  //constructor with pieces from Piece
    }

    public int getRows() {
        return rows;
    }

    /* dont need this
    public void setRows(int rows) {
        this.rows = rows;
    }
    */

    public int getColumns() {
        return columns;
    }

    
    /* dont need this
    public void setColumns(int columns) {
        this.columns = columns;
    }
    */
}