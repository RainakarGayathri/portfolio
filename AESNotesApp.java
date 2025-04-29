import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;

public class AESNotesApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AESNotesApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("AES Notes Encryptor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTextArea noteArea = new JTextArea();
        JPasswordField passwordField = new JPasswordField();
        JButton encryptBtn = new JButton("Encrypt & Save");
        JButton decryptBtn = new JButton("Load & Decrypt");

        JPanel panel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.add(new JLabel("Enter Password:"));
        bottomPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(encryptBtn);
        buttonPanel.add(decryptBtn);

        frame.add(new JScrollPane(noteArea), BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        encryptBtn.addActionListener((ActionEvent e) -> {
            String text = noteArea.getText();
            String password = new String(passwordField.getPassword());
            if (text.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Note and password cannot be empty.");
                return;
            }
            try {
                String encrypted = AESUtil.encrypt(text, password);
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    Files.write(fileChooser.getSelectedFile().toPath(), encrypted.getBytes());
                    JOptionPane.showMessageDialog(frame, "Note encrypted and saved.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Encryption failed: " + ex.getMessage());
            }
        });

        decryptBtn.addActionListener((ActionEvent e) -> {
            String password = new String(passwordField.getPassword());
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter the password to decrypt.");
                return;
            }
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try {
                    String encrypted = Files.readString(fileChooser.getSelectedFile().toPath());
                    String decrypted = AESUtil.decrypt(encrypted, password);
                    noteArea.setText(decrypted);
                    JOptionPane.showMessageDialog(frame, "Note decrypted successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Decryption failed: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
