package Task_1.Shared;

import Task_1.Banks.Bank;
import Task_1.Banks.LockBank;
import Task_1.Banks.SyncBlockBank;
import Task_1.Banks.SyncMethodBank;

public class Task_1 {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        IBank b = new SyncBlockBank(NACCOUNTS, INITIAL_BALANCE);
//        IBank b = new SyncMethodBank(NACCOUNTS, INITIAL_BALANCE);
//        IBank b = new LockBank(NACCOUNTS, INITIAL_BALANCE);
//        IBank b = new Bank(NACCOUNTS, INITIAL_BALANCE);

        int i;
        for (i = 0; i < NACCOUNTS; i++){
            TransferThread t = new TransferThread(b, i,
                    INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start();
        }
    }
}