package Task_1.Shared;

import Task_1.Banks.Bank;

public class TransferThread extends Thread {
    private IBank bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 1000;
    public TransferThread(IBank b, int from, int max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < REPS; i++) {
                int toAccount = (int) (bank.size() * Math.random());
                int amount = (int) (maxAmount * Math.random()/REPS);
                bank.transfer(fromAccount, toAccount, amount);
            }
        }
    }
}