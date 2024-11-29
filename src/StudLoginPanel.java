import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class StudLoginPanel extends JFrame implements ActionListener {

    private JLabel enterUsername, enterPassword;
    private JButton SubmitButtonStud, LoginBackStud;
    private JTextField StudUsername;
    private JPasswordField StudPassword;

    StudLoginPanel() {
        // label to prompt username input
        enterUsername = FrameMethods.labelSetup("Enter Student username: ", "Arial", 25, 0x000000, 250, 50, 500, 100);
        this.add(enterUsername);

        // label to prompt password input
        enterPassword = FrameMethods.labelSetup("Enter Student password: ", "Arial", 25, 0x000000, 250, 200, 500, 100);
        this.add(enterPassword);

        StudUsername = FrameMethods.textFieldSetup(250, 150, 300, 50, "Arial", 15);
        this.add(StudUsername);

        // textbox for lec password
        StudPassword = FrameMethods.passwordFieldSetup(250, 300, 300, 50, "Arial", 15);
        this.add(StudPassword);

        // back button
        LoginBackStud = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 350, 455, 100, 50, 0x7AB2D3);
        this.add(LoginBackStud);

        // submit button
        SubmitButtonStud = FrameMethods.buttonSetup("Login", "Arial", 25, 0x000000, this, 335, 375, 125, 50, 0x7AB2D3);
        this.add(SubmitButtonStud);

        FrameMethods.windowSetup(this);
    }

    @Override
    public void actionPerformed(ActionEvent studActionA) {
        if (studActionA.getSource() == LoginBackStud) {
            new UserSelect();
            dispose();
        } else if (studActionA.getSource() == SubmitButtonStud) {
            String username = StudUsername.getText();
            String password = new String(StudPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter both username and password.");
                return;
            }

            String accountType = verifyCredentials(username, password);
            if (accountType == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again!");
            } else if (!accountType.equals("Student")) {
                JOptionPane.showMessageDialog(this, "This is a Lecturer account. Please log in with a Student account.");
            } else {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username);
                this.dispose();
                //new ();
            }
        }
    }

    private String verifyCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                    return parts[2]; // Return account type (Lecturer or Student)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading account file. Please try again later.");
        }
        return null;
    }
}
