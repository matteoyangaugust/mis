<div class="form-group form-inline selectedComponent" style="display: none">
    <input type="hidden" class="component_sn" value="">
    <span class="fa fa-trash-o mt-0" onclick="deleteComponent(this)"></span>
    <label class="text-left swalLabelText ml-1 componentName" style="width:30%; display:inline-block"></label>
    <input type="text" class="form-control numInput w-50 componentQuantity" style="display:inline-block" placeholder="Q"
           onkeyup="swalNumInputCheck(this), recordQuantity(this)"
           onchange="swalNumInputCheck(this), recordQuantity(this)"
           onmousedown="swalNumInputCheck(this), recordQuantity(this)">
</div>