import java.text.SimpleDateFormat;
import java.util.Properties;
import java.sql.*;
import java.net.URL;
import java.util.Scanner;

public class t4_user_privilege {
    private static URL propertyURL = t4_user_privilege.class
            .getResource("/loader.cnf");

    private static Connection con = null;
    private static boolean verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
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
//            con.setAutoCommit(false);

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
            if (city.equals("NULL")) {
                city = null;
            } else {
                city = "\'" + city + "\'";
            }
            String tempt_lodgement_date;
            if (lodgement_date != null) {
                tempt_lodgement_date = "\'" + lodgement_date + "\'";

            } else {
                tempt_lodgement_date = null;
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
                    + "')");
        }
    }

    public static void main(String[] args) {

        boolean verbose = false;


        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "test5");
        Properties prop = new Properties(defprop);

        try {

            // Empty target table
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            System.out.println("请输入用户名和密码");
            Scanner in = new Scanner(System.in);
            String user_name = in.next();
            String password = in.next();
            ResultSet rs0 = null;
            ResultSet rs1 = null;
            ResultSet rs2 = null;
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ;

            rs0 = stmt.executeQuery("select * from t4_user where user_name= '" + user_name + "' and password= '" + password + "'");

            //rs0.next();
//            System.out.println(rs0.getString(1));
            if (rs0.isBeforeFirst()) {
                rs0.next();


                rs1 = stmt.executeQuery("select role_name from t4_user join user_role ur on t4_user.user_name = ur.user_name where ur.user_name= '" + user_name + "'");
                rs1.next();
                System.out.println("登录成功 用户角色为："+rs1.getString(1));
                rs2 = stmt.executeQuery("select * from role_privilege join t4_privilege t4p on t4p.pri_id = role_privilege.pri_id where role_name= '" + rs1.getString(1) + "'");

                rs2.last();
                int row_num = rs2.getRow();
                System.out.println("There are " + row_num + " privilege(s):");
                rs2.beforeFirst();
                int[] valid_pri_id = new int[row_num];
                int cnt = 0;
                while (rs2.next()) {
                    valid_pri_id[cnt] = Integer.parseInt(rs2.getString(2));
                    cnt++;
                    System.out.println("input " + rs2.getString(2) + " for " + rs2.getString(4) + " " + rs2.getString(5) + " table");

                }
                while (true) {
                    int action = in.nextInt();
                    if (action == 0) {
                        System.out.println("退出登录");
                        break;
                    } else {
                        //check valid
                        boolean check_valid = false;
                        for (int i = 0; i < row_num; i++) {
                            if (valid_pri_id[i] == action) {
                                check_valid = true;
                            }
                        }
                        if (check_valid == false) {
                            System.out.println("This is invalid pri_id ,please input again");
                            continue;
                        }
                        String contract_number;
                        String product_model;
                        String salesman_number;
                        String client_enterprise;
                        String product_code;
                        String supply_center;
                        String country;
                        String city;
                        String industry;
                        String director;
                        String contract_date;
                        String salesman;
                        String gender;
                        int age;
                        String mobile_phone;
                        int quantity;
                        String estimated_delivery_date;
                        String lodgement_date;
                        switch (action) {
                            case 1:
                                System.out.println("please input contract_number");
                                contract_number = in.next();

                                select_contract(contract_number);
                                break;

                            case 2:
                                System.out.println("please input client_enterprise");
                                client_enterprise = in.next();

                                select_client(client_enterprise);
                                break;
                            case 3:
                                System.out.println("please input salesman_number");
                                salesman_number = in.next();

                                select_salesman(salesman_number);
                                break;
                            case 4:
                                System.out.println("please input contract_number, product_model, salesman_number");
                                contract_number = in.next();
                                product_model = in.next();
                                salesman_number = in.next();
                                select_orders(contract_number, product_model, salesman_number);
                                break;
                            case 5:
                                System.out.println("please input product_code");
                                product_code = in.next();

                                select_product(product_code);
                                break;
                            case 6:
                                System.out.println("please input product_model");
                                product_model = in.next();

                                select_product_model(product_model);
                                break;
                            case 7:
                                System.out.println("please input client_enterprise");
                                client_enterprise = in.next();
                                System.out.println("please input supply_center,country,city,industry");
                                supply_center = in.next();
                                country = in.next();
                                city = in.next();
                                industry = in.next();
                                update_client(client_enterprise, supply_center, country, city, industry);

                                break;
                            case 8:
                                System.out.println("please input contract_number");
                                contract_number = in.next();
                                System.out.println("please input director,client_enterprise,contract_date");
                                director = in.next();
                                client_enterprise = in.next();
                                contract_date = in.next();

                                update_contract(contract_number, director, client_enterprise, contract_date);

                                break;
                            case 9:
                                System.out.println("please input salesman_number");
                                salesman_number = in.next();
                                System.out.println("please input salesman,gender,age,mobile_phone");
                                salesman= in.next();
                                gender = in.next();
                                age = in.nextInt();
                                mobile_phone = in.next();
                                update_salesman(salesman_number,salesman,gender,age,mobile_phone);

                                break;
                            case 10:
                                System.out.println("please input contract_number,product_model,salesman_number");

                                contract_number=in.next();
                                product_model=in.next();
                                salesman_number=in.next();

                                System.out.println("please input quantity,estimated_delivery_date,lodgement_date");

                                quantity=in.nextInt();
                                estimated_delivery_date=in.next();
                                lodgement_date=in.next();
                                update_orders(contract_number,product_model,salesman_number,quantity, estimated_delivery_date, lodgement_date);

                                break;

                        }
                    }
                }


            } else {
                System.out.println("用户名不存在或密码错误");
            }


            closeDB();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //1
    public static void select_contract(String contract_number) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = null;
        rs = stmt.executeQuery("select * from contract where contract_number= '" + contract_number + "'");
        while (rs.next()) {
            for (int i = 1; i <= 5; i++) {
                System.out.print(rs.getString(i) + " ");
            }

        }
        System.out.println();

    }

    //2
    public static void select_client(String client_enterprise) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = null;
        rs = stmt.executeQuery("select * from client where client_enterprise= '" + client_enterprise + "'");
        while (rs.next()) {
            for (int i = 1; i <= 6; i++) {
                System.out.print(rs.getString(i) + " ");
            }

        }
        System.out.println();

    }

    //3
    public static void select_salesman(String salesman_number) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = null;
        rs = stmt.executeQuery("select * from salesman where  salesman_number= '" + salesman_number + "'");
        while (rs.next()) {
            for (int i = 1; i <= 6; i++) {
                System.out.print(rs.getString(i) + " ");
            }

        }
        System.out.println();

    }

    //4
    public static void select_orders(String contract_number, String product_model, String salesman_number) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = null;
        rs = stmt.executeQuery("select * from orders where contract_number= '" + contract_number + "' and product_model= '" + product_model + "' and salesman_number= '" + salesman_number + "'");
        while (rs.next()) {
            for (int i = 1; i <= 6; i++) {
                System.out.print(rs.getString(i) + " ");
            }

        }
        System.out.println();

    }

    //5
    public static void select_product(String produrct_code) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = null;
        rs = stmt.executeQuery("select * from product where product_code= '" + produrct_code + "'");
        while (rs.next()) {
            for (int i = 1; i <= 3; i++) {
                System.out.print(rs.getString(i) + " ");
            }

        }
        System.out.println();

    }

    //6
    public static void select_product_model(String product_model) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = null;
        rs = stmt.executeQuery("select * from product_model where  product_model= '" + product_model + "'");
        while (rs.next()) {
            for (int i = 1; i <= 4; i++) {
                System.out.print(rs.getString(i) + " ");
            }

        }
        System.out.println();

    }

    //7
    public static void update_client(String client_enterprise, String supply_center, String country, String city, String industry) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


        stmt.execute("update client set supply_center='" + supply_center + "',country='" + country + "',city='" + city + "',industry='" + industry + "' where client_enterprise='" + client_enterprise + "'");

        System.out.println("update success");

    }

    //8
    public static void update_contract(String contract_number, String director, String client_enterprise, String contract_date) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


        stmt.execute("update contract set director='" + director + "',client_enterprise='" + client_enterprise + "',contract_date='" + contract_date + "' where contract_number='" + contract_number + "'");

        System.out.println("update success");

    }

    //9
    public static void update_salesman(String salesman_number, String salesman, String gender, int age, String mobile_phone) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        stmt.execute("update salesman set salesman='" + salesman + "',gender='" + gender + "',age='" + age + "',mobile_phone='" + mobile_phone + "' where salesman_number='" + salesman_number + "'");
        System.out.println("update success");
    }

    //10
    public static void update_orders(String contract_number,String product_model,String salesman_number,int quantity,String estimated_delivery_date,String lodgement_date) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.execute("update salesman set quantity='" + quantity + "',estimated_delivery_date='" + estimated_delivery_date + "',lodgement_date='" + lodgement_date +  "' where contract_number='" + contract_number +"' and product_model='" + product_model + "' and salesman_number='" + salesman_number +"'");


        System.out.println("update success");
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

