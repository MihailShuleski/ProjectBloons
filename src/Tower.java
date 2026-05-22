import java.awt.*;
import java.util.ArrayList;

public class Tower {
    int type;
    String typeName;
    int x;
    int y;
    int range;
    int cooldown=0;
    int level=1;


    public Tower(int x,int y){
        this.x=x;
        this.y=y;
        this.range=100;
        this.type=0;
        this.typeName="Basic Tower";
}
    public void update(ArrayList<Enemy> enemies,ArrayList<Projectile> projectiles){
        if(cooldown >0){
            cooldown--;
            return;
        }
        for (Enemy enemy:enemies){
            double dist=Math.sqrt(Math.pow(enemy.x-x,2)+Math.pow(enemy.y-y,2));
            if (dist<=range){
                projectiles.add(new Projectile(x,y,enemy));
                cooldown=60-level*10;
                if (cooldown<10)
                    cooldown=10;
                break;
            }
        }
    }
    public void upgrade(){
        this.level++;
        this.range+=40;
    }
    public void draw(Graphics graphics){
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(x-20,y-20,40,40);
        graphics.setColor(new Color(0,0,0,50));
        graphics.drawOval(x-range,y-range,range*2,range*2);
    }

}
