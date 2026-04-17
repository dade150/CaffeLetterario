<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8">
        <title>Login - Apollo & Dafne</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap + Font -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            body {
                margin: 0;
                padding: 0;
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
                position: relative;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
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

            .content-wrapper {
                flex-grow: 1;
                display: flex;
                justify-content: center;
                align-items: center;
                position: relative;
                z-index: 1;
                padding: 2rem 1rem;
            }

            .login-container {
                background-color: #fff;
                padding: 2rem;
                border-radius: 12px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }

            .login-title {
                color: #49486f;
                text-align: center;
                margin-bottom: 1.5rem;
            }

            .form-label {
                color: #49486f;
                font-weight: 500;
            }

            .form-control {
                border-radius: 8px;
            }

            .btn-login {
                background-color: #FFD180;
                color: #49486f;
                font-weight: bold;
                border: none;
                transition: background-color 0.3s ease;
            }

            .btn-login:hover {
                background-color: #ffb74d;
            }
        </style>
    </head>
    <body>

        <!-- NAVBAR -->
        <jsp:include page="util/navbar.jsp" />

        <!-- SFONDO + LOGIN -->
        <div class="sfondo">
            <div class="content-wrapper">
                <div class="login-container">
                    <h2 class="login-title">Accedi al tuo account</h2>

                    <form action="LoginPersonale" method="post">
                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" name="username"
                                   placeholder="Inserisci il tuo username" required>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="Inserisci la tua password" required>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-login">Login</button>
                        </div>
                    </form>
                </div>
            </div>


        </div>
        <!-- FOOTER -->
        <jsp:include page="util/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
