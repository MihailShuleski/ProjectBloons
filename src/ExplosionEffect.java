import java.awt.*;

public class ExplosionEffect {
    int x;
    int y;
    int radius=50;
    int maxRadius=60;
    int timer=30;

    public ExplosionEffect(int x,int y){
        this.x=x;
        this.y=y;
    }
    public void update(){
        timer--;
        radius+=2;
    }
    public void draw(Graphics graphics){
        if (timer>0){
            Graphics2D graphics2D=(Graphics2D) graphics;
            graphics2D.setColor(new Color(255,100,0,Math.min(255,timer*8)));
            graphics2D.fillOval(x-radius,y-radius,radius*2,radius*2);
        }
    }
}
