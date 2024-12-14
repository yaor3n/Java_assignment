import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class FeedbackStud extends JFrame implements ActionListener {

    private JButton back;
    private JScrollPane scrollPane1, scrollPane2;
    private JPanel panel1, panel2;
    private JLabel label1, label2;

    private List<String> lecturerUsernames = new ArrayList<>();
    private Map<String, List<String>> lecturerFeedback = new HashMap<>();

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

        loadLecturers();
        loadFeedback();
        displayLecturers();
        displayFeedback();

        setVisible(true);
    }

    private void loadLecturers() {
        File file = new File("Accounts.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Accounts.txt file not found!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0].trim();
                    String accountType = parts[2].trim();

                    if (accountType.equalsIgnoreCase("Lecturer")) {
                        lecturerUsernames.add(username);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading Accounts.txt: " + e.getMessage());
        }
    }

    private void loadFeedback() {
        File file = new File("feedback.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "feedback.txt file not found!");
            return;
        }

        String studentUsername = SessionManager.getStudentUsername(); // Get the logged-in student's username
        System.out.println("Logged in student: " + studentUsername); // Debugging line

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Feedback for")) {
                    // Example: Feedback for stud1 from lec1: good talk
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String feedbackDetails = parts[1].trim();
                        String lecturerInfo = parts[0].substring(12).trim(); // Extract "stud1 from lec1"
                        String[] lecturerParts = lecturerInfo.split(" from ");

                        if (lecturerParts.length == 2) {
                            String studentName = lecturerParts[0].trim(); // Extract student name
                            String lecturerUsername = lecturerParts[1].trim(); // Extract lecturer name

                            // Only load feedback for the currently logged-in student
                            if (studentName.equals(studentUsername)) {
                                System.out.println("Adding feedback: " + feedbackDetails + " for lecturer: " + lecturerUsername); // Debugging line

                                lecturerFeedback.putIfAbsent(lecturerUsername, new ArrayList<>());
                                lecturerFeedback.get(lecturerUsername).add(feedbackDetails);
                            }
                        } else {
                            System.out.println("Line format error: Incorrect lecturer information - " + line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading feedback.txt: " + e.getMessage());
        }
    }


    private void displayLecturers() {
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
            feedbackButton.addActionListener(e -> showFeedbackDialog(lecturerUsername));

            lecturerPanel.add(lecturerLabel, BorderLayout.CENTER);
            lecturerPanel.add(feedbackButton, BorderLayout.EAST);
            panel1.add(lecturerPanel);
        }

        panel1.revalidate();
        panel1.repaint();
    }

    private void saveFeedback(String lecturerUsername, String feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            String studentUsername = SessionManager.getStudentUsername();

            String formattedFeedback = "Feedback for " + lecturerUsername + " from " + studentUsername + ": " + feedback;

            writer.write(formattedFeedback);
            writer.newLine();

            // Add the feedback to the lecturer feedback map
            lecturerFeedback.putIfAbsent(lecturerUsername, new ArrayList<>());
            lecturerFeedback.get(lecturerUsername).add(feedback);

            JOptionPane.showMessageDialog(this, "Feedback saved for " + lecturerUsername);
            displayFeedback();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving feedback: " + e.getMessage());
        }
    }

    private void showFeedbackDetails(String lecturerUsername) {
        List<String> feedbackList = lecturerFeedback.get(lecturerUsername);
        if (feedbackList != null && !feedbackList.isEmpty()) {
            StringBuilder feedbackDetails = new StringBuilder();
            for (String feedback : feedbackList) {
                feedbackDetails.append(feedback).append("\n");
            }
            JOptionPane.showMessageDialog(this, "Feedback for " + lecturerUsername + ":\n" + feedbackDetails.toString());
        } else {
            JOptionPane.showMessageDialog(this, "No feedback found for " + lecturerUsername);
        }
    }

    private void showFeedbackDialog(String lecturerUsername) {
        String feedback = JOptionPane.showInputDialog(this, "Enter feedback for " + lecturerUsername);
        if (feedback != null && !feedback.trim().isEmpty()) {
            saveFeedback(lecturerUsername, feedback);
        } else {
            JOptionPane.showMessageDialog(this, "Feedback cannot be empty.");
        }
    }

    private void displayFeedback() {
        panel2.removeAll(); // Clear existing components before adding new ones

        for (String lecturerUsername : lecturerFeedback.keySet()) {
            JPanel feedbackPanel = new JPanel();
            feedbackPanel.setLayout(new BorderLayout());
            feedbackPanel.setPreferredSize(new Dimension(680, 50));
            feedbackPanel.setMaximumSize(new Dimension(680, 50));
            feedbackPanel.setBackground(new Color(0xB9E5E8));
            feedbackPanel.setBorder(new LineBorder(Color.GRAY, 1));

            JLabel feedbackLabel = new JLabel("Feedback from " + lecturerUsername);
            feedbackLabel.setPreferredSize(new Dimension(500, 30));

            JButton viewFeedbackButton = new JButton("View Feedback");
            viewFeedbackButton.addActionListener(e -> showFeedbackDetails(lecturerUsername));

            feedbackPanel.add(feedbackLabel, BorderLayout.CENTER);
            feedbackPanel.add(viewFeedbackButton, BorderLayout.EAST);

            panel2.add(feedbackPanel);
        }

        panel2.revalidate();
        panel2.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            new StudentDashboardPanel();
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new FeedbackStud();
    }
}
