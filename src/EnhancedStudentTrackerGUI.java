import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class EnhancedStudentTrackerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea reportArea;
    private ArrayList<Student> students;
    private Student currentStudent;

    public EnhancedStudentTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        students = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 248, 255));

        // Top input panel
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        topPanel.setBackground(new Color(245, 248, 255));

        JLabel nameLabel = new JLabel("Student Name:");
        nameField = new JTextField();
        JLabel gradeLabel = new JLabel("Enter Grade:");
        gradeField = new JTextField();

        JButton addStudentBtn = new JButton("Add Student");
        JButton addGradeBtn = new JButton("Add Grade");

        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(gradeLabel);
        topPanel.add(gradeField);
        topPanel.add(addStudentBtn);
        topPanel.add(addGradeBtn);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 248, 255));
        JButton finishStudentBtn = new JButton("Finish Student");
        JButton showReportBtn = new JButton("Show Report");
        buttonPanel.add(finishStudentBtn);
        buttonPanel.add(showReportBtn);

        // Report area
        reportArea = new JTextArea(20, 50);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        // Action Listeners
        addStudentBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                currentStudent = new Student(name);
                nameField.setText("");
                reportArea.append("\nNew student created: " + name + "\n");
            } else {
                JOptionPane.showMessageDialog(this, "Enter a valid student name.");
            }
        });

        addGradeBtn.addActionListener(e -> {
            if (currentStudent == null) {
                JOptionPane.showMessageDialog(this, "Add a student first.");
                return;
            }
            try {
                int grade = Integer.parseInt(gradeField.getText().trim());
                currentStudent.addGrade(grade);
                gradeField.setText("");
                reportArea.append("Added grade: " + grade + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid integer grade.");
            }
        });

        finishStudentBtn.addActionListener(e -> {
            if (currentStudent != null) {
                students.add(currentStudent);
                reportArea.append("Student completed: " + currentStudent.name + "\n\n");
                currentStudent = null;
            } else {
                JOptionPane.showMessageDialog(this, "No active student.");
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
        SwingUtilities.invokeLater(() -> new EnhancedStudentTrackerGUI().setVisible(true));
    }
}
