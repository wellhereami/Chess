package game;

public class Rook extends Piece {
    public Rook(String path, int xVal, int yVal, int t){
        super(path, xVal, yVal, t);
        name = "Rook";
    }


    public boolean isMoveValid(Piece[][] gameBoard, int xVal, int yVal){
        if(xVal == x){
            int changeVal = (int)Math.signum(y-yVal);
            for(int i = yVal+changeVal; i != y; i+=changeVal){
                if(gameBoard[i][x] != null){
                    return false;
                }
            }
            setSpecialMove(true);
            return true;
        }
        if(yVal == y){
            int changeVal = (int)Math.signum(x-xVal);
            for(int i = xVal+changeVal; i != x; i+=changeVal){
                if(gameBoard[y][i] != null){
                    return false;
                }
            }
            setSpecialMove(true);
            return true;
        }
        return false;
    }
}
