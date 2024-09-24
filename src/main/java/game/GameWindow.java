package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.awt.FontFormatException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//JPanel is added to JFrame in main 
public class GameWindow extends JPanel implements Runnable{
    final int mainMenu = 0;
    final int game = 1;
    final int gameOver = 2;
    final int promotion = 3;
    final int fps = 200000000;

    MouseEvents mouse = new MouseEvents();

    Thread gameThread;

    Piece[][] gameBoard;

    int inCheck = 0;

    Color grey = new Color(116, 125, 146);
    Color purple = new Color(55, 39, 114);
    Color green = new Color(148, 197, 149);

    Piece selectedPiece;

    int columns = 8;
    int rows = 8;
    int stepSize = 64;
    int width = columns * stepSize  + stepSize*2;
    int height = rows * stepSize;
    int gameState = mainMenu;

    int currTeam = 1; 
    Font f;

    public GameWindow(){ 
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(green);

        this.setFocusable(true);

        this.addMouseListener(mouse);

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/VCR_OSD_MONO.ttf");
            f = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameBoard = new Piece[columns][rows];
    }

    public void update(){
        if(mouse.getClick()){
            int x = mouse.getX();
            int y = mouse.getY();        
            if(selectedPiece != null && selectedPiece.getTeam() == currTeam){
                int yVal = -1;
                int xVal = -1;
                for(int i = 0; i < columns; i++){
                    for(int j = 0; j < rows; j++){
                        //if piece x is between j*stepSize & j+1*stepSize
                        //and piece y is between i*stepSize & i+1*stepsize
                        if(x >= j*stepSize && x <=(j+1)*stepSize && y >= i*stepSize && y <=(i+1)*stepSize){
                            yVal = i;
                            xVal = j;
                        }
                    }
                }
                
                if(turn(selectedPiece, xVal, yVal)){
                    Piece tempMove = gameBoard[yVal][xVal];
                    int tempX = selectedPiece.getX(), tempY = selectedPiece.getY(); 
                    gameBoard[yVal][xVal] = selectedPiece;
                    gameBoard[selectedPiece.getY()][selectedPiece.getX()] = null;
                    selectedPiece.setLocation(xVal,yVal);
                    if(!isKingInCheck(currTeam)){
                        selectedPiece = null;
                        repaint();
                        if(currTeam == 1){
                            currTeam = -1;
                        }
                        else{
                            currTeam = 1;
                        }
                        isKingInCheck(currTeam);
                    }
                    else{
                        System.out.println("Invalid move! Places your King in check.");
                        inCheck = 0;
                        gameBoard[tempY][tempX] = selectedPiece;
                        selectedPiece.setLocation(tempX,tempY);
                        gameBoard[yVal][xVal] = tempMove;
                        
                    }
                }
                else{
                    System.out.println("Invalid move");
                    selectedPiece = null;
                }
            }
            else{
               selectedPiece = locatePiece(x, y); 
            }
            
            mouse.setClick(false);
        }

    }

    Piece locatePiece(int x, int y){
        //loop through pieces on the board
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                //if piece x is between j*stepSize & j+1*stepSize
                //and piece y is between i*stepSize & i+1*stepsize
                if(x >= j*stepSize && x <=(j+1)*stepSize && y >= i*stepSize && y <=(i+1)*stepSize){
                    return gameBoard[i][j];
                }
            }   
        }

        return null;
    }

    public boolean isKingInCheck(int team){
        Piece king = gameBoard[0][0];
        boolean found = false;
        for(int i = 0; i < gameBoard.length; i++){
            if(found == true){
                break;
            }
            for(int j = 0; j < gameBoard[0].length; j++){
                if(gameBoard[i][j] != null && gameBoard[i][j].getTeam() == team && gameBoard[i][j].getName().equals("King")){
                    king = gameBoard[i][j];
                    found = true;
                    break;
                }
            }
        }

        //Check if opposing pieces are able to capture king
        for(int i = 0; i < gameBoard.length; i++){
            for(int j = 0; j < gameBoard[0].length; j++){
                if(gameBoard[i][j] != null && !(gameBoard[i][j].getTeam() == team)){
                    if(gameBoard[i][j].isMoveValid(gameBoard, king.getX(), king.getY())){
                        String teamName = team == 1 ? "Green" : "Purple";
                        System.out.println(teamName + " King is in check because of " + gameBoard[i][j].getName());
                        inCheck = team;
                        if(isKingInCheckmate(team)){
                            inCheck = 2;
                        }
                        return true;
                    }
                }
            }
        }
        if(inCheck == team){
            inCheck = 0;
        }
        return false;
    }

    public boolean isKingInCheckmate(int team){
        Piece king = gameBoard[0][0];
        boolean found = false;
        for(int i = 0; i < gameBoard.length; i++){
            if(found == true){
                break;
            }
            for(int j = 0; j < gameBoard[0].length; j++){
                if(gameBoard[i][j] != null && gameBoard[i][j].getTeam() == team && gameBoard[i][j].getName().equals("King")){
                    king = gameBoard[i][j];
                    found = true;
                    break;
                }
            }
        }

        //

        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(!(j == 0 || i == 0)){
                    int xVal = king.getX() + i;
                    int yVal = king.getY() + j;
                if(turn(king,xVal,yVal)){
                    Piece tempMove = gameBoard[yVal][xVal];
                    int tempX = selectedPiece.getX(), tempY = selectedPiece.getY(); 
                    gameBoard[yVal][xVal] = selectedPiece;
                    gameBoard[selectedPiece.getY()][selectedPiece.getX()] = null;
                    selectedPiece.setLocation(xVal,yVal);
                    if(!isKingInCheck(currTeam)){
                        selectedPiece = null;
                        repaint();
                        if(currTeam == 1){
                            currTeam = -1;
                        }
                        else{
                            currTeam = 1;
                        }
                        isKingInCheck(currTeam);
                    }
                    else{
                        System.out.println("Invalid move! Places your King in check.");
                        inCheck = 0;
                        gameBoard[tempY][tempX] = selectedPiece;
                        selectedPiece.setLocation(tempX,tempY);
                        gameBoard[yVal][xVal] = tempMove;
                        
                    }
            }
        }
        }
    }


        return false;
    }

      //row, column, what to divide x by 
      //Y is inverted
    public boolean turn(Piece p, int xVal, int yVal){

        //Check if in bounds
        if(yVal >= rows || xVal >= columns || yVal < 0 || xVal < 0){
            System.out.println("Out of Bounds");
            return false;
        }

        //Check if not capturing King or own team piece
        //Will change King position later
        if(gameBoard[yVal][xVal] != null && gameBoard[yVal][xVal].getTeam() == p.getTeam()){
            System.out.println("Captured own team");
            return false;
        }

        return selectedPiece.isMoveValid(gameBoard, xVal, yVal);
    }

    @Override
    public void run() {
        double nextDrawtime = System.nanoTime() + fps;
        while(gameThread != null){
            update();

            repaint();
            
            try{
                double remainingTime = nextDrawtime - System.nanoTime();
                remainingTime /= 1000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawtime += fps;
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void StartGameThread(){
        //Create pieces
        int x = 0, index = 1;
        boolean left = false;
        for(int i = 0; i < columns; i++){
            gameBoard[1][i] = new Pawn("/images/pieces/1b.png", i, 1, -1);
            gameBoard[rows-2][i] = new Pawn("/images/pieces/1w.png", i, (rows-2), 1);
            if(i%2==0 || index==5){
                index++; 
                left = true; 
            }
            if(left){
                x = (index-2);
            }
            else{
                x = (rows-index+1);
            }
            switch(index){
                case 2:
                    gameBoard[0][x] = new Rook("/images/pieces/2b.png", x, 0, -1);
                    gameBoard[rows-1][x] = new Rook("/images/pieces/2w.png", x, (rows-1), 1);
                    break;
                case 3:
                    gameBoard[0][x] = new Knight("/images/pieces/3b.png", x, 0, -1);
                    gameBoard[rows-1][x] = new Knight("/images/pieces/3w.png", x, (rows-1), 1);
                    break;
                case 4:
                    gameBoard[0][x] = new Bishop("/images/pieces/4b.png", x, 0, -1);
                    gameBoard[rows-1][x] = new Bishop("/images/pieces/4w.png", x, (rows-1), 1);
                    break;
                case 5:
                    gameBoard[0][x] = new Queen("/images/pieces/5b.png", x, 0, -1);
                    gameBoard[rows-1][x] = new Queen("/images/pieces/5w.png", x, (rows-1), 1);
                    break;
                case 6:
                    gameBoard[0][x] = new King("/images/pieces/6b.png", x, 0, -1);
                    gameBoard[rows-1][x] = new King("/images/pieces/6w.png", x, (rows-1), 1);
                    break;
            }
            left = false;
        }    

        gameState = game;
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if(gameState == game){
            paintBoard(g2);
            paintPieces(g2);
            paintUI(g2);
            g2.dispose();
        }        
    }

    public void paintBoard(Graphics2D g2){
        for(int col = 0; col < columns; col++){
            for(int row = 0; row < rows; row++){
                if(row % 2 != col % 2){
                    g2.setColor(grey);
                    g2.fillRect(row*stepSize, col*stepSize, stepSize, stepSize);
                }
            }
        }
    }
    
    public void paintPieces(Graphics2D g2){
        for(int i = 0; i < columns; i++){
            for(int j = 0; j < rows; j++){
              Piece p = gameBoard[i][j];
                if(p != null){
                    g2.drawImage(p.getImage(), p.getX()*stepSize, p.getY()*stepSize, stepSize, stepSize, null);
                }
            }
        }
    }

    public void paintPromotion(Graphics2D g2){
        g2.setColor(purple);
        g2.setFont(f.deriveFont(20f));
        g2.drawString("Promotion!", columns * stepSize + 10, 85);
        char letter = currTeam == 1 ? 'w' : 'b';
        int index = 2;
        for(int i = 0; i < 4; i++, index++){
            String path = "/images/pieces/" + index + letter + ".png";
            BufferedImage img = null;
            try{
                img = ImageIO.read(getClass().getResourceAsStream(path));
            } catch (Exception e){
                System.out.println(e);;
            }
            g2.drawImage(img, columns*stepSize + i*32, 100, 32, 32, null);

        }
    }

    public void paintUI(Graphics2D g2){
        g2.setColor(purple);
        g2.setFont(f.deriveFont(20f));
        //Turn counter
        String teamName = currTeam == 1 ? "Green" : "Purple";
        g2.drawString(teamName + ",", columns * stepSize + 10, 25);
        g2.drawString("Go!", columns * stepSize + 10, 50);

        if(inCheck == 1){
            g2.drawString("Green is", columns * stepSize + 10, 100);
            g2.drawString("in check!", columns * stepSize + 10, 120);
        }
        if(inCheck == -1){
            g2.drawString("Purple is", columns * stepSize + 10, 100);
            g2.drawString("in check!", columns * stepSize + 10, 120);
        }
        if(inCheck == 2){
            g2.drawString("Checkmate!", columns * stepSize + 10, 100);
        }


    }


}
