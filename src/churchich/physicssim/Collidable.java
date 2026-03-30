package churchich.physicssim;

public interface Collidable {
    double getCenterX();
    double getCenterY();
    double getX();
    double getY();
    void setPosition(double x, double y);
    Velocity getVelocity();
    double getMass();
    boolean overlaps(Collidable other);
    void resolveOverlap(Collidable other);
}
