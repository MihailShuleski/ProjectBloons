import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    ArrayList<Enemy> enemies=new ArrayList<>();
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        enemies.add(new Enemy(0));
        for (Enemy e:enemies){
            e.update();
            e.draw(g);
        }
        g.fillRect(50,50,100,100);
    }
}
