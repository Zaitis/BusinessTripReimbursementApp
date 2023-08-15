Create table receipt (Id int primary key, type varchar(50), price DECIMAL);
Create table reimbursement (ID int primary key, first_name varchar(50), last_name varchar(50), start_date DATE, end_date DATE, distance DECIMAL);
Insert into receipt (ID, type, price) values (1, 'Nam Ha Minh', 13.95);
Insert into receipt (ID, type, price) values (2, 'ticket', 13.95);