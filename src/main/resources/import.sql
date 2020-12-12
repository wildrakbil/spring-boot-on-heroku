INSERT INTO role (name,permits) VALUES ('USER','ROLE_USER');
INSERT INTO role (name,permits) VALUES ('ADMINISTRATOR','ROLE_ADMIN');
INSERT INTO user (first_name, last_name, user_name, password, identification, id_role) VALUES ('user', 'Garzon', 'user', '$2a$10$O9wxmH/AeyZZzIS09Wp8YOEMvFnbRVJ8B4dmAMVSGloR62lj.yqXG',100, 1);
INSERT INTO user (first_name, last_name, user_name, password, identification, id_role) VALUES ('admin', 'Bohorquez', 'admin', '$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS',100, 2);
