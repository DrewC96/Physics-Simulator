package churchich.physicssim;

import java.awt.*;

public class Circle extends PhysicsObject implements Collidable {
    private final int diameter;
    private final Color color;

    public Circle(int x, int y, int diameter, Color color) {
        super();
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, diameter, diameter);
    }

    @Override
    public boolean contains(double mouseX, double mouseY) {
        int ballCenterX = (int) x + diameter / 2;
        int ballCenterY = (int) y + diameter / 2;

        double distance = Math.sqrt(Math.pow(mouseX - ballCenterX, 2) +
                Math.pow(mouseY - ballCenterY, 2));

        return distance <= (double) diameter / 2;
    }

    @Override
    public boolean overlaps(Collidable other) {
        if (other instanceof Circle c) {
            double distance = Math.hypot(c.getCenterX() - getCenterX(), c.getCenterY() - getCenterY());
            return distance < (getDiameter() + c.getDiameter()) / 2.0;
        }
        if (other instanceof Rect r) {
            // delegate to Rect's implementation to avoid duplicating the logic
            return r.overlaps(this);
        }
        return false;
    }

    @Override
    public void resolveOverlap(Collidable other) {
        if (other instanceof Circle c) {
            double dx = c.getCenterX() - getCenterX();
            double dy = c.getCenterY() - getCenterY();
            double distance = Math.hypot(dx, dy);
            if (distance == 0) { dx = 1; distance = 0.001; }

            double overlap = (getDiameter() + c.getDiameter()) / 2.0 - distance;
            double nx = dx / distance;
            double ny = dy / distance;

            setPosition(getX() - nx * overlap / 2, getY() - ny * overlap / 2);
            c.setPosition(c.getX() + nx * overlap / 2, c.getY() + ny * overlap / 2);
        }
        if (other instanceof Rect r) {
            r.resolveOverlap(this);
        }
    }

    @Override
    public double getMass() { return Mass.calculateMassCircle(getDiameter()); }


    public double getDiameter() {
        return diameter;
    }

    public double getCenterX() {
        return x + (double) diameter / 2;
    }

    public double getCenterY() {
        return y + (double) diameter / 2;
    }
}