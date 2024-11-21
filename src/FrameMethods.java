import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FrameMethods {
    public static void windowSetup(JFrame frame) {

        // reusable frame template
        frame.setSize(800, 650);
        frame.setTitle("APU Psychology Consultation Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // set false when submit
        frame.getContentPane().setBackground(new Color(0xDFF2EB));
        frame.setLayout(null);
        frame.setVisible(true);
//        frame.setLocation(250,250);
        frame.setLocationRelativeTo(null);

        // window icon
        ImageIcon icon = new ImageIcon("resources/icon1.png");
        frame.setIconImage(icon.getImage());
    }

    // reusable label method
    public static JLabel labelSetup(String LabelText, String FontName, int FontSize, int hex, int x, int y, int w, int h) {
        JLabel label = new JLabel(LabelText);
        label.setFont(new Font(FontName, Font.BOLD, FontSize));
        label.setForeground(new Color(hex));
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setBounds(x, y, w, h);
        return label;
    }

    // reusable button method
    public static JButton buttonSetup(String ButtonText, String FontName, int FontSize, int FGhex, ActionListener AL, int x, int y, int w, int h, int BGhex) {
        JButton button = new JButton(ButtonText);
        button.setFont(new Font(FontName, Font.BOLD, FontSize));
        button.setForeground(new Color(FGhex));
        button.addActionListener(AL);
        button.setBounds(x, y, w, h);
        button.setBackground(new Color(BGhex));
        return button;
    }

    // reusable textfield method
    public static JTextField textFieldSetup(int x, int y, int w, int h, String FontName, int FontSize) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, w, h);
        textField.setFont(new Font(FontName, Font.PLAIN, FontSize));
        return textField;
    }

    public static JPasswordField passwordFieldSetup(int x, int y, int w, int h, String FontName, int FontSize) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(x, y, w, h);
        passwordField.setFont(new Font(FontName, Font.PLAIN, FontSize));
        return passwordField;
    }

    public static JRadioButton radioButtonSetup(String buttonText, String FontName, int FontSize, int FGhex, int x, int y, int w, int h, int BGhex) {
        JRadioButton radioButton = new JRadioButton(buttonText);
        radioButton.setFont(new Font(FontName, Font.PLAIN, FontSize));
        radioButton.setForeground(new Color(FGhex));
        radioButton.setBounds(x, y, w, h);
        radioButton.setBackground(new Color(BGhex));
        return radioButton;
    }
}


