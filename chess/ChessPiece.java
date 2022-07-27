package chess;

import boardgame.Board;
import boardgame.Piece;

//abstract class because extends abstract class Piece (and ChessPiece is also too much generic to implement moves rules)
public abstract class ChessPiece extends Piece{ //chess is a layer higher than the board,
                                       //and therefore pulls the respective classes
    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);  //this.rows = rows | this.colums = colums | pieces = new Piece[rows][colums] | + color
        this.color = color;
    }

    public Color getColor() {   //getColor
        return color;
    }
}