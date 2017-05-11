<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <jsp:include page="fragments/headTag.jsp"/>
</head>
<body>

<div class="login-page">

    <div class="login-info__block">
        <div class="login-info">
            Admin login: admin1
        </div>
        <div class="login-info">
            User login: user1
        </div>
        <div class="login-info">
            Password for all: 1111
        </div>
    </div>

    <div class="login-logo">
        <fmt:message key="app.title"/>
    </div>

    <div class="login-block">
        <form:form class="login-form" role="form" action="spring_security_check" method="post">
            <input type="text" placeholder="Login" class="form-control" name='username'>
            <input type="password" placeholder="Password" class="form-control" name='password'>
            <button type="submit" class="btn btn-success"><fmt:message key="app.login"/></button>
        </form:form>

    </div>

    <hr>

    <div class="login-footer">
        <c:if test="${error}">
            <div class="error">
                <h2>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</h2>
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="message">
                <h2><fmt:message key="${message}"/></h2>
            </div>
        </c:if>
        <p><fmt:message key="app.developerdByYuriiMalikov"/></p>
        <%--<p>Used technologies: <a href="http://projects.spring.io/spring-security/">Spring Security</a>,--%>
        <%--<a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,--%>
        <%--&lt;%&ndash;<a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,&ndash;%&gt;--%>
        <%--<a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security--%>
        <%--Test</a>,--%>
        <%--<a href="http://hibernate.org/orm/">Hibernate ORM</a>,--%>
        <%--<a href="http://hibernate.org/validator/">Hibernate Validator</a>,--%>
        <%--<a href="http://www.slf4j.org/">SLF4J</a>,--%>
        <%--<a href="https://github.com/FasterXML/jackson">Json Jackson</a>,--%>
        <%--<a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,--%>
        <%--<a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,--%>
        <%--<a href="http://tomcat.apache.org/">Apache Tomcat</a>,--%>
        <%--<a href="http://www.webjars.org/">WebJars</a>,--%>
        <%--<a href="http://datatables.net/">DataTables plugin</a>,--%>
        <%--&lt;%&ndash;<a href="http://ehcache.org">Ehcache</a>,&ndash;%&gt;--%>
        <%--<a href="http://www.postgresql.org/">PostgreSQL</a>,--%>
        <%--<a href="http://junit.org/">JUnit</a>,--%>
        <%--<a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>,--%>
        <%--<a href="http://jquery.com/">jQuery</a>,--%>
        <%--<a href="http://ned.im/noty/">jQuery notification</a>,--%>
        <%--<a href="http://getbootstrap.com/">Bootstrap</a>.--%>
        <%--</p>--%>
        <div>
            <a href="https://github.com/malikov-yurii/java-developer-course-freelance-exchange">
                <fmt:message key="app.descriptionTextOnLoginPage"/>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>