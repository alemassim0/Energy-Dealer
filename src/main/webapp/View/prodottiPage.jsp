<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Prodotti</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/ProdottoPage/prodottiPage.css">
</head>
<body>
<jsp:include page="Components/header.jsp"/>
<main>
    <h1>Prodotti</h1>
    <br>
    <br>
    <table>
        <thead>
        <tr>
            <th>Immagine</th>
            <th>Nome</th>
            <th>Categoria</th>
            <th>Prezzo</th>
            <th>Descrizione</th>
            <th>Quantità Disponibile</th>
            <th></th>
        </tr>
        </thead>
        <tbody id="tableBody"></tbody>
    </table>

    <button id="loadButton">Carica Prodotti</button>
</main>

<jsp:include page="Components/footer.jsp"/>

</body>
<script src="${pageContext.request.contextPath}/resources/js/ProdottoPage/prodottoPage.js"></script>
</html>