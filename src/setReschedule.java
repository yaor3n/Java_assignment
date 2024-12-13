import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class setReschedule extends JFrame implements ActionListener {

    private JLabel label, label2;
    private JComboBox<String> timeSlotCB, dayCB, monthCB, yearCB, lecCB;
    private JButton back, confirmBtn;
    private ArrayList<String> lecturers = new ArrayList<>();

    setReschedule() {
        label = FrameMethods.labelSetup("Set Available Time:", "Arial", 25, 0x000000, 50, 5, 300, 100);
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
        timeSlotCB.setBounds(50, 80, 350, 35);
        timeSlotCB.setFont(new Font("Arial", Font.BOLD, 18));
        timeSlotCB.setForeground(new Color(0x000000));
        timeSlotCB.setBackground(new Color(0xFFFFFF));
        this.add(timeSlotCB);

        label2 = FrameMethods.labelSetup("Set Available Date:", "Arial", 25, 0x000000, 50, 150, 300, 100);
        this.add(label2);

        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.format("%02d", i + 1);
        }

        dayCB = new JComboBox<>(days);
        dayCB.setBounds(50, 230, 80, 35);
        dayCB.setFont(new Font("Arial", Font.BOLD, 18));
        dayCB.setForeground(new Color(0x000000));
        dayCB.setBackground(new Color(0xFFFFFF));
        this.add(dayCB);

        String[] months = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };

        monthCB = new JComboBox<>(months);
        monthCB.setBounds(140, 230, 120, 35);
        monthCB.setFont(new Font("Arial", Font.BOLD, 18));
        monthCB.setForeground(new Color(0x000000));
        monthCB.setBackground(new Color(0xFFFFFF));
        this.add(monthCB);

        String[] years = {"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        yearCB = new JComboBox<>(years);
        yearCB.setBounds(270, 230, 100, 35);
        yearCB.setFont(new Font("Arial", Font.BOLD, 18));
        yearCB.setForeground(new Color(0x000000));
        yearCB.setBackground(new Color(0xFFFFFF));
        this.add(yearCB);

        lecCB = new JComboBox<>();
        lecCB.setBounds(50, 380, 350, 35);
        lecCB.setFont(new Font("Arial", Font.BOLD, 18));
        lecCB.setForeground(new Color(0x000000));
        lecCB.setBackground(new Color(0xFFFFFF));
        this.add(lecCB);

        confirmBtn = FrameMethods.buttonSetup("Save", "Arial", 25, 0x000000, this, 550, 500, 100, 50, 0X7AB2D3);
        this.add(confirmBtn);

        back = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 50, 500, 100, 50, 0X7AB2D3);
        this.add(back);

        loadLecturers("Accounts.txt");

        FrameMethods.windowSetup(this);
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

    public static void main(String[] args) {
        new setReschedule();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmBtn) {
            String timeSlot = (String) timeSlotCB.getSelectedItem();
            String day = (String) dayCB.getSelectedItem();
            String month = (String) monthCB.getSelectedItem();
            String year = (String) yearCB.getSelectedItem();

            // Get the logged-in student's username dynamically from SessionManager
            String loggedInStudent = SessionManager.getStudentUsername();

            updateConsultationBooking("consultation.txt", loggedInStudent, lecCB.getSelectedItem().toString(), timeSlot, day + " " + month + " " + year);

            JOptionPane.showMessageDialog(this, "Appointment successfully rescheduled!");
            new RescheduleAppointment();
            this.dispose();
        } else if (e.getSource() == back) {
            new RescheduleAppointment();
            this.dispose();
        }
    }

    private void updateConsultationBooking(String filePath, String loggedInStudent, String selectedLecturer, String selectedTime, String selectedDate) {
        File file = new File(filePath);
        StringBuilder content = new StringBuilder();
        boolean statusUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the current line matches the logged-in student
                if (line.contains("Booked by: " + loggedInStudent)) {
                    // Split the line into parts (lecturer, time, date | status info)
                    String[] parts = line.split(" \\| ");
                    if (parts.length > 1) {
                        // Extract the first part (lecturer, time, date)
                        String[] appointmentDetails = parts[0].split(",");

                        // Update the lecturer, time, and date
                        appointmentDetails[0] = selectedLecturer;
                        appointmentDetails[1] = selectedTime;
                        appointmentDetails[2] = selectedDate;

                        // Rebuild the updated line with the new status 'reschedule'
                        String updatedAppointment = String.join(",", appointmentDetails);
                        line = updatedAppointment + " | Booked by: " + loggedInStudent + ", reschedule";
                        statusUpdated = true;
                    }
                }
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading the file: " + e.getMessage());
            return;
        }

        // Write the updated content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content.toString());
            if (statusUpdated) {
                JOptionPane.showMessageDialog(this, "Consultation booking updated successfully with 'reschedule' status!");
            } else {
                JOptionPane.showMessageDialog(this, "No matching consultation found for the logged-in student.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to the file: " + e.getMessage());
        }
    }

}
