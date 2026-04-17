/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.LibroDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "AggiornaQuantitaLibroAmministrazione", urlPatterns = {"/AggiornaQuantitaLibroAmministrazione"})
public class AggiornaQuantitaLibroAmministrazione extends HttpServlet {

    private LibroDao libroDao = new LibroDao();

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
        int num = Integer.parseInt(request.getParameter("quantita"));

        try {
            libroDao.aggiornaQuantitaLibro(id, num);
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
