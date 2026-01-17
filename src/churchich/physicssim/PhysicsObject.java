package churchich.physicssim;

import java.awt.*;

public abstract class PhysicsObject {
    protected int x;
    protected int y;
    protected Velocity velocity;

    public PhysicsObject() {
        this.velocity = new Velocity();
    }

    public abstract void draw(Graphics g);
    public abstract boolean contains(int mouseX, int mouseY);

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Velocity getVelocity() {
        return velocity;
    }

    // Update position based on velocity
    public void updatePosition() {
        x += (int) velocity.getVx();
        y += (int) velocity.getVy();
    }

    // Apply physics (gravity, friction, etc.)
    public void applyPhysics(double gravity, double friction) {
        // Apply gravity
        velocity.addVelocity(0, gravity);

        // Apply friction/air resistance
        velocity.applyDamping(friction);
    }
}