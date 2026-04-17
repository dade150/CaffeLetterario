/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.UtenteDao;
import com.davide.model.Utente;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
@WebServlet(name = "RicercaEliminaUtenti", urlPatterns = {"/RicercaEliminaUtenti"})
public class RicercaEliminaUtenti extends HttpServlet {

    private UtenteDao utenteDao = new UtenteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String dataString = request.getParameter("dataNascita");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dataUtil = null;
        if (dataString != null && !dataString.trim().isEmpty()) {
            try {
                dataUtil = sdf.parse(dataString);
            } catch (ParseException ex) {
                Logger.getLogger(RicercaEliminaUtenti.class.getName()).log(Level.WARNING, "Formato data non valido: " + dataString, ex);
            }
        }

        List<Utente> utenti = utenteDao.ricercaUtenti(nome, cognome, telefono, email, username, dataUtil);
        request.setAttribute("utenti", utenti);
        request.getServletContext().getRequestDispatcher("/utenti.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            utenteDao.deleteUtente(id);
        } catch (SQLException ex) {
            Logger.getLogger(RicercaEliminaUtenti.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("RicercaEliminaUtenti"); // Ricarica elenco
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
