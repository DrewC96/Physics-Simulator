package churchich.physicssim;

public class Momentum {

    public static double calculateXMomentumCircle(Circle circle) {
        return Mass.calculateMassCircle(circle.getDiameter())
                * circle.getVelocity().getVx();
    }
    public static double calculateYMomentumCircle(Circle circle) {
        return Mass.calculateMassCircle(circle.getDiameter())
                * circle.getVelocity().getVy();
    }

    public static double calculateXMomentumRectangle(Rectangle rectangle) {
        return Mass.calculateMassRectangle(rectangle.getWidth(), rectangle.getHeight())
                * rectangle.getVelocity().getVx();
    }

    public static double calculateYMomentumRectangle(Rectangle rectangle) {
        return Mass.calculateMassRectangle(rectangle.getWidth(), rectangle.getHeight())
                * rectangle.getVelocity().getVy();
    }
}
