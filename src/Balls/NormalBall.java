package Balls;

import java.awt.*;

public class NormalBall extends AbstractEnemy {
    public NormalBall(){
        super(2,2, Color.RED,"Normal");
    }
    @Override
    public int getMaxHealth(){
        return 2;
    }
}
