package churchich.physicssim;

import java.util.List;

public class HandleObjectCollisions {

    private static final double RESTITUTION = 1.5;

    public static void handleAllCollisions(List<PhysicsObject> objects) {
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                PhysicsObject obj1 = objects.get(i);
                PhysicsObject obj2 = objects.get(j);

                if (obj1 instanceof Collidable c1 && obj2 instanceof Collidable c2) {
                    handleCollision(c1, c2);
                }
            }
        }
    }

    private static void handleCollision(Collidable c1, Collidable c2) {
        capSpeed(c1);
        capSpeed(c2);

        if (!c1.overlaps(c2)) return;

        double nx, ny;

        if (c1 instanceof Rect r1 && c2 instanceof Rect r2) {
            // use overlap axis instead of center-to-center
            double overlapX = Math.min(r1.getX() + r1.width, r2.getX() + r2.width) - Math.max(r1.getX(), r2.getX());
            double overlapY = Math.min(r1.getY() + r1.height, r2.getY() + r2.height) - Math.max(r1.getY(), r2.getY());

            if (overlapX < overlapY) {
                nx = r1.getX() < r2.getX() ? 1 : -1;
                ny = 0;
            } else {
                nx = 0;
                ny = r1.getY() < r2.getY() ? 1 : -1;
            }
        } else {
            // circle-circle or circle-rect: center-to-center normal is fine
            double dx = c2.getCenterX() - c1.getCenterX();
            double dy = c2.getCenterY() - c1.getCenterY();
            double distance = Math.hypot(dx, dy);
            if (distance == 0) { dx = 1; distance = 0.001; }
            nx = dx / distance;
            ny = dy / distance;
        }

        double mass1 = c1.getMass();
        double mass2 = c2.getMass();

        double v1x = c1.getVelocity().getVx(), v1y = c1.getVelocity().getVy();
        double v2x = c2.getVelocity().getVx(), v2y = c2.getVelocity().getVy();

        double dvn = (v2x - v1x) * nx + (v2y - v1y) * ny;
        if (dvn >= 0) return;

        double impulse = -(1 + RESTITUTION) * dvn / (1.0 / mass1 + 1.0 / mass2);

        c1.getVelocity().addVelocity( impulse * nx / mass1,  impulse * ny / mass1);
        c2.getVelocity().addVelocity(-impulse * nx / mass2, -impulse * ny / mass2);

        c1.resolveOverlap(c2);
    }

    private static void capSpeed(Collidable c) {
        double speed = Math.hypot(c.getVelocity().getVx(), c.getVelocity().getVy());
        if (speed > 20.0) {
            double scale = 20.0 / speed;
            c.getVelocity().setVelocity(
                    c.getVelocity().getVx() * scale,
                    c.getVelocity().getVy() * scale
            );
        }
    }
}