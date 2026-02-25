package churchich.physicssim;

import java.awt.*;

public class Circle extends PhysicsObject {
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