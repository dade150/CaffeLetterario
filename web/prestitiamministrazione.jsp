<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String ruoloUtente = (String) session.getAttribute("ruolo");
    if (ruoloUtente == null || !ruoloUtente.equals("Amministratore")) {
        response.sendRedirect("login.jsp?errore=autorizzazione");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestione Prestiti Libri</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .sfondo {
                background-color: #f6ae88;
                background-image: url('images/sfondo.png');
                background-repeat: no-repeat;
                background-attachment: fixed;
                background-position: center;
                background-size: cover;
                position: relative;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
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

            .content-wrapper {
                flex-grow: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                position: relative;
                z-index: 1;
                padding: 2rem 1rem;
                width: 100%;
            }

            h1, h2 {
                color: #49486f;
            }

            .form-section, .results-section {
                background: #f9f9f9;
                border: 1px solid #ddd;
                padding: 25px;
                border-radius: 12px;
                margin-bottom: 30px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                width: 100%;
                max-width: 1100px;
            }

            .table-custom {
                background-color: #ffffff;
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }

            .table-custom thead {
                background-color: #343a40;
                color: white;
            }

            .restituisci-button {
                background-color: #dc3545;
                color: white;
                border: none;
                padding: 6px 14px;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="util/navbar.jsp" />

        <div class="sfondo">
            <div class="content-wrapper">
                <div class="form-section">
                    <h1>Ricerca Prestiti</h1>
                    <form method="get" action="VisualizzaPrestitiAmministrazione">
                        <div class="row g-3">
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="titolo" placeholder="Titolo libro">
                            </div>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="autoreNome" placeholder="Nome autore">
                            </div>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="autoreCognome" placeholder="Cognome autore">
                            </div>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="utenteNome" placeholder="Nome utente">
                            </div>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="utenteCognome" placeholder="Cognome utente">
                            </div>
                            <div class="col-md-4">
                                <input type="date" class="form-control" name="utenteDataNascita">
                            </div>
                        </div>
                        <div class="d-flex justify-content-between mt-4">
                            <button type="submit" class="btn btn-primary">Cerca</button>
                            <button type="button" class="btn btn-success" onclick="window.location.href = 'aggiungiprestitoamministrazione.jsp'">+ Aggiungi Prestito</button>
                        </div>
                    </form>
                </div>

                <div class="results-section">
                    <h2>Elenco Prestiti</h2>
                    <c:if test="${not empty prestiti}">
                        <div class="table-responsive">
                            <table class="table table-custom">
                                <thead>
                                    <tr>
                                        <th>Libro</th>
                                        <th>Autore</th>
                                        <th>Utente</th>
                                        <th>Data Prestito</th>
                                        <th>Data Restituzione</th>
                                        <th>Azioni</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="prestito" items="${prestiti}">
                                        <tr>
                                            <td>${prestito.libro.titolo}</td>
                                            <td>${prestito.libro.autore.nome} ${prestito.libro.autore.cognome}</td>
                                            <td>${prestito.utente.nome} ${prestito.utente.cognome}</td>
                                            <td>${prestito.dataDa}</td>
                                            <td>${empty prestito.dataA ? 'In prestito' : prestito.dataA}</td>
                                            <td>
                                                <c:if test="${prestito.restituito==false}">
                                                    <form method="post" action="RestituisciLibroAmministrazione" style="display:inline;">
                                                        <input type="hidden" name="prestitoId" value="${prestito.id}">
                                                        <button type="submit" class="restituisci-button">Restituisci</button>
                                                    </form>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                    <c:if test="${empty prestiti}">
                        <p class="text-center text-muted">Nessun prestito trovato</p>
                    </c:if>
                </div>
            </div>
        </div>

        <jsp:include page="util/footer.jsp" />
    </body>
</html>
