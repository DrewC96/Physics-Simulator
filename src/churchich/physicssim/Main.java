package churchich.physicssim;

import javax.swing.*;
import java.awt.*;

public class Main implements Runnable {

    public static final String VERSION = "0.0.1";
    public static final String MAIN_SCREEN = "Physics Simulator " + VERSION;
    public static final Dimension SCREEN_SIZE = new Dimension(800, 600);
    public static final String MOMENTUM_SCREEN = "Momentum Simulator " + VERSION;

    JPanel cards;
    public JFrame frame;
    public Renderer rend;

    // controls whether a pane is running
    public volatile boolean isSimulating = false;

    public void addComponentToPane(Container pane) {
        // Main menu card
        JPanel mainCard = new JPanel();
        mainCard.add(new JLabel("Welcome to my Physics Simulator!"));
        JButton goToMomentum = new JButton("Go to Momentum Simulator");
        goToMomentum.addActionListener(_ -> {
            showScreen(MOMENTUM_SCREEN);
            isSimulating = true;
        });        mainCard.add(goToMomentum);

        // Renderer card
        rend = new Renderer(frame);
        rend.addObject(new Circle(100, 100, 50, Color.RED));
        rend.addObject(new Circle(300, 200, 60, Color.BLUE));
        rend.addObject(new Circle(500, 150, 40, Color.GREEN));

        JPanel momentumCard = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back to Main");
        backButton.addActionListener(_ -> {
            showScreen(MAIN_SCREEN);
            isSimulating = false;
        });
        momentumCard.add(backButton, BorderLayout.NORTH);
        momentumCard.add(rend, BorderLayout.CENTER); // <-- Renderer lives inside the card

        // CardLayout panel
        cards = new JPanel(new CardLayout());
        cards.add(mainCard, MAIN_SCREEN);
        cards.add(momentumCard, MOMENTUM_SCREEN);

        pane.add(cards, BorderLayout.CENTER);
    }

    public void showScreen(String screenName) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, screenName);
    }

    public Main() {
        frame = new JFrame();
        frame.setSize(SCREEN_SIZE);
        frame.setTitle(MAIN_SCREEN);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        addComponentToPane(frame.getContentPane()); // call directly on this instance

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            rend.updatePhysics();
            rend.render();
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