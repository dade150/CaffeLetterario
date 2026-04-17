<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.davide.model.Genere" %>
<%@ page import="java.util.List" %>
<%@ page import="com.davide.model.Libro" %>
<%
    String ruoloUtente = (String) session.getAttribute("ruolo");
    if (ruoloUtente == null || !ruoloUtente.equals("Amministratore")) {
        response.sendRedirect("login.jsp?errore=autorizzazione");
        return;
    }
    String titolo = request.getParameter("titolo") != null ? request.getParameter("titolo") : "";
    String nome = request.getParameter("nome") != null ? request.getParameter("nome") : "";
    String cognome = request.getParameter("cognome") != null ? request.getParameter("cognome") : "";
    String isbn = request.getParameter("isbn") != null ? request.getParameter("isbn") : "";
%>
<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8">
        <title>Gestione Libri</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

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

            .book-card {
                border-left: 4px solid #b88c4a;
                background-color: #fffdf5;
                border-radius: 0.4rem;
                box-shadow: 0 1px 4px rgba(0,0,0,0.1);
                transition: transform 0.2s ease;
                padding: 0.5rem;
                font-size: 0.85rem;
            }

            .book-card:hover {
                transform: translateY(-5px);
            }

            .book-header {
                font-size: 1rem;
                height: auto;
                padding: 0.4rem;
            }

            .book-body {
                padding: 0.4rem 0;
            }
            .book-title {
                font-size: 1.1rem;
            }

            .book-author {
                color: #777;
                font-size: 0.95rem;
            }

            .book-genres {
                margin-top: 0.5rem;
                font-size: 0.85rem;
                color: #666;
            }

            .book-price {
                margin-top: auto;
                font-weight: bold;
                color: #FFD180;
            }

            .section-title {
                text-align: center;
                font-size: 2rem;
                color: #49486f;
                margin-bottom: 2rem;
            }

            #formAggiungiLibro {
                margin-top: 20px;
                padding: 15px;
                border: 1px solid #ccc;
                background-color: #f9f9f9;
                display: none;
            }

            #generiInseriti span {
                display: inline-block;
                margin-right: 2px;
                padding: 2px 5px;
                background-color: #e0e0e0;
                border-radius: 3px;
                font-size: 0.75em;
            }
        </style>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    </head>
    <body>
        <jsp:include page="util/navbar.jsp" />

        <div class="sfondo">
            <h2 class="section-title">Gestione Libri</h2>

            <div class="row justify-content-center mb-4">
                <div class="col-lg-10">
                    <form class="row gy-2 justify-content-center text-center" method="get" action="RicercaEliminaLibroAmministrazione">
                        <div class="col-md-3">
                            <input type="text" name="titolo" class="form-control form-control-sm" placeholder="Titolo" value="<%= titolo%>">
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="nome" class="form-control form-control-sm" placeholder="Nome Autore" value="<%= nome%>">
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="cognome" class="form-control form-control-sm" placeholder="Cognome Autore" value="<%= cognome%>">
                        </div>
                        <div class="col-md-2">
                            <input type="text" name="isbn" class="form-control form-control-sm" placeholder="ISBN" value="<%= isbn%>">
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-warning text-white btn-sm w-100">Cerca</button>
                        </div>
                        <div class="col-md-2">
                            <button type="button" class="btn btn-success btn-sm w-100" onclick="toggleFormAggiungi()">+ Aggiungi Libro</button>
                        </div>
                    </form>
                </div>
            </div>

            <div id="formAggiungiLibro" class="sezione">
                <h5>Aggiungi Nuovo Libro</h5>
                <form action="AggiungiLibroAmministrazione" method="post" class="row g-2">
                    <div class="col-md-4">
                        <input type="text" class="form-control form-control-sm" name="titolo" placeholder="Titolo" required>
                    </div>
                    <div class="col-md-4">
                        <input type="text" class="form-control form-control-sm" name="isbn" placeholder="ISBN">
                    </div>
                    <div class="col-md-4">
                        <textarea class="form-control form-control-sm" name="sinossi" placeholder="Sinossi" rows="1"></textarea>
                    </div>
                    <div class="col-md-4">
                        <input type="number" step="0.01" class="form-control form-control-sm" name="prezzo" placeholder="Prezzo" required>
                    </div>
                    <div class="col-md-4">
                        <input type="number" class="form-control form-control-sm" name="anno" placeholder="Anno">
                    </div>
                    <div class="col-md-4">
                        <input type="number" class="form-control form-control-sm" name="quantita" placeholder="Quantità" value="1" required>
                    </div>

                    <h5 class="mt-2">Autore</h5>
                    <div class="col-md-6">
                        <input type="text" class="form-control form-control-sm" name="autoreNome" placeholder="Nome" required>
                    </div>
                    <div class="col-md-6">
                        <input type="text" class="form-control form-control-sm" name="autoreCognome" placeholder="Cognome" required>
                    </div>

                    <h5 class="mt-2">Genere</h5>
                    <div class="col-12">
                        <div class="input-group">
                            <input type="text" class="form-control form-control-sm" id="genereNome" placeholder="Nome Genere">
                            <button type="button" class="btn btn-outline-secondary btn-sm" onclick="aggiungiGenere()">Aggiungi</button>
                        </div>
                        <div id="generiInseriti" class="mt-1"></div>
                        <input type="hidden" id="listaGeneri" name="listaGeneri">
                    </div>

                    <div class="col-12 mt-2 text-center">
                        <button type="submit" class="btn btn-primary btn-sm">Aggiungi</button>
                        <button type="button" class="btn btn-secondary btn-sm" onclick="toggleFormAggiungi()">Annulla</button>
                    </div>
                </form>
            </div>

            <div class="row g-4">
                <% List<Libro> libri = (List<Libro>) request.getAttribute("libri"); %>
                <% if (libri != null && !libri.isEmpty()) {
                        for (Libro libro : libri) {%>
                <div class="col-md-6 col-lg-4">
                    <div class="book-card">
                        <div class="book-header">
                            <%= libro.getTitolo()%>
                        </div>
                        <div class="book-body">
                            <div class="book-author">
                                <%= libro.getAutore().getNome()%> <%= libro.getAutore().getCognome()%>
                            </div>
                            <div class="book-genres mt-2">
                                <% if (libro.getGeneri() != null) {
                                        for (Genere genere : libro.getGeneri()) {%>
                                <span class="badge bg-secondary me-1"><%= genere.getNome()%></span>
                                <% }
                                    }%>
                            </div>
                            <div class="book-price mt-3">EUR <%= libro.getPrezzo()%></div>
                            <form action="AggiornaQuantitaLibroAmministrazione" method="post" class="mt-2">
                                <input type="hidden" name="libroId" value="<%= libro.getId()%>">
                                <div class="input-group input-group-sm">
                                    <input type="number" name="quantita" class="form-control" min="0" value="<%= libro.getQuantita()%>">
                                    <button class="btn btn-outline-secondary" type="submit">Aggiorna</button>
                                </div>
                            </form>
                            <form action="AggiornaLibroAmministrazione" method="post" class="mt-2" onsubmit="return confirm('Sei sicuro di voler eliminare questo libro?');">
                                <input type="hidden" name="libroId" value="<%= libro.getId()%>">
                                <button type="button" class="btn btn-success btn-sm w-100" onclick="toggleFormUpdate(<%= libro.getId()%>)">Aggiorna</button>
                            </form>
                            <form action="RicercaEliminaLibroAmministrazione" method="post" class="mt-2" onsubmit="return confirm('Sei sicuro di voler eliminare questo libro?');">
                                <input type="hidden" name="libroId" value="<%= libro.getId()%>">
                                <button type="submit" class="btn btn-danger btn-sm w-100">Elimina</button>
                            </form>
                        </div>
                    </div>

                    <div id="formAggiornaLibro-<%= libro.getId()%>" style="display:none; margin-top:10px; background-color:#f1f1f1; padding:10px; border-radius:5px;">
                        <form action="AggiornaLibroAmministrazione" method="post" class="row g-2">
                            <input type="hidden" name="libroId" value="<%= libro.getId()%>">

                            <div class="col-md-6">
                                <input type="text" class="form-control form-control-sm" name="titolo" value="<%= libro.getTitolo()%>" required>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control form-control-sm" name="isbn" value="<%= libro.getISBN()%>">
                            </div>
                            <div class="col-12">
                                <textarea class="form-control form-control-sm" name="sinossi"><%= libro.getSinossi()%></textarea>
                            </div>
                            <div class="col-md-4">
                                <input type="number" step="0.01" class="form-control form-control-sm" name="prezzo" value="<%= libro.getPrezzo()%>" required>
                            </div>
                            <div class="col-md-4">
                                <input type="number" class="form-control form-control-sm" name="anno" value="<%= libro.getAnno()%>">
                            </div>
                            <div class="col-md-4">
                                <input type="number" class="form-control form-control-sm" name="quantita" value="<%= libro.getQuantita()%>" required>
                            </div>
                            <div class="col-12 mt-2 text-center">
                                <button type="submit" class="btn btn-primary btn-sm">Salva Modifiche</button>
                                <button type="button" class="btn btn-secondary btn-sm" onclick="toggleFormUpdate(<%= libro.getId()%>)">Chiudi</button>
                            </div>
                        </form>
                    </div>
                </div>
                <% }
                } else if (libri != null) { %>
                <p class="text-center">Nessun libro trovato con questi criteri.</p>
                <% } else { %>
                <p class="text-center">Effettua una ricerca o aggiungi un nuovo libro.</p>
                <% }%>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                    const formAggiungiLibroDiv = document.getElementById('formAggiungiLibro');
                                    const generiInseritiDiv = document.getElementById('generiInseriti');
                                    const listaGeneriInput = document.getElementById('listaGeneri');
                                    let generi = [];

                                    function toggleFormAggiungi() {
                                        formAggiungiLibroDiv.style.display = formAggiungiLibroDiv.style.display === 'none' ? 'block' : 'none';
                                    }

                                    function aggiungiGenere() {
                                        const input = document.getElementById('genereNome');
                                        const nomeGenere = input.value.trim();
                                        if (nomeGenere && !generi.includes(nomeGenere)) {
                                            generi.push(nomeGenere);
                                            aggiornaGeneriVisualizzati();
                                            input.value = '';
                                        } else if (generi.includes(nomeGenere)) {
                                            alert('Questo genere è già stato aggiunto.');
                                        } else {
                                            alert('Inserisci un nome per il genere.');
                                        }
                                    }

                                    function aggiornaGeneriVisualizzati() {
                                        generiInseritiDiv.innerHTML = '';
                                        generi.forEach((genere, index) => {
                                            const span = document.createElement('span');
                                            span.classList.add('badge', 'bg-info', 'me-1', 'd-inline-flex', 'align-items-center');
                                            span.style.color = 'black';

                                            const textNode = document.createTextNode(genere);
                                            span.appendChild(textNode);

                                            const button = document.createElement('button');
                                            button.type = 'button';
                                            button.classList.add('btn-close', 'btn-close-white', 'btn-sm', 'ms-2');
                                            button.setAttribute('aria-label', 'Remove');
                                            button.onclick = () => rimuoviGenere(index);

                                            span.appendChild(button);
                                            generiInseritiDiv.appendChild(span);
                                        });

                                        listaGeneriInput.value = generi.join(",");
                                    }

                                    function rimuoviGenere(index) {
                                        generi.splice(index, 1);
                                        aggiornaGeneriVisualizzati();
                                    }

                                    function toggleFormUpdate(bookId) {
                                        const formDiv = document.getElementById('formAggiornaLibro-' + bookId);
                                        formDiv.style.display = (formDiv.style.display === 'none' || formDiv.style.display === '') ? 'block' : 'none';
                                    }
        </script>
        <jsp:include page="util/footer.jsp" />

    </body>
</html>