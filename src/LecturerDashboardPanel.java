import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LecturerDashboardPanel extends JFrame implements ActionListener {

    private JLabel welcomeLabel;
    private JButton setAppointmentBtn, approveAppointmentBtn, logoutBtn;

    SimpleDateFormat timeFormat;
    SimpleDateFormat dateFormat;
    JLabel timeLabel;
    JLabel dateLabel;
    String time;

    LecturerDashboardPanel() {

        String lecturerName = SessionManager.getLecturerUsername();

        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        timeLabel = new JLabel();
        time = timeFormat.format(Calendar.getInstance().getTime());
        timeLabel.setText(time);
        timeLabel.setBounds(600,5,700,100);
        timeLabel.setFont(new Font("Arial",Font.BOLD,25));
        this.add(timeLabel);


        dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        dateLabel = new JLabel();
        dateLabel.setBounds(492, 1, 500, 50);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(dateLabel);

        // call this one first cuz if not it will only show clock after first tick
        updateClock();

        Timer timer = new Timer(1000, e -> updateClock());
        timer.start();

        welcomeLabel = FrameMethods.labelSetup("Good Day, " + lecturerName + "!","Arial",25,0x000000,50,5,700,100);
        this.add(welcomeLabel);

        setAppointmentBtn = FrameMethods.buttonSetup("Set Availability","Arial",25,0x000000,this,225,100,350,100,0X7AB2D3);
        this.add(setAppointmentBtn);

        approveAppointmentBtn = FrameMethods.buttonSetup("Approve Appointment","Arial",25,0x000000,this,225,220,350,100,0X7AB2D3);
        this.add(approveAppointmentBtn);


        logoutBtn = FrameMethods.buttonSetup("LogOut","Arial",20,0x000000,this,50,500,115,50,0X7AB2D3);
        this.add(logoutBtn);

        FrameMethods.windowSetup(this);
    }

    private void updateClock() {
        String time = timeFormat.format(Calendar.getInstance().getTime());
        timeLabel.setText(time);

        String date = dateFormat.format(Calendar.getInstance().getTime());
        dateLabel.setText(date);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == setAppointmentBtn) {
            this.dispose();
            new AppointSetupDash();
        } else if (e.getSource() == approveAppointmentBtn) {
            this.dispose();
            new ApproveAppointmentPanel();
        } else if (e.getSource() == logoutBtn) {
            SessionManager.getInstance().clearSession();
            this.dispose();
            new UserSelect();
        }
    }
    public static void main(String[] args) {
        new LecturerDashboardPanel();
    }
}