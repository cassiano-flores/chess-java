package boardgame;

public abstract class Piece {
    
    protected Position position;    //protected attribute
    private Board board;     //a Board attribute type

    public Piece(Board board) {
        this.board = board;
        position = null;      //position starts without a value
    }

    protected Board getBoard() { //must only be accessed within the board package
        return board;            //and by the subclass of pieces
    }

    public abstract boolean[][] possibleMoves(); //show the possibleMoves in a matrix format
                                                 //false = position not possible | true = position possible

    public boolean possibleMove(Position position){  //like a interface
        return possibleMoves()[position.getRow()][position.getColumn()];
        //a normal method that calls a abstract method
        //will only make sense when there is a concrete implementation for possibleMoves (the abstract one)
    }

    public boolean isThereAnyPossibleMove(){  //verify if there is at least one possible move

        boolean[][] mat = possibleMoves();   //another normal method that calls a abstract method

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j] == true){   //if this position is true, so return true
                    return true;
                }
            }
        }
        return false;  //no one position true, return false
    }
}