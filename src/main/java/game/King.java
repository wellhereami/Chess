package game;

public class King extends Piece{
    public King(String path, int xVal, int yVal, int t){
        super(path, xVal, yVal, t);
        name = "King";
    }

    public boolean isMoveValid(Piece[][] gameBoard, int xVal, int yVal){
        if(Math.abs(yVal-y) <= 1 && Math.abs(xVal-x) <= 1){
            specialMove = true;
            return true;
        }
        //castle
        if(specialMove == false){
            //Check if rook has not moved yet
            //Then if spaces are empty
            //Use Piece.setLocation to move rook 
            //And change gameBoard
            //And set both specialMoves to true
        }
        return false;
    }
}
