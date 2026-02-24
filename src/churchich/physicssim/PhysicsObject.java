package churchich.physicssim;

import java.awt.*;

public abstract class PhysicsObject {
    protected double x;
    protected double y;
    protected Velocity velocity;

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
        x +=  velocity.getVx();
        y +=  velocity.getVy();
    }

    // Apply physics (gravity, friction, etc.)
    public void applyPhysics(double gravity, double friction) {
        // Apply gravity
        velocity.addVelocity(0, gravity);

        // Apply friction/air resistance
        velocity.applyDamping(friction);
    }
}