package churchich.physicssim;

import java.awt.*;

public class Rect extends PhysicsObject implements Collidable {
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
    public boolean overlaps(Collidable other) {
        if (other instanceof Rect r) {
            return getX() < r.getX() + r.width &&
                    getX() + width > r.getX() &&
                    getY() < r.getY() + r.height &&
                    getY() + height > r.getY();
        }
        if (other instanceof Circle c) {
            // find the closest point on rect to circle center
            double closestX = Math.max(getX(), Math.min(c.getCenterX(), getX() + width));
            double closestY = Math.max(getY(), Math.min(c.getCenterY(), getY() + height));
            double distance = Math.hypot(c.getCenterX() - closestX, c.getCenterY() - closestY);
            return distance < c.getDiameter() / 2.0;
        }
        return false;
    }

    @Override
    public void resolveOverlap(Collidable other) {
        if (other instanceof Rect r) {
            double overlapX = Math.min(getX() + width, r.getX() + r.width) - Math.max(getX(), r.getX());
            double overlapY = Math.min(getY() + height, r.getY() + r.height) - Math.max(getY(), r.getY());

            // push out along the axis of the least overlap
            if (overlapX < overlapY) {
                double push = overlapX / 2.0;
                if (getX() < r.getX()) {
                    setPosition(getX() - push, getY());
                    r.setPosition(r.getX() + push, r.getY());
                } else {
                    setPosition(getX() + push, getY());
                    r.setPosition(r.getX() - push, r.getY());
                }
            } else {
                double push = overlapY / 2.0;
                if (getY() < r.getY()) {
                    setPosition(getX(), getY() - push);
                    r.setPosition(r.getX(), r.getY() + push);
                } else {
                    setPosition(getX(), getY() + push);
                    r.setPosition(r.getX(), r.getY() - push);
                }
            }
        }
    }

    @Override
    public double getDiameter() {
        return Math.hypot(width, height);
    }

    @Override
    public double getMass() { return Mass.calculateMassRect(width, height); }

    public double getCenterX() {
        return x + (double) width / 2;
    }

    public double getCenterY() {
        return y + (double) height / 2;
    }
}
