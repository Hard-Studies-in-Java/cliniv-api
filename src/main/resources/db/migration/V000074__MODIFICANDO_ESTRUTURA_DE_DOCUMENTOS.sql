ALTER TABLE `VISITA_EVENTO` 
DROP COLUMN `ID_PACIENTE`;

ALTER TABLE `DOCUMENTO_DIGITAL` 
ADD COLUMN `ID_VISITA_EVENTO` BIGINT(20) NOT NULL AFTER `ID`,
ADD INDEX `FK_DOC_VISITA_EVENT_idx` (`ID_VISITA_EVENTO` ASC);
ALTER TABLE `DOCUMENTO_DIGITAL` 
ADD CONSTRAINT `FK_DOC_VISITA_EVENT`
  FOREIGN KEY (`ID_VISITA_EVENTO`)
  REFERENCES `VISITA_EVENTO` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `INSTITUTO` 
ADD COLUMN `LOGO_TIPO` VARCHAR(45) NULL AFTER `DATA_FIM`;