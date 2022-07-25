package application;

import chess.ChessPiece;

//User Interface class, just to print the board on the execution
public class UI {
    
    //print all the board
    public static void printBoard(ChessPiece[][] pieces){

        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + "    ");  //first column is the numbers

            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j]);   //call printPiece just below
            }
            System.out.println();
        }
        System.out.println("     a    b    c    d    e    f    g    h"); //last row is the letters

    }

    //print just one Piece
    private static void printPiece(ChessPiece piece){

        if (piece == null){
            System.out.print("-");
        }
        else{
            System.out.print(piece);
        }
        System.out.print("    ");
    }
}