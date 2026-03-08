package churchich.physicssim;

import javax.swing.*;
import java.awt.*;

public class WaterRenderer extends JPanel implements SimRenderer {
    private final WaterGrid grid;

    public WaterRenderer(WaterGrid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellW = getWidth() / grid.cols;
        int cellH = getHeight() / grid.rows;

        for (int r = 0; r < grid.rows; r++) {
            for (int c = 0; c < grid.cols; c++) {
                g.setColor(getWaterColor(grid.depth[r][c]));
                g.fillRect(c * cellW, r * cellH, cellW, cellH);
            }
        }
    }

    private Color getWaterColor(double height) {
        int blue = (int) Math.min(255, height * 60);
        int green = (int) Math.min(150, height * 20);
        return new Color(0, green, blue);
    }

    @Override
    public void updatePhysics() {
        grid.updateWater(0.016);
    }

    @Override
    public void render() {
        repaint();
    }
}
