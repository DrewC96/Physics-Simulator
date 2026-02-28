package churchich.physicssim;

import javax.swing.*;
import java.awt.*;

public class WaterCard extends JPanel{
    public final Renderer rend;

    public WaterCard(Main app) {
        setLayout(new BorderLayout());

        rend = new Renderer(app.frame);
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
