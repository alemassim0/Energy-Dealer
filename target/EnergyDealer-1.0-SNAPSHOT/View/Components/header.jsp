<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Dichiarazioni per l'uso di JSTL e impostazione del tipo di contenuto della pagina -->

<%
    // Ottiene la sessione corrente senza crearne una nuova se non esiste
    HttpSession currentSession = request.getSession(false);

    // Inizializza una variabile booleana per tracciare se l'utente è loggato
    boolean isLoggedIn = false;

    // Controlla se la sessione esiste (currentSession != null) e se contiene l'attributo "userEmail"
    // Se vero, imposta isLoggedIn a true
    if (currentSession != null && currentSession.getAttribute("userEmail") != null) {
        isLoggedIn = true;
    }

    // Recupera tutti i cookie dalla richiesta dell'utente
    Cookie[] cookies = request.getCookies();

    // Controlla se isLoggedIn è ancora false (utente non loggato) e se ci sono cookie (cookies != null)
    if (!isLoggedIn && cookies != null) {
        // Ciclo attraverso ogni cookie
        for (Cookie cookie : cookies) {
            // Controlla se esiste un cookie chiamato "userEmail"
            if ("userEmail".equals(cookie.getName())) {
                // Se trovato, imposta isLoggedIn a true e esce dal ciclo
                isLoggedIn = true;
                break;
            }
        }
    }

    boolean isAdmin = false;
    if (currentSession != null && "ADMIN".equals(currentSession.getAttribute("userRole"))) {
        isAdmin = true;
    }
%>

<html>
<head>
    <title>Header</title>
    <!-- Titolo della pagina -->

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Meta tag per il responsive design -->

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/Components/header.css">
    <!-- Collegamento al file CSS per lo stile dell'header -->

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Collegamento alle icone di Material Design di Google -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/img/favIcon.png">
</head>
<body>

<header>
    <div class="brand-name">
        <a href="${pageContext.request.contextPath}/HomePage">Energy Dealer</a>
        <!-- Link al nome del brand che porta alla home page -->
    </div>
    <div class="header-content">
        <div>
            <button class="menu-toggle" aria-label="Toggle menu">
                <i class="material-icons">&#xe5d2;</i>
                <!-- Pulsante per il menu toggle con icona -->
            </button>
        </div>
        <div class="nav-icons">
            <div class="icon">
                <a href="${pageContext.request.contextPath}/HomePage">
                    <i class="material-icons">&#xe88a;</i>
                    <!-- Icona per la home page -->
                </a>
            </div>
            <div class="icon">
                <a href="${pageContext.request.contextPath}/Prodotti">
                    <i class="material-icons">&#xea12;</i>
                    <!-- Icona per i prodotti -->
                </a>
            </div>
            <% if (isAdmin) { %>
            <div class="icon">
                <a href="${pageContext.request.contextPath}/admin/AdminPage">
                    <i class="material-icons">&#xe8b8;</i>
                </a>
            </div>
            <% } %>

            <div class="icon">
                <a href="${pageContext.request.contextPath}/carrello">
                    <i class="material-icons">&#xe8cc;</i>
                    <!-- Icona per il carrello -->
                </a>
            </div>
            <div class="icon">
                <a href="${pageContext.request.contextPath}/user/LogInPage">
                    <i class="material-icons">&#xe7fd;</i>
                    <!-- Icona per il login -->
                </a>
            </div>
            <div class="icon">
                <% if (isLoggedIn) { %>
                <a href="${pageContext.request.contextPath}/user/LogOut">
                    <i class="material-icons">&#xe9ba;</i>
                    <!-- Icona per il logout, visibile solo se l'utente è loggato -->
                </a>
                <% } %>
            </div>
        </div>
    </div>
</header>
<script src="${pageContext.request.contextPath}/resources/js/Components/header.js"></script>
<!-- Collegamento allo script JavaScript per la funzionalità dell'header -->
</body>
</html>
