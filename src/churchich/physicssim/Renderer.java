package churchich.physicssim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Renderer extends JPanel {
    private JFrame frame;
    private List<PhysicsObject> objects;
    private PhysicsObject draggedObject = null;
    private int dragOffsetX;
    private int dragOffsetY;

    // Mouse Velocity Tracking
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long lastMouseTime = 0;
    private double mouseVelocityX = 0;
    private double mouseVelocityY = 0;

    // Physics Constants
    private static final double GRAVITY = 0.5;
    private static final double FRICTION = 0.98;
    private static final double VELOCITY_SCALE = 0.5; // how much mouse velocity affects object

    public Renderer(JFrame frame) {
        this.frame = frame;
        this.objects = new ArrayList<>();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        MouseAdapter mouseHandler = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // initialize mouse tracking
                lastMouseX = mouseX;
                lastMouseY = mouseY;
                lastMouseTime = System.currentTimeMillis();

                // Check objects in reverse order (top to bottom)
                for (int i = objects.size() - 1; i >= 0; i--) {
                    PhysicsObject obj = objects.get(i);
                    if (obj.contains(mouseX, mouseY)) {
                        draggedObject = obj;
                        dragOffsetX = mouseX - obj.getX();
                        dragOffsetY = mouseY - obj.getY();

                        draggedObject.getVelocity().reset();
                        break;
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (draggedObject != null) {
                    draggedObject.getVelocity().setVelocity(
                            mouseVelocityX * VELOCITY_SCALE,
                            mouseVelocityY * VELOCITY_SCALE
                    );
                    draggedObject = null;
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (draggedObject != null) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    long currentTime = System.currentTimeMillis();

                    // calculate mouse velocity
                    long deltaTime = currentTime - lastMouseTime;
                    if (deltaTime > 0) {
                        mouseVelocityX = (mouseX - lastMouseX) / (double) deltaTime * 16.67; // scale to ~60fps
                        mouseVelocityY = (mouseY - lastMouseY) / (double) deltaTime * 16.67;
                    }

                    //update object position
                    draggedObject.setPosition(
                            e.getX() - dragOffsetX,
                            e.getY() - dragOffsetY
                    );

                    //update tracking variables
                    lastMouseX = mouseX;
                    lastMouseY = mouseY;
                    lastMouseTime = currentTime;
                    repaint();
                }
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void addObject(PhysicsObject obj) {
        objects.add(obj);
    }

    public void removeObject(PhysicsObject obj) {
        objects.remove(obj);
    }

    public List<PhysicsObject> getObjects() {
        return objects;
    }

    // Update all physics
    public void updatePhysics() {
        for (PhysicsObject obj : objects) {
            // Don't apply physics to dragged object
            if (obj != draggedObject) {
                // Apply gravity and friction
                obj.applyPhysics(GRAVITY, FRICTION);

                // Update position based on velocity
                obj.updatePosition();

                // Bounce off walls
                handleWallCollisions(obj);
            }
        }
        HandleObjectCollisions.handleAllCollisions(objects);
    }

    // Handle wall collisions
    private void handleWallCollisions(PhysicsObject obj) {
        int width = getWidth();
        int height = getHeight();

        // For circles
        if (obj instanceof Circle) {
            Circle circle = (Circle) obj;
            int diameter = circle.getDiameter();

            // Left/Right walls
            if (circle.getX() < 0) {
                circle.setPosition(0, circle.getY());
                circle.getVelocity().setVx(-circle.getVelocity().getVx() * 0.8);
            } else if (circle.getX() + diameter > width) {
                circle.setPosition(width - diameter, circle.getY());
                circle.getVelocity().setVx(-circle.getVelocity().getVx() * 0.8);
            }

            // Top/Bottom walls
            if (circle.getY() < 0) {
                circle.setPosition(circle.getX(), 0);
                circle.getVelocity().setVy(-circle.getVelocity().getVy() * 0.8);
            } else if (circle.getY() + diameter > height) {
                circle.setPosition(circle.getX(), height - diameter);
                circle.getVelocity().setVy(-circle.getVelocity().getVy() * 0.8);

                // Add friction when on ground
                circle.getVelocity().setVx(circle.getVelocity().getVx() * 0.9);
            }
        }

        // For rectangles
        if (obj instanceof Rectangle) {
            Rectangle rect = (Rectangle) obj;
            int rectWidth = rect.getWidth();
            int rectHeight = rect.getHeight();

            // Left/Right walls
            if (rect.getX() < 0) {
                rect.setPosition(0, rect.getY());
                rect.getVelocity().setVx(-rect.getVelocity().getVx() * 0.8);
            } else if (rect.getX() + rectWidth > width) {
                rect.setPosition(width - rectWidth, rect.getY());
                rect.getVelocity().setVx(-rect.getVelocity().getVx() * 0.8);
            }

            // Top/Bottom walls
            if (rect.getY() < 0) {
                rect.setPosition(rect.getX(), 0);
                rect.getVelocity().setVy(-rect.getVelocity().getVy() * 0.8);
            } else if (rect.getY() + rectHeight > height) {
                rect.setPosition(rect.getX(), height - rectHeight);
                rect.getVelocity().setVy(-rect.getVelocity().getVy() * 0.8);

                // Add friction when on ground
                rect.getVelocity().setVx(rect.getVelocity().getVx() * 0.9);
            }
        }
    }

    public void render() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw all objects
        for (PhysicsObject obj : objects) {
            obj.draw(g);
        }

        // Optional: Draw velocity vectors for debugging
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        for (PhysicsObject obj : objects) {
            if (obj instanceof Circle) {
                Circle circle = (Circle) obj;
                int centerX = circle.getCenterX();
                int centerY = circle.getCenterY();
                int endX = centerX + (int) (circle.getVelocity().getVx() * 2);
                int endY = centerY + (int) (circle.getVelocity().getVy() * 2);
                g2d.drawLine(centerX, centerY, endX, endY);
            }
        }
    }
}