import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class FeedbackLec extends JFrame implements ActionListener {

    private JButton back;
    private JScrollPane scrollPane1, scrollPane2;
    private JPanel panel1, panel2;
    private JLabel label1, label2;

    private List<String> studentUsernames = new ArrayList<>();
    private Map<String, List<String>> studentFeedback = new HashMap<>();

    FeedbackLec() {

        back = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 50, 525, 100, 50, 0X7AB2D3);
        this.add(back);

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        scrollPane1 = new JScrollPane(panel1);

        label1 = FrameMethods.labelSetup("Give Feedback for Students:", "Arial", 25, 0x000000, 50, -5, 700, 100);
        this.add(label1);
        scrollPane1.setBounds(50, 60, 700, 200);

        label2 = FrameMethods.labelSetup("View Feedback from Students:", "Arial", 25, 0x000000, 50, 255, 700, 100);
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

        loadStudents();
        loadFeedback();
        displayStudents();
        displayFeedback();

        setVisible(true);
    }

    private void loadStudents() {
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

                    if (accountType.equalsIgnoreCase("Student")) {
                        studentUsernames.add(username);
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

        String lecturerUsername = SessionManager.getLecturerUsername(); // Get the logged-in lecturer's username

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Feedback for")) {
                    // Extract lecturer's username (the one receiving feedback)
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String feedbackDetails = parts[1].trim();
                        int fromIndex = line.indexOf("from");

                        // Check if 'from' exists in the line
                        if (fromIndex != -1) {
                            String lecturerName = line.substring(12, fromIndex).trim(); // Extract lecturer name

                            // Only load feedback for the currently logged-in lecturer
                            if (lecturerName.equals(lecturerUsername)) {
                                // Extract student username and the feedback
                                String studentUsername = line.substring(line.indexOf("from") + 5, line.indexOf(":")).trim(); // Extract student name
                                String feedback = feedbackDetails;

                                studentFeedback.putIfAbsent(studentUsername, new ArrayList<>());
                                studentFeedback.get(studentUsername).add(feedback);
                            }
                        } else {
                            System.out.println("Line format error: 'from' not found in line - " + line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading feedback.txt: " + e.getMessage());
        }
    }



    private void displayStudents() {
        for (String studentUsername : studentUsernames) {
            JPanel studentPanel = new JPanel();
            studentPanel.setLayout(new BorderLayout());
            studentPanel.setPreferredSize(new Dimension(680, 50));
            studentPanel.setMaximumSize(new Dimension(680, 50));
            studentPanel.setBackground(new Color(0xB9E5E8));
            studentPanel.setBorder(new LineBorder(Color.GRAY, 1));

            JLabel studentLabel = new JLabel(studentUsername);
            studentLabel.setPreferredSize(new Dimension(600, 30));

            JButton feedbackButton = new JButton("Give Feedback");
            feedbackButton.addActionListener(e -> showFeedbackDialog(studentUsername));

            studentPanel.add(studentLabel, BorderLayout.CENTER);
            studentPanel.add(feedbackButton, BorderLayout.EAST);
            panel1.add(studentPanel);
        }

        panel1.revalidate();
        panel1.repaint();
    }

    private void saveFeedback(String studentUsername, String feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            String lecturerUsername = SessionManager.getLecturerUsername();

            String formattedFeedback = "Feedback for " + studentUsername + " from " + lecturerUsername + ": " + feedback;

            writer.write(formattedFeedback);
            writer.newLine();

            // Add the feedback to the student feedback map
            studentFeedback.putIfAbsent(studentUsername, new ArrayList<>());
            studentFeedback.get(studentUsername).add(feedback);

            // Display the feedback after saving it
            displayFeedback();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving feedback: " + e.getMessage());
        }
    }


    private void showFeedbackDetails(String studentUsername) {
        List<String> feedbackList = studentFeedback.get(studentUsername);
        if (feedbackList != null && !feedbackList.isEmpty()) {
            StringBuilder feedbackDetails = new StringBuilder();
            for (String feedback : feedbackList) {
                feedbackDetails.append(feedback).append("\n");
            }
            JOptionPane.showMessageDialog(this, "Feedback for " + studentUsername + ":\n" + feedbackDetails.toString());
        } else {
            JOptionPane.showMessageDialog(this, "No feedback found for " + studentUsername);
        }
    }

    private void showFeedbackDialog(String studentUsername) {
        String feedback = JOptionPane.showInputDialog(this, "Enter feedback for " + studentUsername);
        if (feedback != null && !feedback.trim().isEmpty()) {
            saveFeedback(studentUsername, feedback);
        } else {
            JOptionPane.showMessageDialog(this, "Feedback cannot be empty.");
        }
    }


    private void displayFeedback() {
        panel2.removeAll(); // Clear existing components before adding new ones

        for (String studentUsername : studentFeedback.keySet()) {
            JPanel feedbackPanel = new JPanel();
            feedbackPanel.setLayout(new BorderLayout());
            feedbackPanel.setPreferredSize(new Dimension(680, 50));
            feedbackPanel.setMaximumSize(new Dimension(680, 50));
            feedbackPanel.setBackground(new Color(0xB9E5E8));
            feedbackPanel.setBorder(new LineBorder(Color.GRAY, 1));

            JLabel feedbackLabel = new JLabel("Feedback from " + studentUsername);
            feedbackLabel.setPreferredSize(new Dimension(500, 30));

            JButton viewFeedbackButton = new JButton("View Feedback");
            viewFeedbackButton.addActionListener(e -> showFeedbackDetails(studentUsername));

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
            new LecturerDashboardPanel();
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new FeedbackLec();
    }
}
