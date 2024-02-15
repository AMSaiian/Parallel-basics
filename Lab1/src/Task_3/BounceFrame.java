package Task_3;

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

        JButton buttonTen = new JButton("Create 10");
        JButton buttonHundred = new JButton("Create 100");
        JButton buttonThousand =  new JButton("Create 1000");
        JButton buttonStop = new JButton("Stop");
        JButton buttonAddPocket = new JButton("Add pocket");

        buttonTen.addActionListener(e -> {
            for (int i = 0; i < 10; i++) {
                Ball b = new Ball(canvas, Color.BLUE);
                canvas.addBall(b);

                BallThread thread = new BallThread(b, 1);
                thread.start();
            }

            Ball b = new Ball(canvas, Color.RED);
            canvas.addBall(b);

            BallThread thread = new BallThread(b, 10);
            thread.start();
        });

        buttonHundred.addActionListener(e -> {
            for (int i = 0; i < 100; i++) {
                Ball b = new Ball(canvas, Color.BLUE);
                canvas.addBall(b);

                BallThread thread = new BallThread(b, 1);
                thread.start();
            }

            Ball b = new Ball(canvas, Color.RED);
            canvas.addBall(b);

            BallThread thread = new BallThread(b, 10);
            thread.start();
        });

        buttonThousand.addActionListener(e -> {
            for (int i = 0; i < 1000; i++) {
                Ball b = new Ball(canvas, Color.BLUE);
                canvas.addBall(b);

                BallThread thread = new BallThread(b, 1);
                thread.start();
            }

            Ball b = new Ball(canvas, Color.RED);
            canvas.addBall(b);

            BallThread thread = new BallThread(b, 10);
            thread.start();
        });

        buttonStop.addActionListener(e -> System.exit(0));

        buttonAddPocket.addActionListener(e -> {
            Pocket p = new Pocket(canvas);
            canvas.addPocket(p);
        });

        buttonPanel.add(buttonTen);
        buttonPanel.add(buttonHundred);
        buttonPanel.add(buttonThousand);
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