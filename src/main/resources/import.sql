insert into usuarios(habilitado, password, username) values(1,'$2a$10$561esWd5wPWJ.8ocbqMJnuZ4RmVPUTdPPpJMt4b5qnZ6wD9rM/Wn2','jpablo');
insert into usuarios(habilitado, password, username) values(1,'$2a$10$561esWd5wPWJ.8ocbqMJnuZ4RmVPUTdPPpJMt4b5qnZ6wD9rM/Wn2','dalaniz');
insert into roles(nombre, id_usuario) values ('ROLE_ADMIN', 1);
insert into roles(nombre, id_usuario) values ('ROLE_USER', 2);
insert into conductores(cuil,direccion, nombre_apellido, numero_telefono, vencimiento_licencia, usuario_id)values("20355521983","Aramburu 2130","Daniel Alaniz","2613438409","2024-03-27",2);
insert into vehiculos(anio, created_at, estado, km, marca, patente, tipo, ubicacion, updated_at, vencimiento_ruta, vencimiento_seguro, vencimiento_vtv,conductor_id) values(2015,"2024-02-27","ACTIVO",120222,"Scania 1114","ABC1232","camioneta","Campos San Luis","2024-02-27","2024-02-28","2024-02-28","2024-02-28",1);
insert into mantenimientos(categoria_averia, event_end, estado, event_start, sub_categoria_averia, text, tipo,conductor_id,vehiculo_id) values("motor", "2024-03-12 19:46:00.000000","ACTIVO","2024-03-12 19:46:00.000000","Sobrecalentamiento","Sobrecalienta a las 2000 vueltas","preventivo",1,1);
insert into notificaciones(asunto, created_at, mensaje, tipo, emisor_id, receptor_id) values ("Nueva Orden de Trabajo", "2024-03-19", "Se asigno la OT para el vehiculo: Scania 1114 ABC1232 Para realizar el sgtemantenimiento: electricoLuces Defectuosaspara la fecha:2024-03-20T11:32 Principal problema: Revisar luces", "ORDEN_TRABAJO", 1, 2);