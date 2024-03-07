package Task_3;

import java.util.ArrayList;

public class Main {

    public static ArrayList<Teacher> initTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        Teacher lecturer = new Teacher("Lecturer", "Lecturer");
        Teacher assistant_1 = new Teacher("Assistant1", "Assistant");
        Teacher assistant_2 = new Teacher("Assistant2", "Assistant");
        Teacher assistant_3 = new Teacher("Assistant3", "Assistant");

        teachers.add(lecturer);
        teachers.add(assistant_1);
        teachers.add(assistant_2);
        teachers.add(assistant_3);

        return teachers;
    }

    public static ArrayList<Student> initStudents(int numStudents) {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < numStudents; i++) {
            students.add(new Student(i, "Student" + i));
        }
        return students;
    }

    public static ArrayList<Group> initGroups(int numGroups, int numStudents) {
        ArrayList<Group> groups = new ArrayList<>();

        for (int i = 0; i < numGroups; i++) {
            groups.add(new Group(i, "Group" + i, initStudents(numStudents)));
        }
        return groups;
    }

    public static void main(String[] args) {
        ArrayList<Group> groups = initGroups(3, 3);
        Journal journal = new Journal(groups, 2);
        ArrayList<Teacher> teachers = initTeachers();
        ArrayList<TeacherThread> teacherThreads = new ArrayList<>();

        System.out.printf("%-12s %-14s %-13s %-16s %-18s %-10s\n",
                "Teacher", "Position", "Week", "Group", "Student", "Grade");

        for (Teacher teacher : teachers) {
            TeacherThread teacherThread = new TeacherThread(teacher, journal, groups);
            teacherThreads.add(teacherThread);
        }

        for (TeacherThread teacherThread : teacherThreads) {
            teacherThread.start();
        }

        for (TeacherThread teacherThread : teacherThreads) {
            try {
                teacherThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        journal.printGrades();
    }
}
