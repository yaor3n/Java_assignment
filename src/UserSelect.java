import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserSelect extends JFrame implements ActionListener {

    // declaring lecButton as class lvl variable so can be accessed even out of UserSelect constructor
    private JButton lecButton, studButton, createAcc;
    private JLabel chooseRole;

    UserSelect() {

        // role selection label
        chooseRole = new JLabel("Who are you?");
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
        lecButton.setBackground(new Color(0x7AB2D3));
        this.add(lecButton);

        // student button
        studButton = new JButton();
        studButton.setText("Student");
        studButton.setFont(new Font("Arial",Font.BOLD,25));
        studButton.setForeground(new Color(0x000000));
        studButton.addActionListener(this);
        studButton.setBounds(225,275,300,100);
        studButton.setBackground(new Color(0x7AB2D3));
        this.add(studButton);

        // create acc button
        createAcc = new JButton();
        createAcc.setText("Create an Account");
        createAcc.setFont(new Font("Arial",Font.BOLD,25));
        createAcc.setForeground(new Color(0x000000));
        createAcc.addActionListener(this);
        createAcc.setBounds(225,400,300,100);
        createAcc.setBackground(new Color(0x7AB2D3));
        this.add(createAcc);

        // must be on bottom so it doesnt override the label
        FrameMethods.loginSetup(this);

        }

    @Override
    public void actionPerformed(ActionEvent selectUser) {
        if (selectUser.getSource() == lecButton) {
            new LecLoginPanel(); // opens LoginPanel
            this.dispose(); // kills current window

        } else if (selectUser.getSource() == studButton)  {
            new StudLoginPanel();
            this.dispose();
        } else if (selectUser.getSource() == createAcc) {
            new CreateAccPanel();
            this.dispose();
        }



    }
}
