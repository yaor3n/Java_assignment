import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentDashboardPanel extends JFrame implements ActionListener {

    private JLabel welcomeLabel;
    private JButton MakeConsult, logoutBtn;

    SimpleDateFormat timeFormat;
    SimpleDateFormat dateFormat;
    JLabel timeLabel;
    JLabel dateLabel;
    String time;

    StudentDashboardPanel() {

        FrameMethods.windowSetup(this);

        String studentUsername = SessionManager.getStudentUsername();

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


        welcomeLabel = FrameMethods.labelSetup("Hello, " + studentUsername + "!","Arial",25,0x000000,50,5,700,100);
        this.add(welcomeLabel);

        MakeConsult = FrameMethods.buttonSetup("Make Consultation","Arial",25,0x000000,this,225,100,350,100,0X7AB2D3);
        this.add(MakeConsult);

        logoutBtn = FrameMethods.buttonSetup("LogOut","Arial",20,0x000000,this,50,500,115,50,0X7AB2D3);
        this.add(logoutBtn);


    }

    private void updateClock() {
        String time = timeFormat.format(Calendar.getInstance().getTime());
        timeLabel.setText(time);

        String date = dateFormat.format(Calendar.getInstance().getTime());
        dateLabel.setText(date);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == MakeConsult) {
            new ConsultLec();
            this.dispose();
        } else if (e.getSource() == logoutBtn) {
            SessionManager.getInstance().clearSession();
            new UserSelect();
            this.dispose();
        }
    }
}
