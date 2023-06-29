DELETE FROM public.favorite;
DELETE FROM public.commentary;
DELETE FROM public.recipe;
DELETE FROM public.person;
DELETE FROM public.category;

INSERT INTO public.category (id_category, nome_category) VALUES (2, 'Bolos');
INSERT INTO public.category (id_category, nome_category) VALUES (3, 'Massas');
INSERT INTO public.category (id_category, nome_category) VALUES (4, 'Sobremesas');
INSERT INTO public.category (id_category, nome_category) VALUES (5, 'Saladas');
INSERT INTO public.category (id_category, nome_category) VALUES (6, 'frutas');

INSERT INTO public.person (id_person, email_person, nome_person, senha_person) VALUES (2, 'john.doe@example.com', 'John Doe', 'john');
INSERT INTO public.person (id_person, email_person, nome_person, senha_person) VALUES (3, 'jane@example.com', 'Jane Smith', 'jane');

INSERT INTO public.recipe (id_recipe, description_recipe, difficulty_recipe, preparation_time_recipe, title_recipe, category_id_category, person_id_person) VALUES (2, '1 xc acucar, 100g de trigo, misture ponha forn por 30 min', 2, 30, 'Bolo Simples', 2, 2);
INSERT INTO public.recipe (id_recipe, description_recipe, difficulty_recipe, preparation_time_recipe, title_recipe, category_id_category, person_id_person) VALUES (3, '200g de carne moida, 1 cebola picada, 2 dentes de alho, refogue a carne com a cebola e o alho ate dourar', 3, 45, 'Carne Moida Refogada', 3, 3);
INSERT INTO public.recipe (id_recipe, description_recipe, difficulty_recipe, preparation_time_recipe, title_recipe, category_id_category, person_id_person) VALUES (4, '2 ovos, 200ml de leite, 150g de farinha, bata os ingredientes no liquidificador e faca as panquecas em uma frigideira quente', 1, 20, 'Panquecas Simples', 2, 3);
INSERT INTO public.recipe (id_recipe, description_recipe, difficulty_recipe, preparation_time_recipe, title_recipe, category_id_category, person_id_person) VALUES (5, '500g de macarrao, 400ml de molho de tomate, queijo ralado a gosto, cozinhe o macarrao, adicione o molho de tomate e polvilhe queijo ralado por cima', 2, 25, 'Macarrao com Molho de Tomate', 4, 2);

INSERT INTO public.commentary (id_commentary, content_commentary, person_id_person, recipe_id_recipe) VALUES (2, 'Ótima receita!', 2, 2);
INSERT INTO public.commentary (id_commentary, content_commentary, person_id_person, recipe_id_recipe) VALUES (3, 'Delicioso!', 3, 2);
INSERT INTO public.commentary (id_commentary, content_commentary, person_id_person, recipe_id_recipe) VALUES (4, 'Preciso experimentar!', 2, 3);
INSERT INTO public.commentary (id_commentary, content_commentary, person_id_person, recipe_id_recipe) VALUES (5, 'Adorei o passo a passo!', 3, 3);
INSERT INTO public.commentary (id_commentary, content_commentary, person_id_person, recipe_id_recipe) VALUES (6, 'Fácil de fazer!', 2, 4);
INSERT INTO public.commentary (id_commentary, content_commentary, person_id_person, recipe_id_recipe) VALUES (7, 'Vou fazer no fim de semana!', 3, 4);

INSERT INTO public.favorite (id_favorite, person_id_person, recipe_id_recipe) VALUES (2, 2, 2);
INSERT INTO public.favorite (id_favorite, person_id_person, recipe_id_recipe) VALUES (3, 2, 3);
INSERT INTO public.favorite (id_favorite, person_id_person, recipe_id_recipe) VALUES (4, 3, 2);
INSERT INTO public.favorite (id_favorite, person_id_person, recipe_id_recipe) VALUES (5, 3, 4);
