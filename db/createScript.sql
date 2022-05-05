CREATE TABLE "user"
(
    id  SERIAL PRIMARY KEY,
    username varchar(30) NOT NULL,
    password varchar(150) NOT NULL,
    roles varchar(50)
);

CREATE TABLE guide
(
    id  SERIAL PRIMARY KEY,
    value varchar(30) NOT NULL
);
CREATE TABLE color
(
    id  SERIAL PRIMARY KEY,
    value varchar(30) NOT NULL
);
CREATE TABLE motor
(
    id  SERIAL PRIMARY KEY,
    value varchar(30) NOT NULL
);
CREATE TABLE storage
(
    id  SERIAL PRIMARY KEY,
    name varchar(30),
	location varchar(50)
);
CREATE TABLE buyer
(  
	id  SERIAL PRIMARY KEY,
    firstname varchar(25) NOT NULL,
    lastname varchar(30) NOT NULL,
    address varchar(40) NOT NULL,
    mobile varchar(20)
);
create table employee (
	id  SERIAL PRIMARY KEY,
    firstname varchar(25) NOT NULL,
    lastname varchar(30) NOT NULL
);
CREATE TABLE product
(
    id  SERIAL PRIMARY KEY,
    name   varchar(30) NOT NULL,
    colorid int REFERENCES color (id) NOT NULL,
    guideid int REFERENCES guide (id),
    motorid int REFERENCES motor (id) NOT NULL
);
CREATE TABLE productinstorage
(
    storageid int NOT NULL REFERENCES storage (id),
    productid int NOT NULL REFERENCES product (id),
    quantity int NOT NULL,
    PRIMARY KEY(storageid,productid)
);
CREATE TABLE receipt
(
    id  SERIAL PRIMARY KEY,
    solddate date NOT NULL,
    mountdate date,
    buyerid int NOT NULL REFERENCES buyer (id),
	employeeid int REFERENCES employee (id)
);
CREATE TABLE productonreceipt
(
    receiptid int NOT NULL REFERENCES receipt (id),
    productid int NOT NULL REFERENCES product (id),
    quantity int NOT NULL,
    PRIMARY KEY(receiptid,productid)
);

