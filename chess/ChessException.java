package chess;

import boardgame.BoardException;

//personalized ChessException class to write any error message
//extends BoardException because when we catch a ChessException, we can catch possible BoardException too
public class ChessException extends BoardException{

    public ChessException(String msg){
        super(msg);
    }
}