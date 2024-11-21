import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePanel extends JFrame implements ActionListener {

    private JLabel WelcomeLabel, WelcomeLabel2;
    private JButton WelcomeStartBtn;

    WelcomePanel() {

        WelcomeLabel = FrameMethods.labelSetup("Welcome To","Arial",35,0x000000,280,30,800,300);
        this.add(WelcomeLabel);

        WelcomeLabel2 = FrameMethods.labelSetup("APU Psychology Consultation","Arial",35,0x0000000,140,75,800,300);
        this.add(WelcomeLabel2);


        WelcomeStartBtn = FrameMethods.buttonSetup("Get Started","Arial",25,0x000000,this,225,275,300,100,0x7AB2D3);
        this.add(WelcomeStartBtn);


        FrameMethods.windowSetup(this);

    }

    @Override
    public void actionPerformed(ActionEvent WelcomeActionA) {
        if (WelcomeActionA.getSource() == WelcomeStartBtn) {
            new UserSelect();
            this.dispose();
        }
    }
}