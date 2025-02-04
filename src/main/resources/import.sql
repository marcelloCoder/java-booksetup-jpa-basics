-- Inserindo o autor
INSERT INTO tb_autor (nome, data_nascimento) VALUES ('Machado de Assis', '1839-06-21');
INSERT INTO tb_autor (nome, data_nascimento) VALUES ('Clarice Lispector', '1920-12-10');
INSERT INTO tb_autor (nome, data_nascimento) VALUES ('Jorge Amado', '1912-08-10');
INSERT INTO tb_autor (nome, data_nascimento) VALUES ('Graciliano Ramos', '1892-10-27');


-- Inserindo os livros associados ao autor
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('Dom Casmurro', '1899-01-01', 1);
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('Memórias Póstumas de Brás Cubas', '1881-01-01', 1);


-- Inserindo os livros associados a Clarice Lispector
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('A Hora da Estrela', '1977-01-01', 2);
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('Laços de Família', '1960-01-01', 2);

-- Inserindo os livros associados a Jorge Amado
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('Capitães da Areia', '1937-01-01', 3);
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('Gabriela, Cravo e Canela', '1958-01-01', 3);

-- Inserindo os livros associados a Graciliano Ramos
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('Vidas Secas', '1938-01-01', 4);
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('Memórias do Cárcere', '1953-01-01', 4);

