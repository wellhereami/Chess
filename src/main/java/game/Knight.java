package game;

public class Knight extends Piece {
    public Knight(String path, int xVal, int yVal, int t){
        super(path, xVal, yVal, t);
        name = "Knight";
    }
    public boolean isMoveValid(Piece[][] gameBoard, int xVal, int yVal){
        if((Math.abs(xVal-x) == 2 && Math.abs(yVal-y) == 1) || (Math.abs(xVal-x) == 1 && Math.abs(yVal-y) == 2)){
            return true;
        }
        return false;
    }

}
