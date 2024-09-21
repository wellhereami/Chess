package game;

public class Pawn extends Piece {
    public Pawn(String path, int xVal, int yVal, int t){
        super(path, xVal, yVal, t);
        name = "Pawn";
    }

    public boolean isMoveValid(Piece[][] gameBoard, int xVal, int yVal){
        if(x == xVal){
            if(yVal == y - team && gameBoard[yVal][xVal] == null){
                setSpecialMove(true);
                return true;
            }
            //Move up 2 if able
            if(yVal == y - (2*team) && !getSpecialMove() && gameBoard[yVal][xVal] == null && gameBoard[yVal+team][xVal] == null){
                setSpecialMove(true);
                return true;
            }
        }
        //Move diagonally if can capture
        
        if((yVal == y - team && Math.abs(xVal-x) == 1) && gameBoard[yVal][xVal] != null){
            return true;
        }
        return false;
    }

    public void promote(){
        //
    }
}
