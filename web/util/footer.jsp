<%-- footer.jsp --%>
<style>
    .footer-custom {
        background-color: #FFD180; /* Stesso arancione chiaro della navbar */
        padding-top: 2rem;
        padding-bottom: 2rem;
        color: #444; /* Stesso colore del testo dei link della navbar */
        text-align: center;
        font-size: 0.9rem;
        display: flex;
        flex-direction: column;
        align-items: center;
        bottom: 0;
        width: 100%;
    }

    .footer-links {
        margin-bottom: 1rem;
    }

    .footer-links a {
        color: #444;
        margin: 0 10px;
        text-decoration: none;
        transition: color 0.3s ease;
    }

    .footer-links a:hover {
        color: #26A69A; /* Stesso colore hover dei link della navbar */
    }

    .social-icons a {
        display: inline-block;
        margin: 0 8px;
        color: #444;
        font-size: 1.2rem; /* Dimensione icone social */
        text-decoration: none;
        transition: color 0.3s ease;
    }

    .social-icons a:hover {
        color: #26A69A; /* Stesso colore hover */
    }

    /* Puoi aggiungere stili specifici per le icone se usi una libreria come Font Awesome */
    .fa {
        /* Stili generali per le icone di Font Awesome (se le usi) */
        display: inline-block;
        font-family: FontAwesome;
        font-style: normal;
        font-weight: normal;
        line-height: 1;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }

    .fa-instagram:before {
        content: "\f16d"; /* Codice icona Instagram (Font Awesome) */
    }

    .fa-facebook-square:before {
        content: "\f082"; /* Codice icona Facebook (Font Awesome) */
    }

    .footer-info {
        margin-bottom: 1rem;
    }

    .copyright {
        font-size: 0.8rem;
        color: #777;
        margin-top: 1rem;
    }
</style>

<footer class="footer-custom">
    <div class="footer-info">
        <p><strong>Apollo & Dafne coffee - cup</strong></p>
        <p>Via Immaginaria, 123</p>
        <p>00100 Città Fantastica (RM), Italia</p>
        <p>Email: info@apollodaphne.it</p>
        <p>Telefono: +39 012 345 6789</p>
    </div>

    <div class="social-icons">
        <a href="https://www.instagram.com/" target="_blank" aria-label="Instagram"><i class="fa fa-instagram"></i></a>
        <a href="https://www.facebook.com/" target="_blank" aria-label="Facebook"><i class="fa fa-facebook-square"></i></a>
    </div>

    <p class="copyright">&copy; 2025 Apollo & Daphne Coffee-Cup. Tutti i diritti riservati.</p>
</footer>
