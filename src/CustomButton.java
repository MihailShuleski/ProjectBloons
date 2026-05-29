import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * A custom styled JButton with support for normal, hover, and click colors,
 * rounded corners, and antialiased rendering.
 */
public class CustomButton extends JButton {
    private Color normalColor;
    private Color hoverColor;
    private Color clickColor;
    /**
     * Constructs a CustomButton with custom states.
     *
     * @param text        the label text of the button
     * @param normalColor the default color
     * @param hoverColor  the hover color
     * @param clickColor  the pressed/clicked color
     */
    public CustomButton(String text, Color normalColor, Color hoverColor, Color clickColor) {
        super(text);
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.clickColor = clickColor;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 15));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(clickColor);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getX() >= 0 && e.getX() < getWidth() && e.getY() >= 0 && e.getY() < getHeight()) {
                    setBackground(hoverColor);
                } else {
                    setBackground(normalColor);
                }
                repaint();
            }
        });
        setBackground(normalColor);
    }
    /**
     * Renders the custom button with anti-aliasing, rounded corners, and border.
     *
     * @param g the Graphics context used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

        g2.setColor(new Color(255, 255, 255, 40));
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

        g2.dispose();
        super.paintComponent(g);
    }
}
