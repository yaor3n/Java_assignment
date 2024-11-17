import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserSelect extends JFrame implements ActionListener {

    // declaring lecButton as class lvl variable so can be accessed even out of UserSelect constructor
    private JButton lecButton;

    UserSelect() {

        // role selection label
        JLabel chooseRole = new JLabel("Who are you?");
        chooseRole.setFont(new Font("Arial",Font.BOLD,25));
        chooseRole.setForeground(new Color(0x000000));
        chooseRole.setVerticalTextPosition(JLabel.CENTER);
        chooseRole.setHorizontalTextPosition(JLabel.CENTER);
        chooseRole.setBounds(300,50,300,100); // x, y, width, height
        this.add(chooseRole);

        // lecturer button
        lecButton = new JButton();
        lecButton.setText("Lecturer");
        lecButton.setFont(new Font("Arial",Font.BOLD,25));
        lecButton.setForeground(new Color(0x0000));
        lecButton.addActionListener(this);
        lecButton.setBounds(225,150,300,100);
        this.add(lecButton);

        // must be on bottom so it doesnt override the label
        FrameMethods.loginSetup(this);

        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == lecButton) {
            new LoginPanel(); // opens LoginPanel
            this.dispose(); // kills current window

        }



    }
}
