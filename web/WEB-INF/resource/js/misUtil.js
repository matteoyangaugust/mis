/**
 *
 * @param unitNumber table material.unit
 */
function getUnitForMaterial(unitNumber){
    switch(unitNumber){
        case 1:
            return "g";
        case 2:
            return "kg";
        case 3:
            return "piece";
        default:
            return "";
    }
}