import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * The main game panel representing the playing board and UI stats bar.
 * Handles the game loop timer, entity updates, drawing, and user inputs (mouse and keyboard).
 */
public class GamePanel extends JPanel implements ActionListener {
    ArrayList<Projectile> projectiles = new ArrayList<>();
    ArrayList<AbstractEnemy> enemies = new ArrayList<>();
    ArrayList<AbstractTower> towers = new ArrayList<>();
    ArrayList<ExplosionEffect> explosions = new ArrayList<>();
    int money = 50;
    int lives = 1;
    Timer gameTimer;
    int spawnCounter = 0;
    int currentRound = 0;
    int enemiesSpawned = 0;
    int enemiesToSpawn = 5;
    int totalPops = 0;
    int towerType = 0;
    int spawnDelay = 60;
    boolean roundActive = false;
    int mouseX=-1000;
    int mouseY=-1000;
    /**
     * Constructs a GamePanel. Initializes listeners for keyboard and mouse inputs,
     * and starts the main game loop timer.
     */
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

                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    if (!roundActive && lives > 0) {
                        startNextRound();
                        repaint();
                    }
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
                int cx = e.getX();
                int cy = e.getY();
                int uiY= getHeight()-150;
                if (lives <= 0) {
                    int cardW = 360;
                    int cardH = 280;
                    int cardX = (getWidth() - cardW) / 2;
                    int cardY = (getHeight() - cardH) / 2;
                    int btnW = 240;
                    int btnH = 40;
                    int btnX = cardX + (cardW - btnW) / 2;
                    int restartY = cardY + 170;
                    if (cx >= btnX && cx <= btnX + btnW && cy >= restartY && cy <= restartY + btnH) {
                        restartGame();
                        repaint();
                    }
                    int menuY = cardY + 220;
                    if (cx >= btnX && cx <= btnX + btnW && cy >= menuY && cy <= menuY + btnH) {
                        returnToMainMenu();
                        repaint();
                    }
                    return;
                }


                if (cy >= uiY) {
                    if (cx >= 205 && cx <= 305 && cy >= uiY + 35 && cy <= uiY + 130) {
                        towerType = 0;
                        repaint();
                    }
                    else if (cx >= 325 && cx <= 425 && cy >= uiY + 35 && cy <= uiY + 130) {
                        towerType = 1;
                        repaint();
                    }
                    else if (cx >= 465 && cx <= 575 && cy >= uiY + 45 && cy <= uiY + 105) {
                        startNextRound();
                        repaint();
                    }
                    return;
                }

                for (AbstractTower tower : towers) {
                    double dist = Math.sqrt(Math.pow(tower.x - cx, 2) + Math.pow(tower.y - cy, 2));
                    if (dist < 20) {
                        if (money >= 100) {
                            tower.upgrade();
                            money -= 100;
                        }
                        return;
                    } else if (dist<30) {
                        return;
                    }
                }
                if (isNearPath(cx,cy)){
                    return;
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
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        });
    }
    /**
     * Renders the game board, including path, towers, enemies, projectiles, explosions,
     * the HUD/UI shop bar, and the game over screen if lives reach zero.
     *
     * @param g the Graphics context used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(75, 158, 51));
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setColor(new Color(70, 68, 62));
        graphics2D.setStroke(new BasicStroke(46, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics2D.drawPolyline(AbstractEnemy.pathX, AbstractEnemy.pathY, AbstractEnemy.pathX.length);

        graphics2D.setColor(new Color(129, 126, 114));
        graphics2D.setStroke(new BasicStroke(40, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics2D.drawPolyline(AbstractEnemy.pathX, AbstractEnemy.pathY, AbstractEnemy.pathX.length);

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
            graphics2D.setColor(new Color(15, 15, 25, 200));
            graphics2D.fillRect(0, 0, getWidth(), getHeight());
            int cardW = 360;
            int cardH = 280;
            int cardX = (getWidth() - cardW) / 2;
            int cardY = (getHeight() - cardH) / 2;
            graphics2D.setColor(new Color(25, 25, 38, 245));
            graphics2D.fillRoundRect(cardX, cardY, cardW, cardH, 24, 24);
            graphics2D.setColor(new Color(255, 255, 255, 25));
            graphics2D.setStroke(new BasicStroke(1.5f));
            graphics2D.drawRoundRect(cardX, cardY, cardW, cardH, 24, 24);
            graphics2D.setFont(new Font("Segoe UI", Font.BOLD, 36));
            graphics2D.setColor(new Color(255, 75, 75));
            String gameOverText = "GAME OVER";
            FontMetrics fm = graphics2D.getFontMetrics();
            graphics2D.drawString(gameOverText, cardX + (cardW - fm.stringWidth(gameOverText)) / 2, cardY + 55);
            graphics2D.setFont(new Font("Segoe UI", Font.BOLD, 15));
            graphics2D.setColor(new Color(180, 180, 200));
            String statRound = "Round Reached: " + currentRound;
            String statPops = "Total Pops: " + totalPops;
            graphics2D.drawString(statRound, cardX + (cardW - graphics2D.getFontMetrics().stringWidth(statRound)) / 2, cardY + 105);
            graphics2D.drawString(statPops, cardX + (cardW - graphics2D.getFontMetrics().stringWidth(statPops)) / 2, cardY + 135);
            int btnW = 240;
            int btnH = 40;
            int btnX = cardX + (cardW - btnW) / 2;

            int restartY = cardY + 170;
            boolean hoverRestart = mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= restartY && mouseY <= restartY + btnH;
            graphics2D.setColor(hoverRestart ? new Color(37, 76, 245) : new Color(13, 43, 206));
            graphics2D.fillRoundRect(btnX, restartY, btnW, btnH, 12, 12);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Segoe UI", Font.BOLD, 14));
            String restartText = "RESTART GAME";
            graphics2D.drawString(restartText, btnX + (btnW - graphics2D.getFontMetrics().stringWidth(restartText)) / 2, restartY + 25);

            int menuY = cardY + 220;
            boolean hoverMenu = mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= menuY && mouseY <= menuY + btnH;
            graphics2D.setColor(hoverMenu ? new Color(80, 82, 105) : new Color(58, 59, 76));
            graphics2D.fillRoundRect(btnX, menuY, btnW, btnH, 12, 12);
            graphics2D.setColor(Color.WHITE);
            String menuText = "RETURN TO MENU";
            graphics2D.drawString(menuText, btnX + (btnW - graphics2D.getFontMetrics().stringWidth(menuText)) / 2, menuY + 25);
        }

    }
    /**
     * Renders the user interface panel at the bottom of the screen, including current stats
     * (money, lives, round, pops), tower shop selections, and wave controls.
     *
     * @param g the Graphics context
     */
    private void drawUI(Graphics g) {
        int uiHeight=150;
        int uiY=getHeight()-uiHeight;
        int width=getWidth();
        g.setColor(new Color(25,25,35,240));
        g.fillRect(0,uiY,width,uiHeight);

        g.setColor(new Color(13, 43, 206));
        g.fillRect(0, uiY, width, 4);

        Graphics2D g2 = (Graphics2D) g;

        int statsX = 30;
        g2.setFont(new Font("Segoe UI", Font.BOLD, 15));

        g2.setColor(new Color(255, 215, 0));
        g2.drawString("MONEY: $" + money, statsX, uiY + 35);

        g2.setColor(new Color(255, 75, 75));
        g2.drawString("LIVES: " + lives, statsX, uiY + 65);

        g2.setColor(new Color(65, 180, 255));
        g2.drawString("ROUND: " + currentRound, statsX, uiY + 95);

        g2.setColor(new Color(230, 100, 255));
        g2.drawString("POPS: " + totalPops, statsX, uiY + 125);

        g2.setColor(new Color(255, 255, 255, 30));
        g2.fillRect(180, uiY + 20, 2, 110);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.drawString("TOWER SHOP", 205, uiY + 25);
        int btnY = uiY + 35;
        int btnWidth = 100;
        int btnHeight = 95;
        int btn1X = 205;

        if (towerType == 0) {
            g2.setColor(new Color(13, 43, 206, 60));
            g2.fillRoundRect(btn1X, btnY, btnWidth, btnHeight, 10, 10);
            g2.setColor(new Color(13, 43, 206));
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawRoundRect(btn1X, btnY, btnWidth, btnHeight, 10, 10);
        } else {
            g2.setColor(new Color(255, 255, 255, 10));
            g2.fillRoundRect(btn1X, btnY, btnWidth, btnHeight, 10, 10);
            g2.setColor(new Color(255, 255, 255, 30));
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(btn1X, btnY, btnWidth, btnHeight, 10, 10);
        }
        g2.setColor(new Color(139, 69, 19));
        g2.fillRect(btn1X + 35, btnY + 15, 30, 30);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g2.drawString("Dart Tower", btn1X + 13, btnY + 65);
        g2.setColor(new Color(255, 215, 0));
        g2.drawString("$50", btn1X + 38, btnY + 82);
        int btn2X = 325;
        if (towerType == 1) {
            g2.setColor(new Color(13, 43, 206, 60));
            g2.fillRoundRect(btn2X, btnY, btnWidth, btnHeight, 10, 10);
            g2.setColor(new Color(13, 43, 206));
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawRoundRect(btn2X, btnY, btnWidth, btnHeight, 10, 10);
        } else {
            g2.setColor(new Color(255, 255, 255, 10));
            g2.fillRoundRect(btn2X, btnY, btnWidth, btnHeight, 10, 10);
            g2.setColor(new Color(255, 255, 255, 30));
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(btn2X, btnY, btnWidth, btnHeight, 10, 10);
        }
        g2.setColor(Color.BLACK);
        g2.fillRect(btn2X + 35, btnY + 15, 30, 30);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g2.drawString("Bomb Tower", btn2X + 11, btnY + 65);
        g2.setColor(new Color(255, 215, 0));
        g2.drawString("$150", btn2X + 35, btnY + 82);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(255, 255, 255, 30));
        g2.fillRect(445, uiY + 20, 2, 110);
        int ctrlX = 465;
        if (roundActive) {
            g2.setColor(new Color(65, 180, 255, 30));
            g2.fillRoundRect(ctrlX, uiY + 45, 110, 60, 12, 12);
            g2.setColor(new Color(65, 180, 255));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(ctrlX, uiY + 45, 110, 60, 12, 12);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            g2.drawString("WAVE ACTIVE", ctrlX + 12, uiY + 70);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.drawString("Defend the exit!", ctrlX + 17, uiY + 90);
        } else if (lives > 0) {
            g2.setColor(new Color(255, 195, 0));
            g2.fillRoundRect(ctrlX, uiY + 45, 110, 60, 12, 12);
            g2.setColor(new Color(255, 255, 255, 180));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(ctrlX, uiY + 45, 110, 60, 12, 12);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            g2.drawString("START WAVE", ctrlX + 14, uiY + 72);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.drawString("Press Space or Click", ctrlX + 8, uiY + 90);
        }
    }

    /**
     * Listens to action events from the swing timer to run the core game loop.
     * Updates spawned enemies, round state, and project entities once per tick.
     *
     * @param e the ActionEvent triggered by the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (lives <= 0)
                return;

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
    /**
     * Spawns the next enemy in the current round wave.
     * Higher rounds increase the chance of launching faster and tougher enemies.
     */
    private void spawnNextEnemy() {
        double rand = Math.random();
        if (currentRound > 5 && rand < 0.15)
            enemies.add(new LeadBall());
        else if (currentRound > 3 && rand < 0.3)
            enemies.add(new TankBall());
        else if (currentRound > 2 && rand < 0.4)
            enemies.add(new SpeedBall());
        else
            enemies.add(new NormalBall());
    }

    private void completeRound() {
        roundActive = false;
        enemiesToSpawn = 5 + (currentRound * 2);
        enemiesSpawned = 0;
        spawnDelay = Math.max(10, 60 - (currentRound * 2));
        money += 25;
    }
    /**
     * Updates coordinates, collision, and life cycles of all projectiles, explosion effects,
     * tower attacks, and active path enemies.
     */
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
                money += 1;
                totalPops++;
            } else if (enemy.hasReachedEnd()) {
                toRemove.add(enemy);
                lives -= 1;
            }
        }
        enemies.removeAll(toRemove);
    }
    /**
     * Starts the next round wave, incrementing the round counter and configuring spawning values.
     */
    private void startNextRound() {
        if (!roundActive && lives > 0) {
            currentRound++;
            enemiesToSpawn = 5 + (currentRound * 2);
            enemiesSpawned = 0;
            spawnCounter = 0;
            spawnDelay = Math.max(10, 60 - (currentRound * 2));
            roundActive = true;
        }
    }
    /**
     * Helper method to determine if a set of coordinates is close to the enemy path.
     * Prevents towers from being placed on top of the track.
     *
     * @param cx the click X coordinate
     * @param cy the click Y coordinate
     * @return true if coordinate lies within path width padding; false otherwise
     */
    private boolean isNearPath(int cx, int cy) {
        for (int i = 0; i < AbstractEnemy.pathX.length - 1; i++) {
            int ax = AbstractEnemy.pathX[i];
            int ay = AbstractEnemy.pathY[i];
            int bx = AbstractEnemy.pathX[i+1];
            int by = AbstractEnemy.pathY[i+1];
            int minX = Math.min(ax, bx);
            int maxX = Math.max(ax, bx);
            int minY = Math.min(ay, by);
            int maxY = Math.max(ay, by);
            if (cx >= minX - 45 && cx <= maxX + 45 && cy >= minY - 45 && cy <= maxY + 45) {
                return true;
            }
        }
        return false;
    }
    /**
     * Resets the entire game state to default starting values.
     */
    private void restartGame() {
        projectiles.clear();
        enemies.clear();
        towers.clear();
        explosions.clear();
        money = 50;
        lives = 20;
        spawnCounter = 0;
        currentRound = 0;
        enemiesSpawned = 0;
        enemiesToSpawn = 5;
        totalPops = 0;
        towerType = 0;
        spawnDelay = 60;
        roundActive = false;
    }
    /**
     * Disposes of the active playing board frame and returns the player to the main menu screen.
     */
    private void returnToMainMenu() {
        Window parent=SwingUtilities.getWindowAncestor(this);
        parent.dispose();
        new MainMenu().showTitleScreen();
    }
}


