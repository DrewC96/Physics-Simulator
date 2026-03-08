package churchich.physicssim;

import javax.swing.*;
import java.awt.*;

public class MomentumCard extends JPanel {
    public final MomentumRenderer rend;

    public MomentumCard(Main app) {
        setLayout(new BorderLayout());

        rend = new MomentumRenderer();

        rend.addObject(new Circle(100, 100, 50, Color.RED));
        rend.addObject(new Circle(300, 200, 60, Color.BLUE));
        rend.addObject(new Circle(500, 150, 40, Color.GREEN));

        app.setActiveRenderer(rend); // <-- tell Main to use this renderer

        JButton backButton = new JButton("Back to Main");
        backButton.addActionListener(_ -> {
            app.showScreen(Main.MAIN_SCREEN);
            app.isSimulating = false;
        });

        add(backButton, BorderLayout.NORTH);
        add(rend, BorderLayout.CENTER);
    }

}