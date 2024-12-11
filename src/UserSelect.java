import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserSelect extends JFrame implements ActionListener {

    private JButton lecButton, studButton, createAcc;
    private JLabel chooseRole;

    UserSelect() {

        chooseRole = FrameMethods.labelSetup("Who are you?","Arial",25,0x000000,300,50,300,100);
        this.add(chooseRole);

        lecButton = FrameMethods.buttonSetup("Lecturer","Arial",25,0x000000,this,225,150,300,100,0x7AB2D3);
        this.add(lecButton);

        studButton = FrameMethods.buttonSetup("Student","Arial",25,0x000000,this,225,275,300,100,0X7AB2D3);
        this.add(studButton);

        createAcc = FrameMethods.buttonSetup("Create an Account","Arial",25,0x000000,this,225,400,300,100,0X7AB2D3);
        this.add(createAcc);

        FrameMethods.windowSetup(this);

        }

    @Override
    public void actionPerformed(ActionEvent selectUser) {
        if (selectUser.getSource() == lecButton) {
            new LecLoginPanel();
            this.dispose();

        } else if (selectUser.getSource() == studButton)  {
            new StudLoginPanel();
            this.dispose();
        } else if (selectUser.getSource() == createAcc) {
            new CreateAccPanel();
            this.dispose();
        }



    }
}
