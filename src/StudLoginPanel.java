import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudLoginPanel extends JFrame implements ActionListener {

    private JButton SubmitButtonStud, LoginBackStud;

    StudLoginPanel() {
        // label to prompt username input
        JLabel enterUsername = new JLabel("Enter Student username: ");
        enterUsername.setFont(new Font("Arial", Font.BOLD,25));
        enterUsername.setForeground(new Color(0x000000));
        enterUsername.setVerticalTextPosition(JLabel.CENTER);
        enterUsername.setHorizontalTextPosition(JLabel.CENTER);
        enterUsername.setBounds(250,50,500,100); // x, y, width, height
        this.add(enterUsername);

        // label to prompt password input
        JLabel enterPassword = new JLabel("Enter Student password: ");
        enterPassword.setFont(new Font("Arial", Font.BOLD,25));
        enterPassword.setForeground(new Color(0x000000));
        enterPassword.setVerticalTextPosition(JLabel.CENTER);
        enterPassword.setHorizontalTextPosition(JLabel.CENTER);
        enterPassword.setBounds(250,200,500,100); // x, y, width, height
        this.add(enterPassword);

        // textbox for stud username
        JTextField StudUsername = new JTextField();
        StudUsername.setBounds(250,150,300,50);
        StudUsername.setFont(new Font("Arial",Font.PLAIN,15));
        this.add(StudUsername); // rmb add if not wont show

        // textbox for stud password
        JTextField StudPassword = new JTextField();
        StudPassword.setBounds(250,300,300,50);
        StudUsername.setFont(new Font("Arial",Font.PLAIN,15));
        this.add(StudPassword);

        // back button
        LoginBackStud = new JButton();
        LoginBackStud.setText("Back");
        LoginBackStud.setFont(new Font("Arial",Font.BOLD,25));
        LoginBackStud.setForeground(new Color(0x000000));
        LoginBackStud.addActionListener(this);
        LoginBackStud.setBounds(350,455,100,50);
        LoginBackStud.setBackground(new Color(0x7AB2D3));
        this.add(LoginBackStud);

        // submit button
        SubmitButtonStud = new JButton();
        SubmitButtonStud.setText("Login");
        SubmitButtonStud.setFont(new Font("Arial",Font.BOLD,25));
        SubmitButtonStud.setForeground(new Color(0x000000));
        SubmitButtonStud.addActionListener(this);
        SubmitButtonStud.setBounds(335,375,125,50);
        SubmitButtonStud.setBackground(new Color(0x7AB2D3));
        this.add(SubmitButtonStud);

        FrameMethods.loginSetup(this);

    }

    @Override
    public void actionPerformed(ActionEvent studActionA) {
        if (studActionA.getSource() == LoginBackStud) {
            new UserSelect();
            dispose();
        } else if (studActionA.getSource() == SubmitButtonStud) {
            System.out.println("submitted");
        }

    }

}
