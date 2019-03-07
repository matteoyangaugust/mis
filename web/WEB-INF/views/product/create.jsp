
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/product/create.js"/>'></script>
        <script>
            var components = ${components}
        </script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="row formOutSide mt-3">
                        <div id="template">
                            <c:import url="selectComponentTemplate.jsp"></c:import>
                            <c:import url="selectedComponentTemplate.jsp"></c:import>
                        </div>
                        <div class="container">
                            <div class="row text-center">
                                <form class="col-sm-12" id="componentForm">
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Product Code</div>
                                        </span>
                                        <div class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="form-control notEmpty inputW50w100 identifier" name="identifier" id="identifier" value="" aria-describedby="Product Code">
                                        </div>
                                    </div>
                                    <hr class="col-sm-12 style-sbs labelText">
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Product Name</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="form-control notEmpty inputW50w100 name" name="name" value="" id="name" aria-describedby="Product Name">
                                        </span>
                                    </div>
                                    <hr class="col-sm-12 style-sbs labelText">
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Used Component</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <button type="button" class="btn btn-outline-info inputW50w100" id="selectComponent">Select Component</button>
                                        </span>
                                        <div class="offset-4 col-sm-4 text-center" id="components"></div>
                                    </div>
                                    <hr class="col-sm-12 style-sbs labelText">
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Stock</div>
                                        </span>
                                        <span class="col-sm-8 text-left pl-0 pr-0">
                                            <input type="text" class="form-control notEmpty inputW50w100 name" name="remain" value="" id="remain" aria-describedby="Stock">
                                        </span>
                                    </div>
                                    <div class="col-sm-12 mt-5 mb-5" align="center">
                                        <button type="button" id="reload" class="btn btn-outline-danger">Restore</button>
                                        <button type="button" id="submit" class="btn btn-outline-success">Save</button>
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