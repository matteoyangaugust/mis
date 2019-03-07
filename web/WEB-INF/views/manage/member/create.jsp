
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/manage/member/create.js"/>'></script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="row formOutSide mt-3">
                        <div class="container">
                            <div class="row text-center">
                                <form class="col-sm-12" id="memberForm">
                                    <div class="form-group form-inline mt-3">
                                        <label for="name" class="labelText col-sm-4">Name</label>
                                        <input type="text" class="form-control notEmpty col-sm-4" name="name" id="name" aria-describedby="Name" >
                                    </div>
                                    <div class="form-group form-inline mt-1">
                                        <label for="username" class="labelText col-sm-4">Username</label>
                                        <input type="text" class="form-control notEmpty col-sm-4" name="username" id="username" aria-describedby="Username" >
                                    </div>
                                    <div class="form-group form-inline mt-1">
                                        <label for="password" class="labelText col-sm-4">Password</label>
                                        <input type="password" class="form-control notEmpty col-sm-4" name="password" id="password" aria-describedby="Password" >
                                    </div>
                                    <div class="form-group form-inline mt-1">
                                        <label for="passwordConfirm" class="labelText col-sm-4">Password Confirm</label>
                                        <input type="password" class="form-control notEmpty col-sm-4" name="passwordConfirm" id="passwordConfirm" aria-describedby="Password Confirm" >
                                    </div>
                                    <div class="form-group form-inline mt-1">
                                        <label for="role" class="labelText col-sm-4">Role</label>
                                        <select class="custom-select col-sm-4" name="role" id="role">
                                            <option value="none">Select One</option>
                                            <option value="ROLE_ADMIN">Administrator</option>
                                            <option value="ROLE_PURCHASE">Purchasing</option>
                                            <option value="ROLE_MOLDING">Molding</option>
                                            <option value="ROLE_RUBBER">Rubber</option>
                                            <option value="ROLE_STAMPING">Stamping</option>
                                            <option value="ROLE_PRODUCT">Product</option>
                                        </select>
                                    </div>
                                </form>
                                <div class="col-sm-12 mt-5 mb-5" align="center">
                                    <button type="button" id="reload" class="btn btn-outline-danger">Restore</button>
                                    <button type="button" id="submit" class="btn btn-outline-success">Save</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>