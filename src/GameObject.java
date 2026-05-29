import java.awt.*;
/**
 * Represents any game object that can be updated and drawn in the game loop.
 */
public interface GameObject {
    void update();
    void draw(Graphics graphics);
}
