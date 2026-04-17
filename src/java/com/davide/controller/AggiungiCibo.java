/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.CiboDao;
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
@WebServlet(name = "AggiungiCibo", urlPatterns = {"/AggiungiCibo"})
public class AggiungiCibo extends HttpServlet {

    private CiboDao dao = new CiboDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Recupera i parametri dal form
        String nomeCibo = request.getParameter("nome");
        String prezzoCiboStr = request.getParameter("prezzo");
        String descrizioneCibo = request.getParameter("descrizione");
        String categoriaIdStr = request.getParameter("categoriaId");

        // 2. Verifica che i parametri obbligatori non siano nulli o vuoti
        if (nomeCibo != null && !nomeCibo.trim().isEmpty()
                && prezzoCiboStr != null && !prezzoCiboStr.trim().isEmpty()
                && categoriaIdStr != null && !categoriaIdStr.trim().isEmpty()) {
            try {
                double prezzoCibo = Double.parseDouble(prezzoCiboStr);
                int categoriaId = Integer.parseInt(categoriaIdStr);

                // 3. Chiama il metodo addCibo del tuo DAO passando i parametri direttamente
                dao.addCibo(nomeCibo, descrizioneCibo, prezzoCibo, categoriaId); // Assumi che questo metodo esista

                // 4. Reindirizza l'utente alla pagina del menu (o a un'altra pagina appropriata)
                response.sendRedirect("MenuAmministrazione?categoriaId=" + categoriaId); // Reindirizza alla stessa categoria

            } catch (NumberFormatException e) {
                // Errore se il prezzo o l'ID categoria non sono numeri validi
                request.setAttribute("errore", "Prezzo o ID categoria non validi.");
                request.getRequestDispatcher("Menu2?categoriaId=" + categoriaIdStr).forward(request, response);
            } catch (Exception e) {
                // Gestisci altri errori
                e.printStackTrace();
                request.setAttribute("errore", "Impossibile aggiungere il cibo.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            // Se i parametri obbligatori non sono stati forniti
            request.setAttribute("errore", "Nome, prezzo e categoria sono obbligatori.");
            request.getRequestDispatcher("Menu2?categoriaId=" + categoriaIdStr).forward(request, response);
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
