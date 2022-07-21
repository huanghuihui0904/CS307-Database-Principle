create table product
(
    pruduct_id   serial primary key,
    product_code char(7) unique not null,
    product_name varchar(200)   not null,

    constraint check_code check (
        length(product_code) = 7 )
);
create table product_model
(
    product_model_id serial primary key,
    product_model    varchar(200) unique not null,
    unit_price       integer                 not null,
    product_code     char(7)             not null,

    foreign key (product_code) references product (product_code)

);
create table contract
(
    contract_id       serial primary key,
    contract_number   char(10) unique not null,
    director          varchar(30)     not null,
    client_enterprise varchar(200)    not null,
    contract_date     date            not null,

    foreign key (client_enterprise) references client (client_enterprise),
    constraint check_number check (
        length(contract_number) = 10 )
);

create table salesman
(
    salesman_id     serial primary key,
    salesman_number char(8) unique not null,
    salesman        varchar(100)   not null,

    gender          varchar(10)    not null,
    age             integer            not null,----buqueding
    mobile_phone    char(11)       not null,

    constraint check_phone check (
        length(mobile_phone) = 11 ),
    constraint check_number check (
        length(salesman_number) = 8 )

);

create table orders
(
   contract_id               integer not null,
    product_model_id          integer not null,
    salesman_id              integer not null,
    contract_number         char(10)     not null,
    product_model           varchar(200)  not null,
    salesman_number         char(8)             not null,
    quantity                  integer not null,
    "contract date"           date    not null,
    "estimated delivery date" date    not null,
    primary key (product_model_id, contract_id, salesman_id),
    foreign key (product_model_id) references product_model (product_model_id ),
    foreign key (contract_id) references contract (contract_id ),
    foreign key (salesman_id) references salesman (salesman_id)
);