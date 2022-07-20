CREATE TABLE users
(
   id bigint NOT NULL,
   created timestamp without time zone,
   email character varying(255),
   enabled boolean NOT NULL,
   password character varying(255),
   username character varying(255),
   CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE tokens
(
    id bigint NOT NULL,
    expiry_date timestamp without time zone,
    token character varying(255),
    user_id bigint,
    CONSTRAINT tokens_pkey PRIMARY KEY (id),
    CONSTRAINT fk_tokens_users FOREIGN KEY(id) references users(id)
);

CREATE TABLE subreddit
(
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL,
    user_id bigint,
    CONSTRAINT subreddit_pkey PRIMARY KEY (id),
    CONSTRAINT fk_subreddit_users FOREIGN KEY(id) references users(id)
);
