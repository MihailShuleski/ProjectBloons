import java.awt.*;
import java.util.ArrayList;

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
    @Override
    public void update(){}

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
    @Override
    public void draw(Graphics graphics){
        if (active){
            graphics.setColor(isExplosive ? Color.BLACK:Color.darkGray);
            int size= isExplosive ? 10:6;
            graphics.fillOval(x-size/2,y-size/2,size,size);
        }
    }
}
