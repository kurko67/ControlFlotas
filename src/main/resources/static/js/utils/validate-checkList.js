 document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("miFormulario").addEventListener("submit", function(event) {
            var neumaticosValue = document.getElementById("neumaticos").value;
            var neumaticosDetallesValue = document.getElementById("neumaticosDdetalles").value;

            var fluidosValue = document.getElementById("fluidos").value;
            var fluidosDetallesValue = document.getElementById("fluidosDetalles").value;

            var lucesValue = document.getElementById("luces").value;
            var lucesDetallesValue = document.getElementById("lucesDetalles").value;

            var frenosValue = document.getElementById("frenos").value;
            var frenosDetallesValue = document.getElementById("frenosDetalles").value;

            var trenDelanteroValue = document.getElementById("trenDelantero").value;
            var trenDelanteroDetallesValue = document.getElementById("trenDelanteroDetalles").value;

            var seguridadValue = document.getElementById("seguridad").value;
            var seguridadDetallesValue = document.getElementById("seguridadDetalles").value;

            var carroceriaValue = document.getElementById("carroceria").value;
            var carroceriaDetallesValue = document.getElementById("carroceriaDetalles").value;

            var documentacionValue = document.getElementById("documentacion").value;
            var documentacionDetallesValue = document.getElementById("documentacionDetalles").value;

            if (neumaticosValue === "false" && neumaticosDetallesValue.trim() === "") {
                event.preventDefault(); // Detener el envío del formulario
                alert("Por favor, especifique detalles sobre el problema con los neumáticos.");

            }

             if (fluidosValue === "false" && fluidosDetallesValue.trim() === "") {
                 event.preventDefault(); // Detener el envío del formulario
                 alert("Por favor, especifique detalles sobre el problema con los fluidos.");

             }

              if (lucesValue === "false" && lucesDetallesValue.trim() === "") {
                  event.preventDefault(); // Detener el envío del formulario
                  alert("Por favor, especifique detalles sobre el problema con las luces.");

              }

               if (frenosValue === "false" && frenosDetallesValue.trim() === "") {
                   event.preventDefault(); // Detener el envío del formulario
                   alert("Por favor, especifique detalles sobre el problema con los frenos.");

               }

                if (trenDelanteroValue === "false" && trenDelanteroDetallesValue.trim() === "") {
                    event.preventDefault(); // Detener el envío del formulario
                    alert("Por favor, especifique detalles sobre el problema con el tren delantero.");

                }

                if (seguridadValue === "false" && seguridadDetallesValue.trim() === "") {
                    event.preventDefault(); // Detener el envío del formulario
                    alert("Por favor, especifique detalles sobre el problema con la seguridad.");

                }

                 if (carroceriaValue === "false" && carroceriaDetallesValue.trim() === "") {
                    event.preventDefault(); // Detener el envío del formulario
                    alert("Por favor, especifique detalles sobre el problema con la carroceria.");

                 }

                  if (documentacionValue === "false" && documentacionDetallesValue.trim() === "") {
                      event.preventDefault(); // Detener el envío del formulario
                      alert("Por favor, especifique detalles sobre el problema con la documentación.");

                  }

        });
    });