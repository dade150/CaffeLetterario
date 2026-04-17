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
@WebServlet(name = "EliminaCategoria", urlPatterns = {"/EliminaCategoria"})
public class EliminaCategoria extends HttpServlet {

    CategoriaDao dao = new CategoriaDao();

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
        String categoriaIdStr = request.getParameter("categoriaId");

        // 2. Verifica che l'ID non sia nullo o vuoto e che sia un numero valido
        if (categoriaIdStr != null && !categoriaIdStr.trim().isEmpty()) {
            try {
                int categoriaId = Integer.parseInt(categoriaIdStr);

                dao.deleteCategoria(categoriaId); 
                response.sendRedirect("MenuAmministrazione?categoriaId=" + categoriaId); // Reindirizza alla stessa categoria

            } catch (Exception e) {
                // Gestisci altri errori (es. problemi di database)
                e.printStackTrace();
                request.setAttribute("errore", "Errore durante l'eliminazione della categoria.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            // Se l'ID non è stato fornito
            request.setAttribute("errore", "ID categoria non fornito.");
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
    }

}
