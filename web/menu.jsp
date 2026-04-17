<%@page import="com.davide.model.Cibo"%>
<%@page import="java.util.List"%>
<%@page import="com.davide.model.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Menu - Apollo & Dafne</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
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
            text-align: center;
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

        h1 {
            font-size: 2.5rem;
            font-weight: bold;
            color: #49486f;
            margin-bottom: 2rem;
        }

        .menu-item {
            background-color: #f8f4e3;
            color: #49486f;
            margin: 0.5rem auto;
            padding: 1rem 1.5rem;
            border-radius: 0.5rem;
            max-width: 500px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            text-align: left;
        }

        .menu-header {
            display: flex;
            justify-content: space-between;
            font-size: 1.2rem;
            font-weight: bold;
        }

        .menu-description {
            margin-top: 0.5rem;
            font-size: 0.95rem;
            color: #555;
        }

        .menu-btn {
            background-color: #49486f;
            color: #fffdf5;
            padding: 0.6rem 1.2rem;
            border: none;
            border-radius: 0.4rem;
            margin-top: 2rem;
            font-weight: bold;
            transition: background-color 0.3s ease-in-out;
        }

        .menu-btn:hover {
            background-color: #333354;
        }

        p {
            color: #666;
            font-size: 1.1rem;
        }
    </style>
</head>

<body>
    <!-- NAVBAR -->
    <jsp:include page="util/navbar.jsp" />

    <!-- CONTENUTO PRINCIPALE -->
    <div class="sfondo">
        <% Categoria categoriaCorrente = (Categoria) request.getAttribute("categoriaCorrente"); %>
        <% Integer nextCategoriaId = (Integer) request.getAttribute("nextCategoriaId"); %>
        <% List<Cibo> cibiPerCategoria = (List<Cibo>) request.getAttribute("cibiPerCategoria"); %>

        <h1><%= categoriaCorrente != null ? categoriaCorrente.getNome() : "Nessuna Categoria Disponibile" %></h1>

        <% if (cibiPerCategoria != null && !cibiPerCategoria.isEmpty()) { %>
            <% for (Cibo cibo : cibiPerCategoria) { %>
                <div class="menu-item">
                    <div class="menu-header">
                        <span><%= cibo.getNome() %></span>
                        <span>€ <%= String.format("%.2f", cibo.getPrezzo()) %></span>
                    </div>
                    <% if (cibo.getDescrizione() != null && !cibo.getDescrizione().trim().isEmpty()) { %>
                        <div class="menu-description">
                            <%= cibo.getDescrizione() %>
                        </div>
                    <% } %>
                </div>
            <% } %>
        <% } else if (categoriaCorrente != null) { %>
            <p>Non ci sono cibi disponibili in questa categoria.</p>
        <% } %>

        <% if (nextCategoriaId != null) { %>
            <form action="Menu2" method="get">
                <input type="hidden" name="categoriaId" value="<%= nextCategoriaId %>">
                <button type="submit" class="menu-btn">Cambia Categoria</button>
            </form>
        <% } %>
    </div>

    <!-- FOOTER -->
    <jsp:include page="util/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>