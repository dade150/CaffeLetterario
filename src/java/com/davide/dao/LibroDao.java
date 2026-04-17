/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davide.dao;

import com.davide.connection.DBConnection;
import com.davide.model.Autore;
import com.davide.model.Genere;
import com.davide.model.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author david
 */
public class LibroDao {

    public List<Libro> getAllInfoLibri() {
        List<Libro> libri = new ArrayList<>();
        Map<Integer, Libro> libroMap = new HashMap<>();
        String sql = "SELECT * FROM libri l JOIN autori a ON a.id = l.autore_id left JOIN libri_generi lg ON lg.libro_id = l.id left JOIN generi g ON g.id = lg.genere_id order by l.titolo";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int libroId = rs.getInt("l.id");

                // Se il libro non è ancora stato creato, crealo
                if (!libroMap.containsKey(libroId)) {
                    Libro libro = new Libro();
                    libro.setId(libroId);
                    libro.setTitolo(rs.getString("l.titolo"));
                    libro.setISBN(rs.getString("l.ISBN"));
                    libro.setPrezzo(rs.getDouble("l.prezzo"));
                    libro.setQuantita(rs.getInt("l.quantita"));
                    libro.setSinossi(rs.getString("l.sinossi"));

                    Autore autore = new Autore();
                    autore.setId(rs.getInt("a.id"));
                    autore.setNome(rs.getString("a.nome"));
                    autore.setCognome(rs.getString("a.cognome"));
                    libro.setAutore(autore);

                    libro.setGeneri(new ArrayList<>()); // Inizializza la lista dei generi
                    libri.add(libro);
                    libroMap.put(libroId, libro);
                }

                // Aggiungi il genere corrente al libro
                Genere genere = new Genere();
                genere.setId(rs.getInt("g.id"));
                genere.setNome(rs.getString("g.nome"));
                libroMap.get(libroId).getGeneri().add(genere);

            }

        } catch (SQLException e) {
            System.err.println("Errore durante la lettura dei libri: " + e.getMessage());
        }
        return libri;
    }

    public List<Libro> ricercaLibroByAutoreOrTitolo(String nome, String cognome, String titolo) {
        List<Libro> libri = new ArrayList<>();
        Map<Integer, Libro> libroMap = new HashMap<>();
        String sql = "SELECT * FROM libri l JOIN autori a ON a.id = l.autore_id JOIN libri_generi lg ON lg.libro_id = l.id JOIN generi g ON g.id = lg.genere_id WHERE l.quantita > 0\n"
                + " AND LOWER(l.titolo) LIKE CONCAT('%', LOWER(?), '%')"
                + " AND LOWER(a.nome) LIKE CONCAT('%', LOWER(?), '%')"
                + " AND LOWER(a.cognome) LIKE CONCAT('%', LOWER(?), '%') order by l.titolo";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {

            pstm.setString(1, titolo);
            pstm.setString(2, nome);
            pstm.setString(3, cognome);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int libroId = rs.getInt("l.id");

                if (!libroMap.containsKey(libroId)) {
                    Libro libro = new Libro();
                    libro.setId(libroId);
                    libro.setTitolo(rs.getString("l.titolo"));
                    libro.setISBN(rs.getString("l.ISBN"));
                    libro.setPrezzo(rs.getDouble("l.prezzo"));
                    libro.setQuantita(rs.getInt("l.quantita"));
                    libro.setSinossi(rs.getString("l.sinossi"));

                    Autore autore = new Autore();
                    autore.setId(rs.getInt("a.id"));
                    autore.setNome(rs.getString("a.nome"));
                    autore.setCognome(rs.getString("a.cognome"));
                    libro.setAutore(autore);

                    libro.setGeneri(new ArrayList<>()); // Inizializza la lista dei generi
                    libri.add(libro);
                    libroMap.put(libroId, libro);
                }

                // Aggiungi il genere corrente al libro
                Genere genere = new Genere();
                genere.setId(rs.getInt("g.id"));
                genere.setNome(rs.getString("g.nome"));
                libroMap.get(libroId).getGeneri().add(genere);

            }

        } catch (SQLException e) {
            System.err.println("Errore durante la lettura dei libri: " + e.getMessage());
        }
        return libri;
    }

    public List<Libro> ricercaLibroByAutoreOrTitoloOrISBN(String nome, String cognome, String titolo, String ISBN) {
        List<Libro> libri = new ArrayList<>();
        Map<Integer, Libro> libroMap = new HashMap<>();
        String sql = "SELECT * FROM libri l JOIN autori a ON a.id = l.autore_id left JOIN libri_generi lg ON lg.libro_id = l.id left JOIN generi g ON g.id = lg.genere_id WHERE LOWER(l.titolo) LIKE CONCAT('%', LOWER(?), '%')"
                + " AND LOWER(a.nome) LIKE CONCAT('%', LOWER(?), '%')"
                + " AND LOWER(l.ISBN) LIKE CONCAT('%', LOWER(?), '%')"
                + " AND LOWER(a.cognome) LIKE CONCAT('%', LOWER(?), '%') order by l.titolo";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {

            pstm.setString(1, titolo);
            pstm.setString(2, nome);
            pstm.setString(4, cognome);
            pstm.setString(3, ISBN);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int libroId = rs.getInt("l.id");

                if (!libroMap.containsKey(libroId)) {
                    Libro libro = new Libro();
                    libro.setId(libroId);
                    libro.setTitolo(rs.getString("l.titolo"));
                    libro.setISBN(rs.getString("l.ISBN"));
                    libro.setPrezzo(rs.getDouble("l.prezzo"));
                    libro.setQuantita(rs.getInt("l.quantita"));
                    libro.setSinossi(rs.getString("l.sinossi"));
                    libro.setAnno(rs.getInt("l.anno"));


                    Autore autore = new Autore();
                    autore.setId(rs.getInt("a.id"));
                    autore.setNome(rs.getString("a.nome"));
                    autore.setCognome(rs.getString("a.cognome"));
                    libro.setAutore(autore);

                    libro.setGeneri(new ArrayList<>()); // Inizializza la lista dei generi
                    libri.add(libro);
                    libroMap.put(libroId, libro);
                }

                // Aggiungi il genere corrente al libro
                Genere genere = new Genere();
                genere.setId(rs.getInt("g.id"));
                genere.setNome(rs.getString("g.nome"));
                libroMap.get(libroId).getGeneri().add(genere);

            }

        } catch (SQLException e) {
            System.err.println("Errore durante la lettura dei libri: " + e.getMessage());
        }
        return libri;
    }

    public void aggiungiLibro(Libro libro, Autore autore, List<Genere> generi) throws SQLException {
        
        String sql = "INSERT INTO libri (titolo, anno, quantita, sinossi, prezzo, isbn, autore_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, libro.getTitolo());
            ps.setInt(2, libro.getAnno());
            ps.setInt(3, libro.getQuantita());
            ps.setString(4, libro.getSinossi());
            ps.setDouble(5, libro.getPrezzo());
            ps.setString(6, libro.getISBN());
            ps.setInt(7, autore.getId());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    libro.setId(generatedKeys.getInt(1));
                }
            }

            // Aggiungi generi nella tabella ponte libro_genere
            if (generi != null) {
                String sqlGenere = "INSERT INTO libri_generi (libro_id, genere_id) VALUES (?, ?)";
                try (PreparedStatement psGenere = conn.prepareStatement(sqlGenere)) {
                    for (Genere g : generi) {
                        psGenere.setInt(1, libro.getId());
                        psGenere.setInt(2, g.getId());
                        psGenere.executeUpdate();
                    }
                }
            }
        }
    }

    

    public void deleteLibro(int id) throws SQLException {

        String sql2 = "DELETE FROM libri WHERE id = ?";
        String sql1 = "DELETE FROM libri_generi WHERE libro_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql1)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql2)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
    }

    public void aggiornaQuantitaLibro(int id, int num) throws SQLException {
        String sql = "UPDATE libri SET quantita = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, num);
            pstm.setInt(2, id);

            pstm.executeUpdate();
        }
    }

    public void aggiornaLibro(String titolo, String sinossi, int anno, double prezzo, String ISBN, int id, int num) throws SQLException {
        String sql = "UPDATE libri SET titolo = ?, anno = ?, quantita = ?, sinossi = ?, prezzo = ?, isbn = ? WHERE id = ?;";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titolo);
            pstmt.setInt(2, anno);
            pstmt.setInt(3, num);
            pstmt.setString(4, sinossi);
            pstmt.setDouble(5, prezzo);
            pstmt.setString(6, ISBN);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        }
    }
}
