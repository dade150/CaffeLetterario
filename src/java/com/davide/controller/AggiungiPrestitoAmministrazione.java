package com.davide.controller;

import com.davide.dao.PrestitoDao;
import com.davide.model.Prestito;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */
@WebServlet(name = "AggiungiPrestitoAmministrazione", urlPatterns = {"/AggiungiPrestitoAmministrazione"})
public class AggiungiPrestitoAmministrazione extends HttpServlet {

    private final PrestitoDao prestitoDao = new PrestitoDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String utenteIdStr = request.getParameter("utenteId");
        String libroIdStr = request.getParameter("libroId");
        String dataPrestitoStr = request.getParameter("dataPrestito");
        String dataRestituzioneStr = request.getParameter("dataRestituzionePrevista");

        try {
            // 🔹 Converto gli input
            int utenteId = Integer.parseInt(utenteIdStr);
            int libroId = Integer.parseInt(libroIdStr);

            Date dataPrestito = java.sql.Date.valueOf(LocalDate.parse(dataPrestitoStr));
            Date dataRestituzione = java.sql.Date.valueOf(LocalDate.parse(dataRestituzioneStr));

            // 🔹 Salvo il prestito
            prestitoDao.addPrestito(libroId, utenteId, dataPrestito, dataRestituzione);

            // 🔹 Reindirizzo alla pagina di conferma
            response.sendRedirect("prestitiamministrazione.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            
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
