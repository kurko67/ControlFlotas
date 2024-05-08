$(document).ready(function() {
    $('#formulario').submit(function(event) {
        // Evitar que se envíe el formulario automáticamente
        event.preventDefault();

        // Obtener el ID del conductor seleccionado
        var conductorId = $('#conductorid').val();
        var nombreConductor = $('#conductorid option:selected').text();
        var tipo = $('#tipo').val();
        var marca = $('#vehiculo-marca').val();
        var fecha = $('#start').val();
        var detalles = $('#descripcion_problema').val();
        var lugar = $('#lugar_atencion').val();

        // Realizar la solicitud AJAX al backend para obtener el número de teléfono del conductor
        $.ajax({
            url: '/maintenance/obtener-telefono-conductor',
            type: 'GET',
            data: { conductorId: conductorId },
            success: function(response) {
                // Una vez que se obtenga el número de teléfono del conductor, construir la URL de la API de WhatsApp
                var telefonoConductor = response.telefono;
                var mensaje = encodeURIComponent('Hola ' + nombreConductor + '. Te he asignado un mantenimiento ' + tipo + ', para el vehiculo: ' +
                marca + ', el dia: ' + fecha + '. *Mas detalles:*' + detalles + ', Lugar de atención o taller: ' + lugar + ', Que tengas buen dia!');
                var whatsappURL = 'https://api.whatsapp.com/send?phone=' + telefonoConductor + '&text=' + mensaje;

                // Redirigir a la URL de WhatsApp
                window.open(whatsappURL, '_blank');

                // Enviar el formulario
                $('#formulario').unbind('submit').submit();
            },
            error: function(xhr, status, error) {
                // Manejar el error si la solicitud AJAX falla
                console.error('Error al obtener el número de teléfono del conductor:', error);
            }
        });
    });
});