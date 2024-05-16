function soloNumeros(event) {
    const tecla = event.key;
    // Permitir teclas de control como Enter, retroceso, etc.
    if (tecla === 'Enter' || tecla === 'Backspace' || tecla === 'Delete' || tecla === 'Tab') {
        return true;
    }
    // Permitir solamente números del 0 al 9
    if (/[0-9]/.test(tecla)) {
        return true;
    }
    // Si la tecla ingresada no es un número, impedir su ingreso
    return false;
}

function actualizarEstado(checkbox) {
    var id = checkbox.id; // Obtener el ID del checkbox
    //var activo = checkbox.checked; // Obtener el estado actual del checkbox

    // Enviar una solicitud al backend utilizando AJAX
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/checklist/actualizarestado/" + id, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Respuesta del servidor
            console.log(xhr.responseText);
        }
    };
    xhr.send();
}
