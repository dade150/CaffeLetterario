package com.davide.controller;

import com.davide.dao.LibroDao;
import com.davide.model.Libro;
import java.io.IOException;
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
@WebServlet(name = "VisualizzaLibri", urlPatterns = {"/VisualizzaLibri"})
public class VisualizzaLibri extends HttpServlet {

    LibroDao libroDao = new LibroDao();

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

        List<Libro> libri = libroDao.getAllInfoLibri().stream().filter(libro -> libro.getQuantita()>0).collect(Collectors.toList());

        request.setAttribute("libri", libri);
        request.getServletContext().getRequestDispatcher("/libreria.jsp").forward(request, response);

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
