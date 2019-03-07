
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:base>
    <jsp:attribute name="javascript">
        <script>
            var courses = ${courses};
            var result;
            var afterUploading = ${afterUploading};
            <c:if test="${afterUploading}">
                result = ${result};
            </c:if>
        </script>
        <script src='<c:url value="/js/manage/course/upload.js"/> '></script>
    </jsp:attribute>
    <jsp:body>
        <form:form id="imageForm" method="post" enctype='multipart/form-data' action='${pageContext.request.contextPath}/manage/course/sendImages.do'>
            <input type="hidden" name="course_sn" value="">
            <input type="hidden" name="deletedImageSn" value="">
            <jsp:include page="templates/upload.jsp"></jsp:include>
            <div class="modal fade" id="imageUploadModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="row">
                        <div class="col-sm-12 mb-3" align="center">
                            <h1 class="text-white">上傳照片</h1>
                        </div>
                        <div class="col-sm-12" id="wrapperParent" align="center"></div>
                        <div class="col-sm-12" align="center">
                            <div class="image-holder" id="addHolder" style="opacity: 0.2">
                                <div class="addImage" >
                                    <i class="fa fa-plus addImagePlus"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12" align="center">
                            <div class="btn btn-lg btn-danger" id="cancelImage">取消</div>
                            <div class="btn btn-lg btn-success" id="sendImages">上傳</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-sm-12" align="center">
                        <h1>督課老師：<strong>${userName}</strong> 督課列表</h1>
                    </div>
                </div>
                <hr class="style13">
                <div class="col-sm-12 mt-sm-5" align="center">
                    <table class="table" id="courseList">
                        <thead>
                        <tr>
                            <th>項次</th>
                            <th>課程名稱</th>
                            <th>開課日期</th>
                            <th>講師</th>
                            <th>編輯課程</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </form:form>
    </jsp:body>
</t:base>