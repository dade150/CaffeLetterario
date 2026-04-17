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
        <title>Prenotazioni</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            body {
                background-color: #f8f4e3;
                font-family: 'Georgia', serif;
            }

            .sfondo {
                background-color: #FFD180;
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
                background-color: rgba(255, 255, 255, 0.75);
                z-index: 0;
            }

            .sfondo > * {
                position: relative;
                z-index: 1;
            }

            .container {
                background-color: #f8f4e3;
                padding: 3rem;
                border-radius: 0.75rem;
                box-shadow: 0 2px 12px rgba(0,0,0,0.08);
                max-width: 1100px;
                margin: auto;
            }

            h2 {
                color: #49486f;
                font-weight: bold;
                font-size: 2.2rem;
                text-align: center;
                margin-bottom: 2rem;
            }

            .form-group {
                display: flex;
                align-items: center;
                margin-bottom: 1.5rem;
            }

            .form-group label {
                font-weight: bold;
                color: #49486f;
                width: 160px;
                font-size: 1.1rem;
                margin-right: 1rem;
            }

            .form-group input[type="date"] {
                flex: 1;
                padding: 0.7rem;
                border-radius: 0.5rem;
                border: 1px solid #ccc;
                font-size: 1.1rem;
            }

            .btn-primary {
                background-color: #49486f;
                border: none;
                font-weight: bold;
                padding: 0.6rem 1.5rem;
                font-size: 1.1rem;
                border-radius: 0.5rem;
                transition: background-color 0.3s;
                color: #fffdf5;
            }

            .btn-primary:hover {
                background-color: #333354;
            }

            .btn-success {
                font-size: 1rem;
                background-color: #28a745;
                border: none;
                padding: 0.6rem 1.2rem;
                border-radius: 0.5rem;
                color: #fffdf5;
                transition: background-color 0.3s;
            }

            .btn-success:hover {
                background-color: #1e7e34;
            }

            hr {
                margin: 2rem 0;
            }

            h3 {
                color: #49486f;
                text-align: center;
                font-size: 1.6rem;
                margin-bottom: 1.5rem;
            }

            .table {
                background-color: #fff;
                border-radius: 0.5rem;
                overflow: hidden;
                font-size: 1.05rem;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            }

            .table th {
                background-color: #f8f4e3;
                color: #49486f;
                font-weight: bold;
                padding: 1rem;
                border-bottom: 1px solid #ddd;
            }

            .table td {
                padding: 1rem;
                border-bottom: 1px solid #eee;
            }

            .table tr:last-child td {
                border-bottom: none;
            }

            .btn-action {
                background-color: #007bff;
                color: #fffdf5;
                border: none;
                padding: 0.5rem 1rem;
                border-radius: 0.4rem;
                font-size: 1rem;
                transition: background-color 0.3s;
                text-decoration: none;
            }

            .btn-action:hover {
                background-color: #0056b3;
            }

            .btn-action.annulla {
                background-color: #dc3545;
            }

            .btn-action.annulla:hover {
                background-color: #c82333;
            }

            .alert-info {
                text-align: center;
                font-size: 1.1rem;
                padding: 1rem 1.5rem;
                background-color: #e2e3e5;
                color: #383d41;
                border-radius: 0.5rem;
                margin-top: 2rem;
            }

            .top-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 2rem;
            }

            .top-bar a {
                text-decoration: none;
            }
        </style>
    </head>
    <body>

        <!-- NAVBAR -->
        <jsp:include page="util/navbar.jsp" />

        <div class="sfondo">
            <div class="container">
                <div class="top-bar">
                    <h2>Prenotazioni</h2>
                    <a href="prenotatavoloamministrazione.jsp" class="btn btn-success">
                        <i class="fas fa-plus-circle"></i> Aggiungi Prenotazione
                    </a>
                </div>

                <form method="get" action="VisualizzaPrenotazioni">
                    <div class="form-group">
                        <label for="dataPrenotazione">Seleziona il giorno:</label>
                        <input type="date" id="dataPrenotazione" name="data" value="${param.data != null ? param.data : today}">
                        <button type="submit" class="btn btn-primary">Cerca</button>
                    </div>
                </form>

                <hr>

                <c:if test="${not empty prenotazioni}">
                    <h3>Prenotazioni per il giorno: ${param.data != null ? param.data : today}</h3>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Ora Inizio</th>
                                <th>Ora Fine</th>
                                <th>Posti</th>
                                <th>Note</th>
                                <th>Telefono</th>
                                <th>Azioni</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="prenotazione" items="${prenotazioni}">
                                <tr>
                                    <td>${prenotazione.nomeIdentificativo}</td>
                                    <td>${prenotazione.oraDa}</td>
                                    <td>${prenotazione.oraA}</td>
                                    <td>${prenotazione.posti}</td>
                                    <td>${prenotazione.note}</td>
                                    <td>${prenotazione.telefono}</td>
                                    <td>
                                        <div style="display: flex; gap: 0.5rem;">
                                            <form method="post" action="EliminaPrenotazioneAmministrazione" style="margin: 0;">
                                                <input type="hidden" name="idPrenotazione" value="${prenotazione.id}">
                                                <button type="submit" class="btn-action annulla">Annulla</button>
                                            </form>
                                            <a href="ModificaPrenotazione?id=${prenotazione.id}" class="btn-action">Modifica</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>

                <c:if test="${empty prenotazioni}">
                    <div class="alert alert-info" role="alert">
                        Nessuna prenotazione trovata per il giorno selezionato.
                    </div>
                </c:if>
            </div>
        </div>

        <!-- FOOTER -->
        <jsp:include page="util/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
