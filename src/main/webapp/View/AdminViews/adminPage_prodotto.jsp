<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Admin - Sezione Prodotti</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/AdminPage/adminPageStyles.css">
</head>
<body>

<jsp:include page="../Components/header.jsp"/>

<main>
    <h1>Gestione Prodotti</h1>
    <br>
    <div class="info-line">
        <h4>Tutti i Prodotti</h4>

        <form action="${pageContext.request.contextPath}/admin/AdminPage" method="get">
            <input type="hidden" name="action" value="addProduct">
            <input type="hidden" name="entity" value="products">
            <input type="hidden" name="productId" value="${prodotto.prodotto_id}">
            <input type="submit" value="Aggiungi Prodotto"
                   onclick="return confirm('Sei sicuro di voler aggiungere questo prodotto?');">
        </form>
    </div>
    <br>
    <table>
        <tr>
            <th>Immagine</th>
            <th>ID</th>
            <th>Categoria</th>
            <th>Nome</th>
            <th>Prezzo</th>
            <th>Quantità Inventario</th>
            <th>Descrizione</th>
        </tr>
        <tr>
            <tbody id="tableBody"></tbody>
        </tr>
    </table>

    <button id="loadButton">Carica Prodotti</button>
    <div id="responseContainer"></div>

</main>
</body>
<script src="${pageContext.request.contextPath}/resources/js/AdminPage/adminPage_prodotto.js"></script>
</html>
