/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davide.dao;

import com.davide.connection.DBConnection;
import com.davide.model.Ruolo;
import com.davide.model.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author david
 */
public class UtenteDao {
    
    public int getUtenteByUsernameAndPassword(String username,String password) throws SQLException{
        String sql = "SELECT u.id "
                + "FROM utenti u WHERE u.username = ? AND u.password = ?";

        int idUtente = 0; // Initialize to null

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, username);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) { // Move to the first row if a result exists
                idUtente= rs.getInt("u.id");
                System.out.println(idUtente);
            }

        } catch (SQLException e) {
            
        }

        return idUtente;
    }

    public Ruolo login(String username, String password) throws SQLException {

        String sql = "SELECT r.id AS ruolo_id, r.nome AS ruolo_nome "
                + "FROM utenti u JOIN ruoli r ON r.id = u.ruolo_id "
                + "WHERE u.username = ? AND u.password = ?";

        Ruolo ruolo = null; // Initialize to null

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, username);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) { // Move to the first row if a result exists
                ruolo = new Ruolo();
                ruolo.setId(rs.getInt("ruolo_id")); // Use the alias defined in the SELECT
                ruolo.setNome(rs.getString("ruolo_nome")); // Use the alias defined in the SELECT
            }

        } catch (SQLException e) {
            // Log the exception properly
            e.printStackTrace();
            throw e; // Re-throw the exception to be handled by the servlet
        }

        return ruolo;
    }

    public List<Utente> ricercaUtenti(String nome, String cognome, String telefono, String email, String username, Date dataNascita) {
        
        List<Utente> utenti = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM utenti u JOIN ruoli r ON r.id = u.ruolo_id WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (telefono != null && !telefono.isEmpty()) {
            sql.append(" AND u.telefono LIKE ?");
            params.add("%" + telefono + "%");
        }
        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND u.nome LIKE ?");
            params.add("%" + nome + "%");
        }
        if (cognome != null && !cognome.isEmpty()) {
            sql.append(" AND u.cognome LIKE ?");
            params.add("%" + cognome + "%");
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" AND u.email LIKE ?");
            params.add("%" + email + "%");
        }
        if (username != null && !username.isEmpty()) {
            sql.append(" AND u.username LIKE ?");
            params.add("%" + username + "%");
        }
        if (dataNascita != null) {
            sql.append(" AND u.data_nascita = ?");
            params.add(new java.sql.Date(dataNascita.getTime()));
        }

        // Aggiungi questo per debug
        System.out.println("SQL: " + sql.toString());
        System.out.println("Params: " + params);

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstm.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Utente utente = new Utente();
                utente.setId(rs.getInt("u.id"));
                utente.setNome(rs.getString("u.nome"));
                utente.setCognome(rs.getString("u.cognome"));
                utente.setEmail(rs.getString("u.email"));
                utente.setUsername(rs.getString("u.username"));
                utente.setTelefono(rs.getString("u.telefono"));
                utente.setDataNascita(rs.getDate("u.data_nascita"));
                utente.setPassword(rs.getString("u.password"));

                Ruolo ruolo = new Ruolo();
                ruolo.setId(rs.getInt("r.id"));
                ruolo.setNome(rs.getString("r.nome"));

                utente.setRuolo(ruolo);
                utenti.add(utente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utenti;
    }

    public void deleteUtente(int id) throws SQLException {

        String sql = "DELETE FROM utenti WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
    }

    public void modificaUtente(int id, String nome, String cognome, String telefono, String email, String username, String password, Date data) throws SQLException {

        String sql = "UPDATE utenti SET nome = ? ,cognome = ?,telefono = ?,email = ?,username = ?,password = ? , data_nascita=? WHERE id = ?;";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, nome);
            pstm.setString(2, cognome);
            pstm.setString(3, telefono);
            pstm.setString(4, email);
            pstm.setString(5, username);
            pstm.setString(6, password);
            pstm.setDate(7, new java.sql.Date(data.getTime()));
            pstm.setInt(8, id);
            pstm.executeUpdate();
        }
    }

    public void addUtente(String nome, String cognome, String telefono, String email, String username, String password, Date data) throws SQLException {

        String sql = "INSERT INTO utenti (nome, cognome, email, telefono, data_nascita, username, password, ruolo_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, nome);
            pstm.setString(2, cognome);
            pstm.setString(4, telefono);
            pstm.setString(3, email);
            pstm.setString(6, username);
            pstm.setString(7, password);
            pstm.setDate(5, new java.sql.Date(data.getTime()));
            pstm.setInt(8, 3);//clieente
            pstm.executeUpdate();
        }
    }

    public Utente getUtente(int id) throws SQLException {

        Utente utente = null;
        String sql = "SELECT * FROM utenti u WHERE u.id=?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {

                utente = new Utente();

                utente.setId(rs.getInt("u.id"));
                utente.setNome(rs.getString("u.nome"));
                utente.setCognome(rs.getString("u.cognome"));
                utente.setEmail(rs.getString("u.email"));
                utente.setUsername(rs.getString("u.username"));
                utente.setTelefono(rs.getString("u.telefono"));
                utente.setDataNascita(rs.getDate("u.data_nascita"));
                utente.setPassword(rs.getString("u.password"));

            }
        }
        return utente;
    }

    public static void main(String[] args) throws SQLException {
        UtenteDao dao = new UtenteDao();

        Utente u = dao.getUtente(4);

        System.out.println(u.getNome());
    }
}
