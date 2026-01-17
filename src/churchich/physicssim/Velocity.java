package churchich.physicssim;

public class Velocity {
    private double vx;
    private double vy;

    public Velocity() {
        this.vx = 0.0;
        this.vy = 0.0;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public Velocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void addVelocity(double dvx, double dvy) {
        this.vx += dvx;
        this.vy += dvy;
    }

    public double getSpeed() {
        return Math.sqrt(vx * vx + vy * vy);
    }

    public void applyDamping(double damping) {
        this.vx *= damping;
        this.vy *= damping;
    }

    public void reset() {
        this.vx = 0;
        this.vy = 0;
    }

    @Override
    public String toString() {
        return String.format("Velocity(vx=%.2f, vy=%.2f, speed=%.2f)", vx, vy, getSpeed());
    }

    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }
}
