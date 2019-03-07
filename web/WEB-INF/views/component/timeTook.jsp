<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src='<c:url value="/js/component/timeTook.js"/>'></script>
<div class="btn-group col-sm-12 mb-2 pl-0" data-toggle="buttons">
    <div class="btn btn-outline-primary timePurposeBlock active col-sm-6">
        <input type="radio" class='timePurpose' name="timePurpose" id="total" autocomplete="off" checked value="total" style="display: none">
        <input type="hidden" class="component_sn" value="">
        Total Duration
    </div>
    <div class="btn btn-outline-primary timePurposeBlock col-sm-6">
        <input type="radio" class='timePurpose' name="timePurpose" id="single" autocomplete="off" value="single" style="display: none">
        <input type="hidden" class="component_sn" value="">
        Duration per Procedure
    </div>
</div>
<div class="form-inline form-group">
    <input type="number" class="numInput form-control notEmpty timeNumber" style="width: 18%" value="0" name="time_took_of_day" id="time_took_of_day" aria-describedby="Day">
    <span class="text-muted timeUnit">Day</span>
    <input type="number" class="numInput form-control notEmpty timeNumber" style="width: 18%" value="0" max="23" name="time_took_of_hour" id="time_took_of_hour" aria-describedby="Hour">
    <span class="text-muted timeUnit">Hour</span>
    <input type="number" class="numInput form-control notEmpty timeNumber" style="width: 18%" value="0" max="59" name="time_took_of_minute" id="time_took_of_minute" aria-describedby="Minute">
    <span class="text-muted timeUnit">Minute</span>
    <input type="number" class="numInput form-control notEmpty timeNumber" style="width: 18%" value="0" max="59" name="time_took_of_second" id="time_took_of_second" aria-describedby="Second">
    <span class="text-muted timeUnit">Second</span>
    <input type="hidden" name="total_duration" id="total_duration" value="0" aria-describedby="Duration">
</div>
