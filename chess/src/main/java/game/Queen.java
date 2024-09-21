package game;

public class Queen extends Piece {
    public Queen(String path, int xVal, int yVal, int t){
        super(path, xVal, yVal, t);
        name = "Queen";
    }

    public boolean isMoveValid(Piece[][] gameBoard, int xVal, int yVal){
        if(Math.abs(xVal-x) == Math.abs(yVal-y)){
            int rowIncrease = (int)Math.signum(yVal-y);
            int colIncrease = (int)Math.signum(xVal-x);
            int rowNum = y + rowIncrease;
            int colNum = x + colIncrease;
            for(int i = 1; i < Math.abs(yVal-y); i++){
                if(gameBoard[rowNum][colNum] != null){                
                    rowNum += rowIncrease;
                    colNum += colIncrease;
                    return false;
                }
            }
            return true;
        }
        if(xVal == x){
            int changeVal = (int)Math.signum(y-yVal);
            for(int i = yVal+changeVal; i != y; i+=changeVal){
                if(gameBoard[i][x] != null){
                    return false;
                }
            }
            return true;
        }
        if(yVal == y){
            int changeVal = (int)Math.signum(x-xVal);
            for(int i = xVal+changeVal; i != x; i+=changeVal){
                if(gameBoard[y][i] != null){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
