import java.awt.*;
import java.util.ArrayList;
/**
 * An abstract base class representing a defensive tower.
 * Handles upgrading, range rendering, and cooling down.
 */
public abstract class AbstractTower implements GameObject {
    protected int x;
    protected int y;
    protected int range;
    protected int cooldown=0;
    protected int level=1;
    protected int type;
    protected String typeName;
    /**
     * Constructs an AbstractTower at the given position with specified range, type, and name.
     *
     * @param x        the X coordinate
     * @param y        the Y coordinate
     * @param range    the initial attack range of the tower
     * @param type     the type identifier
     * @param typeName the display name of the tower
     */
    public AbstractTower(int x,int y,int range,int type,String typeName){
        this.x = x;
        this.y = y;
        this.range = range;
        this.type = type;
        this.typeName = typeName;
    }
    /**
     * Upgrades the tower, increasing its level and attack range.
     */
    public void upgrade(){
        this.level++;
        this.range +=20;

    }
    /**
     * Attacks target enemies within range by creating projectiles.
     *
     * @param enemies     the list of active enemies on the field
     * @param projectiles the list of active projectiles to add new attacks to
     */
    public abstract void attack(ArrayList<AbstractEnemy>enemies, ArrayList<Projectile>projectiles);
    /**
     * Draws the specific visual body representation of the tower.
     *
     * @param graphics the Graphics context used for drawing
     */
    protected abstract void drawTowerBody(Graphics graphics);
    /**
     * Updates the tower logical state by decrementing the attack cooldown.
     */
    @Override
    public void update(){
        if (cooldown>0)
            cooldown--;
    }
    /**
     * Renders the tower, including its range preview circle and upgrade level tag.
     *
     * @param graphics the Graphics context used for drawing
     */
    @Override
    public void draw(Graphics graphics){
        graphics.setColor(new Color(255, 255, 255, 8));
        graphics.fillOval(x - range, y - range, range * 2, range * 2);
        graphics.setColor(new Color(0, 0, 0, 42));
        graphics.drawOval(x - range, y - range, range * 2, range * 2);

        drawTowerBody(graphics);

        if (level>1){
            graphics.setColor(Color.YELLOW);
            graphics.drawRect(x - 17, y - 17, 34, 34);
        }
    }
}
