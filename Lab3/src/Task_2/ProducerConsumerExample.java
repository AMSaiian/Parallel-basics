package Task_2;

public class ProducerConsumerExample {
    public static void main(String[] args) {
//        int[] messageAmount = { 100, 1000, 5000 };
//
//        for (int messages : messageAmount) {
//            Drop drop = new Drop();
//            Thread producer = (new Thread(new Producer(drop, messages)));
//            Thread consumer = (new Thread(new Consumer(drop)));
//
//            producer.start();
//            consumer.start();
//
//            try {
//                producer.join();
//                consumer.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("\nNext example\n");
//        }

        Drop drop = new Drop();
        (new Thread(new Producer(drop, 100))).start();
        (new Thread(new Consumer(drop))).start();
    }
}