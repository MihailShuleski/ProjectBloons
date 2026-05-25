import java.awt.*;
import java.util.ArrayList;

public class Tower {
    int type;
    String typeName;
    int x;
    int y;
    int range;
    int cooldown = 0;
    int level = 1;


    public Tower(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        if (type == 0) {
            this.typeName = "Dart Tower";
            this.range = 120;
        } else {
            this.typeName = "Bomb Tower";
        }
    }
            public void update (ArrayList < Enemy > enemies, ArrayList < Projectile > projectiles){
                if (cooldown > 0) {
                    cooldown--;
                    return;
                }
                for (Enemy enemy : enemies) {
                    if (type==0&& enemy.typeName.equals("Lead"))
                        continue;
                    double dist = Math.sqrt(Math.pow(enemy.x - x, 2) + Math.pow(enemy.y - y, 2));
                    if (dist <= range) {
                        boolean isExplosive=(type==1);
                        projectiles.add(new Projectile(x, y, enemy,enemies,isExplosive));
                        if (type==0)
                            cooldown=40-(level*5);
                        else
                            cooldown=80-(level*8);
                        if (cooldown < 10)
                            cooldown = 10;
                        break;
                    }
                }
            }
            public void upgrade () {
                this.level++;
                this.range += 40;
            }
            public void draw (Graphics graphics){
                graphics.setColor(new Color(255,255,255,40));
                graphics.fillOval(x-range,y-range,range*2,range*2);
                graphics.setColor(new Color(0,0,0,100));
                graphics.drawOval(x-range,y-range,range*2,range*2);


                graphics.setColor(type==0 ? new Color(232, 137, 79):Color.BLACK);
                graphics.fillRect(x-15,y-15,30,30);

                if (level > 1) {
                    graphics.setColor(Color.YELLOW);
                    graphics.drawRect(x - 17, y - 17, 34, 34);
                    graphics.setFont(new Font("Arial",Font.BOLD,10));
                    graphics.drawString("Level: " + level, x - 12, y - 20);
                }
                graphics.setColor(new Color(0, 0, 0, 30));
                graphics.drawOval(x - range, y - range, range * 2, range * 2);
            }

        }

