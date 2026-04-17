<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        <title>Aggiungi Prestito</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap & FontAwesome -->
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
                background-attachment: fixed;
                background-position: center;
                background-size: cover;
                padding: 4rem 1rem;
                position: relative;
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

            fieldset {
                border: 1px solid #ddd;
                padding: 1rem 1.5rem;
                border-radius: 8px;
            }
            legend {
                width: auto;
                padding: 0 0.5rem;
                font-weight: bold;
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
                    <h2 class="mb-4">Nuovo Prestito</h2>

                    <!-- Form di ricerca -->
                    <form method="get" action="RicercaPrestitoAmministrazione">

                        <!-- Ricerca Utente -->
                        <fieldset class="mb-4">
                            <legend>Ricerca Utente</legend>
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <input type="text" name="nome" class="form-control" placeholder="Nome">
                                </div>
                                <div class="col-md-4">
                                    <input type="text" name="cognome" class="form-control" placeholder="Cognome">
                                </div>
                                <div class="col-md-4">
                                    <input type="date" name="dataNascita" class="form-control" placeholder="Data di Nascita">
                                </div>
                            </div>
                        </fieldset>

                        <!-- Ricerca Libro -->
                        <fieldset class="mb-4">
                            <legend>Ricerca Libro</legend>
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <input type="text" name="titolo" class="form-control" placeholder="Titolo">
                                </div>
                                <div class="col-md-4">
                                    <input type="text" name="autoreNome" class="form-control" placeholder="Nome Autore">
                                </div>
                                <div class="col-md-4">
                                    <input type="text" name="autoreCognome" class="form-control" placeholder="Cognome Autore">
                                </div>
                            </div>
                        </fieldset>

                        <div class="text-center">
                            <button type="submit" class="btn btn-primary">Cerca</button>
                        </div>
                    </form>
                </div>

                <!-- Risultati Ricerca -->
                <c:if test="${not empty utenti}">
                    <div class="form-section">
                        <h3 class="mb-3">Seleziona Utente e Libro</h3>
                        <form method="post" action="AggiungiPrestitoAmministrazione">

                            <!-- Tabella Utenti -->
                            <table class="table table-hover mb-4">
                                <thead class="table-light">
                                    <tr>
                                        <th>Seleziona</th><th>Nome</th><th>Cognome</th><th>Data di Nascita</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="utente" items="${utenti}">
                                        <tr>
                                            <td>
                                                <input type="radio" name="utenteId" value="${utente.id}" required>
                                            </td>
                                            <td>${utente.nome}</td>
                                            <td>${utente.cognome}</td>
                                            <td>${utente.dataNascita}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <!-- Tabella Libri -->
                            <c:if test="${not empty libri}">
                                <table class="table table-hover mb-4">
                                    <thead class="table-light">
                                        <tr>
                                            <th>Seleziona</th><th>Titolo</th><th>Autore</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="libro" items="${libri}">
                                            <tr>
                                                <td>
                                                    <input type="radio" name="libroId" value="${libro.id}" required>
                                                </td>
                                                <td>${libro.titolo}</td>
                                                <td>${libro.autore.nome} ${libro.autore.cognome}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>

                            <!-- Dettagli Prestito -->
                            <%
                                java.time.LocalDate oggi = java.time.LocalDate.now();
                                java.time.LocalDate restituzione = oggi.plusMonths(1);
                            %>
                            <fieldset class="mb-4">
                                <legend>Dettagli Prestito</legend>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label>Data Prestito</label>
                                        <input type="date" name="dataPrestito" class="form-control" value="<%= oggi%>" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label>Data Restituzione Prevista</label>
                                        <input type="date" name="dataRestituzionePrevista"
                                               class="form-control"
                                               value="<%= restituzione%>" required>
                                    </div>
                                </div>
                            </fieldset>

                            <div class="text-center">
                                <button type="submit" class="btn btn-success">Conferma Prestito</button>
                            </div>

                        </form>
                    </div>
                </c:if>

            </div>
        </div>

        <jsp:include page="util/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
