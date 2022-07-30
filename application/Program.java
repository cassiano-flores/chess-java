package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

    //the game
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch(); //instance a chessMatch (the game)
        List <ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {  //run the game while doesnt have any checkmate
            try{
                UI.clearScreen();  //clear the last board every time that we have another one
                UI.printMatch(chessMatch, captured);//with UI class, print all the match settings (board, turn, currentPlayer, captured pieces)
                System.out.print("\nSource: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source); //possible moves for this source position
                UI.clearScreen();  //clear the last board
                UI.printBoard(chessMatch.getPieces(), possibleMoves);  //print the possible moves

                System.out.print("\nTarget: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                if (capturedPiece != null){     //if the captured piece is not null, add to captured list
                    captured.add(capturedPiece);
                }

                if (chessMatch.getPromoted() != null){   //if there is a piece to be promoted, ask for the user

                    System.out.print("Enter piece for promotion (B/N/R/Q): ");
                    String type = sc.nextLine().toUpperCase();

                    //invalid value, repeat
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")){
                        System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type);  //replace for what is typed
                }
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
        UI.clearScreen();         //when the game is over (checkmate), clear the screen one more time
        UI.printMatch(chessMatch, captured);  //and print the match one more time (with the checkmate message)
    }
}