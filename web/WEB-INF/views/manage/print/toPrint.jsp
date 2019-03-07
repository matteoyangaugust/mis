
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:base>
    <jsp:attribute name="javascript">
        <script src='<c:url value="/js/manage/print/print.js"/>'></script>
        <script>
            var result = ${result};
        </script>
    </jsp:attribute>
    <jsp:attribute name="style"></jsp:attribute>
    <jsp:body>
        <div class="col-sm-12 text-center">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="row formOutSide mt-3">
                        <div class="container">
                            <div class="row text-center">
                                <form class="col-sm-12" id="printForm" action='<c:url value="/manage/print/printPdf.do"/>' method="post">
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Report File Extension</div>
                                        </span>
                                        <div class="col-sm-4 text-left pl-0 pr-0 form-inline" data-toggle="buttons">
                                            <label class="btn btn-outline-primary active inputW50w100 reportTypeLabel">
                                                <input type="radio" name="reportType" class="reportType" id="pdf" value="pdf" autocomplete="off" checked>PDF
                                            </label>
                                            <label class="btn btn-outline-primary inputW50w100 reportTypeLabel">
                                                <input type="radio" name="reportType" class="reportType" id="excel" value="excel" autocomplete="off">EXCEL
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group form-inline mt-3 row">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Report Category</div>
                                        </span>
                                        <div class="col-sm-4 text-left pl-0 pr-0 form-inline" data-toggle="buttons">
                                            <label class="btn btn-outline-primary active inputW50w100 reportChoiceLabel">
                                                <input type="radio" class="reportChoice" name="reportChoice" id="generationReport" value="generation" autocomplete="off" checked>Manufacture Report
                                            </label>
                                            <label class="btn btn-outline-primary inputW50w100 reportChoiceLabel">
                                                <input type="radio" class="reportChoice" name="reportChoice" id="stockReport" value="stock" autocomplete="off">Stock Report
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group form-inline mt-3 row duration">
                                        <span class="col-sm-4 text-right">
                                            <div class="labelText labelDivText">Duration</div>
                                        </span>
                                        <div class="col-sm-8 text-left pl-0 pr-0 greyText">
                                            <input id="beginDate" name="beginDate" type="date" class="form-control date">
                                            <input id="endDate" name="endDate" type="date" class="form-control date">
                                        </div>
                                    </div>
                                </form>
                                <div class="col-sm-12 mt-2 mb-5" align="center">
                                    <button type="button" id="submit" class="btn btn-outline-success">Generate</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>