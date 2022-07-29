package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;  //attribute for castling special move 

    public King(Board board, Color color, ChessMatch chessMatch) {   //the same constructor of ChessPiece class
        super(board, color);
        this.chessMatch = chessMatch;
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

    private boolean canMove(Position position){  //verify if the King can move to a position

        ChessPiece p = (ChessPiece) getBoard().piece(position); //p receives a possible position to move
        return p == null || p.getColor() != getColor();   //verify if p is really a possible position
    ///if p is null (free position) or in this position have a piece with different color (an opponent), is an possible position
    }

    private boolean testRookCastling(Position position){  //test if a Rook is able to castling play
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    @Override //implement the abstract class from (ChessPiece -> Piece), just the King knows his possible moves
    public boolean[][] possibleMoves() {  //just to check, all the movements in a first moment is false
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);  //just to help

        //above -------------------------------------------------------------------------
        p.setValues(position.getRow() - 1, position.getColumn());
        //row - 1 because up in the matrix, the index of the row is decreasing

        if (getBoard().positionExists(p) && canMove(p)){  //if the position above exists and the King can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //upper left diagonal --------------------------------------------------------------
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        //row - 1 and column - 1 because the upper left diagonal in the matrix, the row and column index decrease

        if (getBoard().positionExists(p) && canMove(p)){  //if the position upper left diagonal exists and the King can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //left -------------------------------------------------------------------------
        p.setValues(position.getRow(), position.getColumn() - 1);
        //column - 1 because to the left in the matrix, the column index is decreasing

        if (getBoard().positionExists(p) && canMove(p)){  //if the position left exists and the King can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //lower left diagonal ------------------------------------------------------------
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        //row + 1 and column - 1 because the lower left diagonal in the matrix, the row index increases and the column index decreases

        if (getBoard().positionExists(p) && canMove(p)){  //if the position lower left diagonal exists and the King can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //below -------------------------------------------------------------------------
        p.setValues(position.getRow() + 1, position.getColumn());
        //row + 1 because down in the matrix, the column index is increasing

        if (getBoard().positionExists(p) && canMove(p)){  //if the position below exists and the King can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //lower right diagonal -----------------------------------------------------------
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        //row + 1 and column + 1 because the lower right diagonal in the matrix, the row and column index increase

        if (getBoard().positionExists(p) && canMove(p)){  //if the position lower right diagonal exists and the King can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //right -------------------------------------------------------------------------
        p.setValues(position.getRow(), position.getColumn() + 1);
        //column + 1 because to the right in the matrix, the column index is increasing

        if (getBoard().positionExists(p) && canMove(p)){  //if the position right exists and the King can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //upper right diagonal -----------------------------------------------------------
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        //row - 1 and column + 1 because the upper right diagonal in the matrix, the row index decreases and the column index increases

        if (getBoard().positionExists(p) && canMove(p)){  //if the position upper right diagonal exists and the King can move
            mat[p.getRow()][p.getColumn()] = true;        //set this matrix position to true
        }

        //special move castling -----------------------------------------------------------
        if (getMoveCount() == 0 && !chessMatch.getCheck()){

            //special move castling kingside rook
            Position leftRook = new Position(position.getRow(), position.getColumn() + 3); //rook position
            if (testRookCastling(leftRook)){
                
                Position p1 = new Position(position.getRow(), position.getColumn() + 1); //first position next to king
                Position p2 = new Position(position.getRow(), position.getColumn() + 2); //second position next to king

                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null){  //if both positions are null
                    mat[position.getRow()][position.getColumn() + 2] = true;  //thats a possible movement
                }
            }

            //special move castling queenside rook
            Position rightRook = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(rightRook)){
                
                Position p1 = new Position(position.getRow(), position.getColumn() - 1); //first position before the king
                Position p2 = new Position(position.getRow(), position.getColumn() - 2); //second position before the king
                Position p3 = new Position(position.getRow(), position.getColumn() - 3); //third position before the king

                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null){  //if the three positions are null
                    mat[position.getRow()][position.getColumn() - 2] = true;  //thats a possible movement
                }
            }
        }
 
        return mat;
    }

    /*          if you cant execute the README command to set this UTF-8 symbols, trade for this
    @Override
    public String toString() {
        return "K";
    }
    */
}