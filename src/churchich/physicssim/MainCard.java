package churchich.physicssim;

import javax.swing.*;

public class MainCard extends JPanel {

    public MainCard(Main app) {
        add(new JLabel("Welcome to my Physics Simulator!"));

        JButton goToMomentum = new JButton("Go to Momentum Simulator");
        goToMomentum.addActionListener(e -> {
            app.showScreen(Main.MOMENTUM_SCREEN);
            app.isSimulating = true;
        });
        add(goToMomentum);
    }
}
