import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagementSystem {

    // --- Student Class ---
    static class Student {
        private String id;
        private String name;
        private int age;
        private String grade;

        public Student(String id, String name, int age, String grade) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.grade = grade;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getGrade() { return grade; }
    }

    // --- StudentManager Class ---
    static class StudentManager {
        private ArrayList<Student> studentList = new ArrayList<>();

        public void addStudent(Student student) {
            studentList.add(student);
        }

        public ArrayList<Student> getAllStudents() {
            return studentList;
        }

        public ArrayList<Student> searchStudent(String keyword) {
            ArrayList<Student> results = new ArrayList<>();
            for (Student s : studentList) {
                if (s.getId().equalsIgnoreCase(keyword) || s.getName().equalsIgnoreCase(keyword)) {
                    results.add(s);
                }
            }
            return results;
        }

        public void clearAll() {
            studentList.clear();
        }
    }

    // --- Main GUI Class ---
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Management System");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        StudentManager manager = new StudentManager();

        // --- Top Panel for Form ---
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField gradeField = new JTextField();

        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);

        // --- Buttons ---
        JButton addButton = new JButton("Add Student");
        JButton viewButton = new JButton("View All Students");
        JButton searchButton = new JButton("Search Student");
        JButton clearButton = new JButton("Clear Form");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);

        // --- Table to Display Students ---
        String[] columnNames = {"ID", "Name", "Age", "Grade"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // --- Menu Bar ---
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // --- Add Components to Frame ---
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // --- Event Listeners ---

        // Add Student
        addButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String grade = gradeField.getText().trim();

                if (id.isEmpty() || name.isEmpty() || grade.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Student student = new Student(id, name, age, grade);
                manager.addStudent(student);
                JOptionPane.showMessageDialog(frame, "Student added successfully!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Age must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // View All Students
        viewButton.addActionListener(e -> {
            tableModel.setRowCount(0);
            for (Student s : manager.getAllStudents()) {
                tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getAge(), s.getGrade()});
            }
        });

        // Search Student
        searchButton.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(frame, "Enter Student ID or Name to Search:");
            if (keyword != null && !keyword.trim().isEmpty()) {
                ArrayList<Student> results = manager.searchStudent(keyword.trim());
                tableModel.setRowCount(0);
                for (Student s : results) {
                    tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getAge(), s.getGrade()});
                }
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No student found.");
                }
            }
        });

        // Clear Form
        clearButton.addActionListener(e -> {
            idField.setText("");
            nameField.setText("");
            ageField.setText("");
            gradeField.setText("");
        });

        // Exit Menu
        exitItem.addActionListener(e -> System.exit(0));

        // --- Show Frame ---
        frame.setVisible(true);
    }
}
