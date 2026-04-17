<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
        <title>Amministrazione - Apollo & Dafne</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap e Font Awesome -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: 'Georgia', serif;
                background-color: #f8f4e3;
            }



            .sfondo::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(255, 255, 255, 0.7); /* Velo trasparente */
                z-index: 0;
            }

            .content-wrapper {
                flex-grow: 1;
                display: flex;
                justify-content: center;
                align-items: flex-start;
                position: relative;
                z-index: 1;
                padding: 2rem 1rem;
            }

            .sfondo {
                background-color: #f6ae88;
                background-image: url('images/sfondo.png');
                background-repeat: no-repeat;
                background-attachment: fixed;
                background-position: center;
                background-size: cover;
                position: relative;
                padding-bottom: 1rem; /* limita lo spazio sotto */
                /* min-height: 100vh;  <-- opzionale: rimuovilo se vuoi solo altezza contenuto */
            }

            .container {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                width: 100%;
                max-width: 1100px;
            }

            .menu-button {
                background-color: #FFD180;
                border: none;
                border-radius: 10px;
                padding: 20px;
                margin: 10px;
                width: 40%;
                min-width: 250px;
                height: 150px;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                text-align: center;
                cursor: pointer;
                transition: background-color 0.3s ease;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .menu-button:hover {
                background-color: #f7c161;
            }

            .button-title {
                font-size: 1.5em;
                font-weight: bold;
                color: #49486f;
                margin-bottom: 10px;
            }

            .button-description {
                font-size: 1em;
                color: #666;
            }

            @media (max-width: 768px) {
                .menu-button {
                    width: 80%;
                }
            }
        </style>
    </head>
    <body>

        <!-- NAVBAR -->
        <jsp:include page="util/navbar.jsp" />

        <!-- CONTENUTO -->
        <div class="sfondo">
            <div class="content-wrapper">
                <div class="container">
                    <button class="menu-button" onclick="window.location.href = 'utenti.jsp'">
                        <span class="button-title">Gestione Utenti</span>
                        <span class="button-description">Aggiungi, modifica, e rimuovi utenti del sistema</span>
                    </button>

                    <button class="menu-button" onclick="window.location.href = 'libreriaamministrazione.jsp'">
                        <span class="button-title">Gestione Libreria</span>
                        <span class="button-description">Cataloga, aggiungi e rimuovi libri.</span>
                    </button>

                    <button class="menu-button" onclick="window.location.href = 'MenuAmministrazione'">
                        <span class="button-title">Gestione Menu</span>
                        <span class="button-description">Configura le voci e le opzioni del menu principale</span>
                    </button>

                    <button class="menu-button" onclick="window.location.href = 'VisualizzaPrestitiAmministrazione'">
                        <span class="button-title">Gestione Prestiti</span>
                        <span class="button-description">Inserisci, modifica e visualizza i prestiti della libreria</span>
                    </button>

                    <button class="menu-button" onclick="window.location.href = 'prenotazioniamministrazione.jsp'">
                        <span class="button-title">Gestione Prenotazioni</span>
                        <span class="button-description">Gestisci le prenotazioni tavoli del locale</span>
                    </button>
                </div>
            </div>
        </div>

        <!-- FOOTER -->
        <jsp:include page="util/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
