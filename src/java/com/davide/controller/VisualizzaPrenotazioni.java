/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.PrenotazioneDao;
import com.davide.model.Prenotazione;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */
@WebServlet(name = "VisualizzaPrenotazioni", urlPatterns = {"/VisualizzaPrenotazioni"})
public class VisualizzaPrenotazioni extends HttpServlet {

    private final PrenotazioneDao prenotazioneDao = new PrenotazioneDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dataStr = request.getParameter("data"); // formato atteso: yyyy-MM-dd
        Date data;

        if (dataStr != null && !dataStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                data = sdf.parse(dataStr);
            } catch (ParseException e) {
                data = new Date(); // fallback a oggi se parsing fallisce
            }
        } else {
            data = new Date(); // default: oggi
        }

        try {
            List<Prenotazione> prenotazioni = prenotazioneDao.visualizzaPrenotazioniGiorno(data);
                        
            request.setAttribute("prenotazioni", prenotazioni);
            request.setAttribute("dataVisualizzata", data);

            request.getRequestDispatcher("prenotazioniamministrazione.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
