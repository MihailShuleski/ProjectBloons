import java.awt.*;
/**
 * Represents a fast, blue enemy balloon (Speedy Ball).
 * Moves twice as fast as normal balloons but has lower health.
 */
public class SpeedBall extends AbstractEnemy {
    public SpeedBall(){
        super(1,4, Color.BLUE,"Speedy");
    }

    @Override
    public int getMaxHealth() {
        return 1;
    }
}
