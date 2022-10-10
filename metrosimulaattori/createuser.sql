DROP DATABASE if EXISTS metrosimulaattori;
CREATE DATABASE metrosimulaattori;


DROP USER if EXISTS metrosim@localhost;
CREATE USER metrosim@localhost IDENTIFIED BY 'dbpassword';
GRANT SELECT, INSERT, UPDATE, ALTER, DROP, CREATE, DELETE ON metrosimulaattori.* TO metrosim@localhost;

