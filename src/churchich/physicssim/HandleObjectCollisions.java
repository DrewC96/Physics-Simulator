package churchich.physicssim;

import java.util.List;

public class HandleObjectCollisions {

    // Coefficient of restitution (bounciness)
    private static final double RESTITUTION = 1.5;

    /**
     * Check and resolve all collisions between objects
     */
    public static void handleAllCollisions(List<PhysicsObject> objects) {
        // Check every pair of objects
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                PhysicsObject obj1 = objects.get(i);
                PhysicsObject obj2 = objects.get(j);

                // Handle circle-circle collisions
                if (obj1 instanceof Circle && obj2 instanceof Circle) {
                    handleCircleCollision((Circle) obj1, (Circle) obj2);                }
            }
        }
    }

    // TODO : fix collisions so that circles can not go through each other
    // TODO : add collisions for rectangles

    /**
     * Check if two circles are colliding and resolve the collision
     * Returns true if collision occurred
     */
    private static void handleCircleCollision(Circle circle1, Circle circle2) {
        // Cap speed before collision check to prevent tunneling
        capSpeed(circle1);
        capSpeed(circle2);


        double cx1 = circle1.getCenterX();
        double cy1 = circle1.getCenterY();
        double cx2 = circle2.getCenterX();
        double cy2 = circle2.getCenterY();

        double dx = cx2 - cx1;
        double dy = cy2 - cy1;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double minDistance = (circle1.getDiameter() + circle2.getDiameter()) / 2.0;


        if (distance < minDistance) {
            if (distance == 0) {
                dx = 1;
                dy = 0;
                distance = 0.001;
            }
            resolveCircleCollisions(circle1, circle2, dx, dy, distance, minDistance);
        }

    }

    public static void handleRectCollisions(Rect rect1, Rect rect2) {

    }

    /**
     * Resolve collision using conservation of momentum and energy
     */
    private static void resolveCircleCollisions(Circle circle1, Circle circle2,
                                                double dx, double dy,
                                                double distance, double minDistance) {
        // Step 1: Calculate normal BEFORE separation
        double nx = dx / distance;
        double ny = dy / distance;

        // Step 2: Separate
        separateCircles(circle1, circle2, dx, dy, distance, minDistance);

        double newDx = circle2.getCenterX() - circle1.getCenterX();
        double newDy = circle2.getCenterY() - circle1.getCenterY();
        double newDistance = Math.sqrt(newDx * newDx + newDy * newDy);

        // Step 4: Masses and velocities
        double mass1 = Mass.calculateMassCircle(circle1.getDiameter());
        double mass2 = Mass.calculateMassCircle(circle2.getDiameter());

        System.out.println("mass1: " + mass1);
        System.out.println("mass2: " + mass2);


        double v1x = circle1.getVelocity().getVx();
        double v1y = circle1.getVelocity().getVy();
        double v2x = circle2.getVelocity().getVx();
        double v2y = circle2.getVelocity().getVy();

        // Step 5: Relative velocity along normal
        double dvn = (v2x - v1x) * nx + (v2y - v1y) * ny;

        // Step 6: Skip if moving apart
        if (dvn >= 0) {
            return;
        }

        System.out.println("dvn: " + dvn);

        // Step 7: Apply impulse
        double impulse = -(1 + RESTITUTION) * dvn / (1.0 / mass1 + 1.0 / mass2);

        circle1.getVelocity().addVelocity( impulse * nx / mass1,  impulse * ny / mass1);
        circle2.getVelocity().addVelocity(-impulse * nx / mass2, -impulse * ny / mass2);

        double speed1 = Math.sqrt(Math.pow(circle1.getVelocity().getVx(), 2) + Math.pow(circle1.getVelocity().getVy(), 2));
        double speed2 = Math.sqrt(Math.pow(circle2.getVelocity().getVx(), 2) + Math.pow(circle2.getVelocity().getVy(), 2));
        System.out.println("post-impulse speed1: " + speed1 + " speed2: " + speed2);

        // Step 8: Guarantee separation - project out any remaining approach velocity
        double newDvn = (circle2.getVelocity().getVx() - circle1.getVelocity().getVx()) * nx +
                (circle2.getVelocity().getVy() - circle1.getVelocity().getVy()) * ny;
        if (newDvn < 0) {
            // Still approaching after impulse - force them apart
            circle1.getVelocity().addVelocity(newDvn * nx / 2, newDvn * ny / 2);
            circle2.getVelocity().addVelocity(-newDvn * nx / 2, -newDvn * ny / 2);
        }
    }

    /**
     * Separate overlapping circles
     */
    private static void separateCircles(Circle circle1, Circle circle2,
                                        double dx, double dy,
                                        double distance, double minDistance) {
        // Calculate overlap amount
        double overlap = (minDistance - distance) + 5.0;

        // Calculate separation direction (normalized)
        double nx = dx / distance;
        double ny = dy / distance;

        // Calculate masses for proportional separation
        double mass1 = Mass.calculateMassCircle(circle1.getDiameter());
        double mass2 = Mass.calculateMassCircle(circle2.getDiameter());
        double totalMass = mass1 + mass2;

        // Move circles apart proportionally to their masses
        // Heavier circles move less, lighter circles move more
        double separation1 = overlap * (mass2 / totalMass);
        double separation2 = overlap * (mass1 / totalMass);

        // Move circle1 away from circle2
        circle1.setPosition(
                circle1.getX() - (nx * separation1),
                circle1.getY() - (ny * separation1)
        );

        // Move circle2 away from circle1
        circle2.setPosition(
                circle2.getX() + (nx * separation2),
                circle2.getY() + (ny * separation2)
        );
    }

    private static void capSpeed(Circle circle) {
        double speed = Math.sqrt(Math.pow(circle.getVelocity().getVx(), 2) +
                Math.pow(circle.getVelocity().getVy(), 2));
        if (speed > 20.0) {
            double scale = 20.0 / speed;
            circle.getVelocity().setVelocity(
                    circle.getVelocity().getVx() * scale,
                    circle.getVelocity().getVy() * scale
            );
        }
    }
}