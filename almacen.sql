CREATE TABLE Clientes(

	Documento TEXT PRIMARY KEY,
	Nombre TEXT,
	Telefono TEXT

);

INSERT INTO Clientes VALUES("9726604", "Mauricio Posada", "3113009748");
INSERT INTO Clientes VALUES("1129582973", "Jennifer Sepulveda", "3005872247");
INSERT INTO Clientes VALUES("123456789", "Jhonatan Sanchez", "345212345");

SELECT * FROM Clientes;

UPDATE Clientes SET Nombre = "Jenny Sepulveda" WHERE Documento = "1129582973";

DELETE FROM Clientes WHERE Documento LIKE ("3%");