package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece{ //chess is a layer higher than the board,
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