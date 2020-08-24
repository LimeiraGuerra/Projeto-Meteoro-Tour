CREATE TABLE Administrador(
	senha TEXT NOT NULL PRIMARY KEY
);

CREATE TABLE Funcionario(
	cpf TEXT NOT NULL PRIMARY KEY,
	rg TEXT NOT NULL UNIQUE,
	nome TEXT NOT NULL,
	cargo TEXT NOT NULL
);

CREATE TABLE Onibus(
	renavam TEXT NOT NULL PRIMARY KEY,
	placa TEXT NOT NULL UNIQUE
);

CREATE TABLE Trecho(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	cidadeOrigem TEXT NOT NULL,
	cidadeDestino TEXT NOT NULL,
	quilometragem REAL NOT NULL,
	tempoDuracao INTEGER NOT NULL,
	valorPassagem REAL NOT NULL,
	taxaEmbarque REAL NOT NULL,
	valorSeguro REAL NOT NULL
);

CREATE TABLE Linha(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	nome TEXT NOT NULL UNIQUE
);

CREATE TABLE TrechoLinha(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	horarioSaida TIME NOT NULL,
	ordem INTEGER NOT NULL,
	dPlus INTEGER NOT NULL,
	idLinha INTEGER NOT NULL,
	idTrecho INTEGER NOT NULL,
	FOREIGN KEY(idLinha) REFERENCES Linha(id),
	FOREIGN KEY(idTrecho) REFERENCES Trecho(id)
);

CREATE TABLE AssentoTrechoLinha(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	data DATE NOT NULL,
	idTrechoLinha INTEGER NOT NULL,
	idPassagem INTEGER NOT NULL,
	idAssento TEXT NOT NULL,
	FOREIGN KEY(idPassagem) REFERENCES Passagem(numPassagem) ON DELETE CASCADE,
	FOREIGN KEY(idTrechoLinha) REFERENCES TrechoLinha(id)
);

CREATE TABLE Viagem(
	idPassagem INTEGER NOT NULL,
	data DATE NOT NULL,
	cidadeOrigem TEXT NOT NULL,
	cidadeDestino TEXT NOT NULL,
	idLinha INTEGER NOT NULL,
	PRIMARY KEY(idPassagem),
	FOREIGN KEY(idPassagem) REFERENCES Passagem(numPassagem) ON DELETE CASCADE,
	FOREIGN KEY(idLinha) REFERENCES Linha(id)
);

CREATE TABLE Passagem(
	numPassagem INTEGER PRIMARY KEY AUTOINCREMENT,
	nomeCliente TEXT NOT NULL,
	cpfCliente TEXT NOT NULL,
	rgCliente TEXT NOT NULL,
	telefoneCliente TEXT NOT NULL,
	precoPago REAL NOT NULL,
	seguro INTEGER NOT NULL,
	dataCompra DATETIME NOT NULL
);

CREATE VIEW vPassagensVendidas (numPassagem, nomeCliente, cpfCliente, rgCliente, telefoneCliente, precoPago, seguro, 
dataCompra, dataViagem,idAssento, cidadeOrigem, cidadeDestino, idLinha, nomeLinha, valorViagem, valorSeguro) AS
	SELECT p.*, v.data,ast.idAssento, v.cidadeOrigem, v.cidadeDestino, 
	l.id, l.nome, sum(t.valorPassagem+t.taxaEmbarque), sum(t.valorSeguro) FROM Passagem p
	JOIN Viagem v ON p.numPassagem = v.idPassagem
	JOIN Linha l ON l.id = v.idLinha
	JOIN AssentoTrechoLinha ast ON ast.idPassagem = p.numPassagem
	JOIN TrechoLinha tl ON tl.id = ast.idTrechoLinha
	JOIN Trecho t ON t.id = tl.idTrecho
	GROUP BY p.numPassagem;

CREATE VIEW vLinhaByCidades(idLinha, nomeLinha) AS
SELECT l.* FROM linha l
GROUP by l.id;

CREATE VIEW vTrechoLinhaByLinha (idTrechoLinha, horarioSaida, ordem, dPlus, idLinha, idTrecho,
cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro) AS
SELECT tl.id, tl.horarioSaida, tl.ordem, tl.dPlus, tl.idLinha , t.* 
FROM  trechoLinha tl JOIN trecho t ON tl.idTrecho = t.id;

INSERT INTO Trecho(cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro)
VALUES('Descalvado', 'São Carlos', 40.0, 50, 10.0, 5.0, 0.3);
INSERT INTO Trecho(cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro)
VALUES('São Carlos', 'Ibaté', 15.0, 20, 5.0, 3.0, 0.1);
INSERT INTO Trecho(cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro)
VALUES('São Carlos', 'Araraquara', 30.0, 35, 8.0, 4.0, 0.2);
INSERT INTO Trecho(cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro)
VALUES('Araraquara', 'Ibaté', 20.0, 25, 6.0, 3.5, 0.25);
INSERT INTO Linha(nome) VALUES('Descalvado - Ibaté curto');
INSERT INTO Linha(nome) VALUES('Descalvado - Ibaté longo');
INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('12:00'), 1, 0, 1, 1);
INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('12:55'), 2, 0, 1, 2);
INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('09:00'), 1, 0, 2, 1);
INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('09:55'), 2, 0, 2, 3);
INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('10:30'), 3, 0, 2, 4);
--INSERT INTO AssentoTrechoLinha(data, idTrechoLinha, idAssento) VALUES(date('2020-10-11'), 2, '04');
--INSERT INTO AssentoTrechoLinha(data, idTrechoLinha, idAssento) VALUES(date('2020-10-10'), 1, '05');
--INSERT INTO AssentoTrechoLinha(data, idTrechoLinha, idAssento) VALUES(date('2020-10-11'), 1, '03');
--INSERT INTO AssentoTrechoLinha(data, idTrechoLinha, idAssento) VALUES(date('2020-10-12'), 2, '06');
;