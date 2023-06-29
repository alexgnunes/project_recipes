INSERT INTO public.category (nome_category) VALUES ('Bolos');
INSERT INTO public.category (nome_category) VALUES ('Massas');
INSERT INTO public.category (nome_category) VALUES ('Sobremesas');
INSERT INTO public.category (nome_category) VALUES ('Saladas');

INSERT INTO public.person (email_person, nome_person, senha_person) VALUES ('john.doe@example.com', 'John Doe', 'john');
INSERT INTO public.person (email_person, nome_person, senha_person) VALUES ('jane@example.com', 'Jane Smith', 'jane');

INSERT INTO public.recipe (description_recipe, difficulty_recipe, preparation_time_recipe, title_recipe, category_id_category, person_id_person) VALUES ('1 xc acucar, 100g de trigo, misture ponha forn por 30 min', 2, 30, 'Bolo Simples', 1, 1);
INSERT INTO public.recipe (description_recipe, difficulty_recipe, preparation_time_recipe, title_recipe, category_id_category, person_id_person) VALUES ('200g de carne moida, 1 cebola picada, 2 dentes de alho, refogue a carne com a cebola e o alho ate dourar', 3, 45, 'Carne Moida Refogada', 2, 2);
INSERT INTO public.recipe (description_recipe, difficulty_recipe, preparation_time_recipe, title_recipe, category_id_category, person_id_person) VALUES ('2 ovos, 200ml de leite, 150g de farinha, bata os ingredientes no liquidificador e faca as panquecas em uma frigideira quente', 1, 20, 'Panquecas Simples', 1, 2);
INSERT INTO public.recipe (description_recipe, difficulty_recipe, preparation_time_recipe, title_recipe, category_id_category, person_id_person) VALUES ('500g de macarrao, 400ml de molho de tomate, queijo ralado a gosto, cozinhe o macarrao, adicione o molho de tomate e polvilhe queijo ralado por cima', 2, 25, 'Macarrao com Molho de Tomate', 3, 1);

INSERT INTO public.commentary (content_commentary, person_id_person, recipe_id_recipe) VALUES ('Ótima receita!', 1, 1);
INSERT INTO public.commentary (content_commentary, person_id_person, recipe_id_recipe) VALUES ('Delicioso!', 2, 1);
INSERT INTO public.commentary (content_commentary, person_id_person, recipe_id_recipe) VALUES ('Preciso experimentar!', 1, 2);
INSERT INTO public.commentary (content_commentary, person_id_person, recipe_id_recipe) VALUES ('Adorei o passo a passo!', 2, 2);
INSERT INTO public.commentary (content_commentary, person_id_person, recipe_id_recipe) VALUES ('Fácil de fazer!', 1, 3);
INSERT INTO public.commentary (content_commentary, person_id_person, recipe_id_recipe) VALUES ('Vou fazer no fim de semana!', 2, 3);

INSERT INTO public.favorite (person_id_person, recipe_id_recipe) VALUES (1, 1);
INSERT INTO public.favorite (person_id_person, recipe_id_recipe) VALUES (1, 2);
INSERT INTO public.favorite (person_id_person, recipe_id_recipe) VALUES (2, 1);
INSERT INTO public.favorite (person_id_person, recipe_id_recipe) VALUES (2, 3);