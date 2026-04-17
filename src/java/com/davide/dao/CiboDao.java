/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davide.dao;

import com.davide.connection.DBConnection;
import com.davide.model.Cibo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author david
 */
public class CiboDao {

    public List<Cibo> getAll() {
        List<Cibo> cibi = new ArrayList<>();
        String sql = "SELECT * FROM cibi";

        try (Connection conn = DBConnection.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(sql); 
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cibo cibo = new Cibo();
                cibo.setId(rs.getInt("id"));
                cibo.setNome(rs.getString("nome"));
                cibo.setPrezzo(rs.getDouble("prezzo"));
                cibo.setDescrizione(rs.getString("descrizione"));
                cibo.setCategoria(rs.getInt("categoria_id"));
                cibi.add(cibo);
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la lettura dei cibi: " + e.getMessage());
        }
        return cibi;
    }

    public List<Cibo> getByCategoriaId(int categoriaId) {
        
        List<Cibo> cibi = new ArrayList<>();
        String sql = "SELECT * FROM cibi WHERE categoria_id = " + categoriaId;

        try (Connection conn = DBConnection.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(sql); 
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cibo cibo = new Cibo();
                cibo.setId(rs.getInt("id"));
                cibo.setNome(rs.getString("nome"));
                cibo.setPrezzo(rs.getDouble("prezzo"));
                cibo.setDescrizione(rs.getString("descrizione"));
                cibo.setCategoria(rs.getInt("categoria_id"));
                cibi.add(cibo);
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la lettura dei cibi: " + e.getMessage());
        }
        return cibi;
    }
    

    public void deleteCibo(int id) throws SQLException {

        String sql = "DELETE FROM cibi WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void addCibo(String nome, String descrizione, double prezzo, int idCategoria) throws SQLException {
        
        String sql = "INSERT INTO cibi (nome, descrizione, prezzo, categoria_id) VALUES (?,?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, descrizione);
            pstmt.setDouble(3, prezzo);
            pstmt.setInt(4, idCategoria);
            pstmt.executeUpdate();
        }
    }
    
    public static void main(String []args){
        
        CiboDao ciboDao = new CiboDao();
        List<Cibo> cibi = ciboDao.getByCategoriaId(1);
        
    }

}
