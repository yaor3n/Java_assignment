import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LecLoginPanel extends JFrame implements ActionListener {
// btw ActionListener will have error until u actually implement it in a button
    private JButton SubmitButtonLec, LoginBackLec;

    LecLoginPanel() {

        // label to prompt username input
        JLabel enterUsername = new JLabel("Enter Lecturer username: ");
        enterUsername.setFont(new Font("Arial", Font.BOLD,25));
        enterUsername.setForeground(new Color(0x000000));
        enterUsername.setVerticalTextPosition(JLabel.CENTER);
        enterUsername.setHorizontalTextPosition(JLabel.CENTER);
        enterUsername.setBounds(250,50,500,100); // x, y, width, height
        this.add(enterUsername);

        // label to prompt password input
        JLabel enterPassword = new JLabel("Enter Lecturer password: ");
        enterPassword.setFont(new Font("Arial", Font.BOLD,25));
        enterPassword.setForeground(new Color(0x000000));
        enterPassword.setVerticalTextPosition(JLabel.CENTER);
        enterPassword.setHorizontalTextPosition(JLabel.CENTER);
        enterPassword.setBounds(250,200,500,100); // x, y, width, height
        this.add(enterPassword);

        // textbox for lec username
        JTextField LecUsername = new JTextField();
        LecUsername.setBounds(250,150,300,50);
        LecUsername.setFont(new Font("Arial",Font.PLAIN,15));
        this.add(LecUsername); // rmb add if not wont show

        // textbox for lec password
        JTextField LecPassword = new JTextField();
        LecPassword.setBounds(250,300,300,50);
        LecUsername.setFont(new Font("Arial",Font.PLAIN,15));
        this.add(LecPassword);

        // back button
        LoginBackLec = new JButton();
        LoginBackLec.setText("Back");
        LoginBackLec.setFont(new Font("Arial",Font.BOLD,25));
        LoginBackLec.setForeground(new Color(0x000000));
        LoginBackLec.addActionListener(this);
        LoginBackLec.setBounds(350,455,100,50);
        LoginBackLec.setBackground(new Color(0x7AB2D3));
        this.add(LoginBackLec);
        // didnt work last time cuz i didnt use class level instead i used this:
        // JButon LoginBackLec = new blablabla

        // submit button
        SubmitButtonLec = new JButton();
        SubmitButtonLec.setText("Login");
        SubmitButtonLec.setFont(new Font("Arial",Font.BOLD,25));
        SubmitButtonLec.setForeground(new Color(0x000000));
        SubmitButtonLec.addActionListener(this);
        SubmitButtonLec.setBounds(335,375,125,50);
        SubmitButtonLec.setBackground(new Color(0x7AB2D3));
        this.add(SubmitButtonLec);


        FrameMethods.loginSetup(this); // jus reuse this for window template


    }

    @Override
    public void actionPerformed(ActionEvent lecActionA) {
        if (lecActionA.getSource() == LoginBackLec) {
            new UserSelect();
            this.dispose();
        } else if (lecActionA.getSource() == SubmitButtonLec) {
            System.out.println("submitted");
        }

    }

}