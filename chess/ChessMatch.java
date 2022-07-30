package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
    
    private int turn;
    private Color currentPlayer;
    private Board board;  //board attribute of Board type
    private boolean check;  //a boolean attribute starts for default as false
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;    //attribute for en passant special move
    private ChessPiece promoted;    //attribute for promoted special move

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch(){   //in the start of the game, the constructor will
                           //instance a new Board 8x8 matrix (like a chessboard)
                           //call the initialSetup (configure all the pieces on the board)
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;   //in a chessgame, the white pieces starts
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
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

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        //class to return the possible moves, and in the Program, we print this moves on the board

        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);  //check before

        return board.piece(position).possibleMoves();  //return the possible moves
    }

    //this method isnt the main method to move a piece, this is the action (the act of moving)
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){

        Position source = sourcePosition.toPosition(); //convert the the "matrix" position to "chess" position
        Position target = targetPosition.toPosition();

        validateSourcePosition(source); //verify if there is a piece in the source position
        validateTargetPosition(source, target); //verify if is a possible position to target

        Piece capturedPiece = makeMove(source, target);  //capture a piece

        // check ------------------------------------------
        if (testCheck(currentPlayer)){   //if the current player is now on check, he is wrong  (the "if" test assumes by default the possibility of "true")
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        ChessPiece movedPiece = (ChessPiece)board.piece(target);  //store the target piece (the last piece moved)

        //special move promotion    (have to implement before the testCheck, because the opponent might be in check after a promotion)
        promoted = null;

        if (movedPiece instanceof Pawn){  //if is a Pawn
                    //the last row of white Pawn is 0                                           the last row of black Pawn is 7
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)){

                promoted = (ChessPiece)board.piece(target);  //promoted is the target pawn
                promoted = replacePromotedPiece("Q");  //replace the target pawn for the chosen piece (replace)
            }
        }

        //if the current player put his opponent in check, now check is true
        if (testCheck(opponent(currentPlayer))){  
            check = true;
        }
        else {
            check = false;
        }

        // checkmate --------------------------------------
        if (testCheckMate(opponent(currentPlayer))){ //if the opponent of the current player is in checkmate
            checkMate = true;                        //set checkMate to "true"  (end of the game)
        }
        else{

            nextTurn();    //next player
        }

        //special move en passant                    for the white ones                      for the black ones
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)){

            enPassantVulnerable = movedPiece;     //last moved piece (pawn) is vulnerable to en passant
        }
        else{
            enPassantVulnerable = null;   //no one piece is vulnerable
        }

        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type){

        if (promoted == null){
            throw new IllegalStateException("There is no piece to be promoted");
        }
        //possible choices
        if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")){
            return promoted;
        }

        Position pos = promoted.getChessPosition().toPosition();  //get the promoted piece
        Piece p = board.removePiece(pos);  //remove from the board the promoted piece (the Pawn)
        piecesOnTheBoard.remove(p);  //remove from the piecesOnTheBoard list the promoted piece (the Pawn)
        //need to do that because the polymorphism and the public accesses

        ChessPiece newPiece = newPiece(type, promoted.getColor()); //newPiece is one of the letters (B, N, R or Q) and the same color of the promoted piece
        board.placePiece(newPiece, pos);  //place the newPiece on the promoted position "pos"
        piecesOnTheBoard.add(newPiece);  //newPiece on the list of pieces on the board

        return newPiece;
    }

    //method to create a new piece instance (used to special move promoted, replacePromotedPiece)
    private ChessPiece newPiece(String type, Color color){

        if (type.equals("B")) return new Bishop(board, color);
        if (type.equals("N")) return new Knight(board, color);
        if (type.equals("R")) return new Rook(board, color);
        return new Queen(board, color);
    }

    //method with the movement logic, remove from the original place (source), and put in the final place (target)
    private Piece makeMove(Position source, Position target){

        ChessPiece sourcePiece = (ChessPiece) board.removePiece(source); //remove from the source (base) position
        sourcePiece.increaseMoveCount();  //+1 movement
        Piece capturedPiece = board.removePiece(target); //remove from the target (final) position (if exists)
        board.placePiece(sourcePiece, target); //place the sourcePiece in the desired place (target)

        if (capturedPiece != null){   //if has a captured piece, remove from the board and add on the captured pieces
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // special move castling kingside rook
        if (sourcePiece instanceof King && target.getColumn() == source.getColumn() + 2){
        //if the piece is a King and the last movement is 2 positions

            Position sourceRook = new Position(source.getRow(), source.getColumn() + 3); //right rook source place
            Position targetRook = new Position(source.getRow(), source.getColumn() + 1); //right rook target place
            ChessPiece rook = (ChessPiece)board.removePiece(sourceRook);  //remove from source
            board.placePiece(rook, targetRook);  //add the rook on the target
            rook.increaseMoveCount();  //one more movement
        }

        // special move castling queenside rook
        if (sourcePiece instanceof King && target.getColumn() == source.getColumn() - 2){
        //if the piece is a King and the last movement is 2 positions

            Position sourceRook = new Position(source.getRow(), source.getColumn() - 4); //left rook source place
            Position targetRook = new Position(source.getRow(), source.getColumn() - 1); //left rook target place

            ChessPiece rook = (ChessPiece)board.removePiece(sourceRook);  //remove from source
            board.placePiece(rook, targetRook);  //add the rook on the target
            rook.increaseMoveCount();  //one more movement
        }

        //special move en passant
        if (sourcePiece instanceof Pawn){  //if the source piece is a Pawn

            if (source.getColumn() != target.getColumn() && capturedPiece == null){ //make a diagonal movement and doesnt captured nothing

                Position pawnPosition;
                if (sourcePiece.getColor() == Color.WHITE){  //source piece is a white pawn
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());  //capture below
                }
                else{  //source piece is a black pawn
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());  //capture above
                }

                capturedPiece = board.removePiece(pawnPosition);  //capturedPiece = pawnPosition
                capturedPieces.add(capturedPiece);   //add the capturedPiece to capturedPieces list
                piecesOnTheBoard.remove(capturedPiece);  //remove the capturedPiece from the board
            }
        }

        return capturedPiece;
    }

    //method to undo a movement, this will be useful in a check case, the player try to move a piece
    //and the King stayed under attack, the play will be reversed and throw a exception
    private void undoMove(Position source, Position target, Piece capturedPiece){

        ChessPiece sourcePiece = (ChessPiece) board.removePiece(target); //remove from target
        sourcePiece.decreaseMoveCount();  //-1 movement
        board.placePiece(sourcePiece, source);  //put on source position again

        if (capturedPiece != null){  //if a piece was captured, remove from captured pieces and add again on board
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        // special move castling kingside rook (undo just in an unexpected error)
        if (sourcePiece instanceof King && target.getColumn() == source.getColumn() + 2){

            Position sourceRook = new Position(source.getRow(), source.getColumn() + 3); //right rook source place
            Position targetRook = new Position(source.getRow(), source.getColumn() + 1); //right rook target place
            ChessPiece rook = (ChessPiece)board.removePiece(targetRook);  //remove from target
            board.placePiece(rook, sourceRook);  //add the rook on the source again
            rook.decreaseMoveCount();  //-1 movement
        }

        // special move castling queenside rook (undo just in an unexpected error)
        if (sourcePiece instanceof King && target.getColumn() == source.getColumn() - 2){

            Position sourceRook = new Position(source.getRow(), source.getColumn() - 4); //left rook source place
            Position targetRook = new Position(source.getRow(), source.getColumn() - 1); //left rook target place

            ChessPiece rook = (ChessPiece)board.removePiece(targetRook);  //remove from target
            board.placePiece(rook, sourceRook);  //add the rook on the source again
            rook.decreaseMoveCount();  //-1 movement
        }

        //special move en passant
        if (sourcePiece instanceof Pawn){  //if the source piece is a Pawn

            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable){
                //make a diagonal movement and the capturedPiece was in enPassantVulnerable

                ChessPiece pawn = (ChessPiece)board.removePiece(target);  //remove the target
                Position pawnPosition;

                if (sourcePiece.getColor() == Color.WHITE){  //source piece is a white pawn
                    pawnPosition = new Position(3, target.getColumn());  //right pawn position for white pawn
                }
                else{  //source piece is a black pawn
                    pawnPosition = new Position(4, target.getColumn());  //right pawn position for white pawn
                }

                board.placePiece(pawn, pawnPosition); //place the pawn on the pawnPosition
            }                                         //the position of "pawn" is the target, but the target doesnt have anything
        }                                             //the right target is the pawnPosition (source of enPassantVulnerable)
    }

    private void validateSourcePosition(Position position){ //verify if there is a piece in the source position
                                                            //(the base position)
        if (!board.thereIsAPiece(position)){
            throw new ChessException("There is no piece on source position");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()){  //verify if the chosen piece have the same color that the current player
            throw new ChessException("The chosen piece is not yours");
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

    private void nextTurn(){  //verify which color piece is playing and alternate

        turn++;
        if (currentPlayer == Color.WHITE){  //if white, then now is black
            currentPlayer = Color.BLACK;
        }
        else{
            currentPlayer = Color.WHITE;  //if black, then now is white
        }
    }

    private Color opponent(Color color){

        if (color == Color.WHITE){  //if white, then the opponent is black
            color = Color.BLACK;
        }
        else{
            color = Color.WHITE;  //if black, then the opponent is white
        }

        return color;
    }

    //method to find a determined color king
    private ChessPiece king(Color color){

        //this list will go through the piecesOnTheBoard and add in the list all the same "color" pieces
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
    
        //and now, return a piece instance of King (will return just the color selected king)
        for (Piece p : list) {

            if (p instanceof King){
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
        //important: in this case, we want it to return the King that was traversed in the list
        //but the compiler complains that there might be no return, so we should raise an exception
        //*** this exception should NEVER be thrown! (there must always be the king at stake) ***
    }

    //method to check if the moment right now is a check situation (threatened king)
    private boolean testCheck(Color color){

        //convert the matrix king position to chess king position
        Position kingPosition = king(color).getChessPosition().toPosition();  //** a variable kingPosition of the Position type (class made by me), take the king of an informed color, get king position and convert to chess position (all this things was made by me and not something standard from Java, this is really nice)
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        //list contains all the opponent pieces from this color

        for (Piece p : list) {  //for each opponent in the list
            boolean[][] mat = p.possibleMoves();  //mat contains all the possible movements for this piece (p)

            //verify if any of the opponents' possible moves are the King position
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;  //if yes, this is a check situation (true)
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color){  //method to check if the moment right now is a checkmate situation (threatened king)

        if (!testCheck(color)){  //if just the testCheck is false, this isnt a checkmate
            return false;
        }
        //this "list" contains all the pieces with the same color than mine
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        
        for (Piece p : list) {    //for each piece, verify their possible moves
            boolean[][] mat = p.possibleMoves();  //a possible move on this matrix, is "true"

            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {

                    if (mat[i][j]){  //if mat[i][j] is a possible move...
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();  //get the position to source...
                        Position target = new Position(i, j);  //get the possible move to target...

                        Piece capturedPiece = makeMove(source, target); //move to mat[i][j] position...
                        boolean testCheck = testCheck(color);  //verify with this new position, if is still in check
                        undoMove(source, target, capturedPiece);  //undo the test move

                        if (!testCheck){  //if now isnt in "check", so this is not "checkmate"
                            return false;
                        }
                    }
                }
            }
        }
        return true;   //if it goes through all possible moves of all pieces and always remains in check, it is checkmate
    }


    private void placeNewPiece(char column, int row, ChessPiece piece){
        //informs the column (first), row, and then the piece (ChessPiece)

        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add((piece)); //add the piece on the list of pieces on the board
    }

    private void initialSetup(){  //method to set the initialSetup of the board, each piece starts
                                  //the game in a right place

    //now, initialize a placeNewPiece with the chessboard coordinates (a1), and instance a new piece (ChessPiece piece)
                                                                            //with a board and color

        //white pieces ---------------------------------------------------------------
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));  //this = this ChessMatch
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        //black pieces ---------------------------------------------------------------
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));  //this = this ChessMatch
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}