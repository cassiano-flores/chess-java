package boardgame;

//personalized BoardException class to write any error message 
public class BoardException extends RuntimeException {
    
    public BoardException(String msg){
        super(msg);
    }
}