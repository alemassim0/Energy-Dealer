<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Successful</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<jsp:include page="Components/header.jsp"/>
<main>
    <h1>${ordine.data_ordine}</h1>
    <h1>Ordine effettuato con successo!</h1>
    <h2>Numero ordine №${ordine.ordine_id}</h2>

    <h3>Sintesi Ordine</h3>
    <div>
        <br>
    </div>

    <div>
        <h1>Totale</h1>
        <h1>€ <fmt:formatNumber value="${ordine.totale}" type="number"
                                maxFractionDigits="2" minFractionDigits="2"/></h1>
    </div>
</main>
<jsp:include page="Components/footer.jsp"/>

</body>
</html>
