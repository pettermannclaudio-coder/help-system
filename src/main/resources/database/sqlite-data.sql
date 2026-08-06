INSERT OR IGNORE INTO departamento(nome) VALUES
                                   ('TI'),
                                   ('Financeiro'),
                                   ('RH'),
                                   ('Marketing'),
                                   ('Comercial'),
                                   ('Compras'),
                                   ('Jurídico'),
                                   ('Administrativo');

INSERT OR IGNORE INTO usuario
(
    nome,
    email,
    senha,
    tipo,
    departamento_id
)
VALUES
    (
        'Maria Silva',
        'maria@example.com',
        '123456',
        'COMUM',
        1
    );
