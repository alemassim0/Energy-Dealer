<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Admin Dashboard</title>
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/resources/css/styles.css">
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/resources/css/AdminPage/adminPage.css">
</head>
<body>

<jsp:include page="../Components/header.jsp"/>

<main>
  <h1>Admin Dashboard</h1>
  <br>
  <br>
  <div class="admin-dashboard">
    <div class="container-item">
      <h2>Utenti Registrati</h2>
      <h5>${utenteCount}</h5>
      <button>
        <a href="${pageContext.request.contextPath}/admin/AdminPage?entity=users">Gestione Utenti</a>
      </button>
    </div>
    <div class="container-item">
      <h2>Prodotti Registrati</h2>
      <h5>${prodottoCount}</h5>
      <button>
        <a href="${pageContext.request.contextPath}/admin/AdminPage?entity=products">Gestione Prodotti</a>
      </button>
    </div>
    <div class="container-item">
      <h2>Ordini Registrati</h2>
      <h5>${ordineCount}</h5>
      <button>
        <a href="${pageContext.request.contextPath}/admin/AdminPage?entity=orders">Gestione Ordini</a>
      </button>
    </div>
  </div>
</main>


</body>
</html>
