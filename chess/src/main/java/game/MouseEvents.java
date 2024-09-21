package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseEvents implements MouseListener {
    boolean click = false;
    int x, y;
    //Triggers on click release
    @Override
    public void mouseClicked(MouseEvent e) {
        click = true;
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      
    }

    @Override
    public void mouseExited(MouseEvent e) {
    
    }

    @Override
    public void mousePressed(MouseEvent e) {
      
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      
    }

    public boolean getClick(){
        return click;
    }

    public void setClick(boolean c){
        click = c;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
