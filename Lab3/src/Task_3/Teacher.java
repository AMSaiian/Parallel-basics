package Task_3;

public class Teacher {
    private String name;
    private String position;

    public Teacher(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getGrade() {
        return (int) (Math.random() * 10 + 1);
    }
}