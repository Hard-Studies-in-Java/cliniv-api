CREATE TABLE `METODO_PAGAMENTO` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `NOME` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

INSERT INTO METODO_PAGAMENTO (NOME) VALUES ('DINHEIRO');
INSERT INTO METODO_PAGAMENTO (NOME) VALUES ('C. DÉBITO');
INSERT INTO METODO_PAGAMENTO (NOME) VALUES ('C. CRÉDITO');
INSERT INTO METODO_PAGAMENTO (NOME) VALUES ('PIX');
INSERT INTO METODO_PAGAMENTO (NOME) VALUES ('BOLETO');
INSERT INTO METODO_PAGAMENTO (NOME) VALUES ('CHEQUE');