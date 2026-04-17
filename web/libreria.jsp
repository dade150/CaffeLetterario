<%@page import="com.davide.model.Genere"%>
<%@page import="java.util.List"%>
<%@page import="com.davide.model.Libro"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Libreria - Apollo & Dafne</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        /* Corpo della pagina */
        body {
            background-color: #fffdf5;
            font-family: 'Georgia', serif;
            margin: 0;
            padding: 0;
        }

        /* Sfondo */
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

        .section-title {
            text-align: center;
            font-size: 2.2rem;
            color: #49486f;
            margin-bottom: 2.5rem;
            font-weight: bold;
        }

        /* Card per libro */
        .book-card {
            background-color: #fffdf5;
            border-radius: 0.6rem;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            transition: transform 0.2s ease-in-out;
            height: 100%;
            padding: 1.2rem;
            text-align: left;
            border-left: 6px solid #49486f;
        }

        .book-card:hover {
            transform: translateY(-4px);
        }

        .book-title {
            font-size: 1.2rem;
            color: #49486f;
            font-weight: bold;
        }

        .book-author {
            font-size: 0.95rem;
            color: #666;
        }

        .book-genres {
            margin: 0.8rem 0;
        }

        .book-price {
            margin-top: auto;
            color: #b88c4a;
            font-weight: bold;
        }

        .form-control::placeholder {
            font-style: italic;
            color: #aaa;
        }

        .btn-warning {
            background-color: #49486f !important;
            border: none;
        }

        .btn-warning:hover {
            background-color: #333354 !important;
        }
    </style>
</head>
<body>

    <!-- NAVBAR -->
    <jsp:include page="util/navbar.jsp" />

    <!-- CONTENUTO -->
    <div class="sfondo">
        <h2 class="section-title">I Libri del Caffè Letterario</h2>

        <!-- FORM DI RICERCA -->
        <form class="row g-3 justify-content-center mb-5" method="get" action="RicercaLibri">
            <div class="col-md-3">
                <input type="text" name="titolo" class="form-control" placeholder="Titolo del libro" value="<%= request.getParameter("titolo") != null ? request.getParameter("titolo") : ""%>">
            </div>
            <div class="col-md-3">
                <input type="text" name="nome" class="form-control" placeholder="Nome autore" value="<%= request.getParameter("nome") != null ? request.getParameter("nome") : ""%>">
            </div>
            <div class="col-md-3">
                <input type="text" name="cognome" class="form-control" placeholder="Cognome autore" value="<%= request.getParameter("cognome") != null ? request.getParameter("cognome") : ""%>">
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-warning w-100 text-white">Cerca</button>
            </div>
        </form>

        <!-- LIBRI -->
        <div class="row g-4 justify-content-center">
            <% List<Libro> libri = (List<Libro>) request.getAttribute("libri"); %>
            <% if (libri != null && !libri.isEmpty()) {
                for (Libro libro : libri) { %>
                <div class="col-sm-10 col-md-6 col-lg-4">
                    <div class="book-card d-flex flex-column">
                        <div class="book-title"><%= libro.getTitolo() %></div>
                        <div class="book-author">
                            <%= libro.getAutore().getNome() %> <%= libro.getAutore().getCognome() %>
                        </div>
                        <div class="book-genres">
                            <% for (Genere genere : libro.getGeneri()) { %>
                                <span class="badge me-1"><%= genere.getNome() %></span>
                            <% } %>
                        </div>
                        <div class="book-price">EUR <%= libro.getPrezzo() %></div>
                    </div>
                </div>
            <%  }
            } else if (libri != null) { %>
                <p class="text-center">Nessun libro disponibile</p>
            <% } %>
        </div>
    </div>

    <!-- FOOTER -->
    <jsp:include page="util/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
