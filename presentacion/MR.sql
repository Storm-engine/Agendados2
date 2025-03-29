create database horario;
use horario;

create table estudiantes (
    id_estudiante int primary key auto_increment,
    nombre varchar(50) not null,
    correo varchar(50) unique not null,
    semestre int not null
);

create table aulas (
    id_aula int primary key auto_increment,
    tipo_aula enum('comun', 'informatica', 'laboratorio') not null,
    capacidad int not null
);

create table materias (
    id_materia int primary key auto_increment,
    nombre varchar(50) not null,
    carga_horaria int not null
);

create table profesores (
    id_profesor int primary key auto_increment,
    nombre varchar(50) not null,
    correo varchar(50) unique not null,
    restriccion_horaria enum('mañana', 'tarde', 'noche', 'ninguna') not null
);

create table grupos (
    id_grupo int primary key auto_increment,
    id_materia int not null,
    id_profesor int not null,
    foreign key (id_materia) references materias(id_materia),
    foreign key (id_profesor) references profesores(id_profesor)
);

create table estudiante_grupos (
    id_estudiante int not null,
    id_grupo int not null,
    primary key (id_estudiante, id_grupo),
    foreign key (id_estudiante) references estudiantes(id_estudiante),
    foreign key (id_grupo) references grupos(id_grupo ));

create table horarios (
    id_horario int primary key auto_increment,
    id_materia int not null,
    id_profesor int not null,
    id_aula int not null,
    id_grupo int not null,
    dia_semana enum('lunes', 'martes', 'miercoles', 'jueves', 'viernes', 'sabado') not null,
    hora_inicio time not null,
    hora_fin time not null,
    foreign key (id_materia) references materias(id_materia),
    foreign key (id_profesor) references profesores(id_profesor),
    foreign key (id_aula) references aulas(id_aula)
,
    foreign key (id_grupo) references grupos(id_grupo)
)
;
