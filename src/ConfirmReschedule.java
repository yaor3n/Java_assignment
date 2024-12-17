import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ConfirmReschedule extends JFrame implements ActionListener {

    private JLabel label, label2, label3, label4;
    private JComboBox<String> timeSlotCB, dayCB, monthCB, yearCB, lecCB;
    private JButton confirmBtn;
    private ArrayList<String> lecturers = new ArrayList<>();

    ConfirmReschedule() {
        // Labels and components setup
        label4 = FrameMethods.labelSetup("Enter NEW Appointment Details:", "Arial", 25, 0x000000, 50, -5, 800, 100);
        this.add(label4);

        label = FrameMethods.labelSetup("New Time:", "Arial", 25, 0x000000, 50, 75, 300, 100);
        this.add(label);

        String[] timeSlots = {
                "09:00 AM - 10:00 AM",
                "10:00 AM - 11:00 AM",
                "11:00 AM - 12:00 PM",
                "01:00 PM - 02:00 PM",
                "02:00 PM - 03:00 PM",
                "03:00 PM - 04:00 PM"
        };

        timeSlotCB = new JComboBox<>(timeSlots);
        timeSlotCB.setBounds(50, 150, 350, 35);
        timeSlotCB.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(timeSlotCB);

        label2 = FrameMethods.labelSetup("New Date:", "Arial", 25, 0x000000, 50, 210, 300, 100);
        this.add(label2);

        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.format("%02d", i + 1);
        }

        dayCB = new JComboBox<>(days);
        dayCB.setBounds(50, 290, 80, 35);
        dayCB.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(dayCB);

        String[] months = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };

        monthCB = new JComboBox<>(months);
        monthCB.setBounds(140, 290, 120, 35);
        monthCB.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(monthCB);

        String[] years = {"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        yearCB = new JComboBox<>(years);
        yearCB.setBounds(270, 290, 100, 35);
        yearCB.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(yearCB);

        label3 = FrameMethods.labelSetup("New Lecturer:", "Arial", 25, 0x000000, 50, 335, 300, 100);
        this.add(label3);

        lecCB = new JComboBox<>();
        lecCB.setBounds(50, 420, 350, 35);
        lecCB.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(lecCB);

        confirmBtn = FrameMethods.buttonSetup("Next", "Arial", 25, 0x000000, this, 550, 500, 100, 50, 0X7AB2D3);
        this.add(confirmBtn);

        loadLecturers("Accounts.txt");

        FrameMethods.windowSetup(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == confirmBtn) {
            saveRescheduleDetails("consultation.txt");
            new RescheduleAppointment();
            this.dispose();
        }
    }

    private void loadLecturers(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[2].equalsIgnoreCase("Lecturer")) {
                    lecturers.add(parts[0]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading the file: " + e.getMessage());
        }
        for (String lecturer : lecturers) {
            lecCB.addItem(lecturer);
        }
    }

    private void saveRescheduleDetails(String filePath) {
        String lecturer = (String) lecCB.getSelectedItem();
        String timeSlot = (String) timeSlotCB.getSelectedItem();
        String day = (String) dayCB.getSelectedItem();
        String month = (String) monthCB.getSelectedItem();
        String year = (String) yearCB.getSelectedItem();

        String formattedDate = String.format("%s %s %s", day, month, year);

        String student = SessionManager.getStudentUsername();

        String lineToWrite = String.format("%s,%s,%s | Booked by: %s, reschedule", lecturer, timeSlot, formattedDate, student);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(lineToWrite);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Details saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        new ConfirmReschedule();
    }
}
