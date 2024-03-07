package Task_1.Banks;

import Task_1.Shared.IBank;

import java.util.concurrent.locks.ReentrantLock;

public class LockBank implements IBank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    private ReentrantLock lock = new ReentrantLock();
    public LockBank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }
    public void transfer(int from, int to, int amount) {
        lock.lock();

        try {
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;

            if (ntransacts % NTEST == 0)
                test();

        } finally {
            lock.unlock();
        }
    }
    public void test(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);
    }
    public int size(){
        return accounts.length;
    }
}