import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserSelect extends JFrame implements ActionListener {

    // declaring lecButton as class lvl variable so can be accessed even out of UserSelect constructor
    private JButton lecButton, studButton, createAcc;
    private JLabel chooseRole;

    UserSelect() {

        // role selection label
        chooseRole = FrameMethods.labelSetup("Who are you?","Arial",25,0x000000,300,50,300,100);
        this.add(chooseRole);

        // lecturer button
        lecButton = FrameMethods.buttonSetup("Lecturer","Arial",25,0x000000,this,225,150,300,100,0x7AB2D3);
        this.add(lecButton);

        // student button
        studButton = FrameMethods.buttonSetup("Student","Arial",25,0x000000,this,225,275,300,100,0X7AB2D3);
        this.add(studButton);

        // create acc button
        createAcc = FrameMethods.buttonSetup("Create an Account","Arial",25,0x000000,this,225,400,300,100,0X7AB2D3);
        this.add(createAcc);

        // must be on bottom so it doesnt override the label
        FrameMethods.windowSetup(this);

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
