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
                    double dist = Math.sqrt(Math.pow(enemy.x - x, 2) + Math.pow(enemy.y - y, 2));
                    if (dist <= range) {
                        projectiles.add(new Projectile(x, y, enemy));
                        cooldown = 60 - level * 10;
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
                graphics.setColor(Color.DARK_GRAY);
                graphics.fillRect(x - 20, y - 20, 40, 40);

                if (level > 1) {
                    graphics.setColor(Color.YELLOW);
                    graphics.drawRect(x - 22, y - 22, 44, 44);
                    graphics.drawString("Level: " + level, x - 15, y + 35);
                }
                graphics.setColor(new Color(0, 0, 0, 50));
                graphics.drawOval(x - range, y - range, range * 2, range * 2);
            }

        }

