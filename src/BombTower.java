import java.awt.*;
import java.util.ArrayList;

public class BombTower extends AbstractTower{
    public BombTower(int x, int y) {
        super(x, y, 100, 1, "Bomb Tower");
    }
    @Override
    public void attack(ArrayList<AbstractEnemy> enemies, ArrayList<Projectile> projectiles) {
        if (cooldown > 0)
            return;

        for (AbstractEnemy enemy : enemies) {
            double dist = Math.sqrt(Math.pow(enemy.getX() - x, 2) + Math.pow(enemy.getY() - y, 2));
            if (dist <= range) {
                projectiles.add(new Projectile(x, y, enemy, enemies, true));
                cooldown = 80 - (level * 8);
                if (cooldown < 10)
                    cooldown = 10;
                break;
            }
        }

    }

    @Override
    protected void drawTowerBody(Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(x - 15, y - 15, 30, 30);

    }
}
