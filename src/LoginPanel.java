import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JFrame {

    LoginPanel() {

        // Login UI & some formatting
        this.setSize(800,650);
        this.setTitle("APU Psychology Consultation Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0xDFF2EB));
        this.setLayout(null);

        // Window icon
        ImageIcon icon1 = new ImageIcon("resources/icon1.png");
        this.setIconImage(icon1.getImage());

        // label to prompt username input
        JLabel enterUsername = new JLabel("Enter username: ");
        enterUsername.setFont(new Font("Arial", Font.BOLD,25));
        enterUsername.setForeground(new Color(0x000000));
        enterUsername.setVerticalTextPosition(JLabel.CENTER);
        enterUsername.setHorizontalTextPosition(JLabel.CENTER);
        enterUsername.setBounds(300,50,300,100); // x, y, width, height
        this.add(enterUsername);

        // label to prompt password input
        JLabel enterPassword = new JLabel("Enter password: ");
        enterPassword.setFont(new Font("Arial", Font.BOLD,25));
        enterPassword.setForeground(new Color(0x000000));
        enterPassword.setVerticalTextPosition(JLabel.CENTER);
        enterPassword.setHorizontalTextPosition(JLabel.CENTER);
        enterPassword.setBounds(300,300,300,100); // x, y, width, height
        this.add(enterPassword);





        this.setVisible(true);

    }


}