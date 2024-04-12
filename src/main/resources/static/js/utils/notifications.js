document.addEventListener('DOMContentLoaded', function() {
    // Realizar la solicitud AJAX a /notificaciones
    cargarNotificaciones();
});

function cargarNotificaciones() {
    fetch('/notificaciones')
    .then(response => response.json())
    .then(notificaciones => {
        // Procesar las notificaciones
        const container = document.getElementById('notificaciones-container');
        const ul = document.createElement('ul'); // Crear una lista desordenada
        for (const id in notificaciones) {
            // Crear un elemento <li> para cada asunto
            const li = document.createElement('li');
            // Aplicar clases de Bootstrap
            li.classList.add('py-6', 'px-7', 'd-flex', 'align-items-center', 'dropdown-item');
            // Establecer el texto del asunto como contenido del <li>
            li.textContent = notificaciones[id];
            // Agregar un evento click al <li> para mostrar el mensaje
            li.addEventListener('click', function() {
                mostrarMensaje(id);
            });
            // Agregar el <li> a la lista
            ul.appendChild(li);
        }
        // Agregar la lista al contenedor
        container.appendChild(ul);
    })
    .catch(error => console.error('Error al cargar las notificaciones:', error));
}
function mostrarMensaje(id) {
    // Redireccionar a otra página HTML donde se pueda ver el mensaje completo
    window.location.href = '/view-notifications/' + id; // Reemplaza 'ver-mensaje.html' con la ruta de tu HTML de destino
}