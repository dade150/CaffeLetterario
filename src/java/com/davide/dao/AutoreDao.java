/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davide.dao;

import com.davide.connection.DBConnection;
import com.davide.model.Autore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author david
 */
public class AutoreDao {

    public Autore getByNomeAndCognome(String nome, String cognome) {

        nome = nome.toLowerCase();
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
        cognome = cognome.toLowerCase();
        cognome = cognome.substring(0, 1).toUpperCase() + cognome.substring(1);
        String sql = "SELECT * FROM autori WHERE nome = ? And cognome = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, cognome);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    Autore autore = new Autore();
                    autore.setId(rs.getInt("id"));
                    autore.setCognome(rs.getString("cognome"));
                    autore.setNome(rs.getString("nome"));
                    return autore;
                }

                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addAutore(String nome, String cognome) {

        nome = nome.toLowerCase();
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
        cognome = cognome.toLowerCase();
        cognome = cognome.substring(0, 1).toUpperCase() + cognome.substring(1);

        String sql = "INSERT INTO autori (nome, cognome) VALUES (?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, cognome);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
