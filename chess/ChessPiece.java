package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

//abstract class because extends abstract class Piece (and ChessPiece is also too much generic to implement moves rules)
public abstract class ChessPiece extends Piece{ //chess is a layer higher than the board,
                                       //and therefore pulls the respective classes
    private Color color;
    private int moveCount;  //count movements

    public ChessPiece(Board board, Color color) {
        super(board);  //this.rows = rows | this.colums = colums | pieces = new Piece[rows][colums] | + color
        this.color = color;
    }

    public Color getColor() {   //getColor
        return color;
    }
    
    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount(){  //just increase +1 to move count
        moveCount++;
    }

    public void decreaseMoveCount(){  //just decrease -1 to move count
        moveCount--;
    }

    public ChessPosition getChessPosition(){      //return the chess position from matrix position
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position){  //verify if there is an opponent piece in this position

        ChessPiece piece = (ChessPiece) getBoard().piece(position);  //piece receives a possible Opponent piece
        return piece != null && piece.getColor() != color;  //verify if pieces is really an opponent
                                                        //if piece isnt null and a different color, is an opponent

    }
}