package boardgame;

public class Piece {
    
    protected Position position;    //protected attribute
    private Board board;     //a Board attribute type

    public Piece(Board board) {
        this.board = board;
        position = null;      //position starts without a value
    }

    protected Board getBoard() { //must only be accessed within the board package
        return board;            //and by the subclass of pieces
    }    
}