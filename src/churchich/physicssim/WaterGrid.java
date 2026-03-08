package churchich.physicssim;

public class WaterGrid {
    private final int rows;
    private final int cols;

    // core simulation arrays
    private double[][] height; // water surface height
    private double[][] velocity; // vertical velocity of each cell
    private double[][] terrain; // static terrain/ground height

    // outflow flux per cell edge (volume/time flowing out)
    public double[][] fluxRight;
    public double[][] fluxLeft;
    public double[][] fluxDown;
    public double[][] fluxUp;

    public WaterGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        height = new double[rows][cols];
        velocity = new double[rows][cols];
        terrain = new double[rows][cols];
        fluxRight = new double[rows][cols];
        fluxLeft = new double[rows][cols];
        fluxDown = new double[rows][cols];
        fluxUp = new double[rows][cols];
    }

    public void initPool() {
        int cr = rows / 2, cc = cols / 2;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double dist = Math.hypot(r - cr, c - cc);
                height[r][c] = (dist < rows / 6.0) ? 3.0 : 0.05;
            }
        }
    }

    public void updateWater(double dt) {
//        updateFlux(dt);
        updateHeight(dt);
    }

//    public void updateFlux(double dt) {
//        for (int r = 0; r < WaterGrid.rows; r++) {
//            for (int c = 0; c < WaterGrid.cols; c++) {
//                if (fluxRight[r][c] > 0) {
//                    fluxRight[r][c-1] += 1;
//                }
//
//                if (fluxLeft[r][c] > 0) {
//                    fluxLeft[r][c+1] += 1;
//                }
//
//                if (fluxUp[r][c] > 0) {
//                    fluxUp[r-1][c] += 1;
//                }
//
//                if (fluxDown[r][c] > 0) {
//                    fluxDown[r+1][c] += 1;
//                }
//            }
//        }
//    }

    public void updateHeight(double dt) {

    }
}
