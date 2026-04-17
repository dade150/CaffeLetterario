/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.CategoriaDao;
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
@WebServlet(name = "AggiungiCategoria", urlPatterns = {"/AggiungiCategoria"})
public class AggiungiCategoria extends HttpServlet {

    private CategoriaDao dao = new CategoriaDao();

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recupera il parametro 'nome' dal form
        String nomeCategoria = request.getParameter("nomeCategoria");

        // 2. Verifica che il nome non sia nullo o vuoto
        if (nomeCategoria != null && !nomeCategoria.trim().isEmpty()) {
            try {
                dao.addCategoria(nomeCategoria);
                response.sendRedirect("MenuAmministrazione"); // Esempio: reindirizza alla pagina del menu principale
            } catch (Exception e) {
                // 5. Gestisci eventuali errori
                e.printStackTrace();
                request.setAttribute("errore", "Impossibile aggiungere la categoria.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            // 6. Se il nome è vuoto, reindirizza indietro con un messaggio di errore
            request.setAttribute("errore", "Il nome della categoria non può essere vuoto.");
            request.getRequestDispatcher("Menu").forward(request, response);
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
