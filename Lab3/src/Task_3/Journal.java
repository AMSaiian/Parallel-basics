package Task_3;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Journal {
    private final ArrayList<Group> groups;
    private final ConcurrentHashMap<String, Mark> marks = new ConcurrentHashMap<>();
    private final int weekAmount;

    public Journal(ArrayList<Group> groups, int weekAmount) {
        this.groups = groups;
        this.weekAmount = weekAmount;
    }

    public void putMark(String groupName, String studentName, int week, int mark) {
        if (week > weekAmount)
            throw new IllegalArgumentException("Invalid week num");

        Group needGroup = groups.stream()
                .filter(g -> g.getGroupName().equals(groupName))
                .findFirst()
                .orElseThrow();

        int groupId = needGroup.getId();
        int studentId = needGroup.getStudent(studentName).getId();

        String markIdentificator = String.format("%d-%d-%d", week, groupId, studentId);

        Mark needMark = marks.computeIfAbsent(markIdentificator, g -> new Mark());

        needMark.addMark(mark);
    }

    public int getMark(String groupName, String studentName, int week) {

        if (week > weekAmount)
            throw new IllegalArgumentException("Invalid week num");

        Group needGroup = groups.stream()
                .filter(g -> g.getGroupName().equals(groupName))
                .findFirst()
                .orElseThrow();

        int groupId = needGroup.getId();
        int studentId = needGroup.getStudent(studentName).getId();

        String markIdentificator = String.format("%d-%d-%d", week, groupId, studentId);

        Mark needMark = marks.computeIfAbsent(markIdentificator, g -> new Mark());

        return needMark.getValue();
    }

    public int getWeekAmount() {
        return weekAmount;
    }

    public void printMarks() {
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
                for (int week = 1; week <= weekAmount; week++) {;
                    int mark = getMark(group.getGroupName(), student.getName(), week);
                    System.out.printf("%-10d", mark);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
