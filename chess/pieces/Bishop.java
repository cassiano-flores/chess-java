package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {   //the same constructor of ChessPiece class
        super(board, color);
    }

    @Override
    public String toString() {

        if (getColor() == Color.BLACK){
            return "♝";
        }
        else {
            return "♗";
        }
    }

    @Override //implement the abstract class from (ChessPiece -> Piece), just the Rook knows his possible moves
    public boolean[][] possibleMoves() {  //just to check, all the movements in a first moment is false
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);  //just to help

        //upper left diagonal --------------------------------------------------------------------
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        //row - 1 because up in the matrix, the index of the row is decreasing

        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //check the position
            
            mat[p.getRow()][p.getColumn()] = true;  //set to true
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //check the position
            mat[p.getRow()][p.getColumn()] = true;  //set to true
        }

        //upper right diagonal --------------------------------------------------------------------
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        //row - 1 because up in the matrix, the index of the row is decreasing

        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //check the position
            
            mat[p.getRow()][p.getColumn()] = true;  //set to true
            p.setValues(p.getRow() - 1,  p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //check the position
            mat[p.getRow()][p.getColumn()] = true;  //set to true
        }

        //lower left diagonal --------------------------------------------------------------------
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        //row - 1 because up in the matrix, the index of the row is decreasing

        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //check the position
            
            mat[p.getRow()][p.getColumn()] = true;  //set to true
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //check the position
            mat[p.getRow()][p.getColumn()] = true;  //set to true
        }

        //lower right diagonal --------------------------------------------------------------------
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        //row - 1 because up in the matrix, the index of the row is decreasing

        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //check the position
            
            mat[p.getRow()][p.getColumn()] = true;  //set to true
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  //check the position
            mat[p.getRow()][p.getColumn()] = true;  //set to true
        }

        return mat;
    }

    /*          if you cant execute the README command to set this UTF-8 symbols, trade for this
    @Override
    public String toString() {
        return "B";
    }
    */
}