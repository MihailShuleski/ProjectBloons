import java.awt.*;
import java.util.ArrayList;

public class Projectile {
    int x;
    int y;
    int speed=8;
    Enemy target;
    ArrayList<Enemy> allEnemies;
    boolean active=true;
    boolean isExplosive=false;
    double lastTargetX;
    double lastTargetY;

    public Projectile(int x,int y,Enemy target,ArrayList<Enemy> allEnemies,boolean isExplosive){
        this.x=x;
        this.y=y;
        this.target=target;
        this.allEnemies=allEnemies;
        this.isExplosive=isExplosive;
        this.lastTargetX=target.x;
        this.lastTargetY=target.y;
        if (isExplosive)
            this.speed=4;
    }

    public void update(ArrayList<ExplosionEffect> explosions) {
        if (!active)
            return;
        double tx=lastTargetX;
        double ty=lastTargetY;

        if (target.health>0){
            tx=target.x;
            ty=target.y;
            lastTargetX=tx;
            lastTargetY=ty;
        }
        double dx=tx-x;
        double dy=ty-y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) {
            if (isExplosive) {
                explosions.add(new ExplosionEffect(x,y));
                for (Enemy enemy : allEnemies) {
                    double explosionDistance = Math.sqrt(Math.pow(enemy.x - x, 2) + Math.pow(enemy.y - y, 2));
                    if (explosionDistance < 55) {
                        enemy.health--;
                    }
                }
            } else if (target.health>0){
                target.health--;
            }
            active = false;
        } else {
            x += (dx / dist) * speed;
            y += (dy / dist) * speed;
        }
    }
    public void draw(Graphics graphics){
        if (active){
            graphics.setColor(isExplosive ? Color.BLACK:Color.darkGray);
            int size= isExplosive ? 10:6;
            graphics.fillOval(x-size/2,y-size/2,size,size);
        }
    }
}
