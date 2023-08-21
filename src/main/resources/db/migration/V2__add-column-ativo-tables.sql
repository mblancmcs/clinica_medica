ALTER TABLE pacientes ADD COLUMN ativo tinyint not null default true;
ALTER TABLE consultas ADD COLUMN ativo tinyint not null default true;
ALTER TABLE atendimentos ADD COLUMN ativo tinyint not null default true;