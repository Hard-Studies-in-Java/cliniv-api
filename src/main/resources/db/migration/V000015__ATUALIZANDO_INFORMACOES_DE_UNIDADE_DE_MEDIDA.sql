ALTER TABLE `UNIDADE_MEDIDA` 
DROP INDEX `DESCRICAO_UNIQUE`;

UPDATE UNIDADE_MEDIDA A
SET TERMO = (SELECT SUBSTRING_INDEX(DESCRICAO, ' ', 1) FROM UNIDADE_MEDIDA B WHERE A.ID = B.ID);

UPDATE UNIDADE_MEDIDA A
SET DESCRICAO = (SELECT REPLACE(DESCRICAO, CONCAT(TERMO, ' '), '') FROM UNIDADE_MEDIDA B WHERE A.ID = B.ID);

UPDATE UNIDADE_MEDIDA
SET TERMO = 'COM EFEV', DESCRICAO = 'Comprimido Efervecente'
WHERE ID = 50;

UPDATE UNIDADE_MEDIDA
SET TERMO = 'COM MST', DESCRICAO = 'Comprimido Mastigável'
WHERE ID = 51;

ALTER TABLE `UNIDADE_MEDIDA` 
CHANGE COLUMN `TERMO` `TERMO` VARCHAR(45) NOT NULL;
