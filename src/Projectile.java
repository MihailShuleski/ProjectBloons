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

    public Projectile(int x,int y,Enemy target,ArrayList<Enemy> allEnemies,boolean isExplosive){
        this.x=x;
        this.y=y;
        this.target=target;
        this.allEnemies=allEnemies;
        this.isExplosive=isExplosive;
        if (isExplosive)
            this.speed=5;
    }

    public void update() {
        if (!active)
            return;
        if (target.health <= 0) {
            active = false;
            return;
        }
        double dx = target.x - x;
        double dy = target.y - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) {
            if (isExplosive) {
                for (Enemy enemy : allEnemies) {
                    double explosionDistance = Math.sqrt(Math.pow(enemy.x - x, 2) + Math.pow(enemy.y - y, 2));
                    if (explosionDistance < 50) {
                        enemy.health--;
                    }
                }
            } else {
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
