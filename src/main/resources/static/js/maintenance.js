function actualizarSelect2() {
        var select1 = document.getElementById('select1');
        var select2 = document.getElementById('select2');

        // Limpiar el segundo select
        select2.innerHTML = '<option value="" disabled selected>Selecciona primero una categoría</option>';

        // Obtener la categoría seleccionada en el primer select
        var categoriaSeleccionada = select1.value;

        // Llenar el segundo select basado en la categoría seleccionada
        if (categoriaSeleccionada === 'liquido-fluidos') {
            agregarOpciones(select2, ['Fugas de Aceite', 'Fugas de Líquido Refrigerante', 'Baja Presión de Aceite','Nivel de Líquido de Frenos Bajo']);
        } else if (categoriaSeleccionada === 'motor') {
            agregarOpciones(select2, ['Fallo en el Arranque', 'Sobrecalentamiento', 'Pérdida de Potencia','Ruidos Anormales','Luces de Advertencia del Motor']);
        } else if (categoriaSeleccionada === 'electrico') {
            agregarOpciones(select2, ['Batería Descargada', 'Luces Defectuosas', 'Fusibles Fundidos','Alternador Defectuoso']);
        } else if (categoriaSeleccionada === 'suspencion-direccion') {
           agregarOpciones(select2, ['Desgaste Irregular de Neumáticos', 'Vibraciones al Conducir', 'Dirección Dura o Floja','Ruidos al Girar']);
        } else if (categoriaSeleccionada === 'frenos') {
           agregarOpciones(select2, ['Frenos Desgastados', 'Fuga de Líquido de Frenos', 'Frenado Irregular','Ruido al Frenar']);
         } else if (categoriaSeleccionada === 'transmision') {
           agregarOpciones(select2, ['Transmisión Deslizante', 'Embrague Defectuoso', 'Ruidos en la Transmisión']);
         } else if (categoriaSeleccionada === 'espejos') {
           agregarOpciones(select2, ['Retrovisores', 'Parabrisas', 'Lunetas','Laterales']);
          } else if (categoriaSeleccionada === 'cabina') {
          agregarOpciones(select2, ['Torpedo', 'Asientos']);
          } else if (categoriaSeleccionada === 'preventivo') {
            agregarOpciones(select2, ['Cambio de aceite', 'Cambio de aceite y filtros']);
        }
    }


    function agregarOpciones(select, opciones) {
        for (var i = 0; i < opciones.length; i++) {
            var option = document.createElement('option');
            option.value = opciones[i];
            option.text = opciones[i];
            select.add(option);
        }
    }