ALTER TABLE `PACIENTE` 
ADD COLUMN `CODIGO_SUS` VARCHAR(15) NULL AFTER `ID_PESSOA`,
ADD UNIQUE INDEX `CODIGO_SUS_UNIQUE` (`CODIGO_SUS` ASC);