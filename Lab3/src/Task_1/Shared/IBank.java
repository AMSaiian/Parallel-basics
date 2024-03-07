package Task_1.Shared;

public interface IBank {
    public void transfer(int from, int to, int amount);

    public int size();
}
