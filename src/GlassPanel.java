import javax.swing.*;
import java.awt.*;

public class GlassPanel extends JPanel {
    public GlassPanel(){
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(new Color(25, 25, 38, 175));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);

        g2.setColor(new Color(255, 255, 255, 25));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);

        g2.dispose();
    }
}
