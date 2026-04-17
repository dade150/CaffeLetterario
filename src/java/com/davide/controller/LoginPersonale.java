/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.UtenteDao;
import com.davide.model.Ruolo;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author david
 */
@WebServlet(name = "LoginPersonale", urlPatterns = {"/LoginPersonale"})
public class LoginPersonale extends HttpServlet {
    
    UtenteDao utenteDao = new UtenteDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Ruolo ruolo = utenteDao.login(username, password);

            if (ruolo != null) {
                HttpSession session = request.getSession(); 
                switch (ruolo.getNome()) {
                    case "Amministratore":
                        session.setAttribute("ruolo", "Amministratore"); // Imposta il ruolo nella sessione
                        request.getServletContext().getRequestDispatcher("/administration.jsp").forward(request, response);
                        break;
                    case "Personale":
                        request.getServletContext().getRequestDispatcher("/personale.jsp").forward(request, response);
                        break;
                    default:
                        // Gestisci altri ruoli o utenti non autorizzati
                        response.setContentType("text/html");
                        PrintWriter out = response.getWriter();
                        out.println("<!DOCTYPE html>");
                        out.println("<html><head><title>Errore di Autorizzazione</title></head><body>");
                        out.println("<h1>Ruolo non autorizzato.</h1>");
                        out.println("</body></html>");
                        break;
                }
            } else {
                // Gestisci il login fallito
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html><head><title>Login Fallito</title></head><body>");
                out.println("<h1>Login fallito. Credenziali non valide.</h1>");
                out.println("<p><a href=\"login.jsp\">Riprova</a></p>");
                out.println("</body></html>");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginPersonale.class.getName()).log(Level.SEVERE, null, ex);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Errore del Server</title></head><body>");
            out.println("<h1>Errore durante l'accesso.</h1>");
            out.println("<p>Si è verificato un errore nel server.</p>");
            out.println("</body></html>");
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
