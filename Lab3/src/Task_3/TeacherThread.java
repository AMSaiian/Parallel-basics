package Task_3;

import java.util.ArrayList;

public class TeacherThread extends Thread {
    Teacher teacher;
    ArrayList<Group> groups;
    private final Journal journal;


    public TeacherThread(Teacher teacher, Journal journal, ArrayList<Group> groups) {
        this.groups = groups;
        this.journal = journal;
        this.teacher = teacher;
    }

    public void run() {
        for (int week = 1; week <= journal.getWeekAmount(); week++) {

            for (Group group : groups) {
                for (Student student : group.getStudents()) {
                    int grade = teacher.getGrade();
                    String groupName = group.getGroupName();
                    String studentName = student.getName();

                    journal.putGrade(groupName, studentName, week, grade );

                    System.out.printf("%-12s %-14s week %-8d %-16s %-18s grade %-10d\n",
                            teacher.getName(), teacher.getPosition(), week, groupName, studentName, grade);
                }
            }

        }
    }
}
