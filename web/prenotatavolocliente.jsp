<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8">
        <title>Area Clienti</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <style>
            body {
                background-color: #fffdf5;
                font-family: 'Georgia', serif;
                margin: 0;
                padding: 0;
            }

            .sfondo {
                background-color: #FFD180;
                background-image: url('images/sfondo.png');
                background-repeat: no-repeat;
                background-position: center;
                background-size: cover;
                padding: 4rem 1rem;
                position: relative;
                color: #49486f;
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

            .form-container {
                margin-top: 50px;
                margin-bottom: 50px;
            }

            .section-title {
                text-align: center;
                font-size: 2.2rem;
                color: #49486f;
                margin-bottom: 2.5rem;
                font-weight: bold;
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

            .recensioni-container {
                background-color: rgba(255, 255, 255, 0.9);
                padding: 2rem;
                border-radius: 1rem;
                box-shadow: 0 2px 12px rgba(0,0,0,0.1);
                margin-top: 3rem;
            }

            .recensione {
                margin-bottom: 1.5rem;
                border-left: 4px solid #49486f;
                padding-left: 1rem;
            }

            .recensione strong {
                display: block;
                font-size: 1.1rem;
                margin-bottom: 0.3rem;
            }

            .recensione small {
                color: #666;
                font-style: italic;
            }

            .scroll-area {
                max-height: 300px;
                overflow-y: auto;
            }

            .form-control::placeholder {
                font-style: italic;
                color: #aaa;
            }
        </style>
    </head>
    <body>

        <!-- NAVBAR -->
        <jsp:include page="util/navbar.jsp" />

        <!-- AREA CLIENTI -->
        <div class="sfondo">
            <div class="container form-container">
                <!-- Prenotazione -->
                <h2 class="section-title">Prenotazione Tavolo</h2>
                <form action="AggiungiPrenotazioneCliente" method="post" class="row g-3">
                    <div class="col-md-6">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" name="username" id="username" required>
                    </div>

                    <div class="col-md-6">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" name="password" id="password" required>
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
                        <textarea class="form-control" name="note" id="note" rows="3" placeholder="Inserisci eventuali note o richieste..."></textarea>
                    </div>

                    <div class="col-12">
                        <button type="submit" class="btn btn-primary w-100">Prenota</button>
                    </div>
                </form>

                <!-- Recensione -->
                <hr class="my-5">
                <h2 class="section-title">Lascia una Recensione</h2>
                <form action="VisualizzaRecensione" method="post" class="row g-3">
                    <div class="col-md-6">
                        <label for="usernameRec" class="form-label">Username</label>
                        <input type="text" class="form-control" name="usernameRec" id="usernameRec" required>
                    </div>

                    <div class="col-md-6">
                        <label for="passwordRec" class="form-label">Password</label>
                        <input type="password" class="form-control" name="passwordRec" id="passwordRec" required>
                    </div>

                    <div class="col-12">
                        <label for="testo" class="form-label">Testo Recensione</label>
                        <textarea class="form-control" name="testo" id="testo" rows="4" placeholder="Scrivi la tua esperienza..." required></textarea>
                    </div>

                    <div class="col-md-4">
                        <label for="valutazione" class="form-label">Valutazione</label>
                        <select class="form-control" name="valutazione" id="valutazione" required>
                            <option value="">Seleziona...</option>
                            <option value="5">5</option>
                            <option value="4">4</option>
                            <option value="3">3</option>
                            <option value="2">2</option>
                            <option value="1">1</option>
                        </select>
                    </div>

                    <div class="col-12">
                        <button type="submit" class="btn btn-primary w-100">Invia Recensione</button>
                    </div>
                </form>

                <!-- Recensioni esistenti -->
                <div class="recensioni-container mt-5">
                    <h2 class="section-title">Cosa dicono i nostri clienti</h2>
                    <div class="scroll-area">
                        <c:forEach var="recensione" items="${recensioni}">
                            <div class="recensione">
                                <strong>${recensione.utente.nome}</strong>
                                <small>Valutazione: ${recensione.valutazione} / 5</small>
                                <p>${recensione.testo}</p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <!-- FOOTER -->
        <jsp:include page="util/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
