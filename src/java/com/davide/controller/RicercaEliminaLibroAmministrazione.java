/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.LibroDao;
import com.davide.model.Libro;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */
@WebServlet(name = "RicercaEliminaLibroAmministrazione", urlPatterns = {"/RicercaEliminaLibroAmministrazione"})
public class RicercaEliminaLibroAmministrazione extends HttpServlet {

    LibroDao libroDao = new LibroDao();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String titolo = request.getParameter("titolo");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String ISBN = request.getParameter("isbn");

        List<Libro> libri = libroDao.ricercaLibroByAutoreOrTitoloOrISBN(nome, cognome, titolo,ISBN);

        request.setAttribute("libri", libri);
        request.getServletContext().getRequestDispatcher("/libreriaamministrazione.jsp").forward(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("libroId"));
        try {
            libroDao.deleteLibro(id);
        } catch (SQLException ex) {
            Logger.getLogger(RicercaEliminaUtenti.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("libreriaamministrazione.jsp"); // Ricarica elenco
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
