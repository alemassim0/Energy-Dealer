<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
  <title>Admin - Sezione Ordini</title>
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/resources/css/styles.css">
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/resources/css/AdminPage/adminPageStyles.css">
</head>
<body>
<jsp:include page="../Components/header.jsp"/>

<main>
  <h1>Gestione Ordini</h1>
  <br>
  <table>
    <tr>
      <th>ID</th>
      <th>ID Utente</th>
      <th>Data</th>
      <th>Stato spedizione</th>
      <th>Stato pagamento</th>
      <th>Totale</th>
      <th>Indirizzo di Spedizione</th>
      <th>Note</th>
      <th>Prodotti Acquistati</th>
    </tr>
    <c:forEach var="ordine" items="${ordini}">
      <tr>
        <td>${ordine.ordine_id}</td>
        <td>${ordine.utente_id}</td>
        <td>${ordine.data_ordine}</td>
        <td>${ordine.stato_ordine}</td>
        <td>${ordine.stato_pagamento}</td>
        <td><fmt:formatNumber value="${ordine.totale}" type="number" maxFractionDigits="2" minFractionDigits="2"/>€</td>
        <td>${ordine.indirizzo_spedizione}</td>
        <td>${ordine.note}</td>
        <td>${ordine.prodotti_acquistati}</td>
      </tr>
    </c:forEach>
  </table>
</main>
</body>
</html>
