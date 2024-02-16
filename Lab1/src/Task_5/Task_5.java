package Task_5;

public class Task_5 {
    public static void main(String[] args) {
        boolean isSync = true;

        if (isSync) {
            Syncronizer syncronizer = new Syncronizer();
            Thread verticalSync = new writeThreadSync('|', syncronizer, true);
            Thread horizontalSync = new writeThreadSync('-', syncronizer, false);
            verticalSync.start();
            horizontalSync.start();
        } else {
            Thread vertical = new writeThread('|');
            Thread horizontal = new writeThread('-');
            vertical.start();
            horizontal.start();
        }
    }
}
