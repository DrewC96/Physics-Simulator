package churchich.physicssim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleObjectCollisions {

    // Coefficient of restitution (bounciness)
    private static final double RESTITUTION = 0.8;

    // Collision cooldown tracking
    private static final Map<String, Long> collisionCooldowns = new HashMap<>();
    private static final long COOLDOWN_MS = 50; // 100ms between collisions for same pair

    /**
     * Check and resolve all collisions between objects
     */
    public static void handleAllCollisions(List<PhysicsObject> objects) {
        long currentTime = System.currentTimeMillis();

        // Check every pair of objects
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                PhysicsObject obj1 = objects.get(i);
                PhysicsObject obj2 = objects.get(j);

                // Create unique key for this pair
                String pairKey = i + "-" + j;

                // Check if collision is on cooldown
                Long lastCollision = collisionCooldowns.get(pairKey);
                if (lastCollision != null && currentTime - lastCollision < COOLDOWN_MS) {
                    continue; // Skip this pair, still on cooldown
                }

                // Handle circle-circle collisions
                if (obj1 instanceof Circle && obj2 instanceof Circle) {
                    boolean collided = handleCircleCollision((Circle) obj1, (Circle) obj2);

                    // If collision occurred, add cooldown
                    if (collided) {
                        collisionCooldowns.put(pairKey, currentTime);
                    }
                }
            }
        }

        // Clean up old cooldowns (optional, prevents memory leak)
        collisionCooldowns.entrySet().removeIf(entry ->
                currentTime - entry.getValue() > COOLDOWN_MS * 2
        );
    }

    // TODO : fix collisions so that circles can not go through each other
    // TODO : add collisions for rectangles

    /**
     * Check if two circles are colliding and resolve the collision
     * Returns true if collision occurred
     */
    private static boolean handleCircleCollision(Circle circle1, Circle circle2) {
        // Get centers
        int cx1 = circle1.getCenterX();
        int cy1 = circle1.getCenterY();
        int cx2 = circle2.getCenterX();
        int cy2 = circle2.getCenterY();

        // Calculate distance between centers
        double dx = cx2 - cx1;
        double dy = cy2 - cy1;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Calculate minimum distance for collision (sum of radii)
        double minDistance = (circle1.getDiameter() + circle2.getDiameter()) / 2.0;

        // Check if circles are colliding
        if (distance < minDistance && distance > 0) {
            // Circles are overlapping - resolve collision
            resolveCollision(circle1, circle2, dx, dy, distance, minDistance);
            return true; // Collision occurred
        }

        return false; // No collision
    }

    /**
     * Resolve collision using conservation of momentum and energy
     */
    private static void resolveCollision(Circle circle1, Circle circle2,
                                         double dx, double dy,
                                         double distance, double minDistance) {
        // Step 1: Separate the circles so they're no longer overlapping
        separateCircles(circle1, circle2, dx, dy, distance, minDistance);

        // Step 2: Calculate masses
        double mass1 = Mass.calculateMassCircle(circle1.getDiameter());
        double mass2 = Mass.calculateMassCircle(circle2.getDiameter());

        // Step 3: Get velocities
        double v1x = circle1.getVelocity().getVx();
        double v1y = circle1.getVelocity().getVy();
        double v2x = circle2.getVelocity().getVx();
        double v2y = circle2.getVelocity().getVy();

        // Step 4: Calculate collision normal (direction from circle1 to circle2)
        double nx = dx / distance;  // Normalized x
        double ny = dy / distance;  // Normalized y

        // Step 5: Calculate relative velocity
        double dvx = v2x - v1x;
        double dvy = v2y - v1y;

        // Step 6: Calculate relative velocity along collision normal
        double dvn = dvx * nx + dvy * ny;

        // Step 7: Don't resolve if circles are moving apart
        if (dvn >= 0) {
            return;
        }

        // Step 8: Calculate impulse (using conservation of momentum)
        // Formula: J = -(1 + e) * dvn / (1/m1 + 1/m2)
        double impulse = -(1 + RESTITUTION) * dvn / (1 / mass1 + 1 / mass2);

        // Step 9: Apply impulse to both circles
        // Circle 1 gets impulse in one direction
        double impulse1x = impulse * nx / mass1;
        double impulse1y = impulse * ny / mass1;
        circle1.getVelocity().addVelocity(impulse1x, impulse1y);

        // Circle 2 gets impulse in opposite direction
        double impulse2x = -impulse * nx / mass2;
        double impulse2y = -impulse * ny / mass2;
        circle2.getVelocity().addVelocity(impulse2x, impulse2y);
    }

    /**
     * Separate overlapping circles
     */
    private static void separateCircles(Circle circle1, Circle circle2,
                                        double dx, double dy,
                                        double distance, double minDistance) {
        // Calculate overlap amount
        double overlap = minDistance - distance;

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
                circle1.getX() - (int) (nx * separation1),
                circle1.getY() - (int) (ny * separation1)
        );

        // Move circle2 away from circle1
        circle2.setPosition(
                circle2.getX() + (int) (nx * separation2),
                circle2.getY() + (int) (ny * separation2)
        );
    }

    /**
     * Debug: Print momentum before and after collision
     */
    public static void printMomentumDebug(Circle circle1, Circle circle2, String when) {
        double p1x = Mass.calculateMassCircle(circle1.getDiameter()) * circle1.getVelocity().getVx();
        double p1y = Mass.calculateMassCircle(circle1.getDiameter()) * circle1.getVelocity().getVy();
        double p2x = Mass.calculateMassCircle(circle2.getDiameter()) * circle2.getVelocity().getVx();
        double p2y = Mass.calculateMassCircle(circle2.getDiameter()) * circle2.getVelocity().getVy();

        double totalPx = p1x + p2x;
        double totalPy = p1y + p2y;

        System.out.println(when + " Total Momentum: (" + totalPx + ", " + totalPy + ")");
    }
}