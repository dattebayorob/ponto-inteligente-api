CREATE TABLE empresa(
	id BIGSERIAL PRIMARY KEY,
	cnpj VARCHAR(100) NOT NULL,
	razao_social VARCHAR(255) NOT NULL,
	data_criacao DATE NOT NULL,
	data_atualizacao DATE NOT NULL
);
CREATE TABLE funcionario(
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	senha VARCHAR(255) NOT NULL,
	cpf VARCHAR(11) NOT NULL,
	valor_hora DECIMAL(19,2),
	quantidade_horas_trabalho_dia FLOAT,
	quantidade_horas_almoco FLOAT,
	perfil VARCHAR(30) NOT NULL,
	data_criacao DATE NOT NULL,
	data_atualizacao DATE NOT NULL,
	id_empresa BIGINT NOT NULL REFERENCES empresa(id)
);
CREATE TABLE lancamento(
	id BIGSERIAL PRIMARY KEY,
	data TIMESTAMP NOT NULL,
	descricao VARCHAR(255),
	localizacao VARCHAR(255) NOT NULL,
	data_criacao DATE NOT NULL,
	data_atualizacao DATE NOT NULL,
	tipo VARCHAR(20) NOT NULL,
	id_funcionario BIGINT NOT NULL REFERENCES funcionario(id)
);