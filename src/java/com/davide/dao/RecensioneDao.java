/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davide.dao;

import com.davide.connection.DBConnection;
import com.davide.model.Recensione;
import com.davide.model.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class RecensioneDao {

    private final UtenteDao utenteDao = new UtenteDao();

    public List<Recensione> visualizzaRecensioni() throws SQLException {

        String sql = "SELECT * FROM RECENSIONI r join Utenti u on u.id=r.utente_id";
        List<Recensione> recensioni = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            {

                while (rs.next()) {
                    Recensione recensione = new Recensione();
                    recensione.setId(rs.getInt("r.id"));
                    recensione.setTesto(rs.getString("testo"));
                    recensione.setValutazione(rs.getInt("valutazione"));

                    Utente utente = new Utente();
                    utente.setId(rs.getInt("u.id"));
                    utente.setNome(rs.getString("u.nome"));
                    utente.setCognome(rs.getString("u.cognome"));
                    utente.setEmail(rs.getString("u.email"));
                    utente.setUsername(rs.getString("u.username"));
                    utente.setTelefono(rs.getString("u.telefono"));
                    utente.setDataNascita(rs.getDate("u.data_nascita"));
                    utente.setPassword(rs.getString("u.password"));

                    recensione.setUtente(utente);
                    recensioni.add(recensione);
                }

            }
            return recensioni;
        }
    }

    public void addRecensione(String username, String password, String testo, int valutazione) throws SQLException {

        int idUtente = utenteDao.getUtenteByUsernameAndPassword(username, password);
        System.out.println("DEBUG: username=" + username + ", password=" + password);

        if (idUtente <= 0) {
            throw new SQLException("Utente non trovato: username o password errati.");
        }

        String sql = "INSERT INTO recensioni (utente_id, valutazione, testo) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUtente);
            pstmt.setInt(2, valutazione);
            pstmt.setString(3, testo);
            pstmt.executeUpdate();
        }

    }
}
