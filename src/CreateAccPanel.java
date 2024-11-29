import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CreateAccPanel extends JFrame implements  ActionListener {

    private JLabel createAccLabel, enterUsername, enterPassword, chooseAccType;
    private JButton SubmitButtonCreAcc, CreAccBackBtn;
    private JTextField createUsername, createPassword;
    private JRadioButton lecRadioButton, studRadioButton;
    private ButtonGroup accountTypeGroup;

    CreateAccPanel() {

        createAccLabel = FrameMethods.labelSetup("Account Creation","Arial",30,0x000000,250,25,300,100);
        this.add(createAccLabel);

        // label to prompt username input
        enterUsername = FrameMethods.labelSetup("Enter Username: ","Arial",25,0x000000,50,85,300,100);
        this.add(enterUsername);

        // label to prompt password input
        enterPassword = FrameMethods.labelSetup("Enter Password: ","Arial",25,0x000000,50,225,300,100);
        this.add(enterPassword);

        createUsername = FrameMethods.textFieldSetup(50,150,300,50,"Arial",15);
        this.add(createUsername);

        createPassword = FrameMethods.textFieldSetup(50,300,300,50,"Arial",15);
        this.add(createPassword);

        // back button
        CreAccBackBtn = FrameMethods.buttonSetup("Back","Arial",25,0x000000,this,350,455,100,50,0x7AB2D3);
        this.add(CreAccBackBtn);

        // submit button
        SubmitButtonCreAcc = FrameMethods.buttonSetup("Create Account","Arial",25,0x000000,this,250,375,300,50,0x7AB2D3);
        this.add(SubmitButtonCreAcc);

        // idk anymore
        chooseAccType = FrameMethods.labelSetup("Account Type:","Arial",25,0x000000,450,85,300,100);
        this.add(chooseAccType);

        // radio for student option
        studRadioButton = FrameMethods.radioButtonSetup("Student","Arial",20,0x000000,450,190,200,50,0xDFF2EB);
        this.add(studRadioButton);

        // lecturer radio button
        lecRadioButton = FrameMethods.radioButtonSetup("Lecturer","Arial",20,0x000000,450,250,200,50,0xDFF2EB);
        this.add(lecRadioButton);

        // grouping the radio buttons
        accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(lecRadioButton);
        accountTypeGroup.add(studRadioButton);


        FrameMethods.windowSetup(this);

    }
    private boolean isUsernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true; // Username already exists
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Accounts file not found.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking username. Please try again.");
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent CreAccA) {
        if (CreAccA.getSource() == CreAccBackBtn) {
            new UserSelect();
            this.dispose();
        } else if (CreAccA.getSource() == SubmitButtonCreAcc) {
            String username = createUsername.getText();
            String password = createPassword.getText();
            String accountType = "";

            // checking for empty fields
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter both Username and Password.");
                return;
            }

            // Determine account type based on radio button selection
            if (lecRadioButton.isSelected()) {
                accountType = "Lecturer";
            } else if (studRadioButton.isSelected()) {
                accountType = "Student";
            } else {
                JOptionPane.showMessageDialog(this, "Please select an account type.");
                return;
            }

            // check for duplicate usernames
            if (isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose another.");
                return;
            }

            // writing account details to txt file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Accounts.txt", true))) {
                writer.write(username + "," + password + "," + accountType);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Account created successfully!");
                // Optionally, clear the fields after successful creation
                createUsername.setText("");
                createPassword.setText("");
                accountTypeGroup.clearSelection();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving account. Try again.");
            }
        }
    }

}
