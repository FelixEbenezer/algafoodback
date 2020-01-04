INSERT INTO cozinha (nome) values ('Chinesa'); 
INSERT INTO cozinha (nome) values ('Indiana');
INSERT INTO cozinha (nome) values ('Francesa');  


INSERT INTO estado (nome) values ('Cazenga');
INSERT INTO estado (nome) values ('Caxito');
INSERT INTO estado (nome) values ('Cacuaco');

INSERT INTO cidade (nome, estado_id) values ('Luanda', 1);
INSERT INTO cidade (nome, estado_id) values ('Bengo', 2);
INSERT INTO cidade (nome, estado_id) values ('Luanda', 3);

INSERT INTO forma_pagamento  (descricao) values ('CARTÃO DE CÉDITO');
INSERT INTO forma_pagamento  (descricao) values ('CARTÃO DE DÉBITO');
INSERT INTO forma_pagamento  (descricao) values ('DINHEIRO');

INSERT INTO restaurante_forma_pagamento  (restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,2), (3,3), (2,3);


INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('bacalhau', 'de angola', 150, true, 1);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('peixa', 'de bretagne', 1500, false, 2);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('pao', 'de angola', 50, true, 1);


INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('KOPAKABANA', '200', 1, '51', 'nao sei', '22', 'dd', 'crx rg', 1, utc_timestamp, utc_timestamp); 
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('REIMS', '1200', 1, '51', 'nao sei', '22', 'dd', 'crx rg', 2, utc_timestamp, utc_timestamp); 
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('BOLA BAIXA', '550', 2, '39', 'nao sei', '22', 'dd', 'orgeval', 2, utc_timestamp, utc_timestamp); 
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('COURCELLES', '2500', 3, '51', 'nao sei', '22', 'dd', 'chatillon', 1, utc_timestamp, utc_timestamp); 

INSERT INTO permissao  (nome, descricao) values ('LIS_RES', 'Listar restaurantes');
INSERT INTO permissao  (nome, descricao) values ('DEL_RES', 'deletar restaurantes');
INSERT INTO permissao  (nome, descricao) values ('ATUA_RES', 'atualizar restaurantes');
INSERT INTO permissao  (nome, descricao) values ('BUS_RES', 'Buscar restaurantes');


INSERT INTO grupo  (nome) values ('administrador');
INSERT INTO grupo  (nome) values ('Limitado');

INSERT INTO grupo_permissao  (grupo_id, permissao_id) values (1,1), (1,2), (1,3), (1,4),(2,1);

INSERT INTO usuario  (nome, email, senha, data_cadastro) values ('Felix', 'admin@g.com', '123', utc_timestamp );
INSERT INTO usuario  (nome, email, senha, data_cadastro) values ('Joel', 'limitado@g.com', '123', utc_timestamp );


INSERT INTO usuario_grupo  (usuario_id, grupo_id) values (1,1), (1,2), (2,2);
