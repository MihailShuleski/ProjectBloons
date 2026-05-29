import javax.swing.*;
import java.awt.*;
/**
 * A custom JPanel that renders a rounded, semi-translucent container panel
 * mimicking a frosted-glass (glassmorphism) aesthetic.
 */
public class GlassPanel extends JPanel {
    /**
     * Constructs a GlassPanel, configuring it to be non-opaque so the container
     * background shows through the translucent glass card.
     */
    public GlassPanel(){
        setOpaque(false);
    }
    /**
     * Overrides paintComponent to render a semi-transparent dark container card
     * with rounded corners and a thin translucent border.
     *
     * @param g the Graphics context used for drawing
     */
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
