$(document).ready(function() {
    $('#form-checklist').submit(function(event) {
        // Evitar que se envíe el formulario automáticamente
        event.preventDefault();

        // Obtener el ID del conductor seleccionado
        var conductor = $('#neumaticos').val();

        if(conductor == 'NO'){
               console.log("dijo NO");
        }
    });
});