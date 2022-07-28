package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

//User Interface class, just to print the board on the execution
public class UI {
    
    //colors for pieces
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void clearScreen(){   //method to clear the java console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc) { //read the position informed by player

        try{
            String s = sc.nextLine();  //the player should would write in this format: a1, a2, ...
            char column = s.charAt(0);  //in a "a1", "a" is the column
            int row = Integer.parseInt(s.substring(1)); //in a "a1", "1" is the row (the second substring)
            return new ChessPosition(column, row);   //instance a ChessPosition
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
        }
    }

    //print all the configs of the match in the screen
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured){
        printBoard(chessMatch.getPieces());   //print the board
        System.out.println();
        printCapturedPieces(captured);    //print captured pieces
        System.out.println("\nTurn: " + chessMatch.getTurn());   //print the color turn

        if (!chessMatch.getCheckMate()){  //do this only if isnt in checkmate (because the while is still running)
            System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());  //print the current player color
        
            if (chessMatch.getCheck()){   //if is in check, print this
                System.out.println("CHECK!");
            }
        }
        else{  //if has a checkmate and the game is over, print the winner
            System.out.println("CHECKMATE!");
            System.out.println("Winner: " + chessMatch.getCurrentPlayer());
        }
    }

    //print all the board
    public static void printBoard(ChessPiece[][] pieces){

        System.out.println("   ┌―――――――――――――――――――――――――――――――――――――――――――┐");  //outline from above
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + "  │   ");  //first column is the numbers and left outline

            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);   //call printPiece just below
                                                            //print the background without colors, just the pieces (false in the background)
                if (j == pieces.length - 1){
                    System.out.print("│");  //right outline
                }
            }                                               
            System.out.println();
        }
        System.out.println("   └―――――――――――――――――――――――――――――――――――――――――――┘");  //bottom contour
        System.out.println("       a    b    c    d    e    f    g    h"); //last row is the letters
    }

        //print all the board with the possible moves (overload)
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves){

        System.out.println("   ┌―――――――――――――――――――――――――――――――――――――――――――┐");  //outline from above
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + "  │   ");  //first column is the numbers and left outline

            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);   //call printPiece with the possible moves
                                                                 //in every possible position
                if (j == pieces.length - 1){
                    System.out.print("│");  //right outline
                }
            }                                                   
            System.out.println();
        }
        System.out.println("   └―――――――――――――――――――――――――――――――――――――――――――┘");  //bottom contour
        System.out.println("       a    b    c    d    e    f    g    h"); //last row is the letters
    }

    //print just one Piece
	private static void printPiece(ChessPiece piece, boolean background) {
        if (background){
            System.out.print(ANSI_CYAN_BACKGROUND);  //if the background is true, will color with cyan
        }                                           //in this case, true is a possible move

    	if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);  //color white = white
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);  //color black = yellow (because the terminal is black)
            }
        }
        System.out.print("    ");
	}

    private static void printCapturedPieces(List<ChessPiece> captured){  //method to print the captured pieces
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
        //captured is a generic list that contains all the pieces, with "stream" and "filter",
        //we can filter (x -> x.getColor) only the white pieces and black pieces in different lists

        System.out.println("Captured pieces:");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);  //print white
        System.out.println(Arrays.toString(white.toArray()));  //default way to print lists
        System.out.print(ANSI_RESET);

        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);  //print yellow
        System.out.println(Arrays.toString(black.toArray()));  //default way to print lists
        System.out.print(ANSI_RESET);
    }
}