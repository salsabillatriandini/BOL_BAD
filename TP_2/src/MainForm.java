import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm {
    private JTextField txtFieldNama;
    private JTextField txtFieldNIK;
    private JTextField txtFieldTglLahir;
    private JTextField txtFieldAlamat;
    private JButton cancelButton;
    private JButton submitButton;
    private JPanel rootPanel;

    public MainForm() {
        submitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(rootPanel, "Test button submit, Hello !!", "information", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(rootPanel, "Error on Message", "Error", JOptionPane.ERROR_MESSAGE);
            int result = JOptionPane.showConfirmDialog(rootPanel, "are you sure want to submit ?");
            if (result == JOptionPane.YES_OPTION) {
                String message = JOptionPane.showInputDialog("Input Your Message : ");
                JOptionPane.showMessageDialog(rootPanel, "Inputted Message " + message, "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
