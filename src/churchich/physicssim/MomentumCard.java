package churchich.physicssim;

import javax.swing.*;
import java.awt.*;

public class MomentumCard extends JPanel {

    public MomentumCard(Main app) {
        setLayout(new BorderLayout());

        app.rend.addObject(new Circle(100, 100, 50, Color.RED));
        app.rend.addObject(new Circle(300, 200, 60, Color.BLUE));
        app.rend.addObject(new Circle(500, 150, 40, Color.GREEN));

        JButton backButton = new JButton("Back to Main");
        backButton.addActionListener(_ -> {
            app.showScreen(Main.MAIN_SCREEN);
            app.isSimulating = false;
        });

        add(backButton, BorderLayout.NORTH);
        add(app.rend, BorderLayout.CENTER);
    }

}