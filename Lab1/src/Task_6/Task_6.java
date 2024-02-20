package Task_6;

public class Task_6 {
    public static void main(String[] args) {
        System.out.println("Non-sync counter:");
        testCounter(new NonSyncCounter());
        System.out.println();

        System.out.println("CounterSyncMethod:");
        testCounter(new SyncMethodCounter());
        System.out.println();

        System.out.println("CounterSyncBlock:");
        testCounter(new SyncBlockCounter());
        System.out.println();

        System.out.println("CounterLock:");
        testCounter(new SyncLockCounter());
        System.out.println();
    }

    public static void testCounter(ICounter counter) {

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }

        System.out.println(counter.getValue());
    }
}
