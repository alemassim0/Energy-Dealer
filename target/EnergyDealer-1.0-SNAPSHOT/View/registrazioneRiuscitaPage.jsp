<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrazione Riuscita</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/RegistrazionePage/registrazioneRiuscitaPage.css">
</head>
<body>

<jsp:include page="Components/header.jsp"/>

<main>

    <h1>Registrazione Riuscita!</h1>

    <h2>${email}</h2>

    <h2><a href="${pageContext.request.contextPath}/user/LogInPage">Vai alla pagina di Log In</a></h2>

</main>

<jsp:include page="Components/footer.jsp"/>

</body>
</html>
