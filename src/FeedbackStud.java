import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FeedbackStud extends JFrame implements ActionListener {

    private JButton back;
    private JScrollPane scrollPane1, scrollPane2;
    private JPanel panel1, panel2;
    private JLabel label1, label2;

    private List<String> lecturerUsernames = new ArrayList<>(); // To store student usernames

    FeedbackStud() {

        back = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 50, 525, 100, 50, 0X7AB2D3);
        this.add(back);

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        scrollPane1 = new JScrollPane(panel1);

        label1 = FrameMethods.labelSetup("Give Feedback for Lecturers:", "Arial", 25, 0x000000, 50, -5, 700, 100);
        this.add(label1);

        scrollPane1.setBounds(50, 60, 700, 200);

        label2 = FrameMethods.labelSetup("View Feedback from Lecturers:", "Arial", 25, 0x000000, 50, 255, 700, 100);
        this.add(label2);

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        scrollPane2 = new JScrollPane(panel2);

        scrollPane2.setBounds(50, 320, 700, 200);

        getContentPane().add(scrollPane1);
        getContentPane().add(scrollPane2);

        setTitle("Feedback");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(0xDFF2EB));
        setLayout(null);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon("resources/icon1.png");
        setIconImage(icon.getImage());

        loadStudents(); // Load students from Accounts.txt
        displayStudents(); // Display students with feedback buttons

        setVisible(true);
    }

    private void loadStudents() {
        // Load student usernames from Accounts.txt
        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0].trim();
                String accountType = parts[2].trim();

                if (accountType.equalsIgnoreCase("Lecturer")) {
                    lecturerUsernames.add(username);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayStudents() {
        // Display each student with a feedback button
        for (String lecturerUsername : lecturerUsernames) {
            JPanel lecturerPanel = new JPanel();
            lecturerPanel.setLayout(new BorderLayout());
            lecturerPanel.setPreferredSize(new Dimension(680, 50));
            lecturerPanel.setMaximumSize(new Dimension(680, 50));
            lecturerPanel.setBackground(new Color(0xB9E5E8));
            lecturerPanel.setBorder(new LineBorder(Color.GRAY, 1));

            JLabel lecturerLabel = new JLabel(lecturerUsername);
            lecturerLabel.setPreferredSize(new Dimension(600, 30));

            JButton feedbackButton = new JButton("Give Feedback");
            feedbackButton.addActionListener(e -> {
                // When the button is clicked, open a feedback form or dialog
                showFeedbackDialog(lecturerUsername);
            });

            // Add the student label to the center of the panel
            lecturerPanel.add(lecturerLabel, BorderLayout.CENTER);
            // Add the feedback button to the right of the panel
            lecturerPanel.add(feedbackButton, BorderLayout.EAST);

            panel1.add(lecturerPanel);
        }

        // Refresh the panel to display the students
        panel1.revalidate();
        panel1.repaint();
    }

    private void showFeedbackDialog(String lecturerUsername) {
        // Create a dialog to input feedback for the selected student
        String feedback = JOptionPane.showInputDialog(this, "Enter feedback for " + lecturerUsername);
        if (feedback != null && !feedback.trim().isEmpty()) {
            saveFeedback(lecturerUsername, feedback);
        }
    }

    private void saveFeedback(String lecturerUsername, String feedback) {
        // Save the feedback to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            String studentName = SessionManager.getStudentUsername(); // Get the currently logged-in student's username
            if (studentName == null || studentName.trim().isEmpty()) {
                studentName = "UnknownStudent"; // Default to "UnknownStudent" if not available
            }

            // Write the feedback in the desired format
            writer.write("Feedback for " + lecturerUsername + " from " + studentName + ": " + feedback);
            writer.newLine(); // Write a newline after each entry

            JOptionPane.showMessageDialog(this, "Feedback saved for " + lecturerUsername);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving feedback: " + e.getMessage());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            new LecturerDashboardPanel();
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new FeedbackStud();
    }
}
