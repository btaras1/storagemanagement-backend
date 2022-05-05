CREATE TABLE Item (
  id    SERIAL NOT NULL UNIQUE, 
  value varchar(30) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Color (
  PRIMARY KEY (id))INHERITS (Item);
CREATE TABLE Storage (
  id       SERIAL NOT NULL, 
  name     varchar(30) NOT NULL, 
  location varchar(50) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Motor (
  guideNeeded bool NOT NULL, 
  CONSTRAINT motor_pkey PRIMARY KEY (id))INHERITS (Item);
CREATE TABLE Guide (
  CONSTRAINT guide_pkey PRIMARY KEY (id))INHERITS (Item);
CREATE TABLE Product (
  id      SERIAL NOT NULL, 
  Colorid int4 NOT NULL, 
  Guideid int4, 
  Motorid int4 NOT NULL, 
  Doorid  int4 NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE "user" (
  id       SERIAL NOT NULL, 
  username varchar(30) NOT NULL, 
  password varchar(150) NOT NULL, 
  email varchar(50) NOT NULL,
  PRIMARY KEY (id));
  CREATE TABLE Role(
  id SERIAL NOT NULL, 
  name varchar(30) NOT NULL,
  PRIMARY KEY (id));
  CREATE TABLE UserRole (
  Userid int4 NOT NULL, 
  Roleid    int4 NOT NULL);
CREATE TABLE Employee (
  id        SERIAL NOT NULL, 
  Firstname varchar(25) NOT NULL, 
  Lastname  varchar(30) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Buyer (
  id        SERIAL NOT NULL, 
  Firstname varchar(25) NOT NULL, 
  Lastname  varchar(30) NOT NULL, 
  Address   varchar(40) NOT NULL, 
  Mobile    varchar(20), 
  PRIMARY KEY (id));
CREATE TABLE ItemInStorage (
  Storageid int4 NOT NULL, 
  Itemid    int4 NOT NULL, 
  quantity  int4 NOT NULL);
CREATE TABLE Receipt (
  id           SERIAL NOT NULL, 
  soldDate     date NOT NULL, 
  mountingDate date, 
  Buyerid      int4 NOT NULL, 
  Employeeid   int4, 
  PRIMARY KEY (id));
CREATE TABLE ProductOnReceipt (
  Productid int4 NOT NULL, 
  Receiptid int4 NOT NULL, 
  quantity  int4 NOT NULL, 
  PRIMARY KEY (Productid, 
  Receiptid));
CREATE TABLE Door (
  CONSTRAINT door_pkey PRIMARY KEY (id))INHERITS (Item);
CREATE TABLE Procurement (
  id         SERIAL NOT NULL, 
  dateImport date NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE StorageArchive (
  id         SERIAL NOT NULL, 
  "date"     date NOT NULL, 
  storageid int4 NOT NULL,
  PRIMARY KEY (id));
CREATE TABLE ItemInProcurement (
  Itemid        int4 NOT NULL, 
  Procurementid int4 NOT NULL, 
  amount     int4 NOT NULL, 
  PRIMARY KEY (Itemid, 
  Procurementid));
CREATE TABLE ItemInStorageArchive (
  StorageArchiveid int4 NOT NULL, 
  Itemid           int4 NOT NULL, 
  amount int4 NOT NULL, 
  PRIMARY KEY (StorageArchiveid, 
  Itemid));
ALTER TABLE Product ADD CONSTRAINT FKProduct294713 FOREIGN KEY (Colorid) REFERENCES Color (id);
ALTER TABLE Product ADD CONSTRAINT FKProduct714115 FOREIGN KEY (Guideid) REFERENCES Guide (id);
ALTER TABLE Product ADD CONSTRAINT FKProduct438872 FOREIGN KEY (Motorid) REFERENCES Motor (id);
ALTER TABLE ItemInStorage ADD CONSTRAINT FKItemInStor467494 FOREIGN KEY (Storageid) REFERENCES Storage (id);
ALTER TABLE Receipt ADD CONSTRAINT FKReceipt717278 FOREIGN KEY (Buyerid) REFERENCES Buyer (id);
ALTER TABLE Receipt ADD CONSTRAINT FKReceipt594937 FOREIGN KEY (Employeeid) REFERENCES Employee (id);
ALTER TABLE ProductOnReceipt ADD CONSTRAINT FKProductOnR364094 FOREIGN KEY (Productid) REFERENCES Product (id);
ALTER TABLE ProductOnReceipt ADD CONSTRAINT FKProductOnR415340 FOREIGN KEY (Receiptid) REFERENCES Receipt (id);
ALTER TABLE Product ADD CONSTRAINT FKProduct936787 FOREIGN KEY (Doorid) REFERENCES Door (id);
ALTER TABLE ItemInStorage ADD CONSTRAINT FKItemInStor190381 FOREIGN KEY (Itemid) REFERENCES Item (id);
ALTER TABLE ItemInProcurement ADD CONSTRAINT FKItemInProc367330 FOREIGN KEY (Itemid) REFERENCES Item (id);
ALTER TABLE ItemInProcurement ADD CONSTRAINT FKItemInProc586141 FOREIGN KEY (Procurementid) REFERENCES Procurement (id);
ALTER TABLE ItemInStorageArchive ADD CONSTRAINT FKItemInStor683212 FOREIGN KEY (StorageArchiveid) REFERENCES StorageArchive (id);
ALTER TABLE ItemInStorageArchive ADD CONSTRAINT FKItemInStor761961 FOREIGN KEY (Itemid) REFERENCES Item (id);
ALTER TABLE UserRole ADD CONSTRAINT FKUserRol465594 FOREIGN KEY (Userid) REFERENCES "user" (id);
ALTER TABLE UserRole ADD CONSTRAINT FKUserRol467794 FOREIGN KEY (Roleid) REFERENCES Role (id);
ALTER TABLE StorageArchive ADD CONSTRAINT FKStArch717278 FOREIGN KEY (storageid) REFERENCES Storage (id);
