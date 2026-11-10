<%@ page import="java.util.ArrayList" %>
<%@ page import="energy_dealer.application.energydealer.Model.Entity.Immagine" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <title>Carrello</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/CarrelloPage/carrelloPage.css">
</head>
<body>

<jsp:include page="Components/header.jsp"/>

<main>
    <h1>Carrello</h1>
    <div class="container">
        <div class="flex-prodotto-container">
            <% int i = -1; // piu comodo leggere se la variabile contatore viene incrementata all'inizio che cercare la fine di questo blocco.
                ArrayList<Immagine> immagini = (ArrayList<Immagine>) request.getAttribute("immagini");
            %>
            <c:forEach items="${prodotti}" var="prodotto">
                <%i++;%>
                <div id="product_<%=i%>">
                    <div>
                        <img src="${pageContext.request.contextPath}<%= immagini.get(i).getUrl()%>"
                             alt="Immagine Prodotto">
                    </div>
                    <div class="flex-prodotto-item">
                        <div>
                            <h2 id="product_<%=i%>_name">${prodotto.prodotto.nome}</h2>
                        </div>
                        <div class="prodotto-info">
                            <div>
                                <h2>Quantità: ${prodotto.quantita_selezionata} pz</h2>
                                <h2 id="product_<%=i%>_subtotal">Prezzo: € <fmt:formatNumber
                                        value="${prodotto.prodotto.prezzo * prodotto.quantita_selezionata}"
                                        type="number"
                                        maxFractionDigits="2" minFractionDigits="2"/></h2>
                            </div>
                            <button aria-label="Elimina Prodotto">
                                <!-- AGGIUNGERE JS -->
                                <a onclick="remove_item(<%=i%>)" aria-hidden="true">Elimina</a>
                            </button>
                        </div>
                    </div>
                </div>
                <br>
                <br>
            </c:forEach>
        </div>

        <div class="carrello-info" onclick="location.href = '/checkout'">
            <div>
                <h2>Sub Totale</h2>
                <h2>€ <span id="subtotal"><fmt:formatNumber value="${prezzo_totale}" type="number" maxFractionDigits="2"
                                                            minFractionDigits="2"/></span></h2>
            </div>
            <h2>Procedere al Checkout</h2>
        </div>
    </div>
</main>


<jsp:include page="Components/footer.jsp"/>

</body>
<script src="${pageContext.request.contextPath}/resources/js/CarrelloPage/carrelloPage.js"></script>
</html>
