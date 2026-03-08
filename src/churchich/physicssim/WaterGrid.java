package churchich.physicssim;

public class WaterGrid {
    final int rows;
    final int cols;

    // core simulation arrays
    double[][] depth; // water surface height
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

        depth = new double[rows][cols];
        velocity = new double[rows][cols];
        terrain = new double[rows][cols];
        fluxRight = new double[rows][cols];
        fluxLeft = new double[rows][cols];
        fluxDown = new double[rows][cols];
        fluxUp = new double[rows][cols];
    }

    public void initPool() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int zone = (int)(r / (rows * 0.2));
                switch (zone) {
                    case 0 -> depth[r][c] = 1.0;
                    case 1 -> depth[r][c] = 2.0;
                    case 2 -> depth[r][c] = 3.0;
                    case 3 -> depth[r][c] = 4.0;
                    default -> depth[r][c] = 5.0;
                }
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
