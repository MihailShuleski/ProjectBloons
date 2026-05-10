import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private JFrame frame=new JFrame("Ball TD 6");

    public void showTitleScreen() {
        this.frame.setSize(500, 500);
        this.frame.setLayout(new BorderLayout());
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Vitejte do epic hry Ball TD 6", JLabel.CENTER);
        label.setFont(new Font("Times New Roman",Font.BOLD,24));
        label.setBorder(BorderFactory.createEmptyBorder(50,0,50,0));
        this.frame.add(label, BorderLayout.CENTER);

        JButton button = new JButton("Klikni abys hru zacal");
        CustomButton.startButton(button);
        this.frame.add(button, BorderLayout.SOUTH);

        button.addActionListener(e->{
            this.frame.dispose();
            new Game().showMenu();
    });
        this.frame.setVisible(true);
    }
}
