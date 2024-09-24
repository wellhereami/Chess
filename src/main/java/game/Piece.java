package game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public abstract class Piece {
    int x, y, team;
    String name;
    BufferedImage image;
    boolean specialMove;

    Piece(){

    }
    
    Piece(String path, int xVal, int yVal, int t){
        try{
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (Exception e){
            System.out.println(e);;
        }

        x = xVal;
        y = yVal;
        team = t;
        specialMove = false;
    }

    public abstract boolean isMoveValid(Piece[][] gameBoard, int xVal, int yVal);

    public int getTeam(){
        return team;
    }

    public String getName(){
        return name;
    }

    public BufferedImage getImage(){
        return image;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean getSpecialMove(){
        return specialMove;
    }

    public void setLocation(int xPos, int yPos){
        x = xPos;
        y = yPos;
    }

    public void setSpecialMove(boolean s){
        specialMove = s;
    }

    public void printLocation(){
        String teamName = team == 1 ? "White" : "Black";


        char letter = (char)(x + 65);
        int num = 8-y;
        System.out.println(teamName + " " + name + " is located at " + letter + num + ".");
    }

    
}

