import javax.swing.*;
import java.awt.*;

public class CustomButton {

    public static  void startButton(JButton button){
        button.setBackground(new Color(13, 43, 206));
        button.setForeground(Color.MAGENTA);
        button.setFont(new Font("Comic Sans",Font.BOLD,20));

        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    public static  void settingsButton(JButton button){
        button.setBackground(new Color(136, 38, 165));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Comic Sans",Font.BOLD,20));

        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
}
