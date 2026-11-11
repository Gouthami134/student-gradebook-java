
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** A small console gradebook for practicing Java classes, collections, and input validation. */
public class GradebookApp {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final List<Student> STUDENTS = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Student Gradebook ===");
            System.out.println("1. Add student and marks\n2. List students\n3. Find student\n0. Exit");
            switch (readInt("Choose: ")) {
                case 1 -> addStudent();
                case 2 -> listStudents();
                case 3 -> findStudent();
                case 0 -> running = false;
                default -> System.out.println("Choose one of the listed options.");
            }
        }
        System.out.println("Goodbye.");
    }

    private static void addStudent() {
        System.out.print("Student name: ");
        String name = INPUT.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        int count = readInt("Number of subjects: ");
        if (count < 1 || count > 10) { System.out.println("Enter between 1 and 10 subjects."); return; }
        List<Integer> marks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int mark = readInt("Mark for subject " + (i + 1) + " (0-100): ");
            if (mark < 0 || mark > 100) { System.out.println("Marks must be from 0 to 100. Student was not added."); return; }
            marks.add(mark);
        }
        STUDENTS.add(new Student(name, marks));
        System.out.println("Student added.");
    }

    private static void listStudents() {
        if (STUDENTS.isEmpty()) { System.out.println("No students yet."); return; }
        for (Student student : STUDENTS) System.out.println(student);
    }

    private static void findStudent() {
        System.out.print("Name to find: ");
        String query = INPUT.nextLine().trim();
        STUDENTS.stream().filter(s -> s.name.equalsIgnoreCase(query)).findFirst()
                .ifPresentOrElse(System.out::println, () -> System.out.println("No matching student."));
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(INPUT.nextLine().trim()); }
            catch (NumberFormatException ex) { System.out.println("Please enter a whole number."); }
        }
    }

    private static final class Student {
        private final String name;
        private final List<Integer> marks;
        private Student(String name, List<Integer> marks) { this.name = name; this.marks = List.copyOf(marks); }
        private double average() { return marks.stream().mapToInt(Integer::intValue).average().orElse(0); }
        private String grade() {
            double average = average();
            if (average >= 90) return "A";
            if (average >= 80) return "B";
            if (average >= 70) return "C";
            if (average >= 60) return "D";
            return "F";
        }
        @Override public String toString() {
            return String.format("%s | marks %s | average %.1f | grade %s", name, marks, average(), grade());
        }
    }
}
