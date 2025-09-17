package javaapplication14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class JavaApplication14 {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField phoneField;

    // Simulated user database: username -> password
    private static HashMap<String, String> userDatabase = new HashMap<>();
    private static HashMap<String, String> phoneDatabase = new HashMap<>();

    public JavaApplication14() {
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        usernameField = new JTextField(10);
        frame.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        passwordField = new JPasswordField(10);
        frame.add(passwordField, gbc);

        // Phone Number
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(new JLabel("Cellphone Number:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        phoneField = new JTextField(10);
        frame.add(phoneField, gbc);

        // Login Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        frame.add(loginButton, gbc);

        // Clear Button
        gbc.gridy = 4;
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            phoneField.setText("");
        });
        frame.add(clearButton, gbc);

        // Register Button
        gbc.gridy = 5;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> openRegistrationWindow());
        frame.add(registerButton, gbc);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String phoneNumber = phoneField.getText();

        if (username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username, password, and phone number cannot be empty.");
            return;
        }

        if (!phoneNumber.matches("^\\+27\\d{9}$")) {
            JOptionPane.showMessageDialog(frame, "Invalid phone number. Please enter a phone number starting with +27 followed by 9 digits.");
            return;
        }

        // Check against stored user database
        if (userDatabase.containsKey(username)) {
            if (userDatabase.get(username).equals(password)) {
                String storedPhone = phoneDatabase.get(username);
                if (storedPhone.equals(phoneNumber)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    // TODO: Open main dashboard or application here
                } else {
                    JOptionPane.showMessageDialog(frame, "Phone number does not match.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect password.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "User does not exist. Please register.");
        }
    }

    private void openRegistrationWindow() {
        JFrame regFrame = new JFrame("Registration");
        regFrame.setLayout(new GridBagLayout());
        regFrame.setSize(400, 250);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField regUsernameField = new JTextField(10);
        JPasswordField regPasswordField = new JPasswordField(10);
        JTextField regPhoneField = new JTextField(10);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        regFrame.add(new JLabel("New Username:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        regFrame.add(regUsernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        regFrame.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        regFrame.add(regPasswordField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        regFrame.add(new JLabel("Cellphone (+27...):"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        regFrame.add(regPhoneField, gbc);

        // Register Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton confirmButton = new JButton("Register");
        confirmButton.addActionListener(e -> {
            String newUsername = regUsernameField.getText();
            String newPassword = new String(regPasswordField.getPassword());
            String newPhone = regPhoneField.getText();

            if (newUsername.isEmpty() || newPassword.isEmpty() || newPhone.isEmpty()) {
                JOptionPane.showMessageDialog(regFrame, "All fields are required.");
                return;
            }

            if (!newPhone.matches("^\\+27\\d{9}$")) {
                JOptionPane.showMessageDialog(regFrame, "Phone number must start with +27 followed by 9 digits.");
                return;
            }

            if (userDatabase.containsKey(newUsername)) {
                JOptionPane.showMessageDialog(regFrame, "Username already exists.");
            } else {
                userDatabase.put(newUsername, newPassword);
                phoneDatabase.put(newUsername, newPhone);
                JOptionPane.showMessageDialog(regFrame, "Registration successful!");
                regFrame.dispose(); // Close registration window
            }
        });

        regFrame.add(confirmButton, gbc);

        regFrame.pack();
        regFrame.setLocationRelativeTo(frame); // Center relative to main window
        regFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JavaApplication14::new);
    }
}

