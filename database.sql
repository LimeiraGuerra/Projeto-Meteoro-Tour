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
	--valorTotal REAL NOT NULL
);

CREATE TABLE Linha(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	nome TEXT NOT NULL UNIQUE
	--quilometragemLinha REAL NOT NULL,
	--valorTotalLinha REAL NOT NULL
);

CREATE TABLE AssentoTrechoLinha(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	data TEXT NOT NULL,
	assento01 INTEGER,
	assento02 INTEGER,
	assento03 INTEGER,
	assento04 INTEGER,
	assento05 INTEGER,
	assento06 INTEGER,
	assento07 INTEGER,
	assento08 INTEGER,
	assento09 INTEGER,
	assento10 INTEGER,
	assento11 INTEGER,
	assento12 INTEGER,
	assento13 INTEGER,
	assento14 INTEGER,
	assento15 INTEGER,
	assento16 INTEGER,
	assento17 INTEGER,
	assento18 INTEGER,
	assento19 INTEGER,
	assento20 INTEGER,
	assento21 INTEGER,
	assento22 INTEGER,
	assento23 INTEGER,
	assento24 INTEGER,
	assento25 INTEGER,
	assento26 INTEGER,
	assento27 INTEGER,
	assento28 INTEGER,
	assento29 INTEGER,
	assento30 INTEGER,
	assento31 INTEGER,
	assento32 INTEGER,
	assento33 INTEGER,
	assento34 INTEGER,
	assento35 INTEGER,
	assento36 INTEGER,
	assento37 INTEGER,
	assento38 INTEGER,
	assento39 INTEGER,
	assento40 INTEGER,
	assento41 INTEGER,
	assento42 INTEGER,
	assento43 INTEGER,
	assento44 INTEGER
);

CREATE TABLE TrechoLinha(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	--data TEXT NOT NULL,
	ordem INTEGER NOT NULL,
	linha INTEGER NOT NULL,
	trecho INTEGER NOT NULL,
	assentoPorTrecho INTEGER NOT NULL,
	FOREIGN KEY(linha) REFERENCES Linha(id),
	FOREIGN KEY(trecho) REFERENCES Trecho(id),
	FOREIGN KEY(assentoPorTrecho) REFERENCES AssentoTrechoLinha(id) --acho q o contrario é melhor
);

CREATE TABLE Viagem(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	data TEXT NOT NULL,
	cidadeOrigem TEXT NOT NULL,
	cidadeDestino TEXT NOT NULL
);

CREATE TABLE TrechoLinhaViagem(
	viagem INTEGER NOT NULL,
	trechoLinha INTEGER NOT NULL,
	PRIMARY KEY(viagem, trechoLinha),
	FOREIGN KEY(viagem) REFERENCES Viagem(id),
	FOREIGN KEY(trechoLinha) REFERENCES TrechoLinha(id)
);

CREATE TABLE Passagem(
	numPassagem INTEGER PRIMARY KEY AUTOINCREMENT,
	nomeCliente TEXT NOT NULL,
	cpfCliente TEXT NOT NULL,
	rgCliente TEXT NOT NULL,
	telefoneCliente TEXT NOT NULL,
	precoPago REAL NOT NULL,
	seguro INTEGER NOT NULL,
	dataCompra TEXT NOT NULL,
	dataViagem TEXT NOT NULL,
	viagem INTEGER NOT NULL,
	FOREIGN KEY(viagem) REFERENCES Viagem(id)
);