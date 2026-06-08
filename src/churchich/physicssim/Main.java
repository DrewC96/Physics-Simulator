package churchich.physicssim;

import javax.swing.*;
import java.awt.*;

public class Main implements Runnable {

    public static final String VERSION = "0.0.1";
    public static final String MAIN_SCREEN = "Physics Simulator " + VERSION;
    public static final Dimension SCREEN_SIZE = new Dimension(800, 600);
    public static final String MOMENTUM_SCREEN = "Momentum Simulator " + VERSION;
    public static final String WATER_SCREEN = "Water Simulator " + VERSION;

    JPanel cards;
    public JFrame frame;
    public SimRenderer rend;

    // make new cards
    MomentumCard momentumCard;
    WaterCard waterCard;

    // controls whether a pane is running
    public volatile boolean isSimulating = false;

    // manage which renderer is rendering for when switching panes
    public void setActiveRenderer(SimRenderer r) { rend = r; }

    public void addComponentToPane(Container pane) {
        momentumCard = new MomentumCard(this);
        waterCard = new WaterCard(this);

        cards = new JPanel(new CardLayout());
        cards.add(new MainCard(this), MAIN_SCREEN);
        cards.add(momentumCard, MOMENTUM_SCREEN);
        cards.add(waterCard, WATER_SCREEN);

        pane.add(cards, BorderLayout.CENTER);
    }

    public void showScreen(String screenName) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, screenName);
    }

    public void resetCard(String screenName) {
        
    }

    // initializes JFrame and sets properties
    public Main() {
        frame = new JFrame();
        frame.setSize(SCREEN_SIZE);
        frame.setTitle(MAIN_SCREEN);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        addComponentToPane(frame.getContentPane()); // call directly on this instance

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (rend != null && isSimulating) {
                    rend.updatePhysics();
                    rend.render();
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    static void main() {
        Main main = new Main();
        new Thread(main).start();
    }
}