import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ConsultLec extends JFrame implements ActionListener {

    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton back;

    ConsultLec() {

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panel);

        readAppointmentsFromFile("appointments.txt");

        setTitle("Consult a Lecturer");
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

        back = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 350, 500, 100, 50, 0x7AB2D3);
        this.add(back);


    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == back) {
            new StudentDashboardPanel();
            this.dispose();
        }
    }

    // Method to read appointments from a file and display them in the window
    private void readAppointmentsFromFile(String filePath) {
        // Fetching string from SessionManager so the variable can be written to the file
        String studentUsername = SessionManager.getStudentUsername();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("Booked by:")) {

                    JPanel linePanel = new JPanel();
                    linePanel.setLayout(new BorderLayout());
                    linePanel.setPreferredSize(new Dimension(680,50));
                    linePanel.setMaximumSize(new Dimension(680,50));
                    linePanel.setBackground(new Color(0xB9E5E8));
                    linePanel.setBorder(new LineBorder(Color.GRAY, 1));

                    JLabel label = new JLabel(line);
                    label.setPreferredSize(new Dimension(600, 30));

                    final String appointment = line;
                    JButton bookButton = new JButton("Book");
                    bookButton.addActionListener(e -> {
                        // When the button is clicked, append the studentUsername and write to a file
                        String updatedLine = appointment + " | Booked by: " + studentUsername + "," + "pending";
                        appendToFile("consultation.txt", updatedLine);
                        JOptionPane.showMessageDialog(this, "Booking appointment: " + updatedLine);
                        this.dispose();
                        new ConsultLec();
                    });

                    // Add the label to the center of the linePanel
                    linePanel.add(label, BorderLayout.CENTER);

                    // Add the bookButton to the right of the linePanel
                    linePanel.add(bookButton, BorderLayout.EAST);

                    // Add the linePanel to the main panel
                    panel.add(linePanel);
                }
            }
        } catch (IOException e) {
            // If there's an error reading the file, show an error message in the panel
            JPanel errorPanel = new JPanel();
            errorPanel.add(new JLabel("Error reading file: " + e.getMessage()));
            panel.add(errorPanel);
        }
    }

    // Method to append the updated appointment to a file
    private void appendToFile(String filePath, String updatedLine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(updatedLine);
            writer.newLine(); // Write a newline after each entry
        } catch (IOException e) {
            // Handle any file writing errors
            JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ConsultLec();
    }
}
