package application;

import chess.ChessMatch;

public class Program {

    //testing
    public static void main(String[] args) {
        
        ChessMatch chessMatch = new ChessMatch(); //instance a chessMatch
        UI.printBoard(chessMatch.getPieces());    //with UI class, print all the board
    }
}