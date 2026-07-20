<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/OrderPage/orderPage.css">
</head>

<body>
<jsp:include page="Components/header.jsp"/>

<main>
    <h1 class="pagamento-label">Metodo di pagamento:</h1>
    <h1 class="pagamento-label">Carta di credito</h1>

    <form action="/checkout" method="post">
        <label for="card_number">Numero Carta</label>
        <br>
        <input type="number" id="card_number" name="card_number" placeholder="1234567890" required>
        <br>
        <br>

        <label for="expiration_date">Data di scadenza</label>
        <br>
        <input type="date" id="expiration_date" name="expiration_date" required>
        <br>
        <br>

        <label for="CVV">CVV</label>
        <br>
        <input type="number" id="CVV" name="CVV" placeholder="000" required>
        <br>
        <br>

        <label for="card_holder_full_name">Nome completo del proprietario</label>
        <br>
        <input type="text" id="card_holder_full_name" name="card_holder_full_name" placeholder="Mario Rossi" required>
        <br>
        <br>

        <h1>Dati sulla spedizione</h1>
        <br>

        <label for="country">Paese</label>
        <br>
        <input type="text" id="country" name="country" placeholder="Italia" value="Italia" readonly required>
        <br>
        <br>

        <label for="regions">Regione</label>
        <br>
        <select id="regions" name="regions">
            <option value="abruzzo">Abruzzo</option>
            <option value="basilicata">Basilicata</option>
            <option value="calabria">Calabria</option>
            <option value="campania">Campania</option>
            <option value="emilia-romagna">Emilia-Romagna</option>
            <option value="friuli-venezia-giulia">Friuli Venezia Giulia</option>
            <option value="lazio">Lazio</option>
            <option value="liguria">Liguria</option>
            <option value="lombardia">Lombardia</option>
            <option value="marche">Marche</option>
            <option value="molise">Molise</option>
            <option value="piemonte">Piemonte</option>
            <option value="puglia">Puglia</option>
            <option value="sardegna">Sardegna</option>
            <option value="sicilia">Sicilia</option>
            <option value="toscana">Toscana</option>
            <option value="trentino-alto-adige">Trentino-Alto Adige</option>
            <option value="umbria">Umbria</option>
            <option value="valle-d-aosta">Valle d'Aosta</option>
            <option value="veneto">Veneto</option>
        </select>
        <br>
        <br>

        <label for="province">Provincia</label>
        <br>
        <input type="text" id="province" name="province" placeholder="Avellino" required>
        <br>
        <br>

        <label for="city">Città</label>
        <br>
        <input type="text" id="city" name="city" placeholder="Avellino" required>
        <br>
        <br>

        <label for="postal_code">Codice postale</label>
        <br>
        <input type="number" id="postal_code" name="postal_code" placeholder="83050" required>
        <br>
        <br>

        <label for="address">Indirizzo</label>
        <br>
        <input type="text" id="address" name="address" placeholder="Via Roma, 67" required>
        <br>
        <br>

        <h1>Dati di contatto</h1>
        <br>

        <label for="email">Email</label>
        <br>
        <input type="email" id="email" name="email" placeholder="domain@example.com" required>
        <br>
        <br>

        <label for="phone_number">Numero di telefono</label>
        <br>
        <input type="text" id="phone_number" name="phone_number" placeholder="3334455667" required>
        <br>
        <br>

        <label for="note">Note aggiuntive</label>
        <br>
        <input type="text" id="note" name="note" placeholder="Bussare la porta 2 volte...">
        <br>
        <br>

        <button type="submit">Paga</button>
    </form>

</main>

<jsp:include page="Components/footer.jsp"/>
</body>
</html>
