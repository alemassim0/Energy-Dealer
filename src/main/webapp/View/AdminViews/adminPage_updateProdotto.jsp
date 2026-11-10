<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="energy_dealer.application.energydealer.Model.Entity.Prodotto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Aggiornamento Prodotto</title>
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/resources/css/styles.css">
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/resources/css/AdminPage/adminPageStyles.css">
</head>
<body>
<jsp:include page="../Components/header.jsp"/>

<main>
  <%
    boolean isUpdate = false;
    if (request.getAttribute("action") != null) isUpdate = request.getAttribute("action").equals("updateProduct");

    boolean isProdotto = false;
    Prodotto prodotto = null;

    if (request.getAttribute("prodotto") != null ) {
      isProdotto = true;
      prodotto = (Prodotto) request.getAttribute("prodotto");
    }

    if(isUpdate){ %>
  <h1>Aggiornamento Prodotto</h1>
  <% } else { %>
  <h1>Aggiunta Prodotto</h1>
  <% } %>


  <form action="${pageContext.request.contextPath}/admin/AdminPage" method="post" class="update-product-form">
    <label for="product_id" <%= isUpdate ? "" : "hidden" %>>Prodotto id</label>
    <br>
    <input type="text" name="prodottoId" id="product_id" value="<%= isProdotto ? prodotto.getProdotto_id() : "" %>" <%= isUpdate ? "readonly" : "hidden"%>>
    <br>
    <br>

    <label for="categoria_id">Categoria</label>
    <br>
    <input type="text" name="categoriaId" id="categoria_id" value="<%= isProdotto ? prodotto.getCategoria_id() : "" %>" required>
    <br>
    <br>

    <label for="nome">Nome</label>
    <br>
    <input type="text" name="nome"  id="nome" value="<%= isProdotto ? prodotto.getNome() : "" %>" required>
    <br>
    <br>

    <label for="descrizione">Descrizione</label>
    <br>
    <textarea name="descrizione" id="descrizione" required rows="4" cols="50"><%= isProdotto ? prodotto.getDescrizione() : "" %></textarea>
    <br>
    <br>

    <label for="prezzo">Prezzo</label>
    <br>
    <input type="text" name="prezzo" id="prezzo" value="<fmt:formatNumber value="<%= isProdotto ? prodotto.getPrezzo() : 0 %>" type="number" maxFractionDigits="2" minFractionDigits="2"/>" required>
    <br>
    <br>

    <label for="quantita_inventario">Quantita Invetario</label>
    <br>
    <input type="text" name="quantitaInventario" id="quantita_inventario" value="<%= isProdotto ? prodotto.getQuantita_inventario() : ""%>" required>
    <br>
    <br>

    <input type="text" name="action" value="${action}" hidden>
    <input type="submit" value="Salva">
  </form>
</main>
</body>
</html>
