package com.maybank.java.assesment;

import com.maybank.java.assesment.dao.CustomerDAO;
import com.maybank.java.assesment.entity.CustomerEntity;
import com.maybank.java.assesment.form.CustomerForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CustomerAddressesApplication extends JFrame {

	private CustomerDAO customerDAO;
	private JList<CustomerEntity> customerList;
	private DefaultListModel<CustomerEntity> customerListModel;

	public CustomerAddressesApplication() {
		customerDAO = new CustomerDAO();
		customerListModel = new DefaultListModel<>();

		//main frame to display
		setTitle("Customer Address Managing Application");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		//customer list panel with Created customerList
		JPanel customerPanel = new JPanel(new BorderLayout());
		customerList = new JList<>(customerListModel);
		customerPanel.add(new JScrollPane(customerList), BorderLayout.CENTER);

		// Create buttons for ADD/Update/Delete
		JPanel buttonPanel = new JPanel();
		JButton addButton = new JButton("Add");
		JButton modifyButton = new JButton("Modify");
		JButton deleteButton = new JButton("Delete");
		buttonPanel.add(addButton);
		buttonPanel.add(modifyButton);
		buttonPanel.add(deleteButton);

		// Add panels to frame
		add(customerPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// Load customer data
		loadCustomers();

		// Add button listeners
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showCustomerForm(null);
			}
		});

		// Update button listeners
		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerEntity selectedCustomer = customerList.getSelectedValue();
				if (selectedCustomer != null) {
					showCustomerForm(selectedCustomer);
				} else {
					JOptionPane.showMessageDialog(CustomerAddressesApplication.this, "Please select a customer to modify.");
				}
			}
		});
		// delete button listeners
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerEntity selectedCustomer = customerList.getSelectedValue();
				if (selectedCustomer != null) {
					try {
						customerDAO.deleteCustomer(selectedCustomer.getId());
						loadCustomers();
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(CustomerAddressesApplication.this, "Error deleting customer: " + ex.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(CustomerAddressesApplication.this, "Please select a customer to delete.");
				}
			}
		});
	}

	// Intially load all the customers
	private void loadCustomers() {
		try {
			List<CustomerEntity> customers = customerDAO.getAllCustomers();
			customerListModel.clear();
			for (CustomerEntity customerEntity : customers) {
				customerListModel.addElement(customerEntity);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage());
		}
	}

	private void showCustomerForm(CustomerEntity customerEntity) {
		CustomerForm form = new CustomerForm(this, customerDAO, customerEntity);
		form.setVisible(true);
		loadCustomers();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new CustomerAddressesApplication().setVisible(true);
			}
		});
	}
}

