import java.awt.*;
import java.util.ArrayList;
/**
 * Represents a Bomb Tower (Bomb Shooter).
 * Fires slow, explosive projectiles that deal area-of-effect splash damage.
 * The only way to damage and pop Lead Balls.
 */
public class BombTower extends AbstractTower{
    /**
     * Constructs a BombTower at the specified coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public BombTower(int x, int y) {
        super(x, y, 100, 1, "Bomb Tower");
    }
    /**
     * Attacks the first enemy in range by firing an explosive projectile.
     *
     * @param enemies     the list of active enemies on the field
     * @param projectiles the list of active projectiles to add new attacks to
     */
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
    /**
     * Draws the visual body of the Bomb Tower (dark gray rectangle).
     *
     * @param graphics the Graphics context used for drawing
     */
    @Override
    protected void drawTowerBody(Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(x - 15, y - 15, 30, 30);

    }
}
