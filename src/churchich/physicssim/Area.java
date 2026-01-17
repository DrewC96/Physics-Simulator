package churchich.physicssim;

public class Area {
    public static double calculateAreaCircle(double diameter) {
        return 0.5 * Math.PI * Math.pow(diameter / 2.0, 2);
    }

    public static double calculateAreaRectangle(int width, int height) {
        return width * height;
    }
}
