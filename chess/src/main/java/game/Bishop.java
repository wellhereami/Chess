package game;

public class Bishop extends Piece {
    public Bishop(String path, int xVal, int yVal, int t){
        super(path, xVal, yVal, t);
        name = "Bishop";
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
        return false;
    }
}
