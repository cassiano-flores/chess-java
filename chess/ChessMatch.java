package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    
    private Board board;  //board attribute of Board type

    public ChessMatch(){   //in the start of the game, the contructor will
                           //instance a new Board 8x8 matrix (like a chessboard)
                           //call the initialSetup (configure all the pieces on the board)
        board = new Board(8, 8);
        initialSetup();
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

    private void initialSetup(){  //method to set the initialSetup of the board, each piece starts
                                  //the game in a right place

        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
        board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
        board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
    }
}