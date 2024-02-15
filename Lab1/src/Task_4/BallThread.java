package Task_4;

public class BallThread extends Thread {
    private Ball b;
    private BallThread previous;
    public BallThread(Ball ball, BallThread previous) {
        this.b = ball;
        this.previous = previous;
    }

    @Override
    public void run() {
        try {
            if (previous != null) {
                previous.join();
            }
            while (!b.isInPocket()) {
                b.move();
                b.checkInPocket();
                Thread.sleep(5);
            }
            BounceFrame.addCaught();
        } catch (InterruptedException ex) {
        }
    }
}