<%@ page import="energy_dealer.application.energydealer.Model.Entity.Prodotto" %>
<%@ page import="energy_dealer.application.energydealer.Model.Entity.Categoria" %>
<%@ page import="energy_dealer.application.energydealer.Model.Entity.Immagine" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Prodotto</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/ProdottoPage/prodottoPage.css">
</head>

<body>

<jsp:include page="Components/header.jsp"/>

<main>

    <div>
        <%Immagine immagine = (Immagine) request.getAttribute("immagine"); %>
        <img class="themed_background" src="<%= immagine.getUrl() %>" alt="Drink image should be here...">
    </div>

    <div class="flex-item">
        <div class="flex-item-prodotto">
            <%
                Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
                Categoria categoria = (Categoria) request.getAttribute("categoria");
            %>
            <h1 id="prodotto_nome"><%= prodotto.getNome() %></h1>

            <h2 id="prodotto_categoria"><%= categoria.getNome() %></h2>

            <h2 id="prodotto_descrizione"><%= prodotto.getDescrizione() %></h2>
        </div>

        <!-- AGGIUNGERE JAVASCRIPT PER GESTIRE IL CARRELLO -->
        <div class="flex-item-prodotto-info">
            <div><h2 id="available_products">Disponibile: <%= prodotto.getQuantita_inventario() %>
            </h2></div>
            <button class="button-quantity" onclick="increment_counter()" aria-label="Aumenta quantità">
                <span aria-hidden="true">+</span>
            </button>
            <div id="selected_products"><h2>1</h2></div>
            <button class="button-quantity" onclick="decrement_counter()" aria-label="Diminuisci quantità">
                <span aria-hidden="true">-</span>
            </button>
            <button class="button-aggiungi" onclick="askServerToAddProdotto()">Aggiungi nel Carrello</button>
        </div>
    </div>
</main>

<jsp:include page="Components/footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/resources/js/ProdottoPage/prodottoPage.js"></script>
</html>
