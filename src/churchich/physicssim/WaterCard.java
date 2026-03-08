package churchich.physicssim;

import javax.swing.*;
import java.awt.*;

public class WaterCard extends JPanel{
    public final WaterRenderer rend;

    public WaterCard(Main app) {
        setLayout(new BorderLayout());
        WaterGrid wg = new WaterGrid(100,100);

        rend = new WaterRenderer(wg);

        wg.initPool();

        app.setActiveRenderer(rend);

        JButton backButton = new JButton("Back to Main");
            backButton.addActionListener(_ -> {
            app.showScreen(Main.MAIN_SCREEN);
            app.isSimulating = false;
        });

        add(backButton, BorderLayout.NORTH);
        add(rend, BorderLayout.CENTER);
    }
}
