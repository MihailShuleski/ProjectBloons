import java.awt.*;

public class Projectile {
    int x;
    int y;
    int speed=8;
    Enemy target;
    boolean active=true;

    public Projectile(int x,int y,Enemy target){
        this.x=x;
        this.y=y;
        this.target=target;
    }

    public void update(){
        if (!active)
            return;
        if (target.health<=0) {
            active=false;
            return;
        }
        double dx=target.x-x;
        double dy=target.y-y;
        double dist =Math.sqrt(dx*dx+dy*dy);

        if (dist<speed){
            target.health--;
        }else {
            x+=(dx/dist)*speed;
            y+=(dy/dist)*speed;
        }
    }
    public void draw(Graphics graphics){
        if (active){
            graphics.setColor(Color.black);
            graphics.fillOval((int) x-3,(int)y-3,6,6);
        }
    }
}
