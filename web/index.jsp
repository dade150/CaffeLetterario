<%--
    Document   : index
    Created on : Apr 30, 2025, 3:28:37?PM
    Author     : david
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.davide.model.Recensione"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="it">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" name="viewport" content="width=device-width, initial-scale=1">
        <title>Apollo & Dafne</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

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
        </style>
    </head>

    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <jsp:include page="util/navbar.jsp" />

        <div class="sfondo">
            <div class="container py-5">

                <!-- Chi siamo -->
                <section class="mb-5 py-5 px-3">
                    <h2 class="mb-4 display-6" style="color: #535353;">Chi siamo</h2>
                    <p class="fs-5" style="color: #535353;">
                        Benvenuti all'<strong>Apollo & Dafne</strong>, un rifugio per l'anima e la mente. Tra il profumo avvolgente del caff? e il fruscio delle pagine, offriamo uno spazio dove letteratura, arte e conversazioni si intrecciano in un'atmosfera calda e ispiratrice.
                        Siamo pi? di un caff?: siamo una comunit? di sognatori, poeti e pensatori.
                    </p>
                </section>

                <!-- Galleria -->
                <section class="mb-5 py-5 px-3">
                    <h2 class="mb-4 display-6" style="color: #535353;">Galleria</h2>
                    <div class="row g-4">
                        <div class="col-6 col-md-4">
                            <img src="${pageContext.request.contextPath}/images/foto1.jpeg" class="img-fluid rounded shadow-sm" alt="Interno del locale">
                        </div>
                        <div class="col-6 col-md-4">
                            <img src="images/foto2.jpeg" class="img-fluid rounded shadow-sm" alt="Clienti che leggono">
                        </div>
                        <div class="col-6 col-md-4">
                            <img src="images/foto3.jpg" class="img-fluid rounded shadow-sm" alt="Dettagli vintage">
                        </div>
                    </div>
                </section>

                <!-- Storia -->
                <section class="mb-4 py-5 px-3">
                    <h2 class="mb-4 display-6" style="color: #535353;">La nostra storia</h2>
                    <p class="fs-5" style="color: #535353;">
                        Ispirato al mito di Apollo e Dafne, il nostro caff? nasce dal desiderio di celebrare la bellezza eterna dell'arte e della natura. Ogni elemento del nostro spazio, dai colori caldi alle librerie piene di racconti, rende omaggio alla trasformazione, alla poesia e all'amore platonico per la conoscenza.
                    </p>
                </section>

            </div>


        </div>

        <jsp:include page="util/footer.jsp" />
    </body>
</html>