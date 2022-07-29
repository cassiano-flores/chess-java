package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {   //the same constructor of ChessPiece class
        super(board, color);
    }

    @Override
    public String toString() {

        if (getColor() == Color.BLACK){
            return "♞";
        }
        else {
            return "♘";
        }
    }

    private boolean canMove(Position position){  //verify if the Knight can move to a position

        ChessPiece p = (ChessPiece) getBoard().piece(position); //p receives a possible position to move
        return p == null || p.getColor() != getColor();   //verify if p is really a possible position
    ///if p is null (free position) or in this position have a piece with different color (an opponent), is an possible position
    }

    @Override //implement the abstract class from (ChessPiece -> Piece), just the Rook knows his possible moves
    public boolean[][] possibleMoves() {  //just to check, all the movements in a first moment is false
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);  //just to help

        //above left -------------------------------------------------------------------------
        p.setValues(position.getRow() - 2, position.getColumn() - 1);
        //starting from above, go up 2 and 1 to the left

        if (getBoard().positionExists(p) && canMove(p)){  //if the position above left exists and the Knight can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //above right -------------------------------------------------------------------------
        p.setValues(position.getRow() - 2, position.getColumn() + 1);
        //starting from above, go up 2 and 1 to the right

        if (getBoard().positionExists(p) && canMove(p)){  //if the position above right exists and the Knight can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //left above -------------------------------------------------------------------------
        p.setValues(position.getRow() - 1, position.getColumn() - 2);
        //starting from the left, go 2 to the left and go up 1

        if (getBoard().positionExists(p) && canMove(p)){  //if the position left above exists and the Knight can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //left below -------------------------------------------------------------------------
        p.setValues(position.getRow() + 1, position.getColumn() - 2);
        //starting from the left, go 2 to the left and go down 1

        if (getBoard().positionExists(p) && canMove(p)){  //if the position left below exists and the Knight can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //below right -------------------------------------------------------------------------
        p.setValues(position.getRow() + 2, position.getColumn() + 1);
        //starting from below, go 2 down and 1 to the right

        if (getBoard().positionExists(p) && canMove(p)){  //if the position below right exists and the Knight can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //below left -------------------------------------------------------------------------
        p.setValues(position.getRow() + 2, position.getColumn() - 1);
        //starting from below, go 2 down and 1 to the left

        if (getBoard().positionExists(p) && canMove(p)){  //if the position below left exists and the Knight can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //right below -------------------------------------------------------------------------
        p.setValues(position.getRow() + 1, position.getColumn() + 2);
        //starting from the right, go 2 right and 1 down

        if (getBoard().positionExists(p) && canMove(p)){  //if the position right below exists and the Knight can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //right above -------------------------------------------------------------------------
        p.setValues(position.getRow() - 1, position.getColumn() + 2);
        //starting from the right, go 2 right and 1 up

        if (getBoard().positionExists(p) && canMove(p)){  //if the position right above exists and the Knight can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        return mat;
    }

    /*          if you cant execute the README command to set this UTF-8 symbols, trade for this
    @Override
    public String toString() {
        return "N";
    }
    */
}