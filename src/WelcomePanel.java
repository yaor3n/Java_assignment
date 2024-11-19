import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePanel extends JFrame implements ActionListener {

    private JLabel WelcomeLabel, WelcomeLabel2;
    private JButton WelcomeStartBtn;

    WelcomePanel() {

        WelcomeLabel = new JLabel("Welcome To");
        WelcomeLabel.setFont(new Font("Arial",Font.BOLD,35));
        WelcomeLabel.setForeground(new Color(0x000000));
        WelcomeLabel.setVerticalTextPosition(JLabel.CENTER);
        WelcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
        WelcomeLabel.setBounds(280,30,800,300); // x, y, width, height
        this.add(WelcomeLabel);

        WelcomeLabel2 = new JLabel("APU Psychology Consultation");
        WelcomeLabel2.setFont(new Font("Arial",Font.BOLD,35));
        WelcomeLabel2.setForeground(new Color(0x000000));
        WelcomeLabel2.setVerticalTextPosition(JLabel.CENTER);
        WelcomeLabel2.setHorizontalTextPosition(JLabel.CENTER);
        WelcomeLabel2.setBounds(140,75,800,300); // x, y, width, height
        this.add(WelcomeLabel2);

        WelcomeStartBtn = new JButton();
        WelcomeStartBtn.setText("Get Started");
        WelcomeStartBtn.setFont(new Font("Arial",Font.BOLD,25));
        WelcomeStartBtn.setForeground(new Color(0x000000));
        WelcomeStartBtn.addActionListener(this);
        WelcomeStartBtn.setBounds(225,275,300,100);
        WelcomeStartBtn.setBackground(new Color(0x7AB2D3));
        this.add(WelcomeStartBtn);

        FrameMethods.loginSetup(this);

    }

    @Override
    public void actionPerformed(ActionEvent WelcomeActionA) {
        if (WelcomeActionA.getSource() == WelcomeStartBtn) {
            new UserSelect();
            this.dispose();
        }
    }
}