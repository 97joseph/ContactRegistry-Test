package com.project.services;

import com.project.database.DBConnection;
import com.project.models.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactService {

    public List<Contact> getContactsByOrganization(String organization) {
        List<Contact> contacts = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM contact WHERE organization = ?");
            stmt.setString(1, organization);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                contacts.add(new Contact(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("date_of_birth"),
                        rs.getString("gender"),
                        rs.getString("organization"),
                        rs.getString("masked_name"),
                        rs.getString("masked_phone"),
                        rs.getString("phone_hash")
                ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}