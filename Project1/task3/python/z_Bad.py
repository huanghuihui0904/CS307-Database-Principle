import time

import psycopg2
import csv

# connect to the db
con_out1 = psycopg2.connect(
    host="localhost",
    database="test4",
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
start = time.time() * 1000
con_in = psycopg2.connect(
    host="localhost",
    database="test4",
    user="checker",
    password="123456"
)
cur_in = con_in.cursor()
# sql = "insert into contract_info values(%(contract_number)s,%(client_enterprise)s,%(supply_center)s,%(country)s,%(city)s,%(industry)s,%(product_code)s,%(product_name)s,%(product_model)s,%(unit_price)s,%(quantity)s,%(contract_date)s,%(estimated_delivery_date)s,%(lodgement_date)s,%(director)s,%(salesman)s,%(salesman_number)s,%(gender)s,%(age)s,%(mobile_phone)s)"
sql = "insert into contract_info values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"

with open('contract_info.csv', encoding='utf-8') as f:
    reader = csv.reader(f)
    cnt = -1

    for row in reader:
        cnt = cnt + 1
        if (cnt == 0): continue

        list = [row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9], row[10], row[11],
                row[12], row[13], row[14], row[15], row[16], row[17], row[18], row[19]]
        for num in range(0, 20):

            location = list[num].find('\'')
            if (location != -1):
                list[num] = list[num].replace("\'", "\'\'")
            if (list[num] == ""):
                list[num] = None
            elif (list[num] == "NULL"):
                list[num] = None
            # else:
            #     list[num] = f'\'{list[num]}\''

        # data = {'contract_number': list[0], 'client_enterprise': list[1], 'supply_center': list[2], 'country': list[3],
        #         'city': list[4], 'industry': list[5], 'product_code': list[6], 'product_name': list[7],
        #         'product_model': list[8], 'unit_price': list[9], 'quantity': list[10], 'contract_date': list[11],
        #         'estimated_delivery_date': list[12], 'lodgement_date': list[13], 'director': list[14], 'salesman': list[15],
        #         'salesman_number': list[16], 'gender': list[17], 'age': list[18], 'mobile_phone': list[19]}

        cur_in.execute(sql, list)
        con_in.commit()

cur_in.close()
con_in.close()
end = time.time() * 1000
con_out2 = psycopg2.connect(
    host="localhost",
    database="test4",
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
