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
