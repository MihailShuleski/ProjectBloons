import java.awt.*;
/**
 * An abstract base class representing an enemy balloon (ball) in the game.
 * Handles path movement, drawing, health tracking, and waypoint logic.
 */
public abstract class AbstractEnemy implements GameObject {
    protected double x;
    protected double y;
    protected int health;
    protected int speed;
    protected Color color;
    protected String typeName;

    protected static int[] pathX={-40, 480, 480, 80, 80, 480, 480, -40};
    protected static int[] pathY={80, 80, 220, 220, 360, 360, 500, 500};
    protected int targetWaypoint = 1;
    /**
     * Constructs an AbstractEnemy with specified health, speed, color, and type name.
     *
     * @param health   the starting health of the enemy
     * @param speed    the movement speed of the enemy
     * @param color    the display color of the enemy
     * @param typeName the name identifying the enemy type
     */
    public AbstractEnemy(int health,int speed,Color color,String typeName){
        this.x=pathX[0];
        this.y=pathY[0];
        this.health=health;
        this.speed=speed;
        this.color=color;
        this.typeName=typeName;
    }
    /**
     * Updates the enemy's position, moving it toward the current target waypoint.
     * If a waypoint is reached, it advances to the next waypoint.
     */
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
    /**
     * Renders the enemy on screen. If the enemy is damaged, draws a health bar above it.
     *
     * @param graphics the Graphics context used for drawing
     */
    @Override
    public void draw(Graphics graphics){
        graphics.setColor(color);
        graphics.fillOval((int) (x-15),(int)y-15,30,30);

        if (health< getMaxHealth()){
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

    public String getTypeName() {
        return typeName;
    }
    public void takeDamage(int damage){
        this.health -=damage;
    }
    /**
     * Checks if the enemy has traversed all waypoints and reached the exit of the path.
     *
     * @return true if the enemy has reached the end of the path; false otherwise
     */
    public boolean hasReachedEnd(){
        return targetWaypoint>=pathX.length;
    }
}
