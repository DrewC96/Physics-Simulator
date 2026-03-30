package churchich.physicssim;

import javax.swing.*;
import java.awt.*;

public class MomentumCard extends JPanel {
    public final MomentumRenderer rend;

    public MomentumCard(Main app) {
    setLayout(new BorderLayout());

    rend = new MomentumRenderer();

    rend.addObject(new Circle(50, 300, 50, Color.RED));

    app.setActiveRenderer(rend);

    JButton backButton = new JButton("Back to Main");
    backButton.addActionListener(_ -> {
        app.showScreen(Main.MAIN_SCREEN);
        app.isSimulating = false;
    });

    add(backButton, BorderLayout.NORTH);
    add(rend, BorderLayout.CENTER);

    // Build tower once the renderer has a known size
    rend.addComponentListener(new java.awt.event.ComponentAdapter() {
        public void componentResized(java.awt.event.ComponentEvent e) {
            buildTower();
            rend.removeComponentListener(this); // only run once
        }
    });
}

private void buildTower() {
    int screenHeight = rend.getHeight();
    int blockWidth = 60;
    int blockHeight = 40;
    int numBlocks = screenHeight / 2 / blockHeight; // tower is half the screen height
    int x = 10; // left side

    for (int i = 0; i < numBlocks; i++) {
        int y = screenHeight - (i + 1) * blockHeight;
        Rect block = new Rect(x, y, blockWidth, blockHeight, Color.GRAY);
        block.setPosition(x, y);
        rend.addObject(block);
    }
}
}