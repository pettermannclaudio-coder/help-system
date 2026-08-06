CREATE TABLE IF NOT EXISTS departamento (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    tipo TEXT NOT NULL DEFAULT 'COMUM'
        CHECK (tipo IN ('ADMIN', 'COMUM')),
    departamento_id INTEGER NOT NULL,
    FOREIGN KEY (departamento_id) REFERENCES departamento(id)
);

CREATE TABLE IF NOT EXISTS solicitacao (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo TEXT NOT NULL,
    descricao TEXT NOT NULL,
    status TEXT NOT NULL DEFAULT 'ABERTA'
        CHECK (status IN ('ABERTA', 'RESPONDIDA', 'RESOLVIDA')),
    prioridade TEXT NOT NULL DEFAULT 'MÉDIA' CHECK (status IN ('BAIXA', 'MÉDIA', 'ALTA'))
    data_criacao TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_id INTEGER NOT NULL,
    departamento_id INTEGER NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (departamento_id) REFERENCES departamento(id)
);

CREATE TABLE IF NOT EXISTS resposta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    texto TEXT NOT NULL,
    data_resposta TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_id INTEGER NOT NULL,
    solicitacao_id INTEGER NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id)
);

INSERT OR IGNORE INTO departamento (nome) VALUES
    ('TI'),
    ('Financeiro'),
    ('RH'),
    ('Marketing'),
    ('Comercial'),
    ('Compras'),
    ('Jurídico'),
    ('Administrativo');
