package Task_3;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Journal {
    private final ArrayList<Group> groups;
    private final ConcurrentHashMap<String, Integer> marks = new ConcurrentHashMap<>();
    private final int weekAmount;
    private final ReentrantLock locker = new ReentrantLock();

    public Journal(ArrayList<Group> groups, int weekAmount) {
        this.groups = groups;
        this.weekAmount = weekAmount;
    }

    public void putGrade(String groupName, String studentName, int week, int grade) {


        if (week > weekAmount)
            throw new IllegalArgumentException("Invalid week num");

        Group needGroup = groups.stream()
                .filter(g -> g.getGroupName().equals(groupName))
                .findFirst()
                .orElseThrow();

        int groupId = needGroup.getId();
        int studentId = needGroup.getStudent(studentName).getId();

        String markIdentificator = String.format("%d-%d-%d", week, groupId, studentId);

        locker.lock();

        try {
            marks.put(markIdentificator, grade);
        } finally {
            locker.unlock();
        }

    }

    public int getGrade(String groupName, String studentName, int week) {

        if (week > weekAmount)
            throw new IllegalArgumentException("Invalid week num");

        Group needGroup = groups.stream()
                .filter(g -> g.getGroupName().equals(groupName))
                .findFirst()
                .orElseThrow();

        int groupId = needGroup.getId();
        int studentId = needGroup.getStudent(studentName).getId();

        String markIdentificator = String.format("%d-%d-%d", week, groupId, studentId);

        locker.lock();
        Integer mark;
        try {
            mark = marks.get(markIdentificator);
        } finally {
            locker.unlock();
        }

        if (mark == null)
            mark = -1;

        return mark;
    }

    public int getWeekAmount() {
        return weekAmount;
    }

    public void printGrades() {
        locker.lock();

        try {
            System.out.println("\nJournal\n");

            for (Group group : groups) {
                System.out.println(group.getGroupName());

                for (int week = 1; week <= weekAmount; week++) {
                    if (week == 1) {
                        System.out.printf("%-12s", "");
                    }
                    System.out.printf("Week %-5d", week);
                }

                System.out.println();

                for (Student student : group.getStudents()) {
                    System.out.print(student.getName() + ":   ");
                    for (int week = 1; week <= weekAmount; week++) {
                        String markIdentificator = String.format("%d-%d-%d", week, group.getId(), student.getId());
                        int grade = marks.get(markIdentificator);
                        System.out.printf("%-10d", grade);
                    }
                    System.out.println();
                }
                System.out.println();
            }
        } finally {
            locker.unlock();
        }
    }
}
