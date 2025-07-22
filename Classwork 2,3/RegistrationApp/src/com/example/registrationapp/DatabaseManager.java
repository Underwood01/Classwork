package com.example.registrationapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:registration.db";

    public DatabaseManager() {
        createNewDatabase();
        createNewTable();
    }

    private void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS registrations ("
                   + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + " name TEXT NOT NULL,"
                   + " mobile TEXT NOT NULL,"
                   + " gender TEXT NOT NULL,"
                   + " dob TEXT NOT NULL,"
                   + " address TEXT NOT NULL"
                   + ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'registrations' created or already exists.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertRegistration(String name, String mobile, String gender, String dob, String address) {
        String sql = "INSERT INTO registrations(name,mobile,gender,dob,address) VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, mobile);
            pstmt.setString(3, gender);
            pstmt.setString(4, dob);
            pstmt.setString(5, address);
            pstmt.executeUpdate();
            System.out.println("Registration inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String[]> getAllRegistrations() {
        String sql = "SELECT id, name, mobile, gender, dob, address FROM registrations";
        List<String[]> registrations = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] row = new String[6];
                row[0] = String.valueOf(rs.getInt("id"));
                row[1] = rs.getString("name");
                row[2] = rs.getString("mobile");
                row[3] = rs.getString("gender");
                row[4] = rs.getString("dob");
                row[5] = rs.getString("address");
                registrations.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return registrations;
    }
}
