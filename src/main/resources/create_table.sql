CREATE TABLE users (
                       id BIGINT UNSIGNED AUTO_INCREMENT,
                       username VARCHAR(30) NOT NULL,
                       password VARCHAR(80) NOT NULL,
                       email VARCHAR(50) UNIQUE,
                       PRIMARY KEY (id)
);

CREATE TABLE roles (
                       id INT UNSIGNED AUTO_INCREMENT,
                       name VARCHAR(50) NOT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE users_roles (
                             user_id BIGINT UNSIGNED NOT NULL,
                             role_id INT UNSIGNED NOT NULL,
                             PRIMARY KEY (user_id, role_id),
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (role_id) REFERENCES roles(id)
);

insert into roles(name)
values
    ('ROLE_USER'), ('ROLE_ADMIN');
# добавляем админа
insert into users (username,password,email)
values ('admin', '$2a$12$3Ay9L5jcSx7fm.8.ZIUqzuq3V9Hp3h7znk02W0heRy.VsmYNoEzT6','admin@mail.ru');
# password = admin
insert into users_roles (user_id, role_id) value (1,2);
# добавляем юзера
insert into users (username,password,email)
values ('user', '$2a$10$zoqC6ro3hB22Ko/jW/7Teu57BxY/GKpQFFK8Gq0cqnw/LfLgPaOB.','user@mail.ru');
# password = user
insert into users_roles (user_id, role_id) value (2,1);
