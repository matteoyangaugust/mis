<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title><spring:message code="login.title"/></title>
    <link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>'>
    <link rel="stylesheet" href='<c:url value="/css/font-awesome.css"/>'>
    <link rel="stylesheet" href='<c:url value="/css/login/login.css"/>'>
    <script src='<c:url value="/js/jquery-3.2.0.min.js"/>'></script>
    <script src='<c:url value="/js/tether.min.js"/>'></script>
    <script src='<c:url value="/js/bootstrap.min.js"/>'></script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-12 mt-3" align="center">
                <h1>Manufacture Information System</h1>
            </div>
            <div class="offset-sm-2 col-sm-8 mt-5" id="loginDiv" align="center">
                <c:if test="${not empty error}">
                    <h4 class="text-danger">Login Failed</h4>
                </c:if>
                <form class="mt-sm-5" action='<c:url value="/j_spring_security_check"/> ' method="post">
                    <label class="mt-5"><h3 class="text-muted">Login</h3></label>
                    <div class="form-group row">
                        <div class="offset-sm-2 col-sm-8" align="center">
                            <input type="text" class="form-control" id="j_username" name="j_username" placeholder="username">
                        </div>
                        <div class="offset-sm-2 col-sm-8" align="center">
                            <input type="password" class="form-control" id="j_password" name="j_password" placeholder="password">
                        </div>
                    </div>
                    <%--<input type="checkbox" name="_spring_security_remember_me"/>記住我--%>
                    <br>
                    <button type="submit" class="btn btn-secondary submitBtn">
                        <i class="fa fa-4x fa-arrow-right" aria-hidden="true"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
