insert into usuarios(habilitado, password, username) values(1,'$2a$10$561esWd5wPWJ.8ocbqMJnuZ4RmVPUTdPPpJMt4b5qnZ6wD9rM/Wn2','jpablo');
insert into usuarios(habilitado, password, username) values(1,'$2a$10$561esWd5wPWJ.8ocbqMJnuZ4RmVPUTdPPpJMt4b5qnZ6wD9rM/Wn2','dalaniz');
insert into roles(nombre, id_usuario) values ('ROLE_ADMIN', 1);
insert into roles(nombre, id_usuario) values ('ROLE_USER', 2);
insert into conductores(cuil,direccion, nombre_apellido, numero_telefono, vencimiento_licencia, usuario_id)values("20355521983","Aramburu 2130","Daniel Alaniz","2613438409","2024-04-10",2);
insert into vehiculos(anio, created_at, estado, km, marca, patente, tipo, ubicacion, updated_at, vencimiento_ruta, vencimiento_seguro, vencimiento_vtv,conductor_id) values(2015,"2024-04-10","ACTIVO",120222,"Scania 1114","ABC1232","camioneta","Campos San Luis","2024-04-20","2024-08-28","2024-04-10","2024-02-28",1);
