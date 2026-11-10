<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Admin - Sezione Utente</title>
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/resources/css/styles.css">
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/resources/css/AdminPage/adminPageStyles.css">
</head>
<body>

<jsp:include page="../Components/header.jsp"/>

<main>
  <h1>Gestione Utenti</h1>
  <br>
  <table>
    <tr>
      <th>ID</th>
      <th>Nome</th>
      <th>Cognome</th>
      <th>Email</th>
      <th>Password</th>
      <th>Numero Telefono</th>
      <th>Indirizzo di Spedizione</th>
      <th>Azione</th>
    </tr>
    <c:forEach var="utente" items="${utenti}">
      <tr>
        <td>${utente.utente_id}</td>
        <td>${utente.nome}</td>
        <td>${utente.cognome}</td>
        <td>${utente.email}</td>
        <td>${utente.password}</td>
        <td>${utente.numero_telefono}</td>
        <td>${utente.indirizzo_spedizione}</td>
        <td>
          <form action="${pageContext.request.contextPath}/admin/AdminPage" method="post">
            <input type="hidden" name="action" value="deleteUser">
            <input type="hidden" name="userId" value="${utente.utente_id}">
            <input type="submit" value="Elimina"
                   onclick="return confirm('Sei sicuro di voler eliminare questo utente?');">
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>
</main>

</body>
</html>