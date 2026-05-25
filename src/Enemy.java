import java.awt.*;

public class Enemy {
    String typeName;
    int x;
    int y;
    int health;
    int speed;
    int type;
    Color color;
    int[] pathX={0,200,200,400,400,700};
    int[] pathY={115,115,300,300,115,115};
    int targetWaypoint;

    public Enemy(int type) {
        this.type = type;
        this.y=pathY[0];
        this.x = pathX[0];
        if (type == 0) {
            typeName = "Normal";
            speed = 2;
            health = 1;
            color = Color.RED; // Red bloon
        } else if (type == 1) {
            typeName = "Speedy";
            speed = 4;
            health = 1;
            color = Color.BLUE; // Blue bloon
        } else if (type == 2) {
            typeName = "Tank";
            speed = 1;
            health = 3;
            color = Color.GREEN; // Green bloon
        }else if (type==3){
            typeName= "Lead";
            speed = 1;
            health = 2;
            color = Color.LIGHT_GRAY;

        } else {
            typeName = "Normal";
            speed = 2;
            health = 1;
            color = Color.RED;
        }
    }
    public void update(){
        if (targetWaypoint<pathX.length){
            int targetX=pathX[targetWaypoint];
            int targetY=pathY[targetWaypoint];
            double dx=targetX-x;
            double dy=targetY-y;
            double dist=Math.sqrt(dx*dx+dy*dy);

            if (dist<speed){
                x=targetX;
                y=targetY;
                targetWaypoint++;
            }else {
                x+= (dx/dist)* speed;
                y+= (dy/dist)*speed;
            }
        }else {
            x=800;
        }
    }
    public void draw(Graphics graphics){
        graphics.setColor(color);
        graphics.fillOval(x-15,y-15,30,30);
    }

}

