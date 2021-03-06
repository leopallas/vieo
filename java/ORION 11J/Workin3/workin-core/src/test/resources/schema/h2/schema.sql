    drop table ROLE_TABLE if exists;

    drop table USER_TABLE if exists;

    drop table USER_ROLE if exists;
    
    create table ROLE_TABLE (
        id bigint generated by default as identity,
        name varchar(255) not null unique,
        VERSION bigint(20) DEFAULT NULL,
        primary key (id)
    );

    create table USER_TABLE (
        id bigint generated by default as identity,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        VERSION bigint(20) DEFAULT NULL,
        primary key (id)
    );

    create table USER_ROLE (
        user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    );
    
    alter table USER_ROLE 
        add constraint FK1306854BF125651E 
        foreign key (user_id) 
        references USER_TABLE;

    alter table USER_ROLE 
        add constraint FK1306854B4BFAA13E 
        foreign key (role_id) 
        references ROLE_TABLE;
