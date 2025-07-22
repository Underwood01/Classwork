package com.example.registrationapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

public class RegistrationForm extends JFrame {

    private JTextField nameField, mobileField, addressField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderButtonGroup;
    private JComboBox<String> dayComboBox, monthComboBox, yearComboBox;
    private JCheckBox termsCheckBox;
    private JButton submitButton, resetButton, exitButton, registerButton;
    private JTable registrationTable;
    private DefaultTableModel tableModel;

    private DatabaseManager dbManager;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dbManager = new DatabaseManager();

        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Registration Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Mobile:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mobileField = new JTextField(20);
        formPanel.add(mobileField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        formPanel.add(genderPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("DOB:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        dayComboBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(String.format("%02d", i));
        }
        monthComboBox = new JComboBox<>(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        yearComboBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) {
            yearComboBox.addItem(String.valueOf(i));
        }
        JPanel dobPanel = new JPanel();
        dobPanel.add(dayComboBox);
        dobPanel.add(monthComboBox);
        dobPanel.add(yearComboBox);
        formPanel.add(dobPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.BOTH;
        addressField = new JTextField(20);
        formPanel.add(addressField, gbc);


        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        termsCheckBox = new JCheckBox("Accept Terms And Conditions");
        formPanel.add(termsCheckBox, gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.anchor = GridBagConstraints.WEST;
        JPanel buttonPanelTop = new JPanel();
        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset");
        buttonPanelTop.add(submitButton);
        buttonPanelTop.add(resetButton);
        formPanel.add(buttonPanelTop, gbc);

        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBorder(BorderFactory.createTitledBorder("Registered Users"));

        String[] columnNames = {"ID", "Name", "Mobile", "Gender", "DOB", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0);
        registrationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(registrationTable);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomButtonsPanel = new JPanel();
        exitButton = new JButton("Exit");
        registerButton = new JButton("Register");
        bottomButtonsPanel.add(exitButton);
        bottomButtonsPanel.add(registerButton);

        JPanel formAndBottomButtons = new JPanel(new BorderLayout());
        formAndBottomButtons.add(formPanel, BorderLayout.CENTER);
        JPanel bottomFormButtonsPanel = new JPanel();
        bottomFormButtonsPanel.add(exitButton);
        bottomFormButtonsPanel.add(registerButton);
        formAndBottomButtons.add(bottomFormButtonsPanel, BorderLayout.SOUTH);

        add(formAndBottomButtons, BorderLayout.WEST);

        add(displayPanel, BorderLayout.CENTER);


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        loadRegistrationsIntoTable();
    }

    private void handleSubmit() {
        String name = nameField.getText().trim();
        String mobile = mobileField.getText().trim();
        String address = addressField.getText().trim();
        String gender = "";
        if (maleRadioButton.isSelected()) {
            gender = "Male";
        } else if (femaleRadioButton.isSelected()) {
            gender = "Female";
        }

        String day = (String) dayComboBox.getSelectedItem();
        String month = String.valueOf(monthComboBox.getSelectedIndex() + 1);
        String year = (String) yearComboBox.getSelectedItem();
        String dob = year + "-" + String.format("%02d", Integer.parseInt(month)) + "-" + day;

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty() || gender.isEmpty() || !termsCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields, select gender, and accept terms and conditions.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dbManager.insertRegistration(name, mobile, gender, dob, address);

        resetForm();

        loadRegistrationsIntoTable();

        JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {
        nameField.setText("");
        mobileField.setText("");
        addressField.setText("");
        genderButtonGroup.clearSelection();
        dayComboBox.setSelectedIndex(0);
        monthComboBox.setSelectedIndex(0);
        yearComboBox.setSelectedIndex(0);
        termsCheckBox.setSelected(false);
    }

    private void loadRegistrationsIntoTable() {
        tableModel.setRowCount(0);
        List<String[]> registrations = dbManager.getAllRegistrations();
        for (String[] row : registrations) {
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegistrationForm().setVisible(true);
            }
        });
    }
}
