import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class setReschedule extends JFrame implements ActionListener {

    private JLabel label, label2, label3, label4;
    private JComboBox<String> timeSlotCB, dayCB, monthCB, yearCB, lecCB;
    private JButton confirmBtn;
    private ArrayList<String> lecturers = new ArrayList<>();

    setReschedule() {

        label4 = FrameMethods.labelSetup("Enter PREVIOUS Appointment Details:", "Arial", 25, 0x000000, 50, -5, 800, 100);
        this.add(label4);

        label = FrameMethods.labelSetup("Previous Time:", "Arial", 25, 0x000000, 50, 75, 300, 100);
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
        timeSlotCB.setForeground(new Color(0x000000));
        timeSlotCB.setBackground(new Color(0xFFFFFF));
        this.add(timeSlotCB);

        label2 = FrameMethods.labelSetup("Previous Date:", "Arial", 25, 0x000000, 50, 210, 300, 100);
        this.add(label2);

        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.format("%02d", i + 1);
        }

        dayCB = new JComboBox<>(days);
        dayCB.setBounds(50, 290, 80, 35);
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
        monthCB.setBounds(140, 290, 120, 35);
        monthCB.setFont(new Font("Arial", Font.BOLD, 18));
        monthCB.setForeground(new Color(0x000000));
        monthCB.setBackground(new Color(0xFFFFFF));
        this.add(monthCB);

        String[] years = {"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        yearCB = new JComboBox<>(years);
        yearCB.setBounds(270, 290, 100, 35);
        yearCB.setFont(new Font("Arial", Font.BOLD, 18));
        yearCB.setForeground(new Color(0x000000));
        yearCB.setBackground(new Color(0xFFFFFF));
        this.add(yearCB);

        label3 = FrameMethods.labelSetup("Previous Lecturer:", "Arial", 25, 0x000000, 50, 335, 300, 100);
        this.add(label3);

        lecCB = new JComboBox<>();
        lecCB.setBounds(50, 420, 350, 35);
        lecCB.setFont(new Font("Arial", Font.BOLD, 18));
        lecCB.setForeground(new Color(0x000000));
        lecCB.setBackground(new Color(0xFFFFFF));
        this.add(lecCB);

        confirmBtn = FrameMethods.buttonSetup("Next", "Arial", 25, 0x000000, this, 550, 500, 100, 50, 0X7AB2D3);
        this.add(confirmBtn);

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
            String selectedDate = day + " " + month + " " + year;

            String loggedInStudent = SessionManager.getStudentUsername();

            // Find a match in the consultation file
            String matchedLine = findMatchedLine("consultation.txt", loggedInStudent, lecCB.getSelectedItem().toString(), timeSlot, selectedDate);

            if (matchedLine != null) {
                // Delete the matched line
                try {
                    deleteLineFromFile("consultation.txt", matchedLine);
                    JOptionPane.showMessageDialog(this, "Please select new appointment details.");
                    new ConfirmReschedule();
                    this.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error opening ConfirmReschedule.java");
                }

            }
        }
    }

    private void deleteLineFromFile(String fileName, String lineToDelete) {
        File inputFile = new File(fileName);
        File tempFile = new File("temp_" + fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // Write all lines except the one to delete
                if (!currentLine.equals(lineToDelete)) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing the file: " + e.getMessage());
        }

        // Replace original file with the updated temp file
        if (!inputFile.delete()) {
            JOptionPane.showMessageDialog(this, "Could not delete the original file.");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            JOptionPane.showMessageDialog(this, "Could not rename the temp file.");
        }
    }



    private String findMatchedLine(String fileName, String student, String lecturer, String timeSlot, String date) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by ' | ' to separate the components
                String[] parts = line.split(" \\| ");
                if (parts.length < 2) continue;  // Skip lines that don't have expected parts

                // Get the appointment details and status from the line
                String appointmentDetails = parts[0];  // "lec1, 11:00 AM - 12:00 PM, 02 February 2025"
                String status = parts[1].trim();  // "Booked by: stud1, rescheduled"

                // Split the appointment details into its components
                String[] appointmentParts = appointmentDetails.split(",");
                if (appointmentParts.length < 3) continue;  // Skip lines with incomplete appointment details

                String lineLecturer = appointmentParts[0].trim();  // Lecturer
                String lineTimeSlot = appointmentParts[1].trim();  // Time Slot
                String lineDate = appointmentParts[2].trim();  // Date

                // Now compare each component individually
                if (lineLecturer.equals(lecturer) && lineTimeSlot.equals(timeSlot) && lineDate.equals(date)) {
                    // Check if the student and status match as well
                    if (status.contains("Booked by: " + student)) {
                        return line;  // Found the matching line
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;  // No match found
    }




}
