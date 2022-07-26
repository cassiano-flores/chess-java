package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

    //testing
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch(); //instance a chessMatch (the game)

        while (true) {             //testing the performChessMove (pieces movement)
            UI.printBoard(chessMatch.getPieces());    //with UI class, print all the board
            System.out.print("\nSource: ");
            ChessPosition source = UI.readChessPosition(sc);

            System.out.print("\nTarget: ");
            ChessPosition target = UI.readChessPosition(sc);

            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
        }
    }
}