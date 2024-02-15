package Task_2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Pocket> pockets = new ArrayList<>();

    public void addBall(Ball b) {
        this.balls.add(b);
    }

    public void addPocket(Pocket p) {
        this.pockets.add(p);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        balls.removeIf(Ball::isInPocket);

        for (Ball b : balls) {
            b.draw(g2);
        }

        for (Pocket p : pockets) {
            p.draw(g2);
        }
    }

    public boolean checkInPocket(Ball b) {
        boolean result = false;
        for (Pocket p : pockets) {
            result = p.inPocket(b.getCenterX(), b.getCenterY());
            if (result)
                return result;
        }
        return result;
    }
}