-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE categoria
(
    categoria_id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome         varchar(255)    NOT NULL UNIQUE
);

CREATE TABLE prodotto
(
    prodotto_id         int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    categoria_id        int             NOT NULL,
    nome                varchar(255)    NOT NULL UNIQUE,
    descrizione         TEXT,
    prezzo              decimal(10, 2)  NOT NULL,
    quantita_inventario int             NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)
);

CREATE TABLE utente
(
    utente_id            int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    ruolo                varchar(255)    NOT NULL,
    nome                 varchar(255),
    cognome              varchar(255),
    email                varchar(255)    NOT NULL UNIQUE,
    numero_telefono      varchar(15) UNIQUE,
    indirizzo_spedizione varchar(255),
    password             varchar(255)    NOT NULL
);

CREATE TABLE ordine
(
    ordine_id            int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    utente_id            int             NOT NULL,
    data_ordine          DATETIME        NOT NULL,
    stato_ordine         varchar(255)    NOT NULL,
    stato_pagamento      varchar(255)    NOT NULL,
    totale               decimal(10, 2)  NOT NULL,
    indirizzo_spedizione varchar(255)    NOT NULL,
    note                 varchar(255),
    prodotti_acquistati  TEXT    NOT NULL,
    FOREIGN KEY (utente_id) REFERENCES utente (utente_id)
);

CREATE TABLE immagine
(
    immagine_id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    url         varchar(255)    NOT NULL UNIQUE,
    prodotto_id int             NOT NULL,
    FOREIGN KEY (prodotto_id) REFERENCES prodotto (prodotto_id)
);
