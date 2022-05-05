CREATE TABLE buyer (
  id        SERIAL NOT NULL, 
  firstname varchar(25) NOT NULL, 
  lastname  varchar(30) NOT NULL, 
  address   varchar(40) NOT NULL, 
  city      varchar(40) NOT NULL, 
  mobile    varchar(20), 
  PRIMARY KEY (id));
CREATE TABLE employee (
  id        SERIAL NOT NULL, 
  firstname varchar(25) NOT NULL, 
  lastname  varchar(30) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE product_receipt (
  product_id int4 NOT NULL, 
  receipt_id int4 NOT NULL, 
  quantity   int4 NOT NULL, 
  PRIMARY KEY (product_id, 
  receipt_id));
CREATE TABLE item (
  id           SERIAL NOT NULL, 
  value        varchar(50) NOT NULL, 
  guide_needed bool, 
  itemtype_id  int4 NOT NULL, 
  color_id     int4, 
  PRIMARY KEY (id));
CREATE TABLE procurement (
  id      SERIAL NOT NULL, 
  created date NOT NULL, 
  storage_id int4 NOT NULL,
  PRIMARY KEY (id));
CREATE TABLE receipt (
  id          SERIAL NOT NULL, 
  sold        date NOT NULL, 
  mounted     date, 
  buyer_id    int4 NOT NULL, 
  employee_id int4, 
  PRIMARY KEY (id));
CREATE TABLE storage (
  id       SERIAL NOT NULL, 
  name     varchar(30) NOT NULL, 
  location varchar(50) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE "user" (
  id       SERIAL NOT NULL, 
  username varchar(30) NOT NULL, 
  password varchar(150) NOT NULL, 
  email    varchar(50) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE item_storage (
  storage_id int4 NOT NULL, 
  item_id    int4 NOT NULL, 
  quantity   int4 NOT NULL, 
  PRIMARY KEY (storage_id, 
  item_id));
CREATE TABLE item_procurement (
  item_id        int4 NOT NULL, 
  procurement_id int4 NOT NULL, 
  quantity       int4 NOT NULL, 
  PRIMARY KEY (item_id, 
  procurement_id));
CREATE TABLE storage_archive (
  id        SERIAL NOT NULL, 
  "date"    date NOT NULL, 
  storage_id int4 NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE product (
  id       SERIAL NOT NULL, 
  door_id  int4 NOT NULL, 
  motor_id int4 NOT NULL, 
  guide_id int4, 
  storage_id int4 NOT NULL,
  PRIMARY KEY (id));
CREATE TABLE item_storage_archive (
  storage_archive_id int4 NOT NULL, 
  item_id            int4 NOT NULL, 
  quantity           int4 NOT NULL, 
  PRIMARY KEY (storage_archive_id, 
  item_id));
CREATE TABLE item_type (
  id    SERIAL NOT NULL, 
  value varchar(30) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE color (
  id    SERIAL NOT NULL, 
  value varchar(45) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE role (
  id   SERIAL NOT NULL, 
  name varchar(30) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE role_user (
  role_id int4 NOT NULL, 
  user_id int4 NOT NULL, 
  PRIMARY KEY (role_id, 
  user_id));
ALTER TABLE item_storage ADD CONSTRAINT FKitem_stora191964 FOREIGN KEY (storage_id) REFERENCES storage (id);
ALTER TABLE receipt ADD CONSTRAINT FKreceipt767410 FOREIGN KEY (buyer_id) REFERENCES buyer (id);
ALTER TABLE receipt ADD CONSTRAINT FKreceipt115652 FOREIGN KEY (employee_id) REFERENCES employee (id);
ALTER TABLE product_receipt ADD CONSTRAINT FKproduct_re231484 FOREIGN KEY (product_id) REFERENCES product (id);
ALTER TABLE product_receipt ADD CONSTRAINT FKproduct_re145471 FOREIGN KEY (receipt_id) REFERENCES receipt (id);
ALTER TABLE item_storage ADD CONSTRAINT FKitem_stora778989 FOREIGN KEY (item_id) REFERENCES item (id);
ALTER TABLE item_procurement ADD CONSTRAINT FKitem_procu843418 FOREIGN KEY (item_id) REFERENCES item (id);
ALTER TABLE item_procurement ADD CONSTRAINT FKitem_procu987211 FOREIGN KEY (procurement_id) REFERENCES procurement (id);
ALTER TABLE item_storage_archive ADD CONSTRAINT FKitem_stora739509 FOREIGN KEY (storage_archive_id) REFERENCES storage_archive (id);
ALTER TABLE item_storage_archive ADD CONSTRAINT FKitem_stora73069 FOREIGN KEY (item_id) REFERENCES item (id);
ALTER TABLE storage_archive ADD CONSTRAINT FKstorage_ar157120 FOREIGN KEY (storage_id) REFERENCES storage (id);
ALTER TABLE product ADD CONSTRAINT FKproduct126713 FOREIGN KEY (guide_id) REFERENCES item (id);
ALTER TABLE product ADD CONSTRAINT FKproduct786247 FOREIGN KEY (motor_id) REFERENCES item (id);
ALTER TABLE product ADD CONSTRAINT FKproduct665894 FOREIGN KEY (door_id) REFERENCES item (id);
ALTER TABLE item ADD CONSTRAINT FKitem591133 FOREIGN KEY (itemtype_id) REFERENCES item_type (id);
ALTER TABLE item ADD CONSTRAINT FKitem736906 FOREIGN KEY (color_id) REFERENCES color (id);
ALTER TABLE role_user ADD CONSTRAINT FKrole_user463549 FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE role_user ADD CONSTRAINT FKrole_user538025 FOREIGN KEY (user_id) REFERENCES "user" (id);
ALTER TABLE procurement ADD CONSTRAINT FKstorage_proc157120 FOREIGN KEY (storage_id) REFERENCES storage (id);
ALTER TABLE product ADD CONSTRAINT FKstorage_prod157120 FOREIGN KEY (storage_id) REFERENCES storage (id);
