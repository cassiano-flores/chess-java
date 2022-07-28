package chess;

import boardgame.Position;

//another coordinates system, to simulate the chess coordinates system (a1, b5, and not the matrix system)
public class ChessPosition {
    
    private char column;  //in a chessboard, the columns go first and then the rows
    private int row;

    public ChessPosition(char column, int row) {

        if (column < 'a' || column > 'h' || row < 1 || row > 8){  //defensive programming
            throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8");
        }

        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }   

    protected Position toPosition(){  //in matrix, the first position is 0, 0, and in the chessboard is a8
                                      //so we need to do (8 - row) in the row, because will be 8-0 = 8, 8-1 = 7, ...
                                      //for the column, a=0, b=1, ..., so if is column - 'a', the values ​​will grow one by one properly

        return new Position(8 - row, column - 'a');
        //this is the Position(row, column), so the first is row, only in the chessboard are columns first
    }

    protected static ChessPosition fromPosition (Position position){
        return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
        //now the columns are first, we do the inverse method toPosition
    }

    @Override
    public String toString() {
        return "" + column + row; //"" just to recognize a column + row concatenation
    }
}