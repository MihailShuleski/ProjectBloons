import java.awt.*;
import java.util.ArrayList;
/**
 * Represents a projectile fired by a tower at a target enemy.
 * Can be normal (single-target dart) or explosive (radius damage bomb).
 */
public class Projectile implements GameObject{
    int x;
    int y;
    int speed=8;
    AbstractEnemy target;
    ArrayList<AbstractEnemy> allEnemies;
    boolean active=true;
    boolean isExplosive=false;
    double lastTargetX;
    double lastTargetY;
    /**
     * Constructs a Projectile fired from a tower toward a target.
     *
     * @param x           the starting X coordinate
     * @param y           the starting Y coordinate
     * @param target      the target enemy to track
     * @param allEnemies  the list of all current enemies
     * @param isExplosive whether the projectile should do splash damage
     */
    public Projectile(int x,int y,AbstractEnemy target,ArrayList<AbstractEnemy> allEnemies,boolean isExplosive){
        this.x=x;
        this.y=y;
        this.target=target;
        this.allEnemies=allEnemies;
        this.isExplosive=isExplosive;
        this.lastTargetX=target.getX();
        this.lastTargetY=target.getY();
        if (isExplosive)
            this.speed=4;
    }
    /**
     * Default update method implementing GameObject interface.
     * Does nothing since the projectile needs the explosions list parameter.
     */
    @Override
    public void update(){}
    /**
     * Updates the projectile's movement toward its target.
     * Handles hit registration, explosive blast wave generation, and target damage.
     *
     * @param explosions the list of active explosion effects to add to
     */
    public void update(ArrayList<ExplosionEffect> explosions) {
        if (!active)
            return;
        double tx=lastTargetX;
        double ty=lastTargetY;

        if (target.getHealth()>0){
            tx=target.getX();
            ty=target.getY();
            lastTargetX=tx;
            lastTargetY=ty;
        }
        double dx=tx-x;
        double dy=ty-y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) {
            if (isExplosive) {
                explosions.add(new ExplosionEffect(x,y));
                for (AbstractEnemy enemy : allEnemies) {
                    double explosionDistance = Math.sqrt(Math.pow(enemy.getX() - x, 2) + Math.pow(enemy.getY() - y, 2));
                    if (explosionDistance < 55) {
                        enemy.takeDamage(1);
                    }
                }
            } else if (target.getHealth()>0){
                target.takeDamage(1);
            }
            active = false;
        } else {
            x += (dx / dist) * speed;
            y += (dy / dist) * speed;
        }
    }
    /**
     * Renders the projectile on screen if it is active.
     *
     * @param graphics the Graphics context used for drawing
     */
    @Override
    public void draw(Graphics graphics){
        if (active){
            graphics.setColor(isExplosive ? Color.BLACK:Color.darkGray);
            int size= isExplosive ? 10:6;
            graphics.fillOval(x-size/2,y-size/2,size,size);
        }
    }
}
