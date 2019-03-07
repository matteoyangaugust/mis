
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script>
            $(function(){
                window.location.href = ctxPath + 'material/create/toCreate.do';
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
    </jsp:body>
</t:base>