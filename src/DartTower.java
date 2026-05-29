import java.awt.*;
import java.util.ArrayList;

public class DartTower extends AbstractTower {
    public DartTower(int x, int y) {
        super(x, y, 120, 0, "Dart Tower");
    }


    @Override
    public void attack(ArrayList<AbstractEnemy> enemies, ArrayList<Projectile> projectiles) {
        if (cooldown > 0)
            return;

        for (AbstractEnemy enemy : enemies) {
            // Lead immunity
            if (enemy.getTypeName().equals("Lead"))
                continue;

            double dist = Math.sqrt(Math.pow(enemy.getX() - x, 2) + Math.pow(enemy.getY() - y, 2));
            if (dist <= range) {
                projectiles.add(new Projectile(x, y, enemy, enemies, false));
                cooldown = 40 - (level * 5);
                if (cooldown < 10)
                    cooldown = 10;
                break;
            }
        }
    }

    @Override
    protected void drawTowerBody(Graphics graphics) {
        graphics.setColor(new Color(139, 69, 19));
        graphics.fillRect(x - 15, y - 15, 30, 30);

    }
}
