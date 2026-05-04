import java.awt.*;

public class Enemy {
    String typeName;
    int x;
    int y;
    int health;
    int speed;
    int type;
    public Enemy(int type){
        if (type==0){
            typeName="Normal";
            speed=2;
            health=3;
        } else if (type==1) {
            typeName="Speedy";
            speed=4;
            health=2;
        } else if (type==2) {
            typeName="Tank";
            speed=1;
            health=7;
        }
    }
    public void update(){
        x+=speed;
    }
    public void draw(Graphics graphics){
        graphics.fillOval(x,y,30,30);
    }
}
