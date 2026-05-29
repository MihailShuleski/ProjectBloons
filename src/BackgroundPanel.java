import javax.swing.*;
import java.awt.*;
/**
 * A custom JPanel that paints a premium, modern dark gradient background
 * with decorative translucent blur spheres.
 */
public class BackgroundPanel extends JPanel {
    /**
     * Overrides paintComponent to render a custom modern color gradient
     * with decorative soft spheres.
     *
     * @param g the Graphics context used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(20, 24, 43),
                0, getHeight(), new Color(42, 28, 59)
        );
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(new Color(255, 255, 255, 3));
        g2.fillOval(50, 60, 100, 100);
        g2.fillOval(380, 220, 70, 70);
        g2.fillOval(90, 360, 120, 120);
        g2.setColor(new Color(65, 180, 255, 18));
        g2.fillOval(-120, -120, 320, 320);
        g2.setColor(new Color(230, 100, 255, 12));
        g2.fillOval(getWidth() - 200, getHeight() - 200, 320, 320);
        g2.dispose();
    }
}
