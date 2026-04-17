/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.davide.controller;

import com.davide.dao.AutoreDao;
import com.davide.dao.GenereDao;
import com.davide.dao.LibroDao;
import com.davide.model.Autore;
import com.davide.model.Genere;
import com.davide.model.Libro;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */
@WebServlet(name = "AggiungiLibroAmministrazione", urlPatterns = {"/AggiungiLibroAmministrazione"})
public class AggiungiLibroAmministrazione extends HttpServlet {

    private final LibroDao libroDao = new LibroDao();
    private final AutoreDao autoreDao = new AutoreDao();
    private final GenereDao genereDao = new GenereDao();

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

        String titolo = request.getParameter("titolo");
        String ISBN = request.getParameter("isbn");
        int quantita = Integer.parseInt(request.getParameter("quantita"));
        int anno = Integer.parseInt(request.getParameter("anno"));
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String sinossi = request.getParameter("sinossi");

        Libro libro = new Libro();
        libro.setAnno(anno);
        libro.setTitolo(titolo);
        libro.setISBN(ISBN);
        libro.setPrezzo(prezzo);
        libro.setQuantita(quantita);
        libro.setSinossi(sinossi);

        String nomeAutore = request.getParameter("autoreNome");
        String cognomeAutore = request.getParameter("autoreCognome");

        int idAutore = 0;

        Autore autor = autoreDao.getByNomeAndCognome(nomeAutore, cognomeAutore);

        if (autor == null) {
            autoreDao.addAutore(nomeAutore, cognomeAutore);
            autor = autoreDao.getByNomeAndCognome(nomeAutore, cognomeAutore);
        }

        String lista = request.getParameter("listaGeneri");

        List<Genere> generi = new ArrayList<>();

        if (lista != null) {
            String[] generiArray = lista.split(",");

            for (String s : generiArray) {

                Genere genere = genereDao.getByNome(s);

                if (genere == null) {
                    genereDao.addGenere(s);
                    genere = genereDao.getByNome(s);
                    genere.setId(genereDao.getByNome(s).getId());
                    genere.setNome(genereDao.getByNome(s).getNome());
                }
                generi.add(genere);
            }

        }

        try {
            libroDao.aggiungiLibro(libro, autor, generi);
        } catch (SQLException ex) {
            Logger.getLogger(AggiungiLibroAmministrazione.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getServletContext().getRequestDispatcher("/libreriaamministrazione.jsp").forward(request, response);

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
