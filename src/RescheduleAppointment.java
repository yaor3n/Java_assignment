import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RescheduleAppointment extends JFrame implements ActionListener {

    private JButton back, rescheduleButton;
    private JScrollPane scrollPane;
    private JPanel panel;

    RescheduleAppointment() {

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panel);

        boolean hasRescheduleAppointments = readConsultationsFromFile("consultation.txt");

        if (!hasRescheduleAppointments) {
            JLabel noRescheduleLabel = new JLabel("No more Appointments", JLabel.CENTER);
            noRescheduleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            noRescheduleLabel.setForeground(Color.RED);
            panel.add(noRescheduleLabel);
        }

        back = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 50, 500, 100, 50, 0X7AB2D3);
        this.add(back);

        setTitle("Reschedule Appointments");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            new StudentDashboardPanel();
            this.dispose();
        }
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

                    // Reschedule Button
                    rescheduleButton = new JButton("Reschedule");
                    rescheduleButton.addActionListener(e -> {
                        String updatedLine = appointment.replace("pending", "reschedule");
                        updateAppointmentStatus("consultation.txt", appointment, updatedLine);
                        JOptionPane.showMessageDialog(this, "Appointment rescheduled: " + updatedLine);
                        this.dispose();
                        new StudentDashboardPanel();
                    });


                    // Add components to the panel
                    linePanel.add(label, BorderLayout.CENTER);
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(rescheduleButton);
                    buttonPanel.setBackground(new Color(0xB9E5E8));
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
}
