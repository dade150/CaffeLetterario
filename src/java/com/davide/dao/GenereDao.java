/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davide.dao;

import com.davide.connection.DBConnection;
import com.davide.model.Genere;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author david
 */
public class GenereDao {

    public Genere getByNome(String nome) {

        nome = nome.toLowerCase();
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
        String sql = "SELECT * FROM generi WHERE nome = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Genere genere = new Genere();
                    genere.setId(rs.getInt("id"));
                    genere.setNome(rs.getString("nome"));
                    return genere;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addGenere(String nome) {

        nome = nome.toLowerCase();
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
        String sql = "INSERT INTO generi (nome) VALUES (?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
