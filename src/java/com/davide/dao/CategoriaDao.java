package com.davide.dao;

import com.davide.connection.DBConnection;
import com.davide.model.Categoria;
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
public class CategoriaDao {

    public List<Categoria> getAll() throws SQLException {

        List<Categoria> categorie = new ArrayList<>();
        String sql = "SELECT * FROM categorie";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            {

                while (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setNome(rs.getString("nome"));
                    categorie.add(categoria);
                }
                return categorie;
            }
        }
    }

    public void deleteCategoria(int idCategoria) throws SQLException {

        String sql = "DELETE FROM Categorie WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCategoria);
            pstmt.executeUpdate();
        }
    }

    public void addCategoria(String categoria) throws SQLException {
        String sql = "INSERT INTO Categorie (nome) VALUES (?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoria);
            pstmt.executeUpdate();
        }

    }

}
