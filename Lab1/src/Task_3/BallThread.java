package Task_3;

public class BallThread extends Thread {
    private Ball b;
    public BallThread(Ball ball, int priorityValue) {
        this.b = ball;
        this.setPriority(priorityValue);
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