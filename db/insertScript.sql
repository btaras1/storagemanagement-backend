BEGIN;
INSERT INTO role (name)
VALUES('ROLE_USER');
INSERT INTO role (name)
VALUES('ROLE_ADMIN');
INSERT INTO item_type(value)
VALUES ('DOOR');
INSERT INTO item_type(value)
VALUES ('MOTOR');
INSERT INTO item_type(value)
VALUES ('GUIDE');
INSERT INTO color(value)
VALUES ('RAL 7016 WOOD');
INSERT INTO color(value)
VALUES ('RAL 9016 - WOOD');
INSERT INTO color(value)
VALUES ('DIAMOND BLACK DURA');
INSERT INTO item(value,itemtype_id,color_id)
VALUES ('2750 X 2250',1,1);
INSERT INTO item(value,itemtype_id,color_id)
VALUES ('2500 X 2125',1,2);
INSERT INTO item(value,itemtype_id,color_id)
VALUES ('2750 X 2125',1,3);
INSERT INTO item(value,guide_needed,itemtype_id)
VALUES ('LIFTRONIC 700',false,2);
INSERT INTO item(value,guide_needed,itemtype_id)
VALUES ('SUPRAMATIC E4',false,2);
INSERT INTO item(value,guide_needed,itemtype_id)
VALUES ('PROMATIC 4',true,2);
INSERT INTO item(value,itemtype_id)
VALUES ('VODILICA MOTORA K',3);
INSERT INTO item(value,itemtype_id)
VALUES ('VODILICA MOTORA M',3);
INSERT INTO item(value,itemtype_id)
VALUES ('VODILICA MOTORA L',3);
INSERT INTO employee(firstname,lastname)
VALUES ('Ivo','Ivic');
INSERT INTO employee(firstname,lastname)
VALUES ('Pero','Peric');
INSERT INTO employee(firstname,lastname)
VALUES ('Marin','Maric');
INSERT INTO buyer(firstname,lastname,address,city,mobile)
VALUES('Ana','Anic','Ivana Cankara 5', 'Cakovec',null);
INSERT INTO buyer(firstname,lastname,address,city,mobile)
VALUES('Boris','Boric','Ilica 5', 'Zagreb','0987654321');
INSERT INTO buyer(firstname,lastname,address,city,mobile)
VALUES('Stjepan','Stjepic','Ljudevita Gaja 17', 'Koprivnica','092332559');
INSERT INTO storage(name,location)
VALUES ('CENTRALNO','BJELOVAR');
INSERT INTO storage(name,location)
VALUES ('VANJSKO','ZAGREB');
INSERT INTO procurement (created,storage_id)
VALUES('2020-05-07',1);
INSERT INTO procurement (created,storage_id)
VALUES('2020-07-09',2);
COMMIT;

BEGIN;
INSERT INTO storage_archive (date,storage_id)
VALUES('2020-11-11',1);
INSERT INTO storage_archive (date,storage_id)
VALUES('2020-03-03',2);
INSERT INTO product(door_id,motor_id,guide_id,storage_id)
VALUES(1,4,7,1);
INSERT INTO product(door_id,motor_id,guide_id,storage_id)
VALUES(2,5,8,1);
INSERT INTO product(door_id,motor_id,guide_id,storage_id)
VALUES(3,6,9,2);
INSERT INTO receipt(sold,buyer_id)
VALUES('2021-01-01',1);
INSERT INTO receipt(sold,mounted,buyer_id,employee_id)
VALUES('2021-01-01','2021-03-03',2,1);
COMMIT;


BEGIN;
INSERT INTO product_receipt(product_id,receipt_id,quantity)
VALUES(1,1,2);
INSERT INTO product_receipt(product_id,receipt_id,quantity)
VALUES(2,2,1);
COMMIT;
BEGIN;
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,1,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,2,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,3,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,4,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,5,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,6,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,7,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,8,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,9,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,2,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,3,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,4,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,5,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,6,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,7,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,8,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,9,10);
COMMIT;
BEGIN;
INSERT INTO item_procurement(item_id,procurement_id,quantity)
VALUES(1,1,10);
INSERT INTO item_procurement(item_id,procurement_id,quantity)
VALUES(2,2,10);
INSERT INTO item_procurement(item_id,procurement_id,quantity)
VALUES(3,1,10);
INSERT INTO item_procurement(item_id,procurement_id,quantity)
VALUES(4,2,10);
INSERT INTO item_procurement(item_id,procurement_id,quantity)
VALUES(5,1,10);
INSERT INTO item_procurement(item_id,procurement_id,quantity)
VALUES(6,2,10);
INSERT INTO item_procurement(item_id,procurement_id,quantity)
VALUES(7,1,10);
INSERT INTO item_procurement(item_id,procurement_id,quantity)
VALUES(8,2,10);
INSERT INTO item_procurement(item_id,procurement_id,quantity)
VALUES(8,1,10);
COMMIT;
BEGIN;
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(3,1,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(3,2,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(3,3,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(3,4,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(3,5,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(3,6,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(3,7,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(3,8,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(3,9,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(4,1,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(4,2,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(4,3,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(4,4,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(4,5,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(4,6,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(4,7,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(4,8,10);
INSERT INTO item_storage_archive(storage_archive_id,item_id,quantity)
VALUES(4,9,10);
COMMIT;


