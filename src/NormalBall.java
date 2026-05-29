import java.awt.*;
/**
 * Represents a standard, red enemy balloon (Normal Ball).
 * Has default health and speed.
 */
public class NormalBall extends AbstractEnemy {
    public NormalBall(){
        super(2,2, Color.RED,"Normal");
    }
    @Override
    public int getMaxHealth(){
        return 2;
    }
}
