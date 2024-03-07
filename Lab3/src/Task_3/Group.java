package Task_3;

import java.util.ArrayList;
import java.util.Optional;

public class Group {
    private int groupID;
    private String groupName;
    private ArrayList<Student> students;

    public Group(int groupID, String groupName, ArrayList<Student> students) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.students = students;
    }

    public int getId() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public Student getStudent(String name) {
        Optional<Student> student = students.stream().filter(s -> s.getName().equals(name)).findFirst();

        return student.orElseThrow();
    }
}
