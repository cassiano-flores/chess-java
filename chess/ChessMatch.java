package chess;

import boardgame.Board;

public class ChessMatch {
    
    private Board board;  //board attribute of Board type

    public ChessMatch(){   //contructor will be a new Board 8x8 matrix (like a chessboard)
        board = new Board(8, 8);
    }

    public ChessPiece[][] getPieces(){  //although there is no Pieces attribute, this "getter" return a matrix
                                        //this matrix is the [rows] and [columns] of the "board"
                                        //and in the board, we have a Piece[position.getRow()][position.getColumn()] pieces attribute
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColums()];

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColums(); j++) {
                
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }            
        }
        return mat;  //return like a getter
    }
}