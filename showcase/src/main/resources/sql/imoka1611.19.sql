USE imoka;



###
### machines_types
### specify IF NONE, PLC, HMI, ...
####################################################################
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS machines_types;
CREATE TABLE IF NOT EXISTS machines_types (
	id 					INT 			NOT NULL AUTO_INCREMENT,
	`type`				VARCHAR(15) 	NOT NULL,
    designation			VARCHAR(255) 	NOT NULL,
    deleted				BIT 			NOT NULL 	DEFAULT 0,
	created				DATETIME 		NOT NULL	DEFAULT 0,
	changed				TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	UNIQUE INDEX ui_id (id ASC),
    INDEX `type` (`type` ASC, id ASC)
)
ENGINE=INNODB
DEFAULT CHARACTER SET = utf8;
SET FOREIGN_KEY_CHECKS=1;

INSERT INTO `imoka`.`machines_types` (`type`, `designation`, `created`) VALUES 
    ('NONE', 'Unspecify', NOW()),
    ('PLC', 'Automate', NOW()),
    ('HMI', 'Interface Opérateur', NOW());


###
### machines
### identifies machine
####################################################################
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS machines;
CREATE TABLE IF NOT EXISTS machines (
	id 					INT 			NOT NULL AUTO_INCREMENT,
	adress				VARCHAR(15) 	NOT NULL,
    machine 			VARCHAR(255) 	NOT NULL,
    rack		 		INT 			NOT NULL	DEFAULT 1,
    slot		 		INT 			NOT NULL	DEFAULT 2,
    `type`				VARCHAR(15) 	NOT NULL,
	deleted				BIT 			NOT NULL 	DEFAULT 0,
	created				DATETIME 		NOT NULL	DEFAULT 0,
	changed				TIMESTAMP 		NOT NULL 	DEFAULT CURRENT_TIMESTAMP 
													ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	UNIQUE INDEX ui_id (id ASC),
    INDEX adress (adress ASC, id ASC),
    INDEX machine (machine ASC),
    INDEX `FK_type` (`type` ASC),
    CONSTRAINT `FK_type`
		FOREIGN KEY (`type`) REFERENCES `imoka`.`machines_types` (`type`)
)
ENGINE=INNODB
DEFAULT CHARACTER SET = utf8;
SET FOREIGN_KEY_CHECKS=1;

INSERT INTO `imoka`.`machines` (`adress`, `machine`, `rack`, `slot`, `type`, `deleted`, `created`) VALUES 
	('10.1.21.11', 'C01 : PLC Soutireuse SREV131 HS', '1', '2', 'PLC', 0, NOW()),
    ('10.1.21.14', 'C01 : HMI Soutireuse SREV131 HS', '1', '2', 'HMI', 0, NOW());

