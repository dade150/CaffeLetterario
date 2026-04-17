/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davide.dao;

import com.davide.connection.DBConnection;
import com.davide.model.Prenotazione;
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
public class PrenotazioneDao {

    private final UtenteDao utenteDao = new UtenteDao();

    public Prenotazione addPrenotazioneCliente(String username, String password, Date data, Date oraDa, Date oraA, int posti, String note) throws SQLException {

        int idUtente = utenteDao.getUtenteByUsernameAndPassword(username, password);
        System.out.println(idUtente);
        String s = utenteDao.getUtente(idUtente).getNome() + " " + utenteDao.getUtente(idUtente).getCognome() + " " + utenteDao.getUtente(idUtente).getDataNascita();
        String tel = utenteDao.getUtente(idUtente).getTelefono();

        if (idUtente == -1) {
            throw new SQLException("Utente non trovato");
        }

        String sql = "INSERT INTO prenotazioni (utente_id, data, ora_da, ora_a, note, posti, nome_identificativo, telefono) VALUES (?, ?, ?, ?, ?, ?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUtente);
            pstmt.setDate(2, new java.sql.Date(data.getTime()));
            pstmt.setTime(3, new java.sql.Time(oraDa.getTime()));
            pstmt.setTime(4, new java.sql.Time(oraA.getTime()));
            pstmt.setString(5, note);
            pstmt.setInt(6, posti);
            pstmt.setString(7, s);
            pstmt.setString(8, tel);

            pstmt.executeUpdate();
        }

        // Popolamento oggetto Prenotazione da restituire
        Prenotazione prenotazione = new Prenotazione();
        Utente utente = new Utente(); // oppure utenteDao.getById(idUtente);
        utente.setId(idUtente);
        utente.setUsername(username); // opzionale

        prenotazione.setUtente(utente);
        prenotazione.setData(data);
        prenotazione.setOraDa(oraDa);
        prenotazione.setOraA(oraA);
        prenotazione.setPosti(posti);
        prenotazione.setNote(note);
        prenotazione.setNomeIdentificativo(s);
        prenotazione.setTelefono(tel);

        return prenotazione;
    }
    
    public Prenotazione addPrenotazioneAmministrazione(Date data, Date oraDa, Date oraA, int posti, String note, String telefono, String nomeIdentificativo) throws SQLException {

        String sql = "INSERT INTO prenotazioni (data, ora_da, ora_a, note, posti, nome_identificativo, telefono) VALUES (?, ?, ?, ?, ?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            System.out.println("wewe");
            pstmt.setDate(1, new java.sql.Date(data.getTime()));
            pstmt.setTime(2, new java.sql.Time(oraDa.getTime()));
            pstmt.setTime(3, new java.sql.Time(oraA.getTime()));
            pstmt.setString(4, note);
            pstmt.setInt(5, posti);
            pstmt.setString(6, nomeIdentificativo);
            pstmt.setString(7, telefono);

            pstmt.executeUpdate();
        }

        // Popolamento oggetto Prenotazione da restituire
        Prenotazione prenotazione = new Prenotazione();

        prenotazione.setData(data);
        prenotazione.setOraDa(oraDa);
        prenotazione.setOraA(oraA);
        prenotazione.setPosti(posti);
        prenotazione.setNote(note);
        prenotazione.setNomeIdentificativo(nomeIdentificativo);
        prenotazione.setTelefono(telefono);
                
        return prenotazione;
    }


    public List<Prenotazione> visualizzaPrenotazioniGiorno(Date giorno) throws SQLException {
        List<Prenotazione> prenotazioni = new ArrayList<>();

        String sql = "SELECT * FROM prenotazioni p WHERE data = ? ORDER BY ora_da";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            java.sql.Date sqlDate = new java.sql.Date(giorno.getTime());
            pstmt.setDate(1, sqlDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Prenotazione p = new Prenotazione();
                    p.setId(rs.getInt("id"));
                    p.setData(rs.getDate("data"));
                    p.setOraDa(rs.getTime("ora_da"));
                    p.setOraA(rs.getTime("ora_a"));
                    p.setPosti(rs.getInt("posti"));
                    p.setNote(rs.getString("note"));
                    p.setNomeIdentificativo(rs.getString("nome_identificativo"));
                    p.setTelefono(rs.getString("telefono"));

                    prenotazioni.add(p);
                }
            }
        }
        return prenotazioni;
    }

    public void deletePrenotazione(int id) {

        String sql = "DELETE FROM prenotazioni WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id); // Imposta il valore del parametro
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prenotazione eliminata con successo.");
            } else {
                System.out.println("Nessuna prenotazione trovata con l'ID specificato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestisci l'errore di SQL
        }
    }
}
