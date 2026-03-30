package churchich.physicssim;

import java.awt.*;

public class Rect extends PhysicsObject {
    public final int width;
    public final int height;
    public final Color color;

    public Rect(int x, int y, int width, int height, Color color) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, width, height);
    }

    @Override
    public boolean contains(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    @Override
    public double getDiameter() {
        return Math.hypot(width, height);
    }
}
