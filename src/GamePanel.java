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
    int money=150;
    int lives=20;
    Timer gameTimer;
    int spawnCounter=0;
    int spawnType=0;
    int currentRound=1;

    public GamePanel(){
        gameTimer=new Timer(16,this);
        gameTimer.start();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cx = e.getX();
                int cy = e.getY();

                for (Tower tower : towers) {
                    if (Math.abs(tower.x - cx) < 20 && Math.abs(tower.y - cy) < 20) {
                        if (money >= 100) {
                            tower.upgrade();
                            money -= 100;
                        }
                        return;
                    }
                }
                if (money >= 50 && lives > 0) {
                    towers.add(new Tower(cx, cy));
                    money -= 50;
                }
            }
        });
        }
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(200,200,150));
        Graphics2D graphics2D=(Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(40,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        int[] pathX={0,200,200,400,400,700};
        int[] pathY={115,115,300,300,115,115};
        graphics2D.drawPolyline(pathX,pathY,pathX.length);

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
        g.drawString("Click anywhere to place a tower(50$),Click ON a tower to upgrade(100$)",10,20);
        g.setColor(new Color(0,120,0));
        g.drawString("Money: $ "+money,10,40);
        g.setColor(Color.RED);
        g.drawString("Lives: "+ lives,120,40);
        g.setColor(Color.BLUE);
        g.drawString("Round: "+ currentRound,220,40);

        if (lives<=0){
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.setColor(Color.RED);
            g.drawString("GAME OVER",getWidth()/2-150,getHeight()/2);
        }
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
