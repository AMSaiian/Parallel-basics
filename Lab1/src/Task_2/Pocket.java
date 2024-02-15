package Task_2;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Pocket {
    private Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x;
    private int y;

    public Pocket(Component canvas) {
        this.canvas = canvas;
        x = new Random().nextInt(this.canvas.getWidth() - XSIZE);
        y = new Random().nextInt(this.canvas.getHeight() - YSIZE);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.fill(new Rectangle2D.Double(x, y, XSIZE, YSIZE));
    }

    public boolean inPocket(int ballX, int ballY) {
        return (ballX >= this.x) && (ballX < (this.x + XSIZE)) &&
                (ballY >= this.y) && (ballY < (this.y + YSIZE));
    }
}