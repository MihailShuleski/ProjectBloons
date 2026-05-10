import java.awt.*;

public class Enemy {
    String typeName;
    int x;
    int y;
    int health;
    int speed;
    int type;
    Color color;

    public Enemy(int type) {
        this.type = type;
        this.y = 100; // spawn in the middle of our path
        this.x = 0;
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
        } else {
            typeName = "Normal";
            speed = 2;
            health = 1;
            color = Color.RED;
        }
    }
    public void update(){
        x +=speed;
    }
    public void draw(Graphics graphics){
        graphics.setColor(color);
        graphics.fillOval(x,y-15,30,30);
    }

}

