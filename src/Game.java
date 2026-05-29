import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 * Controller class representing a single game instance.
 * Handles the creation of the main game window and injects the GamePanel board.
 */
public class Game {

    /**
     * Initializes and opens the main game window (JFrame) containing the playing field.
     */
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
