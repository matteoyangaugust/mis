
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/manage/member/manage.js"/>'></script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="row formOutSide mt-3">
                        <c:import url="changePasswordTemplate.jsp"></c:import>
                        <div class="container">
                            <div class="row text-center">
                                <div class="col-sm-12 mt-3 mb-0 form-group form-inline text-center">
                                    <span class="warningText col-sm-2 h5">Name</span>
                                    <span class="warningText col-sm-3 h5">Username</span>
                                    <span class="warningText col-sm-3 h5">Role</span>
                                    <span class="warningText col-sm-4 h5">Action</span>
                                </div>
                                <hr class="col-sm-12 style14 labelText">
                                <c:forEach var="user" items="${users}">
                                    <form class="col-sm-12 mb-3 form-group form-inline mb-4 text-center userForm">
                                        <input type="hidden" class="sn" name="sn" value="${user.sn}">
                                        <input type="text" class="form-control notEmpty col-sm-2 name" name="name" value="${user.name}" aria-describedby="Name" placeholder="Must be less than 20 characters">
                                        <input type="text" class="form-control notEmpty col-sm-3 username" name="username" value="${user.username}" aria-describedby="Username" placeholder="Must be less than 30 characters">
                                        <span class="col-sm-3 p-0">
                                            <select class="custom-select" name="role" id="role">
                                                <option value="ROLE_ADMIN" <c:if test='${user.role eq "ROLE_ADMIN"}'>selected</c:if>>Administrator</option>
                                                <option value="ROLE_PURCHASE" <c:if test='${user.role eq "ROLE_PURCHASE"}'>selected</c:if>>Purchasing</option>
                                                <option value="ROLE_MOLDING" <c:if test='${user.role eq "ROLE_MOLDING"}'>selected</c:if>>Molding</option>
                                                <option value="ROLE_RUBBER" <c:if test='${user.role eq "ROLE_RUBBER"}'>selected</c:if>>Rubber</option>
                                                <option value="ROLE_STAMPING" <c:if test='${user.role eq "ROLE_STAMPING"}'>selected</c:if>>Stamping</option>
                                                <option value="ROLE_PRODUCT" <c:if test='${user.role eq "ROLE_PRODUCT"}'>selected</c:if>>Product</option>
                                            </select>
                                        </span>
                                        <span class="col-sm-4 p-0">
                                            <button type="button" value='${user.sn}' class="save btn btn-outline-success">Modify</button>
                                            <button type="button" value='${user.sn}' class="changePassword btn btn-outline-warning">Change Password</button>
                                            <button type="button" value='${user.sn}' class="delete btn btn-outline-danger">Delete</button>
                                        </span>
                                    </form>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>