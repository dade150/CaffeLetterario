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
        // Specifichiamo i nomi delle colonne e usiamo degli alias (as) per gli ID
        String sql = "SELECT r.id as rec_id, r.testo, r.valutazione, r.utente_id, " +
                "u.id as ut_id, u.nome, u.cognome, u.email, u.username, u.telefono, u.data_nascita, u.password " +
                "FROM recensioni r JOIN Utenti u ON u.id = r.utente_id";

        List<Recensione> recensioni = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Recensione recensione = new Recensione();
                // Usiamo l'alias 'rec_id' definito nella query
                recensione.setId(rs.getInt("rec_id"));
                recensione.setTesto(rs.getString("testo"));
                recensione.setValutazione(rs.getInt("valutazione"));

                Utente utente = new Utente();
                // Usiamo l'alias 'ut_id'
                utente.setId(rs.getInt("ut_id"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setEmail(rs.getString("email"));
                utente.setUsername(rs.getString("username"));
                utente.setTelefono(rs.getString("telefono"));
                utente.setDataNascita(rs.getDate("data_nascita"));
                utente.setPassword(rs.getString("password"));

                recensione.setUtente(utente);
                recensioni.add(recensione);
            }
        }
        return recensioni;
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
