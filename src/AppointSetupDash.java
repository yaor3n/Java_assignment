import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class AppointSetupDash extends JFrame implements ActionListener {

    private JLabel label, label2, label3;
    private JComboBox<String> timeSlotCB, dayCB, monthCB, yearCB;
    private JButton back, confirmBtn;

    public AppointSetupDash() {
        String lecturerUsername = SessionManager.getLecturerUsername();

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

        confirmBtn = FrameMethods.buttonSetup("Save", "Arial", 25, 0x000000, this, 550, 500, 100, 50, 0X7AB2D3);
        this.add(confirmBtn);

        back = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 50, 500, 100, 50, 0X7AB2D3);
        this.add(back);

        label3 = FrameMethods.labelSetup("Enter lecturer name:", "Arial", 25, 0x000000, 50, 295, 300, 100);
        this.add(label3);

        FrameMethods.windowSetup(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            this.dispose();
            new LecturerDashboardPanel();
        } else if (e.getSource() == confirmBtn) {
            saveToFile();
            this.dispose();
            new LecturerDashboardPanel();
        }
    }

    private void saveToFile() {
        String timeSlot = (String) timeSlotCB.getSelectedItem();
        String day = (String) dayCB.getSelectedItem();
        String month = (String) monthCB.getSelectedItem();
        String year = (String) yearCB.getSelectedItem();
//        String lecturerName = EnterLecName.getText();

        // Format the selected date
        String date = day + " " + month + " " + year;

        // Format the line to write
        String line = SessionManager.getLecturerUsername() + "," + timeSlot + "," + date;

        // Write to file
        try (FileWriter writer = new FileWriter("appointments.txt", true)) {
            writer.write(line + System.lineSeparator());
            JOptionPane.showMessageDialog(this, "Appointment saved successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving appointment: " + ex.getMessage());
        }
    }
}
