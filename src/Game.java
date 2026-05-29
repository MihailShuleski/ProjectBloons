import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game {


    public void showMenu(){
        JFrame frame=new JFrame("Ball TD 6");
        frame.setSize(600,800);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel();
        frame.add(panel);
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
}
