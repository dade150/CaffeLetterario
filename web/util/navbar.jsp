<%-- navbar.jsp --%>
<style>
    .navbar-custom {
        background-color: #FFD180; /* Arancione chiaro */
        padding-top: 1rem;
        padding-bottom: 1rem;
    }

    .nav-link {
        color: #444 !important;
        font-weight: 500;
        margin: 0 15px;
        transition: 0.3s ease;
        padding: 0.6rem 1.2rem; /* Aumenta il padding per la dimensione */
        font-size: 1.1rem; /* Aumenta leggermente la dimensione del testo */
    }

    .nav-link:hover {
        background-color: #49486f; /* Verde acqua scuro */
        color: #fff !important;
        border-radius: 5px;
    }

    .navbar-brand {
        display: flex;
        align-items: center;
    }

    /* Contenitore per il logo e il testo */
    .logo-text-container {
        display: flex;
        flex-direction: column; /* Dispone gli elementi verticalmente */
        align-items: center; /* Centra gli elementi orizzontalmente */
    }

    /* Stile per il titolo principale */
    .main-title {
        font-size: 1.5rem;
        font-weight: bold;
        margin-bottom: 0.3rem; /* Aggiunge spazio tra il titolo e il sottotitolo */
        text-align: center; /* Forza la centratura del testo */
        color: #49486f;
    }

    /* Stile per il sottotitolo */
    .sub-title {
        font-size: 1rem;
        font-family: 'Arial', sans-serif; /* Font stampato */
        font-style: italic;
        color: #555; /* Colore grigio per il sottotitolo */
        text-align: center; /* Forza la centratura del testo */
    }

    .navbar-logo {
        width: 40px;
        height: 40px;
    }

    .navbar-nav {
        width: 100%;
        display: flex;
        justify-content: center;
    }

    .navbar-nav .nav-item {
        margin: 0;
    }
    @media (max-width: 576px) {
        .navbar-logo {
            width: 32px;
            height: 32px;
        }

        .main-title {
            font-size: 1.1rem;
        }

        .sub-title {
            font-size: 0.8rem;
        }

        .nav-link {
            font-size: 1rem;
            padding: 0.5rem 0.8rem;
        }

        .navbar-custom {
            padding-top: 0.8rem;
            padding-bottom: 0.8rem;
        }

        .navbar-nav {
            flex-direction: column;
            align-items: center;
        }
    }

</style>

<nav class="navbar navbar-expand-lg navbar-custom">
    <div class="container-fluid">
        <a class="navbar-brand text-dark fw-bold" href="index.jsp">
            <img src="images/logo.png" alt="Logo" class="navbar-logo">
            <div class="logo-text-container">
                <span class="main-title">Apollo & Dafne</span>
                <span class="sub-title">coffee - cup</span>
            </div>
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNav" aria-controls="navbarNav"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Chi siamo</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/CaffeLetterarioProject/Menu2">Menu</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/CaffeLetterarioProject/VisualizzaLibri">Libreria</a>
                </li>
                <li class="nav-item d-flex align-items-center">
                    <a class="nav-link" href="/CaffeLetterarioProject/VisualizzaRecensione">Area Clienti</a>

                </li>
                <li class="nav-item d-flex align-items-center">
                    <a class="nav-link" href="/CaffeLetterarioProject/login.jsp">Area Personale<span class="badge bg-warning text-dark ms-2">Personale</span></a>

                </li>
            </ul>
        </div>
    </div>
</nav>