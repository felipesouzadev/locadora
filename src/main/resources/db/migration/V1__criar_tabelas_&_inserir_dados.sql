CREATE TABLE `filme` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `copias` int NOT NULL,
  `diretor` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `locacao` (
  `usuario_id` bigint NOT NULL,
  `filme_id` bigint NOT NULL,
  KEY `FK4ilvguhqre01t27h32n44a0iy` (`filme_id`),
  KEY `FKkktfq622phc5otk6901ju9eqk` (`usuario_id`)
);
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `usuario_role` (
  `usuario_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKe7gfguqsiox6p89xggm8g2twf` (`role_id`),
  KEY `FKpc2qjts6sqq4hja9f6i3hf0ep` (`usuario_id`)
); 


INSERT INTO filme (titulo, diretor, copias)
values ('O Senhor dos Anéis: A Sociedade do Anel', 'Peter Jackson' , 3);

INSERT INTO filme (titulo, diretor, copias)
values ('Guerra nas Estrelas: O Império Contra-Ataca', 'Irvin Kershner' , 2);

INSERT INTO filme (titulo, diretor, copias)
values ('Matrix Reloaded', ' Lana Wachowski' , 1);

INSERT INTO filme (titulo, diretor, copias)
values ('Jurassic Park - Parque dos Dinossauros', 'Steven Spielberg' , 2);

INSERT INTO filme (titulo, diretor, copias)
values ('Batman: O Cavaleiro das Trevas', 'Christopher Nolan' , 2);

INSERT INTO role(nome)
values('ROLE_USER');
INSERT INTO role(nome)
values('ROLE_ADMIN');

INSERT INTO usuario(email,nome , senha)
VALUES ("admin", "ADMIN", "$2a$10$hjdmjjnkKRLEjYqvC2BM.OpKlYhBb4OhOZkHqmYfCUsC0afoIes3S");
INSERT INTO usuario(email, nome , senha)
VALUES ("user", "USER", "$2a$10$Vq22PjIEPXb.Tm17qoYX7.MFvcLTnASkXQC7UlVIyIwBSomgcz5x6");


insert into usuario_role(usuario_id, role_id)
VALUES(1,1);
insert into usuario_role(usuario_id, role_id)
VALUES(2,2);
