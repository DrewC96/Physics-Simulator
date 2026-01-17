package churchich.physicssim;

import java.awt.*;

public class Circle extends PhysicsObject {
    private int diameter;
    private Color color;

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
        g.fillOval(x, y, diameter, diameter);
    }

    @Override
    public boolean contains(int mouseX, int mouseY) {
        int ballCenterX = x + diameter / 2;
        int ballCenterY = y + diameter / 2;

        double distance = Math.sqrt(Math.pow(mouseX - ballCenterX, 2) +
                Math.pow(mouseY - ballCenterY, 2));

        return distance <= diameter / 2;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getCenterX() {
        return x + diameter / 2;
    }

    public int getCenterY() {
        return y + diameter / 2;
    }
}