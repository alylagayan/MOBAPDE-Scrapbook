DROP SCHEMA IF EXISTS mobapde_db;
CREATE SCHEMA mobapde_db;
USE mobapde_db;

CREATE TABLE mobapde_db.users(
	id INT AUTO_INCREMENT NOT NULL,
    user_name VARCHAR(30) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    pass_code VARCHAR(30) NOT NULL,
    
    PRIMARY KEY (id)
); 

CREATE TABLE mobapde_db.photo(
	photo_id INT NOT NULL,
    user_id  INT NOT NULL,
    url TEXT NOT NULL,
    caption TEXT NOT NULL,
    date_of_upload DATETIME NOT NULL,
    
    PRIMARY KEY (photo_id),
    CONSTRAINT user_id
		FOREIGN KEY (user_id)
        REFERENCES mobapde_db.users (id)
);

INSERT INTO `mobapde_db`.`users` (`user_name`,`first_name`,`last_name`,`pass_code`) VALUES ('icarusLex','Aika','Russell','admin');
INSERT INTO `mobapde_db`.`users` (`user_name`,`first_name`,`last_name`,`pass_code`) VALUES ('wensley','Wes','Jackson','duck1');
INSERT INTO `mobapde_db`.`users` (`user_name`,`first_name`,`last_name`,`pass_code`) VALUES ('montyGUI','Monty','Montague','qwerty');
INSERT INTO `mobapde_db`.`users` (`user_name`,`first_name`,`last_name`,`pass_code`) VALUES ('veritas','Lenny','Crest','lemaRC12');
INSERT INTO `mobapde_db`.`users` (`user_name`,`first_name`,`last_name`,`pass_code`) VALUES ('terraPines','Terry','Russell','lkjrbgHAHA');