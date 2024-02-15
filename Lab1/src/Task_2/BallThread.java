package Task_2;

public class BallThread extends Thread {
    private Ball b;
    public BallThread(Ball ball) {
        this.b = ball;
    }

    @Override
    public void run() {
        try {
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