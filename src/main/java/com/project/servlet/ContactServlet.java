package com.project.servlet;

import com.project.database.DBConnection;
import com.project.models.Contact;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/contacts")
public class ContactServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();

    // Capture/Insert contacts(POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonNode jsonNode = objectMapper.readTree(request.getReader()); // Read JSON body

        String fullName = jsonNode.get("fullName").asText();
        String phoneNumber = jsonNode.get("phoneNumber").asText();
        String email = jsonNode.has("email") ? jsonNode.get("email").asText() : null;
        String idNumber = jsonNode.has("idNumber") ? jsonNode.get("idNumber").asText() : null;
        String dateOfBirth = jsonNode.has("dateOfBirth") ? jsonNode.get("dateOfBirth").asText() : null;
        String gender = jsonNode.has("gender") ? jsonNode.get("gender").asText() : null;
        String organization = jsonNode.has("organization") ? jsonNode.get("organization").asText() : null;

        if (fullName == null || phoneNumber == null || fullName.isEmpty() || phoneNumber.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"fullName and phoneNumber are required\"}");
            return;
        }

        String maskedName = fullName.split(" ")[0] + " " + fullName.charAt(0) + ".";
        String maskedPhone = phoneNumber.substring(0, 6) + "**" + phoneNumber.substring(8);
        String phoneHash = hashSHA256(phoneNumber);

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO contact (full_name, phone_number, email, id_number, date_of_birth, gender, organization, masked_name, masked_phone, phone_hash) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, fullName);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, email);
            stmt.setString(4, idNumber);
            stmt.setString(5, dateOfBirth);
            stmt.setString(6, gender);
            stmt.setString(7, organization);
            stmt.setString(8, maskedName);
            stmt.setString(9, maskedPhone);
            stmt.setString(10, phoneHash);

            stmt.executeUpdate();
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\": \"Contact saved successfully\"}");
        }
        catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error\"}");
        }
    }

    // RETRIEVE Contact(s) (GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Contact> contacts = new ArrayList<>();

        // Extract query parameters
        String phoneHash = request.getParameter("phoneHash");
        String organization = request.getParameter("organization");
        String maskedPhone = request.getParameter("maskedPhone");
        String maskedName = request.getParameter("maskedName");

        try (Connection conn = DBConnection.getConnection()) {
            StringBuilder query = new StringBuilder("SELECT * FROM contact WHERE 1=1");

            List<Object> parameters = new ArrayList<>();

            if (phoneHash != null && !phoneHash.isEmpty()) {
                query.append(" AND phone_hash = ?");
                parameters.add(phoneHash);
            }
            if (organization != null && !organization.isEmpty()) {
                query.append(" AND organization = ?");
                parameters.add(organization);
            }
            if (maskedPhone != null && !maskedPhone.isEmpty()) {
                query.append(" AND masked_phone = ?");
                parameters.add(maskedPhone);
            }
            if (maskedName != null && !maskedName.isEmpty()) {
                query.append(" AND masked_name = ?");
                parameters.add(maskedName);
            }

            PreparedStatement stmt = conn.prepareStatement(query.toString());

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

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

            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(contacts));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error\"}");
        }
    }

    // UPDATE Contact (PUT)
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonNode jsonNode = objectMapper.readTree(request.getReader());
        int id = jsonNode.get("id").asInt();
        String fullName = jsonNode.get("fullName").asText();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE contact SET full_name = ? WHERE id = ?");
            stmt.setString(1, fullName);
            stmt.setInt(2, id);

            int updated = stmt.executeUpdate();
            if (updated > 0) {
                response.getWriter().write("{\"message\": \"Contact updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Contact not found\"}");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error\"}");
        }
    }

    // DELETE Contact (DELETE)
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM contact WHERE id = ?");
            stmt.setInt(1, id);

            int deleted = stmt.executeUpdate();
            if (deleted > 0) {
                response.getWriter().write("{\"message\": \"Contact deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Contact not found\"}");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error\"}");
        }
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}