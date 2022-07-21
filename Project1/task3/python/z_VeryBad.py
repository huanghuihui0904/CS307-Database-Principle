import time

import psycopg2
import csv

# connect to the db
con_out1 = psycopg2.connect(
    host="localhost",
    database="test5",
    user="checker",
    password="123456"
)

# cursor
cur_out1 = con_out1.cursor()
cur_out1.execute(
    f'create table contract_info(contract_number char(10),client_enterprise varchar(200),supply_center varchar(30),country varchar(200),city varchar(200),industry varchar(200),product_code char(7),product_name varchar(200),product_model varchar(200),unit_price int,quantity int,contract_date date,estimated_delivery_date date,lodgement_date date,director varchar(30),salesman varchar(100),salesman_number char(8),gender varchar(10),age int,mobile_phone char(11));')
con_out1.commit();
cur_out1.close()
con_out1.close()
start = time.time() * 1000;
con_in = psycopg2.connect(
    host="localhost",
    database="test5",
    user="checker",
    password="123456"
)
cur_in = con_in.cursor()
with open('contract_info.csv', encoding='utf-8') as f:
    reader = csv.reader(f)
    cnt = -1

    for row in reader:
        cnt = cnt + 1
        if (cnt == 0): continue

        list = [row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9], row[10], row[11],
                row[12], row[13], row[14], row[15], row[16], row[17], row[18], row[19]]
        for num in range(0, 20):
            if (isinstance(list[num], str)):
                location = list[num].find('\'')
                if (location != -1):
                    list[num] = list[num].replace("\'", "\'\'")
            if (list[num] == ""):
                list[num] = list[num].replace("", "null")
            elif (list[num] == "NULL"):
                list[num] = list[num].replace("NULL", "null")
            else:
                list[num] = f'\'{list[num]}\''

        cur_in.execute(
            f'insert into contract_info values({list[0]},{list[1]},{list[2]},{list[3]},{list[4]},{list[5]},{list[6]},{list[7]},{list[8]},{list[9]},{list[10]},{list[11]},{list[12]},{list[13]},{list[14]},{list[15]},{list[16]},{list[17]},{list[18]},{list[19]})')
        con_in.commit()

cur_in.close()
con_in.close()
end = time.time() * 1000
con_out2 = psycopg2.connect(
    host="localhost",
    database="test5",
    user="checker",
    password="123456"
)
cur_out2 = con_out2.cursor()
cur_out2.execute(
    f'insert into client (client_enterprise, supply_center, country, city, industry)select distinct client_enterprise, supply_center, country, city, industry from contract_info;')
cur_out2.execute(
    f'insert into product(product_code, product_name) select distinct product_code,product_name from contract_info;')
cur_out2.execute(
    f'insert into product_model(product_model, unit_price, product_code) select distinct product_model, unit_price, product_code from contract_info;')
cur_out2.execute(
    f'insert into contract(contract_number, director, client_enterprise, contract_date) select distinct contract_number, director, client_enterprise, contract_date from contract_info;')
cur_out2.execute(
    f'insert into salesman(salesman_number, salesman, gender, age, mobile_phone) select distinct salesman_number, salesman, gender, age, mobile_phone from contract_info;')
cur_out2.execute(
    f'insert into orders (contract_number, product_model, salesman_number, quantity, estimated_delivery_date, lodgement_date) select distinct contract_number, product_model, salesman_number, quantity, estimated_delivery_date, lodgement_date from contract_info;')

cur_out2.execute(f'drop table contract_info')

con_out2.commit()
cur_out2.close()
con_out2.close()
# commit the change

# close the cursor


# close the connection

print("%d records successfully loaded" % (cnt))
print("Loading speed : %d records/s" % ((cnt * 1000) / (end - start)))
print(end - start)
