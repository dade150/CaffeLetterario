/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
@WebServlet(name = "RicercaLibri", urlPatterns = {"/RicercaLibri"})
public class RicercaLibri extends HttpServlet {

    LibroDao libroDao = new LibroDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String titolo = request.getParameter("titolo");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");

        List<Libro> libri = libroDao.ricercaLibroByAutoreOrTitolo(nome, cognome, titolo).stream().filter(libro -> libro.getQuantita() > 0).collect(Collectors.toList());

        request.setAttribute("libri", libri);
        request.getServletContext().getRequestDispatcher("/libreria.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
