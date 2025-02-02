CREATE TABLE autuacao (
	id bigint NOT NULL AUTO_INCREMENT,
	veiculo_id bigint NOT NULL,
	descricao text NOT NULL,
	valor_multa decimal(10,2) NOT NULL,
	data_ocorrencia datetime NOT NULL,

	PRIMARY KEY (id)
);

ALTER TABLE autuacao ADD CONSTRAINT fk_autuacao_veiculo
FOREIGN KEY (veiculo_id) REFERENCES veiculo (id);