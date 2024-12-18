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

function buscar() {
    var marca = document.getElementById('input-search').value;

    // Realiza la solicitud AJAX al servidor
    fetch('/vehicles/buscar?marca=' + encodeURIComponent(marca)) // Cambia la URL para que coincida con tu controlador
        .then(response => response.text())
        .then(html => {
            document.getElementById('resultados').innerHTML = html;
        })
        .catch(error => console.error('Error al realizar la búsqueda:', error));
}


let contadorCubiertas = 0; // Contador general para los identificadores únicos
let cubiertasActivas = 0; // Contador dinámico para controlar las cubiertas activas

// Opciones de posiciones para diferentes tipos de vehículos
const opcionesPosicion = {
    automovil: ['Frontal Izquierdo', 'Frontal Derecho', 'Trasero Izquierdo', 'Trasero Derecho'],
    camion: ['Eje Delantero Izquierdo', 'Eje Delantero Derecho', 'Eje Trasero Izquierdo', 'Eje Trasero Derecho'],
    camioneta: ['Frontal Izquierdo', 'Frontal Derecho', 'Trasero Izquierdo', 'Trasero Derecho'],
    agricola: ['Delantera Izquierda', 'Delantera Derecha', 'Trasera Izquierda', 'Trasera Derecha'],
    semi: ['Izquierda', 'Derecha'],
    jaula: ['Eje Frontal', 'Eje Medio', 'Eje Trasero'],
    carreton: ['Izquierda Interna', 'Izquierda Externa', 'Derecha Interna', 'Derecha Externa'],
};

// Función para agregar una nueva cubierta
function agregarCubierta() {
    contadorCubiertas++;
    cubiertasActivas++;

    const tipoVehiculo = document.getElementById('tipo').value; // Obtener el tipo de vehículo seleccionado
    const opciones = opcionesPosicion[tipoVehiculo] || []; // Obtener las opciones correspondientes

    const contenedorCubiertas = document.getElementById('cubiertasContainer');
    const cubiertaDiv = document.createElement('div');
    cubiertaDiv.className = 'mb-3 p-3 border rounded bg-light'; // Clases de Bootstrap para estilo
    cubiertaDiv.id = `cubierta-${contadorCubiertas}`;

    cubiertaDiv.innerHTML = `
        <div class="mb-3">
            <h5 class="fw-semibold">Cubierta ${cubiertasActivas}</h5>
        </div>
        <div class="mb-3">
            <label for="codigo-${contadorCubiertas}" class="form-label fw-semibold">Código:</label>
            <input type="text" class="form-control" id="codigo-${contadorCubiertas}"
                   name="cubiertas[${contadorCubiertas}].codigo" required>
        </div>
        <div class="mb-3">
            <label for="posicion-${contadorCubiertas}" class="form-label fw-semibold">Posición:</label>
            <select class="form-select" id="posicion-${contadorCubiertas}"
                    name="cubiertas[${contadorCubiertas}].posicion" required>
                ${opciones.map(pos => `<option value="${pos}">${pos}</option>`).join('')}
            </select>
        </div>
        <div class="mb-3">
            <label for="marca-${contadorCubiertas}" class="form-label fw-semibold">Marca:</label>
            <input type="text" class="form-control" id="marca-${contadorCubiertas}"
                   name="cubiertas[${contadorCubiertas}].marca">
        </div>
        <div class="mb-3">
            <label for="modelo-${contadorCubiertas}" class="form-label fw-semibold">Modelo:</label>
            <input type="text" class="form-control" id="modelo-${contadorCubiertas}"
                   name="cubiertas[${contadorCubiertas}].modelo">
        </div>
         <div class="mb-3">
                    <label for="medida-${contadorCubiertas}" class="form-label fw-semibold">Medida:</label>
                    <input type="text" class="form-control" id="medida-${contadorCubiertas}"
                           name="cubiertas[${contadorCubiertas}].medida">
                </div>
        <div class="mb-3">
            <label for="presionRecomendada-${contadorCubiertas}" class="form-label fw-semibold">
                Presión Recomendada:
            </label>
            <input type="number" class="form-control" step="0.1" id="presionRecomendada-${contadorCubiertas}"
                   name="cubiertas[${contadorCubiertas}].presionRecomendada">
        </div>
        <button type="button" class="btn btn-danger btn-sm" onclick="eliminarCubierta(${contadorCubiertas})">
            Eliminar Cubierta
        </button>
    `;

    contenedorCubiertas.appendChild(cubiertaDiv);
}

// Función para eliminar una cubierta existente

function eliminarCubierta(id) {
    const cubiertaDiv = document.getElementById(`cubierta-${id}`);
    if (cubiertaDiv) {
        cubiertaDiv.remove();
        cubiertasActivas--; // Reducir el contador dinámico
        actualizarTitulosCubiertas(); // Actualizar todos los títulos, IDs y nombres
    }
}

// Función para actualizar los títulos, IDs y names de las cubiertas
function actualizarTitulosCubiertas() {
    const contenedorCubiertas = document.getElementById('cubiertasContainer');
    const cubiertas = contenedorCubiertas.querySelectorAll('.mb-3.p-3.border'); // Seleccionar todas las cubiertas

    cubiertas.forEach((cubierta, index) => {
        const nuevaPosicion = index + 1; // Nueva posición de la cubierta
        cubierta.id = `cubierta-${nuevaPosicion}`; // Actualizar el ID del contenedor

        // Actualizar el título
        const titulo = cubierta.querySelector('h5.fw-semibold');
        if (titulo) {
            titulo.textContent = `Cubierta ${nuevaPosicion}`;
        }

        // Actualizar los IDs y nombres de los inputs dentro de la cubierta
        const inputs = cubierta.querySelectorAll('input, select');
        inputs.forEach((input) => {
            if (input.id.includes('-')) {
                const [nombreBase] = input.id.split('-'); // Obtener la parte base del ID
                input.id = `${nombreBase}-${nuevaPosicion}`; // Asignar nuevo ID
            }

            if (input.name) {
                const [nombreBase] = input.name.split('['); // Obtener la parte base del name
                input.name = `${nombreBase}[${nuevaPosicion - 1}]${input.name.slice(input.name.indexOf(']') + 1)}`; // Actualizar name
            }
        });
    });
}

// Asignar evento al botón de agregar cubierta
document.addEventListener('DOMContentLoaded', () => {
    const agregarButton = document.getElementById('agregarCubierta');
    if (agregarButton) {
        agregarButton.addEventListener('click', agregarCubierta);
    }
});








