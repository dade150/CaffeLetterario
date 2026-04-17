package com.davide.controller;

import com.davide.dao.PrenotazioneDao;
import com.davide.model.Prenotazione;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AggiungiPrenotazioneCliente", urlPatterns = {"/AggiungiPrenotazioneCliente"})
public class AggiungiPrenotazioneCliente extends HttpServlet {

    private final PrenotazioneDao prenotazioneDao = new PrenotazioneDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String dataStr = request.getParameter("data");       // yyyy-MM-dd
        String oraDaStr = request.getParameter("oraDa");     // HH:mm
        String oraAStr = request.getParameter("oraA");       // HH:mm
        String postiStr = request.getParameter("posti");
        String note = request.getParameter("note");

        try {
            // Parsing date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            Date data = dateFormat.parse(dataStr);
            Date oraDa = timeFormat.parse(oraDaStr);
            Date oraA = timeFormat.parse(oraAStr);
            int posti = Integer.parseInt(postiStr);

            // Aggiungi prenotazione
            Prenotazione prenotazione = prenotazioneDao.addPrenotazioneCliente(
                    username, password, data, oraDa, oraA, posti, note
            );

            request.setAttribute("prenotazione", prenotazione);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (ParseException e) {
            Logger.getLogger(AggiungiPrenotazioneCliente.class.getName()).log(Level.SEVERE, "Errore parsing data/ora", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato data o ora non valido.");
        } catch (SQLException e) {
            Logger.getLogger(AggiungiPrenotazioneCliente.class.getName()).log(Level.SEVERE, "Errore SQL", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel salvataggio della prenotazione.");
        } catch (Exception e) {
            Logger.getLogger(AggiungiPrenotazioneCliente.class.getName()).log(Level.SEVERE, "Errore generico", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Si è verificato un errore.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet per aggiungere una prenotazione da parte del cliente";
    }
}
