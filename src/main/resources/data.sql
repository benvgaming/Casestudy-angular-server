INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email)VALUES ('123 Maple
St','London','On', 'N1N-1N1','(555)555-5555','Trusted','ABC Supply Co.','abc@supply.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('543
Sycamore Ave','Toronto','On', 'N1P-1N1','(999)555-5555','Trusted','Big Bills
Depot','bb@depot.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('922 Oak
St','London','On', 'N1N-1N1','(555)555-5599','Untrusted','Shady Sams','ss@underthetable.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('922 Oak
St','London','On', 'N1N-1N1','(555)555-5599','Trusted','Manh Cuong','mn@underthetable.com');
    
INSERT INTO Product  (id,vendorid,name,costprice,msrp,rop,eoq,qoh,qoo,qrcode,qrcodetxt) VALUES ('1A1',1,'product1',20,10,10,10,10,0,'','');
INSERT INTO Product  (id,vendorid,name,costprice,msrp,rop,eoq,qoh,qoo,qrcode,qrcodetxt) VALUES ('1A2',2,'product2',20,10,10,10,10,0,'','');
INSERT INTO Product  (id,vendorid,name,costprice,msrp,rop,eoq,qoh,qoo,qrcode,qrcodetxt) VALUES ('1A3',3,'product3',20,10,10,10,10,0,'','');

INSERT INTO Product  (id,vendorid,name,costprice,msrp,rop,eoq,qoh,qoo,qrcode,qrcodetxt) VALUES ('1A4',4,'product4',20,10,10,10,10,0,'','');
INSERT INTO Product  (id,vendorid,name,costprice,msrp,rop,eoq,qoh,qoo,qrcode,qrcodetxt) VALUES ('1A5',4,'product5',20,10,10,10,10,0,'','');
INSERT INTO Product  (id,vendorid,name,costprice,msrp,rop,eoq,qoh,qoo,qrcode,qrcodetxt) VALUES ('1A6',4,'product6',20,10,10,10,10,0,'','');