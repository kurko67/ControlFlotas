insert into usuarios(habilitado, password, username) values(1,'$2a$10$561esWd5wPWJ.8ocbqMJnuZ4RmVPUTdPPpJMt4b5qnZ6wD9rM/Wn2','jpablo');
insert into roles(nombre, id_usuario) values ('ROLE_ADMIN', 1)
insert into vehiculos(anio, created_at, estado, km, marca, patente, tipo, ubicacion, updated_at, vencimiento_ruta, vencimiento_seguro, vencimiento_vtv) values('2015',
'2024-02-27 11:45:01.618000','ACTIVO',120222,'Scania 1114','ABC1232','camioneta','Campos a','2024-02-27 11:45:01.618000','2024-02-28 00:00:00.000000','2024-02-28 00:00:00.000000','2024-02-28 00:00:00.000000');

insert into mantenimientos(categoria_averia, created_at, event_end, estado, fecha_vencimiento, event_start, sub_categoria_averia, text, tipo, vehiculo_id) values(
'motor', '2024-02-27 11:49:27.372000','2024-02-27 11:49:27.377407','ACTIVO','2024-02-28 00:00:00.000000','2024-02-27 11:49:27.377407','Sobrecalentamiento','Sobrecalienta a las 2000 vueltas','preventivo',1);