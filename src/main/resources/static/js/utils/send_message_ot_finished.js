$(document).ready(function() {
    $('#formulario-fin-ot').submit(function(event) {
        // Evitar que se envíe el formulario automáticamente
        event.preventDefault();

        // Obtener el ID del conductor seleccionado
        var idMantenimiento = $('#idMantenimiento').val();
        var detalles_mantenimiento = $('#descripcion_mantenimiento').val();
        var costo = $('#costo').val();

        console.log("id desde js: " + idMantenimiento)

        // Realizar la solicitud AJAX al backend para obtener el número de teléfono del conductor
        $.ajax({
            url: '/maintenance/obtener-telefono-emisor',
            type: 'GET',
            data: { idMantenimiento: idMantenimiento },
            success: function(response) {
                // Una vez que se obtenga el número de teléfono del conductor, construir la URL de la API de WhatsApp
                var telefonoEmisor = response.telefono;
                var nombreEmisor = response.nombre;
                var conductor = response.conductor;
                var vehiculo = response.vehiculo;
                var mensaje = encodeURIComponent('Hola ' + nombreEmisor + '. Te informamos que la persona: ' + conductor + ', ha realizado el mantenimiento previsto del vehiculo: ' +
                vehiculo + '. *Detalle de Mantenimiento:* ' + detalles_mantenimiento + ' Costo: $ ' + costo);
                var whatsappURL = 'https://api.whatsapp.com/send?phone=' + telefonoEmisor + '&text=' + mensaje;

                // Redirigir a la URL de WhatsApp
                window.open(whatsappURL, '_blank');

                // Enviar el formulario
                $('#formulario-fin-ot').unbind('submit').submit();
            },
            error: function(xhr, status, error) {
                // Manejar el error si la solicitud AJAX falla
                console.error('Error al obtener el número de teléfono del conductor:', error);
            }
        });
    });
});