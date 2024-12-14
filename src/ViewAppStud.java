import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewAppStud extends JFrame implements ActionListener {

    private JButton back;
    private JScrollPane scrollPane1, scrollPane2;
    private JPanel panel1, panel2;
    private JLabel label1, label2;

    ViewAppStud() {

        back = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 50, 525, 100, 50, 0X7AB2D3);
        this.add(back);

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        scrollPane1 = new JScrollPane(panel1);

        label1 = FrameMethods.labelSetup("View Past Appointments:", "Arial", 25, 0x000000, 50, -5, 700, 100);
        this.add(label1);

        scrollPane1.setBounds(50, 60, 700, 200);

        label2 = FrameMethods.labelSetup("View Upcoming Appointments:", "Arial", 25, 0x000000, 50, 255, 700, 100);
        this.add(label2);

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        scrollPane2 = new JScrollPane(panel2);

        scrollPane2.setBounds(50, 320, 700, 200);

        getContentPane().add(scrollPane1);
        getContentPane().add(scrollPane2);

        setTitle("View Appointments");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(0xDFF2EB));
        setLayout(null);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon("resources/icon1.png");
        setIconImage(icon.getImage());

        loadAppointments(); // Load appointments from consultation.txt
        setVisible(true);
    }

    private void loadAppointments() {
        // Corrected date-time format
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a - hh:mm a,dd MMMM yyyy");
        Date now = new Date();
        String currentStudent = SessionManager.getStudentUsername(); // Logged-in student's username

        try (BufferedReader reader = new BufferedReader(new FileReader("consultation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    // Split line into parts
                    String[] parts = line.split("\\|");
                    String[] firstPart = parts[0].split(",", 2); // lecturerName and dateTime
                    String bookedByPart = parts[1].split(",", 2)[0].trim(); // Booked by: stud1

                    // Extract student username from "Booked by: <username>"
                    String bookedByStudent = bookedByPart.split(":")[1].trim();

                    // Only process appointments booked by the current student
                    if (bookedByStudent.equalsIgnoreCase(currentStudent)) {
                        // Extract dateTime and parse the appointment date
                        String dateTime = firstPart[1].trim(); // e.g., "09:00 AM - 10:00 AM,01 January 2023"
                        Date appointmentDate = dateFormat.parse(dateTime);

                        // Create the appointment details without the status part (after the "|")
                        String appointmentDetails = firstPart[0] + "," + dateTime + " | Booked by: " + bookedByStudent; // Remove status part

                        JLabel appointmentLabel = new JLabel(appointmentDetails);
                        appointmentLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                        // Categorize appointments based on date
                        if (appointmentDate.before(now)) {
                            panel1.add(appointmentLabel); // Past appointments
                        } else {
                            panel2.add(appointmentLabel); // Upcoming appointments
                        }
                    }
                } catch (ParseException e) {
                    System.err.println("Error parsing date for line: " + line + " - " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Refresh panels to reflect loaded data
        panel1.revalidate();
        panel1.repaint();
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
        new ViewAppStud();
    }
}
