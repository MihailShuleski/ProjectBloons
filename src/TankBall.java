import java.awt.*;

public class TankBall extends AbstractEnemy {
    public TankBall(){
        super(3,1, Color.GREEN,"Tank");
    }

    @Override
    public int getMaxHealth() {
        return 3;
    }
}
