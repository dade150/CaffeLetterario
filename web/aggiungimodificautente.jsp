<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.davide.model.Utente"%>
<%
    Utente utente = (Utente) request.getAttribute("utente");
    boolean modifica = (utente != null);
%>
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
        <title><%= modifica ? "Modifica Utente" : "Nuovo Utente"%></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            body {
                margin: 0;
                font-family: 'Georgia', serif;
                background-color: #f8f4e3;
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
                min-height: 100vh;
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

            .form-container {
                position: relative;
                z-index: 1;
                max-width: 600px;
                margin: auto;
                padding: 2rem;
                background-color: #ffffffd9;
                border-radius: 12px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }

            h2 {
                text-align: center;
                color: #49486f;
                margin-bottom: 20px;
            }

            label {
                font-weight: bold;
                color: #333;
            }

            input[type="text"],
            input[type="email"],
            input[type="date"],
            input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
            }

            button[type="submit"] {
                background-color: #49486f;
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 6px;
                font-weight: bold;
                width: 100%;
                transition: background-color 0.3s;
            }

            button[type="submit"]:hover {
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
            }

            .back-link:hover {
                color: #2c2c47;
            }
        </style>
    </head>
    <body>
        <jsp:include page="util/navbar.jsp" />

        <!-- Freccia indietro -->


        <div class="sfondo">
            <a href="utenti.jsp" class="back-link" title="Torna a Admin Panel">
                <i class="fas fa-arrow-left"></i>
            </a>
            <div class="form-container">
                <h2><%= modifica ? "Modifica Utente" : "Aggiungi Utente"%></h2>
                <form action="${pageContext.request.contextPath}/AggiungiModificaUtente" method="post">
                    <% if (modifica) {%>
                    <input type="hidden" name="id" value="<%= utente.getId()%>">
                    <% }%>

                    <label>Nome:</label>
                    <input type="text" name="nome" value="<%= modifica ? utente.getNome() : ""%>">

                    <label>Cognome:</label>
                    <input type="text" name="cognome" value="<%= modifica ? utente.getCognome() : ""%>">

                    <label>Email:</label>
                    <input type="email" name="email" value="<%= modifica ? utente.getEmail() : ""%>">

                    <label>Telefono:</label>
                    <input type="text" name="telefono" value="<%= modifica ? utente.getTelefono() : ""%>">

                    <label>Data di Nascita:</label>
                    <input type="date" name="dataNascita" value="<%= modifica ? utente.getDataNascita() : ""%>">

                    <label>Username:</label>
                    <input type="text" name="username" value="<%= modifica ? utente.getUsername() : ""%>">

                    <label>Password:</label>
                    <input type="password" name="password" value="<%= modifica ? utente.getPassword() : ""%>">

                    <button type="submit"><%= modifica ? "Aggiorna" : "Aggiungi"%></button>
                </form>
            </div>
        </div>
        <jsp:include page="util/footer.jsp" />

    </body>
</html>
