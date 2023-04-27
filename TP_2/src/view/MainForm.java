package view;

import entity.Users;
import utils.CustomField;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainForm {
    private JTextField fieldNama;
    private JTextField fieldNIK;
    private JTextField fieldBirth;
    private JTextArea fieldAlamat;
    private JSplitPane rootPanel;
    private JTable tableData;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton addButton;
    private JButton previousRecordButton;
    private JButton currentRecordButton;
    private int nextId;
    private int selectedIndex;
    private Users selectedUser;
    private List<Users> previousUsers = new ArrayList<>();
    private List<Users> currentUsers = new ArrayList<>();

    CustomField.CustomTextField fieldNamaDocument = new CustomField.CustomTextField(20, "^[a-zA-Z0-9 ]+$");
    CustomField.CustomTextField fieldNIKDocument = new CustomField.CustomTextField(15, "^[0-9]+$");
    CustomField.CustomTextArea fieldAlamatDocument = new CustomField.CustomTextArea(50, "^[a-zA-Z0-9\\s]+$");
    CustomField.CustomDateTextField fieldBirthDocument = new CustomField.CustomDateTextField(11);
    public MainForm() {
        fieldNama.setDocument(fieldNamaDocument.getDocument());
        fieldNIK.setDocument(fieldNIKDocument.getDocument());
        fieldAlamat.setDocument(fieldAlamatDocument.getDocument());
        fieldAlamat.setLineWrap(true);
        fieldAlamat.setWrapStyleWord(true);
        fieldBirth.setDocument(fieldBirthDocument.getDocument());

        List<Users> users = new ArrayList<>();
        users.add(new Users(1, "Hary", "1232133123", "2003-Jan-12", "Alamat abcdefghijklmn"));
        users.add(new Users(2, "Hary2", "1232133124", "2003-Jan-12", "Alamat abcdefghijklmn"));
        users.add(new Users(3, "Hary3", "1232133125", "2003-Jan-12", "Alamat abcdefghijklmn"));
        nextId = users.size() + 1;

        UserTableModel userTableModel = new UserTableModel(users);
        tableData.setModel(userTableModel);
        tableData.setAutoCreateRowSorter(true);

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        previousRecordButton.setEnabled(false);
        currentRecordButton.setEnabled(false);

        tableData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!tableData.getSelectionModel().isSelectionEmpty()) {
                    selectedIndex = tableData.convertRowIndexToModel(tableData.getSelectedRow());
                    selectedUser = users.get(selectedIndex);
                    if (selectedUser != null) {
                        fieldNamaDocument.setText(selectedUser.getNama());
                        fieldNIKDocument.setText(selectedUser.getNik());
                        fieldAlamatDocument.setText(selectedUser.getAlamat());
                        fieldBirthDocument.setText(selectedUser.getBirth());

                        addButton.setEnabled(false);
                        updateButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                    }
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Users newUser = new Users();
                newUser.setId(nextId);
                newUser.setNama(fieldNama.getText());
                newUser.setBirth(fieldBirth.getText());
                newUser.setAlamat(fieldAlamat.getText());
                newUser.setNik(fieldNIK.getText());

                if (errorMessage(users, newUser) != "") {
                    JOptionPane.showMessageDialog(rootPanel , errorMessage(users, newUser),"Error Record", JOptionPane.ERROR_MESSAGE);
                } else {
                    previousUsers.clear();
                    previousUsers.addAll(users);
                    previousRecordButton.setEnabled(true);

                    users.add(newUser);
                    nextId = users.size() + 1;
                    userTableModel.fireTableDataChanged();
                    clearAndReset();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUser.setNama(fieldNama.getText());
                selectedUser.setNik(fieldNIK.getText());
                selectedUser.setAlamat(fieldAlamat.getText());
                selectedUser.setBirth(fieldBirth.getText());

                users.set(selectedIndex, selectedUser);
                userTableModel.fireTableDataChanged();
                clearAndReset();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousUsers.clear();
                previousUsers.addAll(users);
                previousRecordButton.setEnabled(true);

                List<Users> filteredUser = users.stream()
                        .filter(str -> users.indexOf(str) != selectedIndex)
                        .collect(Collectors.toList());

                users.clear();
                users.addAll(filteredUser);
                userTableModel.fireTableDataChanged();
                clearAndReset();
            }
        });
        previousRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addButton.setEnabled(false);
                updateButton.setEnabled(false);
                deleteButton.setEnabled(false);
                currentRecordButton.setEnabled(true);

                currentUsers.clear();
                currentUsers.addAll(users);

                users.clear();
                users.addAll(previousUsers);
                userTableModel.fireTableDataChanged();
                previousRecordButton.setEnabled(false);

                clearAndReset();
            }
        });
        currentRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addButton.setEnabled(true);
                currentRecordButton.setEnabled(false);

                users.clear();
                users.addAll(currentUsers);

                userTableModel.fireTableDataChanged();
                previousRecordButton.setEnabled(true);

                clearAndReset();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Record Pasien");
        frame.setContentPane(new MainForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }
    private void clearAndReset() {
       fieldNamaDocument.setText("");
       fieldNIKDocument.setText("");
       fieldBirthDocument.setText("");
       fieldAlamatDocument.setText("");
       tableData.clearSelection();
       addButton.setEnabled(true);
       updateButton.setEnabled(false);
       deleteButton.setEnabled(false);
       selectedUser = null;
       selectedIndex = -1;
    }
    private String errorMessage (List<Users> users, Users user) {
        boolean isSameNik = false;
        String DATE_FORMAT_REGEX = "\\d{0,4}(-[a-zA-Z]{0,3})(-\\d{0,2})";
        String emptyNama = "- Harap masukkan Nama",
                emptyNik = "- Harap masukkan NIK",
                emptyBirth = "- Harap masukkan Tanggal Lahir",
                emptyAlamat = "- Harap masukkan Alamat";
        String validasiBirth = "- Harap masukkan Tanggal Lahir sesuai format YYYY-MMM-DD, contoh : 12-Jan-2003",
                validasiNik = "- Nik sudah terdaftar";

        String message = "";

        for (Users currentUser : users) {
            if (currentUser.getNik().equals(user.getNik())) {
                isSameNik = true;
                break;
            }
        }

        if (fieldNama.getText().equals("")) {
            message = emptyNama + "\n";
        }
        if (fieldNIK.getText().equals("")) {
            message = message + emptyNik + "\n";
        } else if (isSameNik) {
            message = message + validasiNik + "\n";
        }
        if (fieldBirth.getText().equals("YYYY-MMM-DD") || fieldBirth.getText().equals("")) {
            message = message + emptyBirth + "\n";
        } else if (!fieldBirth.getText().matches(DATE_FORMAT_REGEX)) {
            message = message + validasiBirth + "\n";
        }
        if (fieldAlamat.getText().equals("")) {
            message = message + emptyAlamat + "\n";
        }
        return message;
    }
    private static class UserTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"ID", "NAMA", "NIK", "TANGGAL LAHIR", "ALAMAT"};
        private List<Users> users;

        private UserTableModel(List<Users> users) {
            this.users = users;
        }

        @Override
        public int getRowCount() {
            return users.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (users.size() > 0) {
                return switch (columnIndex) {
                    case 0 -> users.get(rowIndex).getId();
                    case 1 -> users.get(rowIndex).getNama();
                    case 2 -> users.get(rowIndex).getNik();
                    case 3 -> users.get(rowIndex).getBirth();
                    case 4 -> users.get(rowIndex).getAlamat();
                    default -> "-";
                };
            } else {
                return  null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            }else {
                return Object.class;
            }
        }
    }
}
