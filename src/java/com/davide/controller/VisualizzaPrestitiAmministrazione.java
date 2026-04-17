/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.PrestitoDao;
import com.davide.model.Prestito;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "VisualizzaPrestitiAmministrazione", urlPatterns = {"/VisualizzaPrestitiAmministrazione"})
public class VisualizzaPrestitiAmministrazione extends HttpServlet {

    private final PrestitoDao prestitoDao = new PrestitoDao();

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
        String autoreNome = request.getParameter("autoreNome");
        String autoreCognome = request.getParameter("autoreCognome");
        String utenteNome = request.getParameter("utenteNome");
        String utenteCognome = request.getParameter("utenteCognome");
        String utenteDataNascita = request.getParameter("utenteDataNascita"); // yyyy-MM-dd

        try {
            List<Prestito> prestiti = prestitoDao.visualizzaPrestitiConFiltri(
                    titolo, autoreNome, autoreCognome, utenteNome, utenteCognome, utenteDataNascita
            );

            request.setAttribute("prestiti", prestiti);
            request.getRequestDispatcher("/prestitiamministrazione.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(VisualizzaPrestitiAmministrazione.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero dei prestiti.");
        }
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
