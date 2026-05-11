import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    ArrayList<Projectile> projectiles=new ArrayList<>();
    ArrayList<Enemy> enemies=new ArrayList<>();
    ArrayList<Tower> towers=new ArrayList<>();
    Timer gameTimer;
    int spawnCounter=0;
    int spawnType=0;

    public GamePanel(){
        gameTimer=new Timer(16,this);
        gameTimer.start();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                towers.add(new Tower(e.getX(),e.getY()));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(200,200,150));
        g.fillRect(0,85,getWidth(),60);

        for (Tower tower:towers){
            tower.draw(g);
        }
        for (Enemy enemy:enemies){
            enemy.draw(g);
        }
        for (Projectile projectile:projectiles){
            projectile.draw(g);
        }
        g.setFont(new Font("Arial",Font.BOLD,14));
        g.setColor(Color.BLACK);
        g.drawString("Click anywhere to place a tower",10,20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        spawnCounter++;
        if (spawnCounter>=60){
            enemies.add(new Enemy(spawnType));
            spawnType=(spawnType+1)% 3;
            spawnCounter=0;
        }
        for (Tower tower:towers){
            tower.update(enemies,projectiles);
        }
        ArrayList<Enemy> toRemove=new ArrayList<>();
        for (Enemy enemy:enemies){
            enemy.update();
            if (enemy.health <= 0|| enemy.x>getWidth()) {
                toRemove.add(enemy);
            }
        }
        for (Projectile projectile:projectiles){
            projectile.update();
        }
        enemies.removeAll(toRemove);
        repaint();
    }
}
