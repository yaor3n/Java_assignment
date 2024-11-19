import javax.swing.*;
import java.awt.*;

public class FrameMethods {
    public static void loginSetup(JFrame frame) {

        // basic frame template
        frame.setSize(800, 650);
        frame.setTitle("APU Psychology Consultation Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // set false when submit
        frame.getContentPane().setBackground(new Color(0xDFF2EB));
        frame.setLayout(null);
        frame.setVisible(true);

        // window icon
        ImageIcon icon1 = new ImageIcon("resources/icon1.png");
        frame.setIconImage(icon1.getImage());

    }
}
