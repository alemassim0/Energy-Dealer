<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/RegistrazionePage/registrazionePage.css">
</head>
<body>
<jsp:include page="Components/header.jsp"/>

<main>
    <h1 id="sign-up-label">Sign Up</h1>

    <div class="log-in-text">
        <h1>Already have an account?</h1>
        <a href="${pageContext.request.contextPath}/user/LogInPage">Log In</a>
    </div>
    <br>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <h5 class="error-text"><%= request.getAttribute("errorMessage") %>
    </h5>
    <% } %>

    <form action="${pageContext.request.contextPath}/user/RegistrazionePage" class="register-form" method="post">
        <label for="email">Email</label>
        <% if (request.getAttribute("email") == null){ %>
        <input type="email" id="email" name="email" required>
        <% } else { %>
        <input type="email" id="email" name="email" required value="<%= request.getAttribute("email")%>">
        <% }%>

        <label for="confirm-email">Confirm Email</label>
        <input type="email" id="confirm-email" name="confirm-email" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <label for="confirm-password">Confirm Password</label>
        <input type="password" id="confirm-password" name="confirm-password" required>

        <h5 class="error-text" id="password-error"></h5>
        <h5 class="error-text" id="email-error"></h5>
        <br>
        <button type="submit" id="sign-up-button">Log In</button>
    </form>

    <div class="sign-up-text">
        <h6>By signing up, you agree to our Terms of Use and acknowledge you’ve read our Privacy Policy.</h6>
        <h6>This site is protected by reCAPTCHA Enterprise. </h6>
        <h6>Google's Privacy Policy and Terms of Service apply.</h6>
    </div>
</main>
</body>
<script src="${pageContext.request.contextPath}/resources/js/RegistrazionePage/registrazionePage.js"></script>
</html>
