package churchich.physicssim;

import java.awt.*;

public abstract class PhysicsObject {
    protected double x;
    protected double y;
    protected Velocity velocity;
    public abstract double getDiameter();

    public PhysicsObject() {
        this.velocity = new Velocity();
    }

    public abstract void draw(Graphics g);
    public abstract boolean contains(double mouseX, double mouseY);

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }


    public Velocity getVelocity() {
        return velocity;
    }

    // Update position based on velocity
    public void updatePosition() {
        x += velocity.getVx();
        y += velocity.getVy();
    }

    // Apply physics (gravity, friction, etc.)
    public void applyPhysics(double gravity, double friction) {
        velocity.addVelocity(0, gravity);
        velocity.setVelocity(velocity.getVx() * friction, velocity.getVy());

        // Kill tiny velocities to prevent micro-vibration
        if (Math.abs(velocity.getVx()) < 0.01) velocity.setVelocity(0, velocity.getVy());
        if (Math.abs(velocity.getVy()) < 0.01) velocity.setVelocity(velocity.getVx(), 0);
    }
}