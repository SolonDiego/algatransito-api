CREATE TABLE veiculo (
	id bigint not null auto_increment,
	proprietario_id bigint NOT NULL,
	marca varchar(20) NOT NULL,
	modelo varchar(20) NOT NULL,
	placa varchar(7) NOT NULL,
	status varchar(20) NOT NULL,
	data_cadastro datetime NOT NULL,
	data_apreensao datetime,

	PRIMARY KEY (id)
);

ALTER TABLE veiculo ADD CONSTRAINT fk_veiculo_proprietario
FOREIGN KEY (proprietario_id) REFERENCES proprietario (id);

ALTER TABLE veiculo ADD CONSTRAINT uk_veiculo UNIQUE (placa);