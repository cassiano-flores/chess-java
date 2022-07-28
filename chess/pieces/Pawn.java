package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {   //the same constructor of ChessPiece class
        super(board, color);
    }

    @Override
    public String toString() {

        if (getColor() == Color.BLACK){
            return "♟";
        }
        else {
            return "♙";
        }
    }

    @Override   //implement the abstract class from (ChessPiece -> Piece), just the Rook knows his possible moves
    public boolean[][] possibleMoves() {  //just to check, all the movements in a first moment is false
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);  //just to help

        //white Pawn ----------------------------------------------------------------
        if (getColor() == Color.WHITE){
            p.setValues(position.getRow() - 1, position.getColumn());
            //the white pawn only can move one up, so its - 1 row

            //if the position exists and doesnt have a piece there, the pawn can move
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;  //set to true (can move)
            }
            //in his first movement, the pawn can move two spaces
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());

            //if the position exists and doesnt have a piece there (in the two positions)
            //and the move count = 0 (first movement) the pawn can move
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
                mat[p.getRow()][p.getColumn()] = true;  //set to true (can move)
            }

            //upper left diagonal
            p.setValues(position.getRow() - 1, position.getColumn() - 1);

            //when the white pawn upper diagonal have an opponent, he can move too
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;  //set to true (can move)
            }

            //upper right diagonal
            p.setValues(position.getRow() - 1, position.getColumn() + 1);

            //when the white pawn upper diagonal have an opponent, he can move too
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;  //set to true (can move)
            }
        }

        //black Pawn ----------------------------------------------------------------
        else{
            p.setValues(position.getRow() + 1, position.getColumn());
            //the black pawn only can move one down, so its + 1 row

            //if the position exists and doesnt have a piece there, the pawn can move
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;  //set to true (can move)
            }
            //in his first movement, the pawn can move two spaces
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());

            //if the position exists and doesnt have a piece there (in the two positions)
            //and the move count = 0 (first movement) the pawn can move
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
                mat[p.getRow()][p.getColumn()] = true;  //set to true (can move)
            }

            //lower left diagonal
            p.setValues(position.getRow() + 1, position.getColumn() - 1);

            //when the black pawn lower diagonal have an opponent, he can move too
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;  //set to true (can move)
            }

            //lower right diagonal
            p.setValues(position.getRow() + 1, position.getColumn() + 1);

            //when the black pawn lower diagonal have an opponent, he can move too
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;  //set to true (can move)
            }
        }

        return mat;
    }

    /*          if you cant execute the README command to set this UTF-8 symbols, trade for this
    @Override
    public String toString() {
        return "P";
    }
    */
}