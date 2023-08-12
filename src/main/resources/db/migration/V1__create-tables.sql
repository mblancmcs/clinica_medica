CREATE TABLE pacientes (
	id int not null primary key auto_increment,
    cpf bigint not null,
    nome varchar(255) not null,
    data_nascimento date not null,
    telefone text not null
);

CREATE TABLE consultas (
	id int not null auto_increment primary key,
    data_consulta datetime null,
    senha int null,
    motivo varchar(255) not null,
    plano_particular varchar(50) not null,
    id_paciente int not null,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id)
);

CREATE TABLE atendimentos (
	id int not null auto_increment primary key,
    diagnostico text null,
    receita_remedios text null,
    solicitacao_retorno  text null,
    complemento  text null,
    id_consulta int not null,
    FOREIGN KEY (id_consulta) REFERENCES consultas(id)
);

CREATE TABLE usuarios (
	id int not null auto_increment primary key,
    login varchar(30) not null unique,
    senha varchar(255) not null,
    perfil varchar(30) not null default 'USER'
)