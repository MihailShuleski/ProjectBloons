import java.awt.*;
import java.util.ArrayList;

public class Tower {
    int type;
    String typeName;
    int x;
    int y;
    int range;
    int cooldown=0;


    public Tower(int x,int y){
        this.x=x;
        this.y=y;
        this.range=100;
        this.type=0;
        this.typeName="Basic Tower";
}
    public void update(ArrayList<Enemy> enemies){
        if(cooldown >0)
            cooldown--;
        for (Enemy enemy:enemies){
            double dist=Math.sqrt(Math.pow(enemy.x-x,2)+Math.pow(enemy.y-y,2));
            if (dist<=range && cooldown==0){
                enemy.health -=1;
                cooldown=60;
                break;
            }
        }
    }
    public void draw(Graphics graphics){
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(x-20,y-20,40,40);
        graphics.setColor(new Color(0,0,0,50));
        graphics.drawOval(x-range,y-range,range*2,range*2);
    }

}
