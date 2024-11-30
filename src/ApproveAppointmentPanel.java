import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ApproveAppointmentPanel extends JFrame implements ActionListener {

    private JButton back;
    private JFrame parentFrame;

    ApproveAppointmentPanel(JFrame parentFrame) {

        this.parentFrame = parentFrame;

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
