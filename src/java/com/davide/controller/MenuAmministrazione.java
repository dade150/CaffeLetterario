/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.CategoriaDao;
import com.davide.dao.CiboDao;
import com.davide.model.Categoria;
import com.davide.model.Cibo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "MenuAmministrazione", urlPatterns = {"/MenuAmministrazione"})
public class MenuAmministrazione extends HttpServlet {

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

        CategoriaDao categoriaDao = new CategoriaDao();
        CiboDao ciboDao = new CiboDao();

        try {
            List<Categoria> categorie = categoriaDao.getAll();
            request.setAttribute("categorie", categorie);

            Categoria categoriaCorrente = null;
            Integer currentIndex = null;

            String categoriaIdParam = request.getParameter("categoriaId");

            if (categoriaIdParam != null && !categoriaIdParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(categoriaIdParam);
                    for (int i = 0; i < categorie.size(); i++) {
                        if (categorie.get(i).getId() == id) {
                            currentIndex = i;
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    // Gestisci l'errore
                    currentIndex = 0;
                }
            } else {
                currentIndex = 0; // Mostra la prima categoria per default
            }

            if (currentIndex != null && !categorie.isEmpty()) {
                categoriaCorrente = categorie.get(currentIndex);
                int nextIndex = (currentIndex + 1) % categorie.size(); // Passa alla categoria successiva (ciclicamente)
                request.setAttribute("nextCategoriaId", categorie.get(nextIndex).getId());
                List<Cibo> cibiPerCategoria = ciboDao.getByCategoriaId(categoriaCorrente.getId());
                request.setAttribute("cibiPerCategoria", cibiPerCategoria);
            }

            request.setAttribute("categoriaCorrente", categoriaCorrente);
            request.getServletContext().getRequestDispatcher("/menuamministrazione.jsp").forward(request, response);
        } catch (SQLException ex) {
            System.err.println("Errore nel recupero di cibi o categorie");
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
