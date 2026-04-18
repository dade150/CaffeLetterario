/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.RecensioneDao;
import com.davide.model.Recensione;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "VisualizzaRecensione", urlPatterns = {"/VisualizzaRecensione"})
public class VisualizzaRecensione extends HttpServlet {

    private final RecensioneDao dao = new RecensioneDao();

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
        try {
            List<Recensione> recensioni = dao.visualizzaRecensioni();

            if (recensioni == null) {
                recensioni = new ArrayList<>();
            }

            request.setAttribute("recensioni", recensioni);

            request.getRequestDispatcher("index.jsp").forward(request, response);

        }  catch (SQLException ex) {
        // Invece del logger complicato di Tomcat, usa un semplice print per il debug
        System.err.println("Errore Database nelle Recensioni: " + ex.getMessage());
        ex.printStackTrace();

        request.setAttribute("recensioni", new ArrayList<Recensione>());
        request.getRequestDispatcher("index.jsp").forward(request, response);
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

        String username = request.getParameter("usernameRec");
        String password = request.getParameter("passwordRec");
        String testo = request.getParameter("testo");
        int valutazione = Integer.parseInt(request.getParameter("valutazione"));

        try {
            dao.addRecensione(username, password, testo, valutazione);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (SQLException ex) {
        // Invece del logger complicato di Tomcat, usa un semplice print per il debug
        System.err.println("Errore Database nelle Recensioni: " + ex.getMessage());
        ex.printStackTrace();

        request.setAttribute("recensioni", new ArrayList<Recensione>());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
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
