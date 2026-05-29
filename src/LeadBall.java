import java.awt.*;
/**
 * Represents a Lead Ball.
 * Lead Balls are immune to standard darts and require explosive attacks to be damaged.
 */
public class LeadBall extends AbstractEnemy {
    public LeadBall(){
        super(2,2, Color.lightGray,"Lead");
    }

    @Override
    public int getMaxHealth() {
        return 2;
    }
}
