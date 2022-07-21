import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

public class t3_AwfulLoader {
    private static URL        propertyURL = t3_AwfulLoader.class
            .getResource("/loader.cnf");

    private static Connection         con = null;
    private static boolean            verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch(Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    private static void loadData(String contract_number, String client_enterprise, String supply_center, String country, String city, String industry, String product_code, String product_name, String product_model, int unit_price, int quantity, java.sql.Date contract_date, java.sql.Date estimated_delivery_date, java.sql.Date lodgement_date, String director, String salesman, String salesman_number, String gender, int age, String mobile_phone)
            throws SQLException {
        Statement stmt;
        if (con != null) {
            stmt = con.createStatement();
            stmt = con.createStatement();
            if (city.equals("NULL")) {
                city = null;
            } else {
                city = "\'" + city + "\'";
            }
            String tempt_lodgement_date;
            if (lodgement_date != null) {
                tempt_lodgement_date= "\'" + lodgement_date + "\'";

            }else {
                tempt_lodgement_date=null;
            }

            stmt.execute("insert into contract_info values('"
                    + contract_number + "','"
                    + client_enterprise + "','"
                    + supply_center + "','"
                    + country + "',"
                    + city + ",'"
                    + industry + "','"
                    + product_code + "','"
                    + product_name + "','"
                    + product_model + "','"
                    + unit_price + "','"
                    + quantity + "','"
                    + contract_date + "','"
                    + estimated_delivery_date + "',"
                    + tempt_lodgement_date + ",'"
                    + director + "','"
                    + salesman + "','"
                    + salesman_number + "','"
                    + gender + "','"
                    + age + "','"
                    + mobile_phone
                    + "')");}
    }

    public static void main(String[] args) {
        String  fileName = null;
        boolean verbose = false;
        long    start;
        long    end;
        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
                switch (args[0]) {
                    case "-v":
                        verbose = true;
                        break;
                    default:
                        System.err.println("Usage: java [-v] AwfulLoader filename");
                        System.exit(1);
                }
                fileName = args[1];
                break;
            default:
                System.err.println("Usage: java [-v] AwfulLoader filename");
                System.exit(1);
        }


        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "test_java");
        Properties prop = new Properties(defprop);



        try (BufferedReader infile
                     = new BufferedReader(new FileReader(fileName))) {
            String   line;
            String[] parts;
            String contract_number;
            String client_enterprise;
            String supply_center;
            String country;
            String city;
            String industry;
            String product_code;
            String product_name;
            String product_model;
            int unit_price;
            int quantity;
            java.sql.Date contract_date;
            java.sql.Date estimated_delivery_date;
            java.sql.Date lodgement_date;
            String director;
            String salesman;
            String salesman_number;
            String gender;
            int age;
            String mobile_phone;
            int      cnt = 0;
            // Empty target table
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            create_contract_info_table();
            closeDB();

            start = System.currentTimeMillis();
            int counter = -1;

            while ((line = infile.readLine()) != null) {
                counter++;
                if (counter == 0) {
                    continue;
                }
                parts = line.split(",");
                if (parts.length > 1) {
                    contract_number = parts[0];
                    client_enterprise = parts[1].replace("'", "''");
                    supply_center = parts[2];
                    country = parts[3];

                    city = parts[4];

                    industry = parts[5];
                    product_code = parts[6];
                    product_name = parts[7].replace("'", "''");
                    product_model = parts[8].replace("'", "''");
                    unit_price = Integer.parseInt(parts[9]);
                    quantity = Integer.parseInt(parts[10]);
                    contract_date = strToDate(parts[11]);
                    estimated_delivery_date = strToDate(parts[12]);
                    lodgement_date = strToDate(parts[13]);
                    director = parts[14];
                    salesman = parts[15];
                    salesman_number = parts[16];
                    gender = parts[17];
                    age = Integer.parseInt(parts[18]);
                    mobile_phone = parts[19];
                    openDB(prop.getProperty("host"), prop.getProperty("database"),
                            prop.getProperty("user"), prop.getProperty("password"));

                    loadData(contract_number, client_enterprise, supply_center, country, city, industry, product_code, product_name, product_model, unit_price, quantity, contract_date, estimated_delivery_date, lodgement_date, director, salesman, salesman_number, gender, age, mobile_phone);
                    closeDB();
                    cnt++;
                }
            }

            end = System.currentTimeMillis();
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            insert_data();
            drop_contract_info_table();

            closeDB();
            System.out.println(cnt + " records successfully loaded");
            System.out.println("Loading speed : "
                    + (cnt * 1000) / (end - start)
                    + " records/s");
            System.out.println(end - start);
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            closeDB();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            closeDB();
            System.exit(1);
        }
        closeDB();
    }
    public static java.sql.Date strToDate(String strDate) {
        if (strDate.equals("")) {
            return null;
        } else {
            String str = strDate;
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            java.util.Date d = null;
            try {

                d = format.parse(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date date = new java.sql.Date(d.getTime());
            return date;
        }
    }

    private static void create_contract_info_table()
            throws SQLException {
        Statement stmt;
        if (con != null) {
            stmt = con.createStatement();
            stmt.execute("create table contract_info(contract_number char(10),client_enterprise varchar(200),supply_center varchar(30),country varchar(200),city varchar(200),industry varchar(200),product_code char(7),product_name varchar(200),product_model varchar(200),unit_price int,quantity int,contract_date date,estimated_delivery_date date,lodgement_date date,director varchar(30),salesman varchar(100),salesman_number char(8),gender varchar(10),age int,mobile_phone char(11));");
        }
    }

    private static void drop_contract_info_table()
            throws SQLException {
        Statement stmt;
        if (con != null) {
            stmt = con.createStatement();
            stmt.execute("drop table contract_info");
        }
    }

    private static void insert_data()
            throws SQLException {
        Statement stmt;
        if (con != null) {
            stmt = con.createStatement();
            stmt.execute("insert into client (client_enterprise, supply_center, country, city, industry)select distinct client_enterprise, supply_center, country, city, industry from contract_info;");
            stmt.execute("insert into product(product_code, product_name) select distinct product_code,product_name from contract_info;");
            stmt.execute("insert into product_model(product_model, unit_price, product_code) select distinct product_model, unit_price, product_code from contract_info;");
            stmt.execute("insert into contract(contract_number, director, client_enterprise, contract_date) select distinct contract_number, director, client_enterprise, contract_date from contract_info;");
            stmt.execute("insert into salesman(salesman_number, salesman, gender, age, mobile_phone) select distinct salesman_number, salesman, gender, age, mobile_phone from contract_info;");
            stmt.execute("insert into orders (contract_number, product_model, salesman_number, quantity, estimated_delivery_date, lodgement_date) select distinct contract_number, product_model, salesman_number, quantity, estimated_delivery_date, lodgement_date from contract_info;");
        }
    }
}

