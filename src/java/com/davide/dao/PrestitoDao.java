/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davide.dao;

import com.davide.connection.DBConnection;
import com.davide.model.Autore;
import com.davide.model.Libro;
import com.davide.model.Prestito;
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
public class PrestitoDao {

    public void addPrestito(int idLibro, int idUtente, Date dataDa, Date dataA) throws SQLException {

        String insertPrestitoSQL = "INSERT INTO prestiti (utente_id, libro_id, data_da, data_a, restituito) "
                + "VALUES (?, ?, ?, ?, ?)";
        String updateLibroSQL = "UPDATE libri SET quantita = quantita - 1 WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement pstmtPrestito = conn.prepareStatement(insertPrestitoSQL); PreparedStatement pstmtLibro = conn.prepareStatement(updateLibroSQL)) {

                // Decrementa quantità libro
                pstmtLibro.setInt(1, idLibro);
                pstmtLibro.executeUpdate();

                // Inserisci prestito
                pstmtPrestito.setInt(1, idUtente);
                pstmtPrestito.setInt(2, idLibro);
                pstmtPrestito.setDate(3, new java.sql.Date(dataDa.getTime()));
                pstmtPrestito.setDate(4, new java.sql.Date(dataA.getTime()));
                pstmtPrestito.setBoolean(5, false);
                pstmtPrestito.executeUpdate();

            } catch (SQLException e) {

            }
        }
    }

    public void restituisciPrestito(int idPrestito) throws SQLException {

        String updatePrestitoSQL = "UPDATE prestiti SET restituito = true WHERE id = ?";
        String updateLibroSQL = "UPDATE libri SET quantita = quantita + 1 WHERE id = "
                + "(SELECT libro_id FROM prestiti WHERE id = ?)";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement pstmtPrestito = conn.prepareStatement(updatePrestitoSQL); PreparedStatement pstmtLibro = conn.prepareStatement(updateLibroSQL)) {

                // Aggiorna prestito
                pstmtPrestito.setInt(1, idPrestito);
                pstmtPrestito.executeUpdate();

                // Incrementa quantità libro
                pstmtLibro.setInt(1, idPrestito);
                pstmtLibro.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Prestito> visualizzaPrestiti() throws SQLException {

        List<Prestito> prestiti = new ArrayList<>();
        String sql = "SELECT * FROM prestiti p JOIN libri l on l.id = p.libro_id JOIN utenti u ON p.utente_id = u.id join autori a on a.id = l.autore_id order by p.data_a ";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Prestito prestito = new Prestito();

                Libro libro = new Libro();
                libro.setId(rs.getInt("l.id"));
                libro.setTitolo(rs.getString("l.titolo"));
                libro.setISBN(rs.getString("l.ISBN"));
                libro.setPrezzo(rs.getDouble("l.prezzo"));
                libro.setQuantita(rs.getInt("l.quantita"));
                libro.setSinossi(rs.getString("l.sinossi"));

                Autore autore = new Autore();

                autore.setCognome(rs.getString("a.cognome"));
                autore.setNome(rs.getString("a.nome"));
                autore.setId(rs.getInt("a.id"));

                libro.setAutore(autore);

                prestito.setLibro(libro);

                Utente utente = new Utente();

                utente.setId(rs.getInt("u.id"));
                utente.setNome(rs.getString("u.nome"));
                utente.setCognome(rs.getString("u.cognome"));
                utente.setEmail(rs.getString("u.email"));
                utente.setUsername(rs.getString("u.username"));
                utente.setTelefono(rs.getString("u.telefono"));
                utente.setDataNascita(rs.getDate("u.data_nascita"));
                utente.setPassword(rs.getString("u.password"));

                prestito.setUtente(utente);

                prestito.setDataA(rs.getDate("data_a"));
                prestito.setDataDa(rs.getDate("data_da"));
                prestito.setId(rs.getInt("p.id"));
                prestito.setRestituito(rs.getBoolean("p.restituito"));

                prestiti.add(prestito);
            }
        }

        return prestiti;
    }

    public List<Prestito> visualizzaPrestitiConFiltri(String titolo, String autoreNome, String autoreCognome,
            String utenteNome, String utenteCognome, String utenteDataNascita)
            throws SQLException {

        List<Prestito> prestiti = new ArrayList<>();

        String sql = "SELECT * FROM prestiti p JOIN libri l ON p.libro_id = l.id JOIN autori a ON l.autore_id = a.id JOIN utenti u ON p.utente_id = u.id WHERE 1=1";

        List<Object> params = new ArrayList<>();

        if (titolo != null && !titolo.trim().isEmpty()) {
            sql += " AND LOWER(l.titolo) LIKE ?";
            params.add("%" + titolo.toLowerCase() + "%");
        }

        if (autoreNome != null && !autoreNome.trim().isEmpty()) {
            sql += " AND LOWER(a.nome) LIKE ?";
            params.add("%" + autoreNome.toLowerCase() + "%");
        }

        if (autoreCognome != null && !autoreCognome.trim().isEmpty()) {
            sql += " AND LOWER(a.cognome) LIKE ?";
            params.add("%" + autoreCognome.toLowerCase() + "%");
        }

        if (utenteNome != null && !utenteNome.trim().isEmpty()) {
            sql += " AND LOWER(u.nome) LIKE ?";
            params.add("%" + utenteNome.toLowerCase() + "%");
        }

        if (utenteCognome != null && !utenteCognome.trim().isEmpty()) {
            sql += " AND LOWER(u.cognome) LIKE ?";
            params.add("%" + utenteCognome.toLowerCase() + "%");
        }

        if (utenteDataNascita != null && !utenteDataNascita.isEmpty()) {
            sql += " AND u.data_nascita = ?";
            params.add(java.sql.Date.valueOf(utenteDataNascita));
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Prestito p = new Prestito();

                    p.setId(rs.getInt("id"));
                    p.setDataDa(rs.getDate("data_da")); 
                    p.setDataA(rs.getDate("data_a"));
                    p.setRestituito(rs.getBoolean("restituito"));

                    Utente u = new Utente();
                    u.setId(rs.getInt("u.id"));
                    u.setNome(rs.getString("u.nome"));
                    u.setCognome(rs.getString("u.cognome"));
                    u.setDataNascita(rs.getDate("u.data_nascita"));
                    p.setUtente(u);

                    // Autore
                    Autore a = new Autore();
                    a.setId(rs.getInt("a.id"));
                    a.setNome(rs.getString("a.nome"));
                    a.setCognome(rs.getString("a.cognome"));

                    // Libro
                    Libro l = new Libro();
                    l.setId(rs.getInt("l.id"));
                    l.setTitolo(rs.getString("titolo"));
                    l.setAutore(a);
                    p.setLibro(l);

                    prestiti.add(p);

                }
            }
        }

        return prestiti;
    }
}
