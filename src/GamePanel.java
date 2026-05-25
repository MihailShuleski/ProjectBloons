import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    ArrayList<Projectile> projectiles=new ArrayList<>();
    ArrayList<Enemy> enemies=new ArrayList<>();
    ArrayList<Tower> towers=new ArrayList<>();
    ArrayList<ExplosionEffect> explosions=new ArrayList<>();
    int money=150;
    int lives=20;
    Timer gameTimer;
    int spawnCounter=0;
    int currentRound=1;
    int enemiesSpawned=0;
    int enemiesToSpawn=5;
    int totalPops=0;
    int towerType=0;
    int spawnDelay=60;
    boolean roundActive = true;

    public GamePanel(){
        setFocusable(true);
        gameTimer=new Timer(16,this);
        gameTimer.start();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar()=='1')
                    towerType=0;
                if (e.getKeyChar()=='2')
                    towerType=1;
            }
        });
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
                int cost=(towerType ==0) ? 50:150;
                if (money >=cost&& lives>0){
                    towers.add(new Tower(cx,cy,towerType));
                    money-=cost;
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
        String selectionText=(towerType==0) ? "SELECTED: Dart Tower(50$)":"SELECTED: Bomb Tower(150$)";
        g.drawString("Press 1 or 2 to switch between tower types: "+selectionText,10,20);
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
        } else if (enemiesSpawned>=enemiesToSpawn && enemies.isEmpty()) {
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.setColor(Color.ORANGE);
            g.drawString("WAVE CLEARED!",getWidth()/2-170,getHeight()/2);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        roundActive=true;
        if (lives<= 0)
            return;
        if (roundActive){
            if (enemiesSpawned<enemiesToSpawn) {
                spawnCounter++;
                if (spawnCounter >= spawnDelay) {
                    int type = (int) (Math.random() * 3);
                    if (currentRound > 5 && Math.random() < 0.2)
                        type = 3;
                    enemies.add(new Enemy(2));
                    enemiesSpawned++;
                    spawnCounter = 0;
                }
            } else if (enemies.isEmpty()) {
                roundActive=false;
                currentRound++;
                enemiesToSpawn=5+(currentRound * 2);
                enemiesSpawned=0;
                spawnCounter=0;
                spawnDelay=Math.max(10,60-(currentRound*2));
                money+=50;
            }
        }
        ArrayList<Projectile> deadProjectiles =new ArrayList<>();
        for (Projectile projectile:projectiles){
            projectile.update(explosions);
            if (!projectile.active)
                deadProjectiles.add(projectile);
        }
        projectiles.removeAll(deadProjectiles);
        ArrayList<ExplosionEffect> deadExplosions=new ArrayList<>();
        for (ExplosionEffect explosionEffect:deadExplosions){
            explosionEffect.update();
            if (explosionEffect.timer<=0)
                deadExplosions.add(explosionEffect);
        }
        explosions.removeAll(deadExplosions);
        for (Tower tower:towers){
            tower.update(enemies,projectiles);
        }
        ArrayList<Enemy> toRemove=new ArrayList<>();
        for (Enemy enemy:enemies){
            enemy.update();
            if (enemy.health <= 0|| enemy.x>getWidth()) {
                toRemove.add(enemy);
                money+=5;
                totalPops++;
            } else if (enemy.x>650) {
                toRemove.add(enemy);
                lives -=1;

            }
        }
        enemies.removeAll(toRemove);
        repaint();
    }
}
