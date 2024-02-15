package Task_3;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball {
    private BallCanvas canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;

    private Color color;

    private boolean isInPocket = false;

    public Ball(BallCanvas c, Color color) {
        this.canvas = c;
        this.color = color;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(this.color);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    public void move() {
        x += dx;
        y += dy;
        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }

    public int getCenterX() {
        return x + XSIZE / 2;
    }

    public int getCenterY() {
        return y + YSIZE / 2;
    }

    public void checkInPocket() {
        this.isInPocket = canvas.checkInPocket(this);
    }

    public boolean isInPocket() {
        return isInPocket;
    }
}
