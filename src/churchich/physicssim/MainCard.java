package churchich.physicssim;

import javax.swing.*;

public class MainCard extends JPanel {

    public MainCard(Main app) {
        add(new JLabel("Welcome to my Physics Simulator!"));

        JButton goToMomentum = new JButton("Go to Momentum Simulator");
        goToMomentum.addActionListener(_ -> {
            app.rend = app.momentumCard.rend;
            app.showScreen(Main.MOMENTUM_SCREEN);
            app.isSimulating = true;
        });
        add(goToMomentum);

        JButton goToWater = new JButton("Go to Water Simulator");
        goToWater.addActionListener(_ -> {
            app.rend = app.waterCard.rend;
            app.showScreen(Main.WATER_SCREEN);
            app.isSimulating = true;
        });
        add(goToWater);
    }
}
