import java.awt.*;

public abstract class AbstractEnemy implements GameObject {
    protected double x;
    protected double y;
    protected int health;
    protected int speed;
    protected Color color;
    protected String typename;

    protected static int[] pathX={0,200,200,400,400,700};
    protected static int[] pathY={115,115,300,300,115,115};
    protected int targetWaypoint = 1;

    public AbstractEnemy(int health,int speed,Color color,String typename){
        this.x=pathX[0];
        this.y=pathY[0];
        this.health=health;
        this.speed=speed;
        this.color=color;
        this.typename=typename;
    }

    @Override
    public void update(){
        if (targetWaypoint<pathX.length){
            int targetX=pathX[targetWaypoint];
            int targetY=pathY[targetWaypoint];
            double dx= targetX-x;
            double dy=targetY-y;
            double dist = Math.sqrt(dx*dx+dy*dy);

            if (dist < speed){
                x=targetX;
                y=targetY;
                targetWaypoint++;
            }else {
                x+= (dx/dist) * speed;
                y += (dy/dist) * speed;
            }
        }else {
            x=800;
        }
    }
    @Override
    public void draw(Graphics graphics){
        graphics.setColor(color);
        graphics.fillOval((int) (x-15),(int)y-15,30,5);

        if (health>1){
            graphics.setColor(Color.red);
            graphics.fillRect((int) (x-15), (int) (y-25),30,5);
            graphics.setColor(Color.GREEN);
            int barWidth= (int) (30 * (health / (double) getMaxHealth()));
            graphics.fillRect((int) x-15,(int) y-25,barWidth,5);
        }
    }

    public abstract int getMaxHealth();

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public String getTypename() {
        return typename;
    }
    public void takeDamage(int damage){
        this.health -=damage;
    }
}
