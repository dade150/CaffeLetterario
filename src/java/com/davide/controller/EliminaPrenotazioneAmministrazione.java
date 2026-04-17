/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.PrenotazioneDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */
@WebServlet(name = "EliminaPrenotazioneAmministrazione", urlPatterns = {"/EliminaPrenotazioneAmministrazione"})
public class EliminaPrenotazioneAmministrazione extends HttpServlet {

    private PrenotazioneDao prenotazioneDao = new PrenotazioneDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idPrenotazioneStr = request.getParameter("idPrenotazione");

        if (idPrenotazioneStr != null && !idPrenotazioneStr.isEmpty()) {
            try {
                int idPrenotazione = Integer.parseInt(idPrenotazioneStr);
                
                prenotazioneDao.deletePrenotazione(idPrenotazione);

                request.getRequestDispatcher("/prenotazioniamministrazione.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                request.setAttribute("errore", "ID della prenotazione non valido.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errore", "ID della prenotazione mancante.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

    }
}
