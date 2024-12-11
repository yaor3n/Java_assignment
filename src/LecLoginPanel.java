import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class LecLoginPanel extends JFrame implements ActionListener {

    private JLabel enterUsername, enterPassword;
    private JButton SubmitButtonLec, LoginBackLec;
    private JTextField LecUsername;
    private JPasswordField LecPassword;

    LecLoginPanel() {
        // label to prompt username input
        enterUsername = FrameMethods.labelSetup("Enter Lecturer username: ", "Arial", 25, 0x000000, 250, 50, 500, 100);
        this.add(enterUsername);

        // label to prompt password input
        enterPassword = FrameMethods.labelSetup("Enter Lecturer password: ", "Arial", 25, 0x000000, 250, 200, 500, 100);
        this.add(enterPassword);

        // textbox for lec username
        LecUsername = FrameMethods.textFieldSetup(250, 150, 300, 50, "Arial", 15);
        this.add(LecUsername);

        // textbox for lec password
        LecPassword = FrameMethods.passwordFieldSetup(250, 300, 300, 50, "Arial", 15);
        this.add(LecPassword);

        // back button
        LoginBackLec = FrameMethods.buttonSetup("Back", "Arial", 25, 0x000000, this, 350, 455, 100, 50, 0x7AB2D3);
        this.add(LoginBackLec);

        // submit button
        SubmitButtonLec = FrameMethods.buttonSetup("Login", "Arial", 25, 0x000000, this, 335, 375, 125, 50, 0x7AB2D3);
        this.add(SubmitButtonLec);

        FrameMethods.windowSetup(this);
    }

    @Override
    public void actionPerformed(ActionEvent lecActionA) {
        if (lecActionA.getSource() == LoginBackLec) {
            new UserSelect();
            dispose();
        } else if (lecActionA.getSource() == SubmitButtonLec) {
            String username = LecUsername.getText();
            String password = new String(LecPassword.getPassword());

            // Check if username and password are empty
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter both username and password.");
                return;
            }

            String accountType = verifyCredentials(username, password);
            if (accountType == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again!");
            } else if (!accountType.equals("Lecturer")) {
                JOptionPane.showMessageDialog(this, "This is a Student account. Please log in with a Lecturer account.");
            } else {
                // Successful login
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username);
                SessionManager.setLecturerUsername(username);
                this.dispose();
                new LecturerDashboardPanel();
            }
        }
    }

    private String verifyCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    // Debugging to check if we are reading the file correctly
                    System.out.println("Checking account: " + parts[0] + " against " + username);
                    if (parts[0].equals(username) && parts[1].equals(password)) {
                        return parts[2]; // Return account type (Lecturer or Student)
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading account file. Please try again later.");
        }
        return null;
    }

}
