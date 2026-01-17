package churchich.physicssim;

import javax.swing.*;
import java.awt.*;

public class Main implements Runnable {

    public static final String VERSION = "0.0.1";
    public static final String TITLE = "Physics Simulator " +  VERSION;
    public static final Dimension SCREEN_SIZE = new Dimension(800,600);

    public JFrame frame;
    public Renderer rend;

    public Main() {
        frame = new JFrame();
        frame.setSize(SCREEN_SIZE);
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        rend = new Renderer(frame);

        // Add multiple balls
        rend.addObject(new Circle(100, 100, 50, Color.RED));
        rend.addObject(new Circle(300, 200, 60, Color.BLUE));
        rend.addObject(new Circle(500, 150, 40, Color.GREEN));
        rend.addObject(new Rectangle(200, 400, 150, 100, Color.YELLOW));

        frame.add(rend);

        frame.setVisible(true);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            rend.updatePhysics();  // Update physics each frame
            rend.repaint();
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        new Thread(main).start();
    }
}