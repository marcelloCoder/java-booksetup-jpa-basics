-- Inserindo o autor
INSERT INTO tb_autor (nome, data_nascimento) VALUES ('Machado de Assis', '1839-06-21');

-- Inserindo os livros associados ao autor
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('Dom Casmurro', '1899-01-01', 1);
INSERT INTO tb_livro (titulo, ano_publicacao, autor_id) VALUES ('Memórias Póstumas de Brás Cubas', '1881-01-01', 1);
