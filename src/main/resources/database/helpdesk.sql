DROP DATABASE IF EXISTS helpdesk;

CREATE DATABASE helpdesk
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE helpdesk;

CREATE TABLE departamento (

    id INT AUTO_INCREMENT PRIMARY KEY,

    nome VARCHAR(100) NOT NULL UNIQUE

);

INSERT INTO departamento(nome) VALUES
                                   ('TI'),
                                   ('Financeiro'),
                                   ('RH'),
                                   ('Marketing'),
                                   ('Comercial'),
                                   ('Compras'),
                                   ('Jurídico'),
                                   ('Administrativo');

CREATE TABLE usuario (

    id INT AUTO_INCREMENT PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,

    email VARCHAR(150) NOT NULL UNIQUE,

    senha VARCHAR(255) NOT NULL,

    tipo ENUM('ADMIN','COMUM') NOT NULL,

    departamento_id INT NOT NULL,

    FOREIGN KEY (departamento_id)
        REFERENCES departamento(id)

);

insert into usuario (nome, email, senha, tipo, departamento_id) values (
        'Oliver Edson Gomes',
        'oliver.edson.gomes@hotmail.it',
        "M3dRN3JWx0",
        "Admin",
        8
      );

CREATE TABLE solicitacao(

    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT NOT NULL,
    status ENUM('ABERTA','RESPONDIDA','RESOLVIDA')
        DEFAULT 'ABERTA',
    prioridade ENUM(
        'BAIXA',
        'MEDIA',
        'ALTA'
    ) DEFAULT 'MEDIA'
    data_criacao DATETIME
        DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    departamento_id INT NOT NULL,
    FOREIGN KEY(usuario_id)
        REFERENCES usuario(id),
    FOREIGN KEY(departamento_id)
        REFERENCES departamento(id)
);

CREATE TABLE resposta (

    id INT AUTO_INCREMENT PRIMARY KEY,

    texto TEXT NOT NULL,

    data_resposta DATETIME DEFAULT CURRENT_TIMESTAMP,

    usuario_id INT NOT NULL,

    solicitacao_id INT NOT NULL,

    FOREIGN KEY(usuario_id)
        REFERENCES usuario(id),

    FOREIGN KEY(solicitacao_id)
        REFERENCES solicitacao(id)

);