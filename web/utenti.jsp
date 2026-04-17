<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.davide.model.Utente"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
        <title>Gestione Utenti</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            body {
                background-color: #f8f4e3;
                font-family: 'Georgia', serif;
                margin: 0;
                padding: 0;
                min-height: 100vh;
            }

            .sfondo {
                background-color: #f6ae88;
                background-image: url('images/sfondo.png');
                background-repeat: no-repeat;
                background-attachment: fixed;
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

            .form-section {
                background-color: #f9f9f9;
                border-radius: 12px;
                padding: 30px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                margin-bottom: 3rem;
            }

            label {
                font-weight: bold;
                color: #535353;
            }

            .search-button {
                background-color: #49486f;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 6px;
                font-weight: bold;
                transition: background-color 0.3s;
            }

            .search-button:hover {
                background-color: #333354;
            }

            .edit-button {
                background-color: #4CAF50;
                color: white;
                padding: 6px 12px;
                border: none;
                border-radius: 5px;
            }

            .edit-button:hover {
                background-color: #45a049;
            }

            .delete-button {
                background-color: #f44336;
                color: white;
                padding: 6px 12px;
                border: none;
                border-radius: 5px;
            }

            .delete-button:hover {
                background-color: #d32f2f;
            }

            .back-link {
                position: absolute;
                top: 20px;
                left: 20px;
                z-index: 2;
                font-size: 1.5rem;
                color: #49486f;
                text-decoration: none;
                font-weight: normal;
                transition: color 0.3s ease;
            }

            .back-link:hover {
                color: #2c2c47;
            }

            .back-link i {
                margin-right: 0; /* rimuovo spazio a destra perché nella prima pagina la freccia è senza testo */
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

                <div class="form-section">
                    <h2 class="mb-4" style="color: #535353;">Ricerca Utenti</h2>
                    <form action="${pageContext.request.contextPath}/RicercaEliminaUtenti" method="get">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="nome">Nome:</label>
                                <input type="text" id="nome" name="nome" class="form-control">
                            </div>
                            <div class="col-md-6">
                                <label for="cognome">Cognome:</label>
                                <input type="text" id="cognome" name="cognome" class="form-control">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="telefono">Telefono:</label>
                                <input type="text" id="telefono" name="telefono" class="form-control">
                            </div>
                            <div class="col-md-6">
                                <label for="dataNascita">Data di Nascita:</label>
                                <input type="date" id="dataNascita" name="dataNascita" class="form-control">
                            </div>
                        </div>
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <label for="email">Email:</label>
                                <input type="email" id="email" name="email" class="form-control">
                            </div>
                            <div class="col-md-6">
                                <label for="username">Username:</label>
                                <input type="text" id="username" name="username" class="form-control">
                            </div>
                        </div>
                        <div class="d-flex justify-content-center gap-4 mt-4">
                            <button type="submit" class="search-button">Cerca</button>
                            <a href="${pageContext.request.contextPath}/AggiungiModificaUtente"
                               class="btn btn-success fw-bold px-4 py-2 rounded">Aggiungi Utente</a>
                        </div>
                    </form>
                </div>

                <div class="form-section">
                    <h2 class="mb-4" style="color: #535353;">Elenco Utenti</h2>
                    <%
                        List<Utente> utenti = (List<Utente>) request.getAttribute("utenti");
                        if (utenti == null || utenti.isEmpty()) {
                    %>
                    <p class="text-center">Nessun utente trovato.</p>
                    <%
                    } else {
                    %>
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped align-middle text-center">
                            <thead class="table-dark">
                                <tr>
                                    <th>Nome</th>
                                    <th>Cognome</th>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Telefono</th>
                                    <th>Data Nascita</th>
                                    <th>Ruolo</th>
                                    <th>Azioni</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (Utente utente : utenti) {
                                %>
                                <tr>
                                    <td><%= utente.getNome()%></td>
                                    <td><%= utente.getCognome()%></td>
                                    <td><%= utente.getUsername()%></td>
                                    <td><%= utente.getEmail()%></td>
                                    <td><%= utente.getTelefono()%></td>
                                    <td><%= utente.getDataNascita()%></td>
                                    <td><%= utente.getRuolo().getNome()%></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/AggiungiModificaUtente" method="get" style="display:inline;">
                                            <input type="hidden" name="id" value="<%= utente.getId()%>">
                                            <button type="submit" class="edit-button">Modifica</button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/RicercaEliminaUtenti" method="post" style="display:inline;" onsubmit="return confirm('Sei sicuro di voler eliminare questo utente?');">
                                            <input type="hidden" name="azione" value="elimina">
                                            <input type="hidden" name="id" value="<%= utente.getId()%>">
                                            <button type="submit" class="delete-button">Cancella</button>
                                        </form>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                    <%
                        }
                    %>
                </div>

            </div>
        </div>

        <jsp:include page="util/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
