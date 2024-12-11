import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class ApproveAppointmentPanel extends JFrame implements ActionListener {

    private JButton back;
    private JScrollPane scrollPane;
    private JPanel panel;

    ApproveAppointmentPanel() {
        back = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 50, 500, 100, 50, 0X7AB2D3);
        this.add(back);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panel);

        // Read consultations and check if any pending appointments exist
        boolean hasPendingAppointments = readConsultationsFromFile("consultation.txt");

        // If there are no pending appointments, display "No more requests"
        if (!hasPendingAppointments) {
            JLabel noRequestsLabel = new JLabel("No more requests", JLabel.CENTER);
            noRequestsLabel.setFont(new Font("Arial", Font.BOLD, 18));
            noRequestsLabel.setForeground(Color.RED);
            panel.add(noRequestsLabel);
        }

        setTitle("Approve Appointments");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(0xDFF2EB));
        setLayout(null);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon("resources/icon1.png");
        setIconImage(icon.getImage());

        scrollPane.setBounds(50, 25, 700, 450);

        getContentPane().add(scrollPane);
        setVisible(true);
    }

    private boolean readConsultationsFromFile(String filePath) {
        boolean hasPendingAppointments = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Only show appointments that are pending
                if (line.contains("pending")) {
                    hasPendingAppointments = true;

                    JPanel linePanel = new JPanel();
                    linePanel.setLayout(new BorderLayout());
                    linePanel.setPreferredSize(new Dimension(680, 50));
                    linePanel.setMaximumSize(new Dimension(680, 50));
                    linePanel.setBackground(new Color(0xB9E5E8));
                    linePanel.setBorder(new LineBorder(Color.GRAY, 1));

                    JLabel label = new JLabel(line);
                    label.setPreferredSize(new Dimension(600, 30));

                    final String appointment = line;

                    // Approve Button
                    JButton approveButton = new JButton("Approve");
                    approveButton.addActionListener(e -> {
                        String updatedLine = appointment.replace("pending", "approved");
                        updateAppointmentStatus("consultation.txt", appointment, updatedLine);
                        JOptionPane.showMessageDialog(this, "Appointment approved: " + updatedLine);
                        this.dispose();
                        new ApproveAppointmentPanel(); // Refresh the list after approval
                    });

                    // Reject Button
                    JButton rejectButton = new JButton("Reject");
                    rejectButton.addActionListener(e -> {
                        String updatedLine = appointment.replace("pending", "rejected");
                        updateAppointmentStatus("consultation.txt", appointment, updatedLine);
                        JOptionPane.showMessageDialog(this, "Appointment rejected: " + updatedLine);
                        this.dispose();
                        new ApproveAppointmentPanel(); // Refresh the list after rejection
                    });

                    // Add components to the panel
                    linePanel.add(label, BorderLayout.CENTER);
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(approveButton);
                    buttonPanel.add(rejectButton);
                    linePanel.add(buttonPanel, BorderLayout.EAST);

                    panel.add(linePanel);
                }
            }
        } catch (IOException e) {
            // If there's an error reading the file, show an error message
            JPanel errorPanel = new JPanel();
            errorPanel.add(new JLabel("Error reading file: " + e.getMessage()));
            panel.add(errorPanel);
        }

        return hasPendingAppointments;
    }

    private void updateAppointmentStatus(String filePath, String oldLine, String updatedLine) {
        try {
            // Read the file and update the appointment status
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder fileContents = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(oldLine)) {
                    fileContents.append(updatedLine);
                } else {
                    fileContents.append(line);
                }
                fileContents.append(System.lineSeparator()); // Ensure new lines are preserved
            }
            reader.close();

            // Write the updated content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(fileContents.toString());
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating appointment status: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            this.dispose();
            new LecturerDashboardPanel();
        }
    }

    public static void main(String[] args) {
        new ApproveAppointmentPanel();
    }
}
