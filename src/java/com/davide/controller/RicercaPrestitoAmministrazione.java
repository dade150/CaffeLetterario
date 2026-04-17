/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.LibroDao;
import com.davide.dao.UtenteDao;
import com.davide.model.Libro;
import com.davide.model.Utente;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */
@WebServlet(name = "RicercaPrestitoAmministrazione", urlPatterns = {"/RicercaPrestitoAmministrazione"})
public class RicercaPrestitoAmministrazione extends HttpServlet {

    private final UtenteDao utenteDao = new UtenteDao();
    private final LibroDao libroDao = new LibroDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parametri ricerca utente
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String dataNascitaStr = request.getParameter("dataNascita");

        // Parametri ricerca libro
        String titolo = request.getParameter("titolo");
        String autoreNome = request.getParameter("autoreNome");
        String autoreCognome = request.getParameter("autoreCognome");

        Date dataNascita = null;
        if (dataNascitaStr != null && !dataNascitaStr.isEmpty()) {
            try {
                LocalDate localDate = LocalDate.parse(dataNascitaStr);
                dataNascita = java.sql.Date.valueOf(localDate); // converte LocalDate → java.util.Date (via java.sql.Date)
            } catch (DateTimeParseException e) {
                request.setAttribute("erroreUtente", "Formato data di nascita non valido");
            }
        }

        List<Utente> utenti = utenteDao.ricercaUtenti(nome, cognome, null, null, null, dataNascita).stream().limit(5).collect(Collectors.toList());
        request.setAttribute("utenti", utenti);

        List<Libro> libri = libroDao.ricercaLibroByAutoreOrTitolo(autoreNome, autoreCognome, titolo).stream().limit(5).collect(Collectors.toList());;
        request.setAttribute("libri", libri);
        // Inoltro alla JSP
        request.getRequestDispatcher("aggiungiprestitoamministrazione.jsp").forward(request, response);
    }

}
