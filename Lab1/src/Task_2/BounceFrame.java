package Task_2;

import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame {
    private static int caughtBalls = 0;
    private BallCanvas canvas;
    private static final JLabel caughtLabel = new JLabel("Caught: 0");

    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    public BounceFrame() {

        this.setSize(WIDTH, HEIGHT);

        this.setTitle("Bounce program'");

        this.canvas = new BallCanvas();

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        JButton buttonAddPocket = new JButton("Add pocket");
        buttonStart.addActionListener(e -> {

            Ball b = new Ball(canvas);
            canvas.addBall(b);

            BallThread thread = new BallThread(b);
            thread.start();
        });

        buttonStop.addActionListener(e -> System.exit(0));

        buttonAddPocket.addActionListener(e -> {
            Pocket p = new Pocket(canvas);
            canvas.addPocket(p);
        });

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonAddPocket);
        buttonPanel.add(caughtLabel);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public static synchronized void addCaught() {
        caughtBalls++;
        caughtLabel.setText("Caught: " + caughtBalls);
    }
}