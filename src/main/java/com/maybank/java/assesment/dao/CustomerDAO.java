package com.maybank.java.assesment.dao;

import com.maybank.java.assesment.entity.CustomerEntity;
import com.maybank.java.assesment.jdbcconn.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<CustomerEntity> getAllCustomers() throws SQLException {
        List<CustomerEntity> customers = new ArrayList<>();
        String query = "SELECT * FROM Customers";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                CustomerEntity customer = new CustomerEntity();
                customer.setId(rs.getInt("id"));
                customer.setShortName(rs.getString("short_name"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress1(rs.getString("address1"));
                customer.setAddress2(rs.getString("address2"));
                customer.setAddress3(rs.getString("address3"));
                customer.setCity(rs.getString("city"));
                customer.setPostalCode(rs.getString("postal_code"));

                customers.add(customer);
            }
        }

        return customers;
    }

    public void addCustomer(CustomerEntity customer) throws SQLException {
        String query = "INSERT INTO Customers (short_name, full_name, address1, address2, address3, city, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customer.getShortName());
            stmt.setString(2, customer.getFullName());
            stmt.setString(3, customer.getAddress1());
            stmt.setString(4, customer.getAddress2());
            stmt.setString(5, customer.getAddress3());
            stmt.setString(6, customer.getCity());
            stmt.setString(7, customer.getPostalCode());

            stmt.executeUpdate();
        }
    }

    public void updateCustomer(CustomerEntity customer) throws SQLException {
        String query = "UPDATE Customers SET short_name=?, full_name=?, address1=?, address2=?, address3=?, city=?, postal_code=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customer.getShortName());
            stmt.setString(2, customer.getFullName());
            stmt.setString(3, customer.getAddress1());
            stmt.setString(4, customer.getAddress2());
            stmt.setString(5, customer.getAddress3());
            stmt.setString(6, customer.getCity());
            stmt.setString(7, customer.getPostalCode());
            stmt.setInt(8, customer.getId());

            stmt.executeUpdate();
        }
    }

    public void deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM Customers WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        }
    }
}
