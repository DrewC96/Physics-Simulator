package churchich.physicssim;

import java.awt.*;

public class Rect extends PhysicsObject {
    private final int width;
    private final int height;
    private final Color color;

    public Rect(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = (int) width;
        this.height = (int) height;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x,(int) y, width, height);
    }

    @Override
    public boolean contains(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}