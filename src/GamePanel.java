import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    ArrayList<Projectile> projectiles = new ArrayList<>();
    ArrayList<AbstractEnemy> enemies = new ArrayList<>();
    ArrayList<AbstractTower> towers = new ArrayList<>();
    ArrayList<ExplosionEffect> explosions = new ArrayList<>();
    int money = 150;
    int lives = 20;
    Timer gameTimer;
    int spawnCounter = 0;
    int currentRound = 1;
    int enemiesSpawned = 0;
    int enemiesToSpawn = 5;
    int totalPops = 0;
    int towerType = 0;
    int spawnDelay = 60;
    boolean roundActive = true;

    public GamePanel() {
        setFocusable(true);
        gameTimer = new Timer(16, this);
        gameTimer.start();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '1'){
                    towerType = 0;
                    repaint();
                }
                if (e.getKeyChar() == '2'){
                    towerType = 1;
                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (!roundActive && lives > 0) {
                        currentRound++;
                        enemiesToSpawn = 5 + (currentRound * 2);
                        enemiesSpawned = 0;
                        spawnCounter = 0;
                        spawnDelay = Math.max(10, 60 - (currentRound * 2));
                        money += 50;
                        roundActive = true;
                    }
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
                int cx = e.getX();
                int cy = e.getY();

                for (AbstractTower tower : towers) {
                    if ((tower.x - cx) < 20 && (tower.y - cy) < 20) {
                        if (money >= 100) {
                            tower.upgrade();
                            money -= 100;
                        }
                        return;
                    }
                }
                int cost = (towerType == 0) ? 50 : 150;
                if (money >= cost && lives > 0) {
                    if (towerType == 0)
                        towers.add(new DartTower(cx, cy));
                    else
                        towers.add(new BombTower(cx, cy));
                    money -= cost;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(200, 200, 150));
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(40, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int[] pathX = {0, 200, 200, 400, 400, 700};
        int[] pathY = {115, 115, 300, 300, 115, 115};
        graphics2D.drawPolyline(pathX, pathY, pathX.length);

        for (AbstractTower tower : towers) {
            tower.draw(g);
        }
        for (AbstractEnemy enemy : enemies) {
            enemy.draw(g);
        }
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }
        for (ExplosionEffect explosionEffect : explosions) {
            explosionEffect.draw(g);
        }
        drawUI(graphics2D);
        if (lives <= 0) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 110, getHeight() / 2);
        }

    }

    private void drawUI(Graphics g) {
        g.setColor(new Color(60, 60, 60, 220));
        g.fillRect(450, 0, 150, getHeight());

        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.setColor(Color.WHITE);
        g.drawString("MONEY: $" + money, 460, 40);
        g.setColor(Color.RED);
        g.drawString("LIVES: " + lives, 460, 70);
        g.setColor(Color.BLUE);
        g.drawString("ROUND: " + currentRound, 460, 100);
        g.setColor(Color.MAGENTA);
        g.drawString("POPS: " + totalPops, 460, 130);

        g.setColor(Color.WHITE);
        g.drawString("BUILD:", 460, 190);
        if (towerType == 0) {
            g.setColor(new Color(139, 69, 19));
            g.fillRect(460, 205, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString("Dart ($50)", 460, 255);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(460, 205, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString("Bomb ($150)", 460, 255);
        }

        if (!roundActive && lives > 0) {
            g.setColor(Color.YELLOW);
            g.fillRect(460, 400, 130, 40);
            g.setColor(Color.BLACK);
            g.drawString("PRESS SPACE TO START WAVE", 475, 425);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (lives <= 0)
                return;

            // Spawning logic
            if (roundActive) {
                if (enemiesSpawned < enemiesToSpawn) {
                    spawnCounter++;
                    if (spawnCounter >= spawnDelay) {
                        spawnNextEnemy();
                        enemiesSpawned++;
                        spawnCounter = 0;
                    }
                } else if (enemies.isEmpty()) {
                    completeRound();
                }
            }

            updateGameObjects();
            repaint();
        } catch (Exception ex) {
            System.err.println("Critical error in game loop: " + ex.getMessage());
        }
    }

    private void spawnNextEnemy() {
        double rand = Math.random();
        if (currentRound > 3 && rand < 0.2)
            enemies.add(new TankBall());
        else if (currentRound > 5 && rand < 0.15)
            enemies.add(new LeadBall());
        else if (currentRound > 2 && rand < 0.3)
            enemies.add(new SpeedBall());
        else
            enemies.add(new NormalBall());
    }

    private void completeRound() {
        roundActive = false;
        currentRound++;
        enemiesToSpawn = 5 + (currentRound * 2);
        enemiesSpawned = 0;
        spawnDelay = Math.max(10, 60 - (currentRound * 2));
        money += 100;
    }

    private void updateGameObjects() {
        ArrayList<Projectile> deadProjectiles = new ArrayList<>();
        for (Projectile p : projectiles) {
            p.update(explosions);
            if (!p.active)
                deadProjectiles.add(p);
        }
        projectiles.removeAll(deadProjectiles);

        ArrayList<ExplosionEffect> deadEx = new ArrayList<>();
        for (ExplosionEffect ex : explosions) {
            ex.update();
            if (ex.timer <= 0)
                deadEx.add(ex);
        }
        explosions.removeAll(deadEx);

        for (AbstractTower t : towers) {
            t.update();
            t.attack(enemies, projectiles);
        }

        ArrayList<AbstractEnemy> toRemove = new ArrayList<>();
        for (AbstractEnemy enemy : enemies) {
            enemy.update();
            if (enemy.getHealth() <= 0) {
                toRemove.add(enemy);
                money += 5;
                totalPops++;
            } else if (enemy.getX() > 650) {
                toRemove.add(enemy);
                lives -= 1;
            }
        }
        enemies.removeAll(toRemove);
    }
}