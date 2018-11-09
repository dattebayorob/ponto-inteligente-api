INSERT INTO empresa(cnpj,razao_social,data_criacao,data_atualizacao)
	VALUES('19002831000120','DTB',CURRENT_DATE,CURRENT_DATE);
INSERT INTO funcionario(nome,email,senha,cpf,perfil,data_criacao,data_atualizacao,id_empresa)
	VALUES('Default Admin','admin@dtb.com','$2a$10$tO74GezITYdX4Q8miThvr.hhND8SxctwpbMeGvTC8MEzjzVpzYsTe','00000000000','ROLE_ADMIN',CURRENT_DATE,CURRENT_DATE,'1');
INSERT INTO funcionario(nome,email,senha,cpf,perfil,data_criacao,data_atualizacao,id_empresa)
	VALUES('Robson William','robson.william65@gmail.com','$2a$10$dXfG/z5WrgNjYg8KZy0O8OH66IFEycjgoTBU8vGIxc51VYupyzevG','00000000001','ROLE_USUARIO',CURRENT_DATE,CURRENT_DATE,'1');

