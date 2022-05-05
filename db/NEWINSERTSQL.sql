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
INSERT INTO item_type(value)
VALUES ('SUSPENSION');


INSERT INTO color(value)
VALUES ('RAL 7016 WOOD');
INSERT INTO color(value)
VALUES ('RAL 9016 - WOOD');
INSERT INTO color(value)
VALUES ('DIAMOND BLACK DURA');

INSERT INTO item(value,description,itemtype_id,color_id, created, modified)
VALUES ('2750 X 2250','Jako dobar opis vrata 1',1,1,NOW(),NOW());
INSERT INTO item(value,description,itemtype_id,color_id, created, modified)
VALUES ('2500 X 2125','Jako dobar opis vrata 2',1,2,NOW(),NOW());
INSERT INTO item(value,description,itemtype_id,color_id, created, modified)
VALUES ('2750 X 2125','Jako dobar opis vrata 3',1,3,NOW(),NOW());
INSERT INTO item(value,description,guide_needed,itemtype_id, created, modified)
VALUES ('LIFTRONIC 700','Jako dobar opis motora 1',false,2,NOW(),NOW());
INSERT INTO item(value,description,guide_needed,itemtype_id, created, modified)
VALUES ('SUPRAMATIC E4','Jako dobar opis motora  2',false,2,NOW(),NOW());
INSERT INTO item(value,description,guide_needed,itemtype_id, created, modified)
VALUES ('PROMATIC 4','Jako dobar opis motora 3',true,2,NOW(),NOW());
INSERT INTO item(value,description,itemtype_id, created, modified)
VALUES ('VODILICA MOTORA K','Jako dobar opis vodilice 1',3,NOW(),NOW());
INSERT INTO item(value,description,itemtype_id, created, modified)
VALUES ('VODILICA MOTORA M','Jako dobar opis vodilice 2',3,NOW(),NOW());
INSERT INTO item(value,description,itemtype_id, created, modified)
VALUES ('VODILICA MOTORA L','Jako dobar opis vodilice 3',3,NOW(),NOW());
INSERT INTO item(value,description,itemtype_id, created, modified)
VALUES ('OVJES K','Jako dobar opis ovjesa 1',4,NOW(),NOW());
INSERT INTO item(value,description,itemtype_id, created, modified)
VALUES ('OVJES M','Jako dobar opis ovjesa 2',4,NOW(),NOW());
INSERT INTO item(value,description,itemtype_id, created, modified)
VALUES ('OVJES L','Jako dobar opis ovjesa 3',4,NOW(),NOW());



INSERT INTO employee(firstname,lastname, created, modified)
VALUES ('Ivo','Ivic',NOW(),NOW());
INSERT INTO employee(firstname,lastname, created, modified)
VALUES ('Pero','Peric',NOW(),NOW());
INSERT INTO employee(firstname,lastname, created, modified)
VALUES ('Marin','Maric',NOW(),NOW());
INSERT INTO buyer(firstname,lastname,address,city,mobile, created, modified)
VALUES('Ana','Anic','Ivana Cankara 5', 'Cakovec',null,NOW(),NOW());
INSERT INTO buyer(firstname,lastname,address,city,mobile, created, modified)
VALUES('Boris','Boric','Ilica 5', 'Zagreb','0987654321',NOW(),NOW());
INSERT INTO buyer(firstname,lastname,address,city,mobile, created, modified)
VALUES('Stjepan','Stjepic','Ljudevita Gaja 17', 'Koprivnica','092332559',NOW(),NOW());
INSERT INTO storage(name,location, created, modified)
VALUES ('CENTRALNO','BJELOVAR',NOW(),NOW());
INSERT INTO storage(name,location, created, modified)
VALUES ('VANJSKO','ZAGREB',NOW(),NOW());

INSERT INTO procurement (created,storage_id, modified)
VALUES('2020-05-07',1,NOW());
INSERT INTO procurement (created,storage_id,modified)
VALUES('2020-07-09',2,NOW());
COMMIT;

BEGIN;
INSERT INTO storage_archive (date,storage_id,item_id,quantity)
VALUES('2020-11-11',1,1,10);
INSERT INTO storage_archive (date,storage_id,item_id,quantity)
VALUES('2020-03-03',2,2,10);


INSERT INTO receipt(sold,buyer_id, description, created, modified)
VALUES('2021-01-01',1,'Jako dobra montaža',NOW(),NOW());
INSERT INTO receipt(sold,mounted_date,mounted,buyer_id, created, modified)
VALUES('2021-01-01','2021-03-03',true,2,NOW(),NOW());

INSERT INTO employee_receipt(employee_id,receipt_id)
VALUES(1,1);
INSERT INTO employee_receipt(employee_id,receipt_id)
VALUES(2,1);
INSERT INTO employee_receipt(employee_id,receipt_id)
VALUES(3,1);
INSERT INTO employee_receipt(employee_id,receipt_id)
VALUES(1,2);
INSERT INTO employee_receipt(employee_id,receipt_id)
VALUES(2,2);
INSERT INTO employee_receipt(employee_id,receipt_id)
VALUES(3,2);

INSERT INTO item_receipt(receipt_id,item_id,storage_id, quantity)
VALUES(1,1,1,1);
INSERT INTO item_receipt(receipt_id,item_id,storage_id,quantity)
VALUES(1,4,2,2);
INSERT INTO item_receipt(receipt_id,item_id,storage_id,quantity)
VALUES(1,7,1,1);
INSERT INTO item_receipt(receipt_id,item_id,storage_id,quantity)
VALUES(1,2,2,2);
INSERT INTO item_receipt(receipt_id,item_id,storage_id,quantity)
VALUES(2,5,1,2);
INSERT INTO item_receipt(receipt_id,item_id,storage_id,quantity)
VALUES(2,8,2,1);
INSERT INTO item_receipt(receipt_id,item_id,storage_id,quantity)
VALUES(2,3,2,1);
INSERT INTO item_receipt(receipt_id,item_id,storage_id,quantity)
VALUES(2,6,2,1);
INSERT INTO item_receipt(receipt_id,item_id,storage_id,quantity)
VALUES(2,9,2,4);
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
VALUES(1,10,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,11,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(1,12,10);
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
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,10,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,11,10);
INSERT INTO item_storage(storage_id,item_id,quantity)
VALUES(2,12,10);
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



