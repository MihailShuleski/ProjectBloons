import java.awt.*;
/**
 * Represents a slow but highly resilient green enemy balloon (Tank Ball).
 * Has high health but moves slowly.
 */
public class TankBall extends AbstractEnemy {
    public TankBall(){
        super(3,1, Color.GREEN,"Tank");
    }

    @Override
    public int getMaxHealth() {
        return 3;
    }
}
