package game;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        //JFrame is container
        JFrame mainFrame = new JFrame("CHESS");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        GameWindow mainGame = new GameWindow();

        mainFrame.add(mainGame);
        mainFrame.pack();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        mainGame.StartGameThread();
    }
}