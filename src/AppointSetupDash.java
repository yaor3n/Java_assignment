import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppointSetupDash extends JFrame implements ActionListener {

    private JLabel label, label2;
    private  JComboBox timeSlotCB, dayCB, monthCB, yearCB;
    private JButton back;
    private JFrame parentFrame;

    AppointSetupDash(JFrame parentFrame) {

        this.parentFrame = parentFrame;  // Set the parent frame

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
        timeSlotCB.setBounds(50,80,350,35);
        timeSlotCB.setFont(new Font("Arial", Font.BOLD,18));
        timeSlotCB.setForeground(new Color(0x000000));
        timeSlotCB.setBackground(new Color(0xFFFFFF));
        this.add(timeSlotCB);

        label2 = FrameMethods.labelSetup("Set Available Date:","Arial",25,0x000000,50,150,300,100);
        this.add(label2);

        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.format("%02d", i + 1);
        }

        dayCB = new JComboBox<>(days);
        dayCB.setBounds(50,230,80,35);
        dayCB.setFont(new Font("Arial", Font.BOLD,18));
        dayCB.setForeground(new Color(0x000000));
        dayCB.setBackground(new Color(0xFFFFFF));
        this.add(dayCB);

        String[] months = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };

        monthCB = new JComboBox<>(months);
        monthCB.setBounds(140,230,120,35);
        monthCB.setFont(new Font("Arial", Font.BOLD,18));
        monthCB.setForeground(new Color(0x000000));
        monthCB.setBackground(new Color(0xFFFFFF));
        this.add(monthCB);

        String[] years = {"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        yearCB = new JComboBox<>(years);
        yearCB.setBounds(270,230,100,35);
        yearCB.setFont(new Font("Arial", Font.BOLD,18));
        yearCB.setForeground(new Color(0x000000));
        yearCB.setBackground(new Color(0xFFFFFF));
        this.add(yearCB);

        back = FrameMethods.buttonSetup("Back","Arial",25,0x000000,this,50,500,100,50,0X7AB2D3);
        this.add(back);

        FrameMethods.windowSetup(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            this.dispose();
            parentFrame.setVisible(true);
        }
    }
}
