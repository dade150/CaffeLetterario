package com.davide.controller;

import com.davide.dao.CiboDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */
@WebServlet(name = "EliminaCibo", urlPatterns = {"/EliminaCibo"})
public class EliminaCibo extends HttpServlet {

    private CiboDao dao = new CiboDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ciboIdStr = request.getParameter("ciboId");
        String categoriaIdStr = request.getParameter("categoriaId");
        int categoriaId = Integer.parseInt(categoriaIdStr);
        
        if (ciboIdStr != null && !ciboIdStr.trim().isEmpty()) {
            try {
                int ciboId = Integer.parseInt(ciboIdStr);

                dao.deleteCibo(ciboId);
                response.sendRedirect("MenuAmministrazione?categoriaId=" + categoriaId); // Reindirizza alla stessa categoria

            } catch (NumberFormatException e) {
                request.setAttribute("errore", "ID cibo non valido.");
                request.getRequestDispatcher("MenuAmministrazione?categoriaId=" + categoriaId).forward(request, response);
            } catch (Exception e) {
                // Gestisci altri errori
                e.printStackTrace();
                request.setAttribute("errore", "Errore durante l'eliminazione del cibo.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            // Se l'ID del cibo non è stato fornito
            request.setAttribute("errore", "ID cibo non fornito.");
            request.getRequestDispatcher("MenuAmministrazione?categoriaId=" + categoriaIdStr).forward(request, response);
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
