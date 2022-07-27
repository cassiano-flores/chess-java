package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {   //the same constructor of ChessPiece class
        super(board, color);
    }

    @Override
    public String toString() {

        if (getColor() == Color.BLACK){
            return "♚";
        }
        else {
            return "♔";
        }
    }

    @Override //implement the abstract class from (ChessPiece -> Piece), just the King knows his possible moves
    public boolean[][] possibleMoves() {  //just to check, all the movements in a first moment is false
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }

    /*          if you cant execute the README command to set this UTF-8 symbols, trade for this
    @Override
    public String toString() {
        return "K";
    }
    */
}