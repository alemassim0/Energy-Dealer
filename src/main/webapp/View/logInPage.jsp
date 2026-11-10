<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LogIn Page</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/LogInPage/logInPage.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<jsp:include page="Components/header.jsp"/>

<main>
    <h1 id="log-in-label">Log In</h1>

    <div class="sign-up-text">
        <h1>Don't have an account?</h1>
        <a href="${pageContext.request.contextPath}/user/RegistrazionePage">Sign Up</a>
    </div>

    <br>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <h5 class="error-text"><%= request.getAttribute("errorMessage") %>
    </h5>
    <% } %>

    <form action="${pageContext.request.contextPath}/user/LogInPage" class="log-in-form" method="post">
        <label for="email">Email</label>
        <% if (request.getAttribute("email") == null){ %>
        <input type="email" id="email" name="email" required>
        <% } else { %>
        <input type="email" id="email" name="email" required value="<%= request.getAttribute("email")%>">
        <% }%>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <h5 class="error-text" id="password-error"></h5>
        <h5 class="error-text" id="email-error"></h5>
        <br>
        <button type="submit" id="log-in-button">Log In</button>
    </form>

    <div class="log-in-text">
        <div class="log-in-text-content">
            <div>
                <h6>Terms of Use</h6>
            </div>
            <div>
                <h6>Privacy Policy</h6>
            </div>
        </div>
        <h6>This site is protected by reCAPTCHA Enterprise. Google's Privacy Policy and Terms of Service apply</h6>
    </div>
</main>

</body>
</html>
