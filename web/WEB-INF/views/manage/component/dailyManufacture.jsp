
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script>
            $(function(){
                if($(".isOpen").length == 1){
                    $("#menuTrigger").trigger('click');
                }
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <a href='<c:url value=""/>'>
                        <button type="button" class="btn btn-outline-secondary" id="imageScanningLink">人員管理</button>
                    </a>
                    <a href='<c:url value=""/>'>
                        <button type="button" class="btn btn-outline-secondary">原料管理</button>
                    </a>
                    <a href='<c:url value=""/>'>
                        <button type="button" class="btn btn-secondary" id="liveScanningLink">配件管理</button>
                    </a>
                    <a href='<c:url value=""/>'>
                        <button type="button" class="btn btn-outline-secondary">產品管理</button>
                    </a>
                    <div class="row formOutSide mt-3">
                        <div class="container">
                            <div class="row">
                                <form class="col-sm-12">
                                    <div class="form-group form-inline mt-3">
                                        <label for="identifier" class="labelText col-sm-4">品號(Identifier)</label>
                                        <input type="text" class="form-control notEmpty col-sm-4" name="identifier" id="identifier" aria-describedby="品號" >
                                    </div>
                                    <div class="form-group form-inline mt-1">
                                        <label for="name" class="labelText col-sm-4">品名(Name)</label>
                                        <input type="text" class="form-control notEmpty col-sm-4" name="name" id="name" aria-describedby="品名" >
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>