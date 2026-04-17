package com.davide.controller;

import com.davide.dao.UtenteDao;
import com.davide.model.Utente;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "AggiungiModificaUtente", urlPatterns = {"/AggiungiModificaUtente"})
public class AggiungiModificaUtente extends HttpServlet {

    private UtenteDao utenteDao = new UtenteDao();

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            Utente utente = null;
            try {
                utente = utenteDao.getUtente(id);
                request.setAttribute("utente", utente);
                request.getRequestDispatcher("/aggiungimodificautente.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(AggiungiModificaUtente.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            // Mostra la pagina per l'aggiunta di un nuovo utente (form vuoto)
            request.getRequestDispatcher("/aggiungimodificautente.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password"); // Considera la gestione della password
        String dataNascitaStr = request.getParameter("dataNascita");

        Date dataNascita = null;
        if (dataNascitaStr != null && !dataNascitaStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = sdf.parse(dataNascitaStr);
                dataNascita = new Date(parsedDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace(); // Gestisci l'errore di parsing della data
                response.sendRedirect(request.getContextPath() + "/aggiungimodificautente.jsp?error=data_non_valida");
                return;
            }
        }

        try {
            if (idParam != null && !idParam.isEmpty()) {
                // Modifica utente esistente
                int id = Integer.parseInt(idParam);
                utenteDao.modificaUtente(id, nome, cognome, telefono, email, username, password, dataNascita);
            } else {
                // Aggiungi nuovo utente
                utenteDao.addUtente(nome, cognome, telefono, email, username, password, dataNascita);
                System.out.println("Implementare il metodo aggiungiUtente in UtenteDao");
            }
            response.sendRedirect(request.getContextPath() + "/RicercaEliminaUtenti"); // Reindirizza alla pagina di elenco aggiornata
        } catch (SQLException e) {
            e.printStackTrace(); // Gestisci l'errore di database
            String messaggioErrore = (idParam != null && !idParam.isEmpty()) ? "errore_modifica" : "errore_aggiunta";
            response.sendRedirect(request.getContextPath() + "/aggiungimodificautente.jsp?error=" + messaggioErrore);
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