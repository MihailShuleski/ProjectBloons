package Balls;

import java.awt.*;

public class LeadBall extends AbstractEnemy {
    public LeadBall(){
        super(2,2, Color.lightGray,"Lead");

    }

    @Override
    public int getMaxHealth() {
        return 2;
    }
}
