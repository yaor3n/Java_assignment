import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateAccPanel extends JFrame implements  ActionListener {

    private JLabel createAccLabel, enterUsername, enterPassword;
    private JButton SubmitButtonCreAcc, CreAccBackBtn;

    CreateAccPanel() {

        createAccLabel = new JLabel("Account Creation");
        createAccLabel.setFont(new Font("Arial",Font.BOLD,30));
        createAccLabel.setForeground(new Color(0x000000));
        createAccLabel.setVerticalTextPosition(JLabel.CENTER);
        createAccLabel.setHorizontalTextPosition(JLabel.CENTER);
        createAccLabel.setBounds(250,25,300,100);
        this.add(createAccLabel);

        enterUsername = new JLabel("Enter Username:");
        enterUsername.setFont(new Font("Arial",Font.BOLD,25));
        enterUsername.setForeground(new Color(0x000000));
        enterUsername.setVerticalTextPosition(JLabel.CENTER);
        enterUsername.setHorizontalTextPosition(JLabel.CENTER);
        enterUsername.setBounds(50,85,300,100);
        this.add(enterUsername);

        JTextField createUsername = new JTextField();
        createUsername.setBounds(50,150,300,50);
        createUsername.setFont(new Font("Arial",Font.PLAIN,15));
        this.add(createUsername);

        enterPassword = new JLabel("Enter Password:");
        enterPassword.setFont(new Font("Arial",Font.BOLD,25));
        enterPassword.setForeground(new Color(0x000000));
        enterPassword.setVerticalTextPosition(JLabel.CENTER);
        enterPassword.setHorizontalTextPosition(JLabel.CENTER);
        enterPassword.setBounds(50,225,300,100);
        this.add(enterPassword);

        JTextField createPassword = new JTextField();
        createPassword.setBounds(50,300,300,50);
        createPassword.setFont(new Font("Arial",Font.PLAIN,15));
        this.add(createPassword);

        CreAccBackBtn = new JButton();
        CreAccBackBtn.setText("Back");
        CreAccBackBtn.setFont(new Font("Arial",Font.BOLD,25));
        CreAccBackBtn.setForeground(new Color(0x000000));
        CreAccBackBtn.addActionListener(this);
        CreAccBackBtn.setBounds(350,455,100,50);
        CreAccBackBtn.setBackground(new Color(0x7AB2D3));
        this.add(CreAccBackBtn);

        SubmitButtonCreAcc = new JButton();
        SubmitButtonCreAcc.setText("Create Account");
        SubmitButtonCreAcc.setFont(new Font("Arial",Font.BOLD,25));
        SubmitButtonCreAcc.setForeground(new Color(0x000000));
        SubmitButtonCreAcc.addActionListener(this);
        SubmitButtonCreAcc.setBounds(250,375,300,50);
        SubmitButtonCreAcc.setBackground(new Color(0x7AB2D3));
        this.add(SubmitButtonCreAcc);


        FrameMethods.loginSetup(this);

    }

    @Override
    public void actionPerformed(ActionEvent CreAccA) {
        if (CreAccA.getSource() == CreAccBackBtn) {
            new UserSelect();
            this.dispose();
        } else if (CreAccA.getSource() == SubmitButtonCreAcc) {
            System.out.println("created");
        }
    }

}
