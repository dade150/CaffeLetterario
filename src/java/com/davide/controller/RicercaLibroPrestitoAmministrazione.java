package com.davide.controller;

import com.davide.dao.LibroDao;
import com.davide.model.Libro;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "RicercaLibroPrestitoAmministrazione", urlPatterns = {"/RicercaLibroPrestitoAmministrazione"})
public class RicercaLibroPrestitoAmministrazione extends HttpServlet {

    private final LibroDao libroDao = new LibroDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ Recupero dei parametri della richiesta
        String titolo = request.getParameter("titolo");
        String autoreNome = request.getParameter("autoreNome");
        String autoreCognome = request.getParameter("autoreCognome");

        // 2️⃣ Esegui la ricerca nel database
        List<Libro> libri = libroDao.ricercaLibroByAutoreOrTitolo(titolo, autoreCognome, autoreNome).stream().limit(5).collect(Collectors.toList());

        // 3️⃣ Passa i risultati alla pagina JSP
        request.setAttribute("libri", libri);
                request.getRequestDispatcher("prestitiamministrazione.jsp").forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
