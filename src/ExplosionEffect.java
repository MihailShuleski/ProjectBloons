import java.awt.*;
/**
 * Represents a visual explosion effect that expands and fades out over time.
 * Spawned when explosive projectiles hit their target.
 */
public class ExplosionEffect {
    int x;
    int y;
    int radius=50;
    int maxRadius=60;
    int timer=30;
    /**
     * Constructs an ExplosionEffect at the specified center coordinates.
     *
     * @param x the center X coordinate
     * @param y the center Y coordinate
     */
    public ExplosionEffect(int x,int y){
        this.x=x;
        this.y=y;
    }
    /**
     * Updates the animation state, expanding the radius and decrementing the life timer.
     */
    public void update(){
        timer--;
        radius+=2;
    }
    /**
     * Renders the fading orange explosion circle on the screen.
     *
     * @param graphics the Graphics context used for drawing
     */
    public void draw(Graphics graphics){
        if (timer>0){
            Graphics2D graphics2D=(Graphics2D) graphics;
            graphics2D.setColor(new Color(255,100,0,Math.min(255,timer*8)));
            graphics2D.fillOval(x-radius,y-radius,radius*2,radius*2);
        }
    }
}
