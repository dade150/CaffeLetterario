<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <title>Prenotazione Tavolo</title>
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

            .form-section {
                background-color: #fff;
                border-radius: 12px;
                padding: 2rem;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                margin-bottom: 2rem;
            }

            .section-title {
                font-size: 2rem;
                color: #49486f;
                font-weight: bold;
                margin-bottom: 1.5rem;
                text-align: center;
            }

            .form-label {
                font-weight: bold;
                color: #49486f;
            }

            .form-control {
                border-radius: 0.5rem;
            }

            .btn-primary {
                background-color: #49486f;
                border: none;
                font-weight: bold;
                transition: background-color 0.3s;
            }

            .btn-primary:hover {
                background-color: #333354;
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

            .form-control::placeholder {
                font-style: italic;
                color: #aaa;
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
                    <h2 class="section-title">Prenotazione Tavolo</h2>

                    <form action="PrenotaTavoloAmministrazione" method="post" class="row g-3">
                        <div class="col-md-6">
                            <label for="nome" class="form-label">Nome Identificativo</label>
                            <input type="text" class="form-control" name="nome" id="nome" required>
                        </div>

                        <div class="col-md-6">
                            <label for="telefono" class="form-label">Telefono</label>
                            <input type="tel" class="form-control" name="telefono" id="telefono"
                                   required pattern="[0-9]{10}" placeholder="Es: 3331234567">
                        </div>

                        <div class="col-md-4">
                            <label for="posti" class="form-label">Numero di Posti</label>
                            <input type="number" class="form-control" name="posti" id="posti" min="1" required>
                        </div>

                        <div class="col-md-4">
                            <label for="giorno" class="form-label">Giorno</label>
                            <input type="date" class="form-control" name="data" id="data" required>
                        </div>

                        <div class="col-md-2">
                            <label for="oraInizio" class="form-label">Ora Inizio</label>
                            <input type="time" class="form-control" name="oraDa" id="oraDa" required>
                        </div>

                        <div class="col-md-2">
                            <label for="oraFine" class="form-label">Ora Fine</label>
                            <input type="time" class="form-control" name="oraA" id="oraA" required>
                        </div>

                        <div class="col-12">
                            <label for="note" class="form-label">Note (opzionale)</label>
                            <textarea class="form-control" name="note" id="note" rows="3"
                                      placeholder="Inserisci eventuali note o richieste..."></textarea>
                        </div>

                        <div class="col-12 text-center">
                            <button type="submit" class="btn btn-primary px-5">Prenota</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <jsp:include page="util/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
