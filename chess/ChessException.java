package chess;

//personalized ChessException class to write any error message 
public class ChessException extends RuntimeException{

    public ChessException(String msg){
        super(msg);
    }
}