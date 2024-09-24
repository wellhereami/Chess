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
            for(int i = 2; i < Math.abs(yVal-y); i++){
                rowNum += rowIncrease;
                colNum += colIncrease;
                if(gameBoard[rowNum][colNum] != null){                
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
