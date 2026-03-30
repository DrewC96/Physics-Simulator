package churchich.physicssim;

public class Mass {
    private final static double MASS_FACTOR = 2;

    public static double calculateMassCircle(double diameter) {
        return MASS_FACTOR * diameter;
    }

    public static double calculateMassRect(int width, int height) {
        return MASS_FACTOR * Area.calculateAreaRectangle(width, height);
    }
}
