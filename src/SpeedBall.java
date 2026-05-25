import java.awt.*;

public class SpeedBall extends AbstractEnemy {
    public SpeedBall(){
        super(1,4, Color.BLUE,"Speedy");
    }

    @Override
    public int getMaxHealth() {
        return 1;
    }
}
