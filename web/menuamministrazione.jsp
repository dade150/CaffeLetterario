<%@page import="com.davide.model.Cibo"%>
<%@page import="com.davide.model.Categoria"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String ruoloUtente = (String) session.getAttribute("ruolo");
    if (ruoloUtente == null || !ruoloUtente.equals("Amministratore")) {
        response.sendRedirect("login.jsp?errore=autorizzazione");
        return;
    }
%>

<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8">
        <title>Gestione Categorie e Cibi</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            body {
                background-color: #f8f4e3;
                font-family: 'Georgia', serif;
            }

            .sfondo {
                background-color: #f6ae88;
                background-image: url('images/sfondo.png');
                background-repeat: no-repeat;
                background-position: center;
                background-size: auto;
                padding: 4rem 1rem;
                position: relative;
                text-align: center;
                color: #333;
            }

            .sfondo::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(255, 255, 255, 0.7);
                z-index: 0;
            }

            .sfondo > * {
                position: relative;
                z-index: 1;
            }

            .back-link {
                position: absolute;
                top: 20px;
                left: 20px;
                z-index: 2;
                font-size: 1.5rem;
                color: #49486f;
                text-decoration: none;
                transition: color 0.3s ease;
            }

            .back-link:hover {
                color: #2c2c47;
            }

            .form-section {
                background-color: #fff;
                border-radius: 12px;
                padding: 2rem;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                margin-bottom: 2rem;
                text-align: left;
            }

            .menu-item {
                background-color: #fefefe;
                padding: 1rem 1.5rem;
                border-radius: 0.5rem;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                margin-bottom: 1rem;
            }

            .menu-header {
                font-weight: bold;
                font-size: 1.1rem;
                display: flex;
                justify-content: space-between;
            }

            .menu-description {
                font-size: 0.95rem;
                color: #666;
                margin-top: 0.5rem;
            }

            .btn-space {
                margin-right: 0.5rem;
            }

            .hidden {
                display: none;
            }
        </style>
    </head>
    <body>

        <jsp:include page="util/navbar.jsp" />

        <div class="sfondo">
            <a href="administration.jsp" class="back-link" title="Torna a Admin Panel">
                <i class="fas fa-arrow-left"></i>
            </a>

            <div class="container">

                <% Categoria categoriaCorrente = (Categoria) request.getAttribute("categoriaCorrente"); %>
                <% Integer nextCategoriaId = (Integer) request.getAttribute("nextCategoriaId"); %>
                <% List<Cibo> cibiPerCategoria = (List<Cibo>) request.getAttribute("cibiPerCategoria");%>

                <div class="form-section text-center">
                    <h2 class="mb-3"><%= categoriaCorrente != null ? categoriaCorrente.getNome() : "Nessuna Categoria"%></h2>

                    <button class="btn btn-success btn-space" onclick="toggleForm('formAggiungiCategoria')">Aggiungi Categoria</button>

                    <% if (categoriaCorrente != null) {%>
                    <form action="EliminaCategoria" method="post" class="d-inline">
                        <input type="hidden" name="categoriaId" value="<%= categoriaCorrente.getId()%>">
                        <button type="submit" class="btn btn-danger">Elimina Categoria</button>
                    </form>
                    <% } %>
                </div>

                <!-- Form Aggiungi Categoria -->
                <div id="formAggiungiCategoria" class="form-section hidden">
                    <h4>Aggiungi Nuova Categoria</h4>
                    <form action="AggiungiCategoria" method="post">
                        <label>Nome:</label>
                        <input type="text" name="nomeCategoria" class="form-control mb-3" required>
                        <button type="submit" class="btn btn-success">Aggiungi</button>
                    </form>
                </div>

                <!-- Lista Cibi -->
                <% if (categoriaCorrente != null) { %>
                <% if (cibiPerCategoria != null && !cibiPerCategoria.isEmpty()) { %>
                <% for (Cibo cibo : cibiPerCategoria) {%>
                <div class="menu-item">
                    <div class="menu-header">
                        <span><%= cibo.getNome()%></span>
                        <span>€ <%= String.format("%.2f", cibo.getPrezzo())%></span>
                    </div>
                    <% if (cibo.getDescrizione() != null && !cibo.getDescrizione().isEmpty()) {%>
                    <div class="menu-description"><%= cibo.getDescrizione()%></div>
                    <% }%>
                    <form action="EliminaCibo" method="post" class="mt-2 text-end">
                        <input type="hidden" name="ciboId" value="<%= cibo.getId()%>">
                        <input type="hidden" name="categoriaId" value="<%= categoriaCorrente.getId()%>">
                        <button type="submit" class="btn btn-danger">Elimina</button>
                    </form>
                </div>
                <% } %>
                <% } else { %>
                <p class="text-muted text-center">Nessun cibo in questa categoria.</p>
                <% }%>

                <!-- Aggiungi Cibo -->
                <div class="text-center mb-4">
                    <button class="btn btn-primary" onclick="toggleForm('formAggiungiCibo')">Aggiungi Cibo</button>
                </div>

                <div id="formAggiungiCibo" class="form-section hidden">
                    <h4>Aggiungi Nuovo Cibo</h4>
                    <form action="AggiungiCibo" method="post">
                        <input type="hidden" name="categoriaId" value="<%= categoriaCorrente.getId()%>">
                        <label>Nome:</label>
                        <input type="text" name="nome" class="form-control mb-2" required>
                        <label>Prezzo (€):</label>
                        <input type="number" name="prezzo" step="0.01" class="form-control mb-2" required>
                        <label>Descrizione:</label>
                        <textarea name="descrizione" class="form-control mb-3"></textarea>
                        <button type="submit" class="btn btn-success">Aggiungi</button>
                    </form>
                </div>
                <% } %>

                <!-- Cambia Categoria -->
                <% if (nextCategoriaId != null) {%>
                <div class="text-center mt-4">
                    <form action="MenuAmministrazione" method="get">
                        <input type="hidden" name="categoriaId" value="<%= nextCategoriaId%>">
                        <button type="submit" class="btn btn-secondary">Cambia Categoria</button>
                    </form>
                </div>
                <% }%>
            </div>
        </div>

        <script>
            function toggleForm(id) {
                document.getElementById(id).classList.toggle("hidden");
            }
        </script>

    </body>
</html>
