create table cidade (id bigint not null auto_increment, nome varchar(255) not null, estado_id bigint not null, primary key (id)) engine=InnoDB
create table cozinha (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB
create table estado (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table forma_pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB
create table grupo (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB
create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB
create table produto (id bigint not null auto_increment, ativo bit, descricao varchar(255), nome varchar(255) not null, preco decimal(19,2) not null, restaurante_id bigint not null, primary key (id)) engine=InnoDB
create table restaurante (id bigint not null auto_increment, data_atualizacao datetime(6) not null, data_cadastro datetime(6) not null, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255), tx_frete decimal(19,2) not null, cozinha_id bigint not null, endereco_cidade_id bigint, primary key (id)) engine=InnoDB
create table restaurante_forma_pagamento (restaurante_id bigint not null, forma_pagamento_id bigint not null) engine=InnoDB
create table usuario (id bigint not null auto_increment, data_cadastro datetime(6), email varchar(255), nome varchar(255) not null, senha varchar(255), primary key (id)) engine=InnoDB
create table usuario_grupo (usuario_id bigint not null, grupo_id bigint not null) engine=InnoDB
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id)
alter table grupo_permissao add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permissao_id) references permissao (id)
alter table grupo_permissao add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (grupo_id) references grupo (id)
alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id)
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id)
alter table restaurante add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (endereco_cidade_id) references cidade (id)
alter table restaurante_forma_pagamento add constraint FK7aln770m80358y4olr03hyhh2 foreign key (forma_pagamento_id) references forma_pagamento (id)
alter table restaurante_forma_pagamento add constraint FKa30vowfejemkw7whjvr8pryvj foreign key (restaurante_id) references restaurante (id)
alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id)
alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id)
INSERT INTO cozinha (nome) values ('Chinesa')
INSERT INTO cozinha (nome) values ('Indiana')
INSERT INTO cozinha (nome) values ('Francesa')
INSERT INTO estado (nome) values ('Cazenga')
INSERT INTO estado (nome) values ('Caxito')
INSERT INTO estado (nome) values ('Cacuaco')
INSERT INTO cidade (nome, estado_id) values ('Luanda', 1)
INSERT INTO cidade (nome, estado_id) values ('Bengo', 2)
INSERT INTO cidade (nome, estado_id) values ('Luanda', 3)
INSERT INTO forma_pagamento  (descricao) values ('CARTÃO DE CÉDITO')
INSERT INTO forma_pagamento  (descricao) values ('CARTÃO DE DÉBITO')
INSERT INTO forma_pagamento  (descricao) values ('DINHEIRO')
INSERT INTO restaurante_forma_pagamento  (restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,2), (3,3), (2,3)
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('bacalhau', 'de angola', 150, true, 1)
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('peixa', 'de bretagne', 1500, false, 2)
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('pao', 'de angola', 50, true, 1)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('KOPAKABANA', '200', 1, '51', 'nao sei', '22', 'dd', 'crx rg', 1, utc_timestamp, utc_timestamp)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('REIMS', '1200', 1, '51', 'nao sei', '22', 'dd', 'crx rg', 2, utc_timestamp, utc_timestamp)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('BOLA BAIXA', '550', 2, '39', 'nao sei', '22', 'dd', 'orgeval', 2, utc_timestamp, utc_timestamp)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('COURCELLES', '2500', 3, '51', 'nao sei', '22', 'dd', 'chatillon', 1, utc_timestamp, utc_timestamp)
INSERT INTO permissao  (nome, descricao) values ('LIS_RES', 'Listar restaurantes')
INSERT INTO permissao  (nome, descricao) values ('DEL_RES', 'deletar restaurantes')
INSERT INTO permissao  (nome, descricao) values ('ATUA_RES', 'atualizar restaurantes')
INSERT INTO permissao  (nome, descricao) values ('BUS_RES', 'Buscar restaurantes')
INSERT INTO grupo  (nome) values ('administrador')
INSERT INTO grupo  (nome) values ('Limitado')
INSERT INTO grupo_permissao  (grupo_id, permissao_id) values (1,1), (1,2), (1,3), (1,4),(2,1)
INSERT INTO usuario  (nome, email, senha, data_cadastro) values ('Felix', 'admin@g.com', '123', utc_timestamp )
INSERT INTO usuario  (nome, email, senha, data_cadastro) values ('Joel', 'limitado@g.com', '123', utc_timestamp )
INSERT INTO usuario_grupo  (usuario_id, grupo_id) values (1,1), (1,2), (2,2)
create table cidade (id bigint not null auto_increment, nome varchar(255) not null, estado_id bigint not null, primary key (id)) engine=InnoDB
create table cozinha (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB
create table estado (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table forma_pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB
create table grupo (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB
create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB
create table produto (id bigint not null auto_increment, ativo bit, descricao varchar(255), nome varchar(255) not null, preco decimal(19,2) not null, restaurante_id bigint not null, primary key (id)) engine=InnoDB
create table restaurante (id bigint not null auto_increment, data_atualizacao datetime(6) not null, data_cadastro datetime(6) not null, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255), tx_frete decimal(19,2) not null, cozinha_id bigint not null, endereco_cidade_id bigint, primary key (id)) engine=InnoDB
create table restaurante_forma_pagamento (restaurante_id bigint not null, forma_pagamento_id bigint not null) engine=InnoDB
create table usuario (id bigint not null auto_increment, data_cadastro datetime(6), email varchar(255), nome varchar(255) not null, senha varchar(255), primary key (id)) engine=InnoDB
create table usuario_grupo (usuario_id bigint not null, grupo_id bigint not null) engine=InnoDB
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id)
alter table grupo_permissao add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permissao_id) references permissao (id)
alter table grupo_permissao add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (grupo_id) references grupo (id)
alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id)
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id)
alter table restaurante add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (endereco_cidade_id) references cidade (id)
alter table restaurante_forma_pagamento add constraint FK7aln770m80358y4olr03hyhh2 foreign key (forma_pagamento_id) references forma_pagamento (id)
alter table restaurante_forma_pagamento add constraint FKa30vowfejemkw7whjvr8pryvj foreign key (restaurante_id) references restaurante (id)
alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id)
alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id)
INSERT INTO cozinha (nome) values ('Chinesa')
INSERT INTO cozinha (nome) values ('Indiana')
INSERT INTO cozinha (nome) values ('Francesa')
INSERT INTO estado (nome) values ('Cazenga')
INSERT INTO estado (nome) values ('Caxito')
INSERT INTO estado (nome) values ('Cacuaco')
INSERT INTO cidade (nome, estado_id) values ('Luanda', 1)
INSERT INTO cidade (nome, estado_id) values ('Bengo', 2)
INSERT INTO cidade (nome, estado_id) values ('Luanda', 3)
INSERT INTO forma_pagamento  (descricao) values ('CARTÃO DE CÉDITO')
INSERT INTO forma_pagamento  (descricao) values ('CARTÃO DE DÉBITO')
INSERT INTO forma_pagamento  (descricao) values ('DINHEIRO')
INSERT INTO restaurante_forma_pagamento  (restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,2), (3,3), (2,3)
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('bacalhau', 'de angola', 150, true, 1)
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('peixa', 'de bretagne', 1500, false, 2)
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('pao', 'de angola', 50, true, 1)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('KOPAKABANA', '200', 1, '51', 'nao sei', '22', 'dd', 'crx rg', 1, utc_timestamp, utc_timestamp)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('REIMS', '1200', 1, '51', 'nao sei', '22', 'dd', 'crx rg', 2, utc_timestamp, utc_timestamp)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('BOLA BAIXA', '550', 2, '39', 'nao sei', '22', 'dd', 'orgeval', 2, utc_timestamp, utc_timestamp)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('COURCELLES', '2500', 3, '51', 'nao sei', '22', 'dd', 'chatillon', 1, utc_timestamp, utc_timestamp)
INSERT INTO permissao  (nome, descricao) values ('LIS_RES', 'Listar restaurantes')
INSERT INTO permissao  (nome, descricao) values ('DEL_RES', 'deletar restaurantes')
INSERT INTO permissao  (nome, descricao) values ('ATUA_RES', 'atualizar restaurantes')
INSERT INTO permissao  (nome, descricao) values ('BUS_RES', 'Buscar restaurantes')
INSERT INTO grupo  (nome) values ('administrador')
INSERT INTO grupo  (nome) values ('Limitado')
INSERT INTO grupo_permissao  (grupo_id, permissao_id) values (1,1), (1,2), (1,3), (1,4),(2,1)
INSERT INTO usuario  (nome, email, senha, data_cadastro) values ('Felix', 'admin@g.com', '123', utc_timestamp )
INSERT INTO usuario  (nome, email, senha, data_cadastro) values ('Joel', 'limitado@g.com', '123', utc_timestamp )
INSERT INTO usuario_grupo  (usuario_id, grupo_id) values (1,1), (1,2), (2,2)
create table cidade (id bigint not null auto_increment, nome varchar(255) not null, estado_id bigint not null, primary key (id)) engine=InnoDB
create table cozinha (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB
create table estado (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table forma_pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB
create table grupo (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB
create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB
create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB
create table produto (id bigint not null auto_increment, ativo bit, descricao varchar(255), nome varchar(255) not null, preco decimal(19,2) not null, restaurante_id bigint not null, primary key (id)) engine=InnoDB
create table restaurante (id bigint not null auto_increment, data_atualizacao datetime(6) not null, data_cadastro datetime(6) not null, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255), tx_frete decimal(19,2) not null, cozinha_id bigint not null, endereco_cidade_id bigint, primary key (id)) engine=InnoDB
create table restaurante_forma_pagamento (restaurante_id bigint not null, forma_pagamento_id bigint not null) engine=InnoDB
create table usuario (id bigint not null auto_increment, data_cadastro datetime(6), email varchar(255), nome varchar(255) not null, senha varchar(255), primary key (id)) engine=InnoDB
create table usuario_grupo (usuario_id bigint not null, grupo_id bigint not null) engine=InnoDB
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id)
alter table grupo_permissao add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permissao_id) references permissao (id)
alter table grupo_permissao add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (grupo_id) references grupo (id)
alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id)
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id)
alter table restaurante add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (endereco_cidade_id) references cidade (id)
alter table restaurante_forma_pagamento add constraint FK7aln770m80358y4olr03hyhh2 foreign key (forma_pagamento_id) references forma_pagamento (id)
alter table restaurante_forma_pagamento add constraint FKa30vowfejemkw7whjvr8pryvj foreign key (restaurante_id) references restaurante (id)
alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id)
alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id)
INSERT INTO cozinha (nome) values ('Chinesa')
INSERT INTO cozinha (nome) values ('Indiana')
INSERT INTO cozinha (nome) values ('Francesa')
INSERT INTO estado (nome) values ('Cazenga')
INSERT INTO estado (nome) values ('Caxito')
INSERT INTO estado (nome) values ('Cacuaco')
INSERT INTO cidade (nome, estado_id) values ('Luanda', 1)
INSERT INTO cidade (nome, estado_id) values ('Bengo', 2)
INSERT INTO cidade (nome, estado_id) values ('Luanda', 3)
INSERT INTO forma_pagamento  (descricao) values ('CARTÃO DE CÉDITO')
INSERT INTO forma_pagamento  (descricao) values ('CARTÃO DE DÉBITO')
INSERT INTO forma_pagamento  (descricao) values ('DINHEIRO')
INSERT INTO restaurante_forma_pagamento  (restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,2), (3,3), (2,3)
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('bacalhau', 'de angola', 150, true, 1)
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('peixa', 'de bretagne', 1500, false, 2)
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) values ('pao', 'de angola', 50, true, 1)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('KOPAKABANA', '200', 1, '51', 'nao sei', '22', 'dd', 'crx rg', 1, utc_timestamp, utc_timestamp)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('REIMS', '1200', 1, '51', 'nao sei', '22', 'dd', 'crx rg', 2, utc_timestamp, utc_timestamp)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('BOLA BAIXA', '550', 2, '39', 'nao sei', '22', 'dd', 'orgeval', 2, utc_timestamp, utc_timestamp)
INSERT INTO restaurante (nome, tx_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao ) values ('COURCELLES', '2500', 3, '51', 'nao sei', '22', 'dd', 'chatillon', 1, utc_timestamp, utc_timestamp)
INSERT INTO permissao  (nome, descricao) values ('LIS_RES', 'Listar restaurantes')
INSERT INTO permissao  (nome, descricao) values ('DEL_RES', 'deletar restaurantes')
INSERT INTO permissao  (nome, descricao) values ('ATUA_RES', 'atualizar restaurantes')
INSERT INTO permissao  (nome, descricao) values ('BUS_RES', 'Buscar restaurantes')
INSERT INTO grupo  (nome) values ('administrador')
INSERT INTO grupo  (nome) values ('Limitado')
INSERT INTO grupo_permissao  (grupo_id, permissao_id) values (1,1), (1,2), (1,3), (1,4),(2,1)
INSERT INTO usuario  (nome, email, senha, data_cadastro) values ('Felix', 'admin@g.com', '123', utc_timestamp )
INSERT INTO usuario  (nome, email, senha, data_cadastro) values ('Joel', 'limitado@g.com', '123', utc_timestamp )
INSERT INTO usuario_grupo  (usuario_id, grupo_id) values (1,1), (1,2), (2,2)
