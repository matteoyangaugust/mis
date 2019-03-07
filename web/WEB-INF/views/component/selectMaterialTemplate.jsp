<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="row selectMaterialTemplate swalLabelText">
    <hr class="col-sm-12 style14 labelText">
    <div class="swalLabelText" style="display:inline-block; width: 50%; height:300px; overflow:auto; border-right: 1px solid #c0c0c0">
        <h3 class="mb-5">Material List</h3>
        <div class="componentList btn-group-vertical">
            <c:forEach var="material" items="${materials}">
                <c:choose>
                    <c:when test="${material.active eq 0}">
                        <input type="button" class="text-left btn btn-outline-danger" value="${material.identifier}-${material.name}(Disabled)">
                    </c:when>
                    <c:otherwise>
                        <input type="button" class="text-left btn btn-outline-success materialBtn" value="${material.identifier}-${material.name}">
                    </c:otherwise>
                </c:choose>

                <input type="hidden" class="sn" value="${material.sn}">
            </c:forEach>
        </div>
    </div>
    <div class="swalLabelText" id="selectedBlock" style="display:inline-block; width: 50%; height:300px; overflow:auto;">
        <h3 class="mb-5">Selected</h3>
    </div>
    <hr class="col-sm-12 style14 labelText">
</form>