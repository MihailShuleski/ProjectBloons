import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractTower implements GameObject {
    protected int x;
    protected int y;
    protected int range;
    protected int cooldown=0;
    protected int level=1;
    protected int type;
    protected String typeName;

    public AbstractTower(int x,int y,int range,int type,String typeName){
        this.x = x;
        this.y = y;
        this.range = range;
        this.type = type;
        this.typeName = typeName;
    }
    public void upgrade(){
        this.level++;
        this.range +=20;

    }
    public abstract void attack(ArrayList<AbstractEnemy>enemies, ArrayList<Projectile>projectiles);

    protected abstract void drawTowerBody(Graphics graphics);

    @Override
    public void update(){
        if (cooldown>0)
            cooldown--;
    }

    @Override
    public void draw(Graphics graphics){
        graphics.setColor(new Color(255, 255, 255, 40));
        graphics.fillOval(x - range, y - range, range * 2, range * 2);
        graphics.setColor(new Color(0, 0, 0, 100));
        graphics.drawOval(x - range, y - range, range * 2, range * 2);

        drawTowerBody(graphics);

        if (level>1){
            graphics.setColor(Color.YELLOW);
            graphics.drawRect(x - 17, y - 17, 34, 34);
            graphics.setFont(new Font("Arial", Font.BOLD, 10));
            graphics.drawString("LVL" + level, x - 12, y - 20);
        }
    }
}
