<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Direttive per includere la libreria JSTL Core e impostare il tipo di contenuto della pagina -->

<html>
<head>
    <title>Home Page</title>
    <!-- Titolo della pagina -->

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/HomePage/homePage.css">
    <!-- Collegamenti ai fogli di stile CSS -->

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Collegamento alle icone di Material Design di Google -->
</head>

<body>
<jsp:include page="Components/header.jsp"/>
<!-- Inclusione dell'header della pagina -->

<main>
    <img class="home-background-image" src="${pageContext.request.contextPath}/resources/img/*inserire-immagine*"
         alt="background-image">
    <!-- Immagine di sfondo della home page -->

    <div class="showcases">
        <c:if test="${not empty error}">
            <p style="color: rgb(205, 93, 93);">Error: ${error}</p>
        </c:if>
        <!-- Visualizzazione di eventuali messaggi di errore -->

        <c:forEach items="${prodotti}" var="prodotto">
            <!-- Ciclo su tutti i prodotti -->
            <div class="showcase">
                <div class="showcase-drinks">
                    <c:choose>
                        <c:when test="${not empty immagini[prodotto.prodotto_id]}">
                            <img class="drinkImage"
                                 src="${pageContext.request.contextPath}${immagini[prodotto.prodotto_id].url}"
                                 alt="${prodotto.nome}"/>
                        </c:when>
                        <c:otherwise>
                            <p>No image available</p>
                        </c:otherwise>
                    </c:choose>
                    <!-- Visualizzazione dell'immagine del prodotto se disponibile -->

                    <div class="showcase-items">
                        <a href="${pageContext.request.contextPath}/ProductPage?prodotto_id=${prodotto.prodotto_id}" class="showcase-link">
                            <h2>${prodotto.nome}</h2>
                        </a>
                        <p class="descrizione">${prodotto.descrizione}...</p>

                        <div class="categoria-prezzo">
                            <p class="categoria">Categoria Drink: ${categorie[prodotto.categoria_id].nome}</p>
                            <p class="prezzo">€ <fmt:formatNumber value="${prodotto.prezzo}" type="number"
                                                                  maxFractionDigits="2" minFractionDigits="2"/></p>
                        </div>
                    </div>
                    <!-- Visualizzazione dei dettagli del prodotto -->
                </div>
            </div>
        </c:forEach>
    </div>
</main>
<jsp:include page="Components/footer.jsp"/>
<!-- Inclusione del footer della pagina -->
</body>
<script src="${pageContext.request.contextPath}/resources/js/HomePage/homePage.js"></script>
<!-- Inclusione di uno script JavaScript -->
</html>