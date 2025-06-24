import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    ArrayList<Integer> grades;

    Student(String name) {
        this.name = name;
        grades = new ArrayList<>();
    }

    void addGrade(int grade) {
        grades.add(grade);
    }

    double getAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int g : grades) sum += g;
        return (double) sum / grades.size();
    }

    int getHighest() {
        if (grades.isEmpty()) return 0;
        int max = grades.get(0);
        for (int g : grades) if (g > max) max = g;
        return max;
    }

    int getLowest() {
        if (grades.isEmpty()) return 0;
        int min = grades.get(0);
        for (int g : grades) if (g < min) min = g;
        return min;
    }

    String getReport() {
        return "\nStudent: " + name +
                "\nGrades: " + grades +
                "\nAverage: " + getAverage() +
                "\nHighest: " + getHighest() +
                "\nLowest: " + getLowest() + "\n";
    }
}

public class StudentGradeTrackerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea reportArea;
    private ArrayList<Student> students;
    private Student currentStudent;

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        students = new ArrayList<>();

        add(new JLabel("Student Name:"));
        nameField = new JTextField(20);
        add(nameField);

        JButton startStudentBtn = new JButton("Add Student");
        add(startStudentBtn);

        add(new JLabel("Enter Grade:"));
        gradeField = new JTextField(10);
        add(gradeField);

        JButton addGradeBtn = new JButton("Add Grade");
        add(addGradeBtn);

        JButton finishStudentBtn = new JButton("Finish Student Entry");
        add(finishStudentBtn);

        JButton showReportBtn = new JButton("Show Summary Report");
        add(showReportBtn);

        reportArea = new JTextArea(20, 40);
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea));

        // Button actions
        startStudentBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                currentStudent = new Student(name);
                nameField.setText("");
                reportArea.append("New student '" + name + "' created.\n");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a student name.");
            }
        });

        addGradeBtn.addActionListener(e -> {
            if (currentStudent == null) {
                JOptionPane.showMessageDialog(this, "Please create a student first.");
                return;
            }
            try {
                int grade = Integer.parseInt(gradeField.getText().trim());
                currentStudent.addGrade(grade);
                gradeField.setText("");
                reportArea.append("Grade " + grade + " added to " + currentStudent.name + ".\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer grade.");
            }
        });

        finishStudentBtn.addActionListener(e -> {
            if (currentStudent != null) {
                students.add(currentStudent);
                reportArea.append("Finished entering grades for " + currentStudent.name + ".\n\n");
                currentStudent = null;
            } else {
                JOptionPane.showMessageDialog(this, "No student in progress.");
            }
        });

        showReportBtn.addActionListener(e -> {
            reportArea.setText("=== Student Grade Report ===\n");
            for (Student s : students) {
                reportArea.append(s.getReport());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeTrackerGUI().setVisible(true));
    }
}
