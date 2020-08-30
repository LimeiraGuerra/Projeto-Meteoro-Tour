CREATE TABLE Administrador (
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
	nome TEXT NOT NULL UNIQUE,
	inativo INTEGER
);

CREATE TABLE TrechoLinha(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	horarioSaida TIME NOT NULL,
	ordem INTEGER NOT NULL,
	dPlus INTEGER NOT NULL,
	idLinha INTEGER NOT NULL,
	idTrecho INTEGER NOT NULL,
	FOREIGN KEY(idLinha) REFERENCES Linha(id) ON DELETE CASCADE,
	FOREIGN KEY(idTrecho) REFERENCES Trecho(id)
);

CREATE TABLE AssentoTrechoLinha(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	data DATETIME NOT NULL,
	idTrechoLinha INTEGER NOT NULL,
	idPassagem INTEGER NOT NULL,
	idAssento TEXT NOT NULL,
	precoPago REAL NOT NULL,
	precoTrecho REAL NOT NULL,
	seguroTrecho REAL NOT NULL,
	FOREIGN KEY(idPassagem) REFERENCES Passagem(numPassagem) ON DELETE CASCADE,
	FOREIGN KEY(idTrechoLinha) REFERENCES TrechoLinha(id)
);

CREATE TABLE Viagem(
	idPassagem INTEGER NOT NULL,
	data DATE NOT NULL,
	cidadeOrigem TEXT NOT NULL,
	cidadeDestino TEXT NOT NULL,
	idLinha INTEGER NOT NULL,
	nomeLinha TEXT NOT NULL,
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
	desconto REAL NOT NULL,
	seguro INTEGER NOT NULL,
	dataCompra DATETIME NOT NULL
);

CREATE VIEW vInfoRelatorio (dataCompra, data, nomeLinha, horarioSaida, cidadeOrigem, cidadeDestino, uso, lucro) AS
	SELECT date(p.dataCompra), date(v.data), v.nomeLinha, tl.horarioSaida, 
		t.cidadeOrigem, t.cidadeDestino, count(ast.idTrechoLinha), sum(precoPago) 
	FROM Viagem v JOIN Passagem p ON p.numPassagem = v.idPassagem
	JOIN AssentoTrechoLinha ast ON ast.idPassagem = v.idPassagem
	JOIN TrechoLinha tl ON ast.idTrechoLinha = tl.id
	JOIN Trecho t ON t.id = tl.idTrecho
	GROUP BY date(v.data), tl.id ORDER BY date(v.data), v.nomeLinha, tl.ordem;

CREATE VIEW vPassagensVendidas (numPassagem, nomeCliente, cpfCliente, rgCliente, telefoneCliente, desconto, seguro, 
dataCompra, dataViagem, idAssento, cidadeOrigem, cidadeDestino, idLinha, nomeLinha, valorViagem, valorSeguro, precoPago) AS
	SELECT p.*, v.data, ast.idAssento, v.cidadeOrigem, v.cidadeDestino, v.idLinha, v.nomeLinha, 
		sum(ast.precoTrecho), sum(ast.seguroTrecho), sum(ast.precoPago) FROM Passagem p
	JOIN Viagem v ON p.numPassagem = v.idPassagem
	JOIN AssentoTrechoLinha ast ON ast.idPassagem = p.numPassagem
	GROUP BY p.numPassagem;

CREATE VIEW vLinhaByCidades(idLinha, nomeLinha, inativo) AS
	SELECT l.* FROM linha l
	GROUP by l.id;

CREATE VIEW vTrechoLinhaByLinha (idTrechoLinha, horarioSaida, ordem, dPlus, idLinha, idTrecho,
cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro) AS
	SELECT tl.id, tl.horarioSaida, tl.ordem, tl.dPlus, tl.idLinha , t.* 
	FROM  trechoLinha tl JOIN trecho t ON tl.idTrecho = t.id;

CREATE VIEW vNomeCidades (nomes) AS
	SELECT t.cidadeOrigem FROM Trecho t
	JOIN TrechoLinha tl ON tl.idTrecho = t.id
	UNION
	SELECT t.cidadeDestino FROM Trecho t
	JOIN TrechoLinha tl ON tl.idTrecho = t.id;

SELECT tl.id, tl.horarioSaida, tl.ordem, tl.dPlus
FROM  trechoLinha tl JOIN trecho t ON tl.idTrecho = t.id;

CREATE VIEW vLinhaViagem(id) AS SELECT l.id FROM Linha l JOIN Viagem V on l.id = V.idLinham;
	
INSERT INTO Administrador VALUES('d033e22ae348aeb5660fc2140aec35850c4da997');

INSERT INTO Trecho(cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro)
VALUES('Descalvado', 'São Carlos', 40.0, 50, 10.0, 5.0, 0.3);

INSERT INTO Trecho(cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro)
VALUES('São Carlos', 'Ibaté', 15.0, 20, 5.0, 3.0, 0.1);

INSERT INTO Trecho(cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro)
VALUES('São Carlos', 'Araraquara', 30.0, 35, 8.0, 4.0, 0.2);

INSERT INTO Trecho(cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao, valorPassagem, taxaEmbarque, valorSeguro)
VALUES('Araraquara', 'Ibaté', 20.0, 25, 6.0, 3.5, 0.25);

INSERT INTO Linha(nome, inativo) VALUES('Descalvado - Ibaté curto', 0);

INSERT INTO Linha(nome, inativo) VALUES('Descalvado - Ibaté longo', 0);

INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('12:00'), 1, 0, 1, 1);
INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('12:55'), 2, 0, 1, 2);
INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('09:00'), 1, 0, 2, 1);
INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('09:55'), 2, 0, 2, 3);
INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time('10:30'), 3, 0, 2, 4);