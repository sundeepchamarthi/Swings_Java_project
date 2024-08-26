package com.maybank.java.assesment.form;

import com.maybank.java.assesment.dao.CustomerDAO;
import com.maybank.java.assesment.entity.CustomerEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CustomerForm extends JDialog {

    private CustomerDAO customerDAO;
    private CustomerEntity customerEntity;

    private JTextField shortNameField;
    private JTextField fullNameField;
    private JTextField address1Field;
    private JTextField address2Field;
    private JTextField address3Field;
    private JTextField cityField;
    private JTextField postalCodeField;

    public CustomerForm(Frame parent, CustomerDAO customerDAO, CustomerEntity customerEntity) {
        super(parent, true);
        this.customerDAO = customerDAO;
        this.customerEntity=  customerEntity;

        setTitle(customerEntity == null ? "Add Customer" : "Modify Customer");
        setSize(400, 300);
        setLayout(new GridLayout(8, 2));

        add(new JLabel("Short Name:"));
        shortNameField = new JTextField();
        add(shortNameField);

        add(new JLabel("Full Name:"));
        fullNameField = new JTextField();
        add(fullNameField);

        add(new JLabel("Address 1:"));
        address1Field = new JTextField();
        add(address1Field);

        add(new JLabel("Address 2:"));
        address2Field = new JTextField();
        add(address2Field);

        add(new JLabel("Address 3:"));
        address3Field = new JTextField();
        add(address3Field);

        add(new JLabel("City:"));
        cityField = new JTextField();
        add(cityField);

        add(new JLabel("Postal Code:"));
        postalCodeField = new JTextField();
        add(postalCodeField);

        JButton saveButton = new JButton("Save");
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);

        // Pre-fill form if customer exists
        if (customerEntity != null) {
            shortNameField.setText(customerEntity.getShortName());
            fullNameField.setText(customerEntity.getFullName());
            address1Field.setText(customerEntity.getAddress1());
            address2Field.setText(customerEntity.getAddress2());
            address3Field.setText(customerEntity.getAddress3());
            cityField.setText(customerEntity.getCity());
            postalCodeField.setText(customerEntity.getPostalCode());
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    try {
                        if (customerEntity == null) {
                            CustomerEntity newCustomer = new CustomerEntity();
                            fillCustomerData(newCustomer);
                            customerDAO.addCustomer(newCustomer);
                        } else {
                            fillCustomerData(customerEntity);
                            customerDAO.updateCustomer(customerEntity);
                        }
                        dispose();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(CustomerForm.this, "Error saving customer: " + ex.getMessage());
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private boolean validateForm() {
        if (postalCodeField.getText().length() != 5 || !postalCodeField.getText().matches("\\d{5}")) {
            JOptionPane.showMessageDialog(this, "Invalid postal code. Must be 5 digits.");
            return false;
        }
        return true;
    }

    private void fillCustomerData(CustomerEntity customerEntity) {
        customerEntity.setShortName(shortNameField.getText());
        customerEntity.setFullName(fullNameField.getText());
        customerEntity.setAddress1(address1Field.getText());
        customerEntity.setAddress2(address2Field.getText());
        customerEntity.setAddress3(address3Field.getText());
        customerEntity.setCity(cityField.getText());
        customerEntity.setPostalCode(postalCodeField.getText());
    }
}

