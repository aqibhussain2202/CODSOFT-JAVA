import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;
    int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public boolean isFull() {
        return enrolledStudents >= capacity;
    }

    public void enrollStudent() {
        enrolledStudents++;
    }

    public void dropStudent() {
        enrolledStudents--;
    }

    public void displayCourse() {
        System.out.println(courseCode + ": " + title);
        System.out.println("Description: " + description);
        System.out.println("Capacity: " + capacity + " | Enrolled: " + enrolledStudents);
        System.out.println("Schedule: " + schedule);
        System.out.println();
    }
}

class Student {
    String studentId;
    String name;
    List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
        course.enrollStudent();
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
        course.dropStudent();
    }

    public void displayRegisteredCourses() {
        System.out.println("Registered Courses for " + name + " (" + studentId + "):");
        for (Course course : registeredCourses) {
            System.out.println(course.courseCode + ": " + course.title);
        }
        System.out.println();
    }
}

class RegistrationSystem {
    Map<String, Course> courseDatabase;
    Map<String, Student> studentDatabase;

    public RegistrationSystem() {
        courseDatabase = new HashMap<>();
        studentDatabase = new HashMap<>();
    }

    public void addCourse(Course course) {
        courseDatabase.put(course.courseCode, course);
    }

    public void addStudent(Student student) {
        studentDatabase.put(student.studentId, student);
    }

    public void listCourses() {
        System.out.println("Available Courses:");
        for (Course course : courseDatabase.values()) {
            course.displayCourse();
        }
    }

    public void registerStudentForCourse(String studentId, String courseCode) {
        Student student = studentDatabase.get(studentId);
        Course course = courseDatabase.get(courseCode);

        if (student != null && course != null) {
            if (!course.isFull()) {
                student.registerCourse(course);
                System.out.println(student.name + " has been registered for " + course.title);
            } else {
                System.out.println("Course " + course.title + " is full.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void removeStudentFromCourse(String studentId, String courseCode) {
        Student student = studentDatabase.get(studentId);
        Course course = courseDatabase.get(courseCode);

        if (student != null && course != null) {
            student.dropCourse(course);
            System.out.println(student.name + " has been removed from " + course.title);
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void displayStudentCourses(String studentId) {
        Student student = studentDatabase.get(studentId);

        if (student != null) {
            student.displayRegisteredCourses();
        } else {
            System.out.println("Invalid student ID.");
        }
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        RegistrationSystem regSystem = new RegistrationSystem();
        Scanner scanner = new Scanner(System.in);

        // Sample data
        regSystem.addCourse(new Course("CS101", "Introduction to Computer Science", "Basic concepts of computer science.", 30, "Mon-Wed-Fri 10:00-11:00"));
        regSystem.addCourse(new Course("MATH101", "Calculus I", "Introduction to differential calculus.", 40, "Tue-Thu 09:00-10:30"));
        regSystem.addCourse(new Course("PHY101", "Physics I", "Fundamentals of mechanics.", 35, "Mon-Wed 14:00-15:30"));

        regSystem.addStudent(new Student("S001", "Alice Johnson"));
        regSystem.addStudent(new Student("S002", "Bob Smith"));

        while (true) {
            System.out.println("\nCourse Registration System");
            System.out.println("1. List Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    regSystem.listCourses();
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.next();
                    System.out.print("Enter Course Code: ");
                    String courseCode = scanner.next();
                    regSystem.registerStudentForCourse(studentId, courseCode);
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.next();
                    System.out.print("Enter Course Code: ");
                    courseCode = scanner.next();
                    regSystem.removeStudentFromCourse(studentId, courseCode);
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.next();
                    regSystem.displayStudentCourses(studentId);
                    break;
                case 5:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }
}
