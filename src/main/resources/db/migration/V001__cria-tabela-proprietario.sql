CREATE TABLE proprietario (
	id bigint NOT NULL AUTO_INCREMENT,
	nome varchar(60) NOT NULL,
	email varchar(255) NOT NULL,
	telefone varchar(20) NOT NULL,

	PRIMARY KEY (id)
);

ALTER TABLE proprietario ADD CONSTRAINT uk_proprietario UNIQUE (email);