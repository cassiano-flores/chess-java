package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    
    private Board board;  //board attribute of Board type

    public ChessMatch(){   //in the start of the game, the constructor will
                           //instance a new Board 8x8 matrix (like a chessboard)
                           //call the initialSetup (configure all the pieces on the board)
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces(){  //although there is no Pieces attribute, this "getter" return a matrix
                                        //this matrix is the [rows] and [columns] of the "board"
                                        //and in the board, we have a Piece[position.getRow()][position.getColumn()] pieces attribute
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }            
        }
        return mat;  //return like a getter
    }

    //this method isnt the main method to move a piece, this is the action (the act of moving)
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){

        Position source = sourcePosition.toPosition(); //convert the the "matrix" position to "chess" position
        Position target = targetPosition.toPosition();

        validateSourcePosition(source); //verify if there is a piece in the source position
        validateTargetPosition(source, target); //verify if is a possible position to target

        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece) capturedPiece;
    }

    //method with the movement logic, remove from the original place (source), and put in the final place (target)
    private Piece makeMove(Position source, Position target){

        Piece sourcePiece = board.removePiece(source); //remove from the source (base) position
        Piece capturedPiece = board.removePiece(target); //remove from the target (final) position (if exists)
        board.placePiece(sourcePiece, target); //place the sourcePiece in the desired place (target) 
        return capturedPiece;
    }

    private void validateSourcePosition(Position position){ //verify if there is a piece in the source position
                                                            //(the base position)
        if (!board.thereIsAPiece(position)){
            throw new ChessException("There is no piece on source position");
        }
        if (!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target){

        if (!board.piece(source).possibleMove(target)){  //verify if the chosen target is a possible position to chosen piece
            throw new ChessException("This piece can't move to target position");
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece){
        //informs the column (first), row, and then the piece (ChessPiece)

        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup(){  //method to set the initialSetup of the board, each piece starts
                                  //the game in a right place

    //now, initialize a placeNewPiece with the chessboard coordinates (a1), and instance a new piece (ChessPiece piece)
                                                                            //with a board and color

        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
    }
}