ALTER TABLE `USUARIO`
DROP FOREIGN KEY `FK_USUARIO_PESSOA;`

ALTER TABLE `USUARIO`
ADD CONSTRAINT `FK_USUARIO_PESSOA`
FOREIGN KEY (`ID_PESSOA`) REFERENCES PESSOA_FISICA(`ID`);