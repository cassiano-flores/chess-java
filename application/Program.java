package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

    //testing
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch(); //instance a chessMatch (the game)

        while (true) {  //testing the performChessMove (pieces movement)
            try{
                UI.clearScreen();  //clear the last board every time that we have another one
                UI.printMatch(chessMatch);    //with UI class, print all the match settings (board, turn, currentPlayer)
                System.out.print("\nSource: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source); //possible moves for this source position
                UI.clearScreen();  //clear the last board
                UI.printBoard(chessMatch.getPieces(), possibleMoves);  //print the possible moves

                System.out.print("\nTarget: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
            }
            catch (ChessException e){    //already have messages to ChessException, so here, just getMessage
                System.out.println(e.getMessage());
                sc.nextLine();   //wait for the next enter (this is the time to user read the message)
            }
            catch (InputMismatchException e){  //already have messages to InputMismatchException, so here, just getMessage
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }
}