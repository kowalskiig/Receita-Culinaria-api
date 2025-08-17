INSERT INTO tb_user (first_Name, last_Name, email, password) VALUES ('User', 'User', 'user@gmail.com', '$2a$10$.mmz3OqUecF234Bic.FuYO5uZF9eZZGYM7aDkVLpqGVKUqBfhwrAC');
INSERT INTO tb_user (first_Name, last_Name, email, password) VALUES ('AdminAndUser', 'AdminAndUser', 'admin@gmail.com', '$2a$10$.mmz3OqUecF234Bic.FuYO5uZF9eZZGYM7aDkVLpqGVKUqBfhwrAC');

INSERT INTO tb_role (authority) VALUES ('ROLE_USER');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_category(name) VALUES ('Fitnes');
INSERT INTO tb_category(name) VALUES ('Doce');
INSERT INTO tb_category(name) VALUES ('Salgado');
INSERT INTO tb_category(name) VALUES ('Rápido');
INSERT INTO tb_category(name) VALUES ('Devagar');

INSERT INTO tb_ingredients(name) VALUES ('Açúcar');
INSERT INTO tb_ingredients(name) VALUES ('Sal');
INSERT INTO tb_ingredients(name) VALUES ('Farinha de Trigo');
INSERT INTO tb_ingredients(name) VALUES ('Ovo');
INSERT INTO tb_ingredients(name) VALUES ('Leite');
INSERT INTO tb_ingredients(name) VALUES ('Manteiga');
INSERT INTO tb_ingredients(name) VALUES ('Cebola');
INSERT INTO tb_ingredients(name) VALUES ('Alho');
INSERT INTO tb_ingredients(name) VALUES ('Azeite');
INSERT INTO tb_ingredients(name) VALUES ('Arroz');
INSERT INTO tb_ingredients(name) VALUES ('Feijão');
INSERT INTO tb_ingredients(name) VALUES ('Tomate');
INSERT INTO tb_ingredients(name) VALUES ('Batata');
INSERT INTO tb_ingredients(name) VALUES ('Cenoura');
INSERT INTO tb_ingredients(name) VALUES ('Frango');
INSERT INTO tb_ingredients(name) VALUES ('Carne Bovina');
INSERT INTO tb_ingredients(name) VALUES ('Água');
INSERT INTO tb_ingredients(name) VALUES ('Pimenta do Reino');
INSERT INTO tb_ingredients(name) VALUES ('Cheiro Verde (Salsinha e Cebolinha)');
INSERT INTO tb_ingredients(name) VALUES ('Óleo Vegetal');


INSERT INTO tb_recipe(title, short_description, instructions, time_minutes, rendiment, publication_date, url_img, user_id) VALUES ('Onigiri', 'Bolinhos de arroz deliciosos', 'faça bllallba', 30, 5, TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 'https://example.com/onigiri.jpg', 1);
INSERT INTO tb_recipe(title, short_description, instructions, time_minutes, rendiment, publication_date, url_img, user_id) VALUES ('Tacos de Carne Moída', 'Tacos saborosos com carne moída temperada, perfeitos para uma refeição divertida.', '1. Cozinhe a carne moída com temperos. 2. Aqueça as tortilhas de taco. 3. Monte os tacos com alface, tomate e queijo.', 40, 4, TIMESTAMP WITH TIME ZONE '2025-06-15T19:00:00Z', 'https://example.com/tacos_carne.jpg', 2);
INSERT INTO tb_recipe(title, short_description, instructions, time_minutes, rendiment, publication_date, url_img, user_id) VALUES ('Lasanha à Bolonhesa', 'A clássica lasanha com molho bolonhesa, queijo e massa fresca, ideal para o almoço de domingo.', '1. Prepare o molho bolonhesa. 2. Cozinhe a massa da lasanha. 3. Monte as camadas com molho, massa e queijo. 4. Asse no forno.', 120, 6, TIMESTAMP WITH TIME ZONE '2025-06-20T12:00:00Z', 'https://example.com/lasanha.jpg', 1);
INSERT INTO tb_recipe(title, short_description, instructions, time_minutes, rendiment, publication_date, url_img, user_id) VALUES ('Smoothie de Frutas Vermelhas', 'Um smoothie refrescante e nutritivo, cheio de vitaminas para começar bem o dia.', '1. Lave as frutas. 2. Bata as frutas congeladas com iogurte e um pouco de mel no liquidificador até ficar homogêneo. 3. Sirva imediatamente.', 5, 1, TIMESTAMP WITH TIME ZONE '2025-07-01T08:00:00Z', 'https://example.com/smoothie.jpg', 2);
INSERT INTO tb_recipe(title, short_description, instructions, time_minutes, rendiment, publication_date, url_img, user_id) VALUES ('Sopa de Lentilha', 'Uma sopa reconfortante e nutritiva, perfeita para os dias mais frios.', '1. Refogue cebola e alho. 2. Adicione lentilha, legumes picados e caldo. 3. Cozinhe até a lentilha ficar macia. 4. Tempere a gosto.', 60, 4, TIMESTAMP WITH TIME ZONE '2025-07-05T17:30:00Z', 'https://example.com/sopa_lentilha.jpg', 1);
INSERT INTO tb_recipe(title, short_description, instructions, time_minutes, rendiment, publication_date, url_img, user_id) VALUES ('Brownie de Chocolate', 'Brownie denso e chocolatudo, com casquinha crocante por fora e macio por dentro.', '1. Derreta o chocolate com a manteiga. 2. Misture com açúcar, ovos e farinha. 3. Despeje em forma e asse até as bordas ficarem firmes e o centro ligeiramente úmido.', 45, 12, TIMESTAMP WITH TIME ZONE '2025-06-28T16:00:00Z', 'https://example.com/brownie.jpg', 2);
INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (1, 3); -- Onigiri: Salgado (ID da Receita 1)
INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (1, 4); -- Onigiri: Rápido (ID da Receita 1)

INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (2, 3); -- Tacos de Carne Moída: Salgado (ID da Receita 2)
INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (2, 4); -- Tacos de Carne Moída: Rápido (ID da Receita 2)

INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (3, 3); -- Lasanha à Bolonhesa: Salgado (ID da Receita 3)
INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (3, 5); -- Lasanha à Bolonhesa: Devagar (ID da Receita 3)

INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (4, 1); -- Smoothie de Frutas Vermelhas: Fitnes (ID da Receita 4)
INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (4, 4); -- Smoothie de Frutas Vermelhas: Rápido (ID da Receita 4)

INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (5, 1); -- Sopa de Lentilha: Fitnes (ID da Receita 5)
INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (5, 3); -- Sopa de Lentilha: Salgado (ID da Receita 5)
INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (5, 5); -- Sopa de Lentilha: Devagar (ID da Receita 5)

INSERT INTO tb_recipe_category (recipe_id, category_id) VALUES (6, 2); -- Brownie de Chocolate: Doce (ID da Receita 6)
-- Onigiri (agora ID 1 na sua sequência atual de inserts)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (1, 10, 300, 3.50); -- Arroz (ID 10)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (1, 2, 5, 0.10);    -- Sal (ID 2)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (1, 1, 10, 0.20);   -- Açúcar (ID 1)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (1, 17, 50, 0.05);  -- Água (ID 17)

-- Tacos de Carne Moída (agora ID 2)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (2, 16, 400, 20.00); -- Carne Bovina (ID 16)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (2, 7, 100, 1.00);   -- Cebola (ID 7)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (2, 8, 10, 0.20);    -- Alho (ID 8)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (2, 12, 150, 1.50);  -- Tomate (ID 12)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (2, 2, 5, 0.10);     -- Sal (ID 2)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (2, 18, 2, 0.50);    -- Pimenta do Reino (ID 18)

-- Lasanha à Bolonhesa (agora ID 3)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (3, 16, 500, 25.00); -- Carne Bovina (ID 16)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (3, 7, 150, 1.50);   -- Cebola (ID 7)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (3, 8, 15, 0.30);    -- Alho (ID 8)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (3, 12, 400, 4.00);  -- Tomate (ID 12)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (3, 2, 8, 0.15);     -- Sal (ID 2)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (3, 5, 300, 1.50);   -- Leite (ID 5)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (3, 6, 50, 1.50);    -- Manteiga (ID 6)

-- Smoothie de Frutas Vermelhas (agora ID 4)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (4, 5, 200, 1.00);   -- Leite (ID 5)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (4, 1, 30, 0.25);    -- Açúcar (ID 1)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (4, 17, 100, 0.05);  -- Água (ID 17)

-- Sopa de Lentilha (agora ID 5)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (5, 11, 250, 3.00);  -- Feijão (Lentilha - ID 11)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (5, 7, 80, 0.80);    -- Cebola (ID 7)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (5, 8, 10, 0.20);    -- Alho (ID 8)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (5, 14, 100, 1.20);  -- Cenoura (ID 14)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (5, 17, 1000, 0.50); -- Água (ID 17)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (5, 2, 5, 0.10);     -- Sal (ID 2)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (5, 18, 2, 0.50);    -- Pimenta do Reino (ID 18)

-- Brownie de Chocolate (agora ID 6)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (6, 1, 200, 1.50);  -- Açúcar (ID 1)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (6, 3, 100, 0.80);  -- Farinha de Trigo (ID 3)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (6, 4, 3, 1.80);    -- Ovo (ID 4)
INSERT INTO tb_recipe_ingredients (recipe_id, ingredients_id, quantity, price) VALUES (6, 6, 150, 4.50);  -- Manteiga (ID 6)

INSERT INTO tb_review (nota, comment, data_review, recipe_id, user_id) VALUES (4, 'Muito bom para um lanche rápido e diferente!', TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 1, 1); -- Onigiri (User 1)

INSERT INTO tb_review (nota, comment, data_review, recipe_id, user_id) VALUES (5, 'Receita de tacos perfeita! Fácil de fazer e super saborosa para a família.',  TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 2, 2); -- Tacos de Carne Moída (User 2)

INSERT INTO tb_review (nota, comment, data_review, recipe_id, user_id) VALUES (5, 'A lasanha ficou incrível, um verdadeiro almoço de domingo! Valeu cada minuto.',  TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 3, 1); -- Lasanha à Bolonhesa (User 1)

INSERT INTO tb_review (nota, comment, data_review, recipe_id, user_id) VALUES (4, 'Smoothie refrescante e saudável. Adorei a combinação de frutas.',  TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 4, 2); -- Smoothie de Frutas Vermelhas (User 2)

INSERT INTO tb_review (nota, comment, data_review, recipe_id, user_id) VALUES (4, 'Sopa de lentilha muito reconfortante, ótima para um dia frio.',  TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 5, 1); -- Sopa de Lentilha (User 1)

INSERT INTO tb_review (nota, comment, data_review, recipe_id, user_id) VALUES (5, 'O brownie ficou sensacional, exatamente como eu gosto: crocante por fora e macio por dentro!',  TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 6, 2); -- Brownie de Chocolate (User 2)

INSERT INTO tb_review (nota, comment, data_review, recipe_id, user_id) VALUES (3, 'O Onigiri é bom, mas o recheio não me agradou muito. Prefiro simples.',  TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 1, 2); -- Onigiri (segunda review - User 2)

INSERT INTO tb_review (nota, comment, data_review, recipe_id, user_id) VALUES (4, 'Demorou um pouco para preparar, mas o resultado final da lasanha compensou.',  TIMESTAMP WITH TIME ZONE '2022-07-25T13:00:00Z', 3, 1); -- Lasanha à Bolonhesa (segunda review - User 1)

INSERT INTO tb_favorite (user_id, recipe_id) VALUES (1, 1);
INSERT INTO tb_favorite (user_id, recipe_id) VALUES (1, 3);


INSERT INTO tb_favorite (user_id, recipe_id) VALUES (2, 2);
INSERT INTO tb_favorite (user_id, recipe_id) VALUES (2, 4);
INSERT INTO tb_favorite (user_id, recipe_id) VALUES (2, 6);