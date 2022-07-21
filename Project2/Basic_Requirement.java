

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.net.URL;

public class Basic_Requirement {
    private static URL propertyURL = Basic_Requirement.class
            .getResource("/loader.cnf");
    private static PreparedStatement stmt = null;
    private static Connection con = null;
    private static boolean verbose = false;
    private static String selectStatement = "select ";
    private static String updateStatement;//后面加
    private static String deleteStatement = "delete from ";


    static HashMap<String, Integer> centerLength = new HashMap<>();

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname + "?useUnicode=true&characterEncoding=GBK";
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
            con.prepareStatement("set client_encoding=GBK;");
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


    public static void main(String[] args) throws SQLException {
        String center_file = null;
        String model_file = null;
        String staff_file = null;
        String enterprise_file = null;
        String task1_file;
        String task2_file;
        String task3_file;
        String task4_file;
        boolean verbose = true;
        verbose = true;
        center_file = args[1];
        model_file = args[2];
        staff_file = args[3];
        enterprise_file = args[4];
        task1_file = args[5];
        task2_file = args[6];
        task3_file = args[7];
        task4_file = args[8];
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "proj2");
        Properties prop = new Properties(defprop);
        String line;
        ArrayList<String[]> center_parts = new ArrayList<>();
        ArrayList<String[]> model_parts = new ArrayList<>();
        ArrayList<String[]> staff_parts = new ArrayList<>();
        ArrayList<String[]> enterprise_parts = new ArrayList<>();
        ArrayList<String[]> task1_parts = new ArrayList<>();
        ArrayList<String[]> task2_parts = new ArrayList<>();
        ArrayList<String[]> task3_parts = new ArrayList<>();
        ArrayList<String[]> task4_parts = new ArrayList<>();

//read file/////////////////////////////////////////////////////////////////////////
        try (BufferedReader infile = new BufferedReader(new FileReader(center_file))) {
            while ((line = infile.readLine()) != null) {
                center_parts.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader infile = new BufferedReader(new FileReader(model_file));) {
            while ((line = infile.readLine()) != null) {
                model_parts.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader infile = new BufferedReader(new FileReader(staff_file));) {
            while ((line = infile.readLine()) != null) {
                staff_parts.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader infile = new BufferedReader(new FileReader(enterprise_file));) {
            while ((line = infile.readLine()) != null) {
                enterprise_parts.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader infile = new BufferedReader(new FileReader(task1_file))) {
            while ((line = infile.readLine()) != null) {
                task1_parts.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader infile = new BufferedReader(new FileReader(task2_file))) {
            while ((line = infile.readLine()) != null) {
                task2_parts.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader infile = new BufferedReader(new FileReader(task3_file))) {
            while ((line = infile.readLine()) != null) {
                task3_parts.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader infile = new BufferedReader(new FileReader(task4_file))) {
            while ((line = infile.readLine()) != null) {
                task4_parts.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//open db///////////////////////////////////////////////////////////////////
        long start;
        long end;
        start = System.currentTimeMillis();
        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));
        int cnt = 0;
//center///////////////////////////////////////////////////////////////////////////


//        {
//            int id;
//            String name;
//
//            for (int i = 0; i < center_parts.size(); i++) {
//                try {
//                    if (i == 0) {
//
//                        continue;
//                    }
//                    cnt++;
//                    if (center_parts.get(i).length > 1) {
//                        if (i != 8) {
//                            id = Integer.parseInt(center_parts.get(i)[0]);
//
//                            name = center_parts.get(i)[1];
//                        } else {
//                            id = Integer.parseInt(center_parts.get(i)[0]);
//                            name = center_parts.get(i)[1].replace("\"", "") + "," + center_parts.get(i)[2].replace("\"", "");
//                        }
//
//
//                        load_center_data(id, name);
//
//                    }
//                } catch (SQLException se) {
//                    System.err.println("SQL error: " + se.getMessage());
//                    try {
//                        con.rollback();
//                    } catch (Exception e2) {
//                    }
//                }
//            }
//            con.commit();
//        }
////model////////////////////////////////////////////////////////////////
//        {
//            int id;
//            String number;
//            String model;
//            String name;
//            int unit_price;
//            for (int i = 0; i < model_parts.size(); i++) {
//                try {
//                    if (i == 0) {
//                        continue;
//                    }
//                    cnt++;
//                    if (model_parts.get(i).length > 1) {
//                        id = Integer.parseInt(model_parts.get(i)[0]);
//                        number = model_parts.get(i)[1];
//                        model = model_parts.get(i)[2].replace("'", "''");
//                        name = model_parts.get(i)[3].replace("'", "''");
//                        unit_price = Integer.parseInt(model_parts.get(i)[4]);
//
//                        load_model_data(id, number, model, name, unit_price);
//                    }
//                } catch (SQLException se) {
//                    System.err.println("SQL error: " + se.getMessage());
//                    try {
//                        con.rollback();
//                    } catch (Exception e2) {
//                    }
//                }
//            }
//            con.commit();
//        }
////staff////////////////////////////////////////////////////////
//        {
//            int id;
//            String name;
//            int age;
//            String gender;
//            String number;
//            String supply_center;
//            String mobile_number;
//            String type;
//            for (int i = 0; i < staff_parts.size(); i++) {
//                try {
//                    if (i == 0) {
//                        continue;
//                    }
//                    cnt++;
//                    if (staff_parts.get(i).length > 1) {
//                        if (staff_parts.get(i).length == 8) {
//                            id = Integer.parseInt(staff_parts.get(i)[0]);
//                            name = staff_parts.get(i)[1];
//                            age = Integer.parseInt(staff_parts.get(i)[2]);
//                            gender = staff_parts.get(i)[3];
//                            number = staff_parts.get(i)[4];
//                            supply_center = staff_parts.get(i)[5];
//                            mobile_number = staff_parts.get(i)[6];
//                            type = staff_parts.get(i)[7];
//                        } else {
//                            id = Integer.parseInt(staff_parts.get(i)[0]);
//                            name = staff_parts.get(i)[1];
//                            age = Integer.parseInt(staff_parts.get(i)[2]);
//                            gender = staff_parts.get(i)[3];
//                            number = staff_parts.get(i)[4];
//                            supply_center = staff_parts.get(i)[5].replace("\"", "") + "," + staff_parts.get(i)[6].replace("\"", "");
//
//                            mobile_number = staff_parts.get(i)[7];
//                            type = staff_parts.get(i)[8];
//                        }
//                        load_staff_data(id, name, age, gender, number, supply_center, mobile_number, type);
//
//                    }
//                } catch (SQLException se) {
//                    System.err.println("SQL error: " + se.getMessage());
//                    try {
//                        con.rollback();
//                    } catch (Exception e2) {
//                    }
//                }
//            }
//            con.commit();
//        }
////enterprise////////////////////////////////////////////////////////////////
//        {
//            int id;
//            String name;
//            String country;
//            String city;
//            String supply_center;
//            String industry;
//            for (int i = 0; i < enterprise_parts.size(); i++) {
//                try {
//                    if (i == 0) {
//                        continue;
//                    }
//                    cnt++;
//                    if (enterprise_parts.get(i).length > 1) {
//                        if (enterprise_parts.get(i).length == 6) {
//                            id = Integer.parseInt(enterprise_parts.get(i)[0]);
//                            name = enterprise_parts.get(i)[1].replace("'", "''");
//                            country = enterprise_parts.get(i)[2];
//                            if (enterprise_parts.get(i)[3].length() == 0) {
//                                city = null;
//                            } else {
//                                city = enterprise_parts.get(i)[3];
//
//                            }
//                            supply_center = enterprise_parts.get(i)[4];
//                            industry = enterprise_parts.get(i)[5];
//                        } else {
//                            id = Integer.parseInt(enterprise_parts.get(i)[0]);
//                            name = enterprise_parts.get(i)[1].replace("'", "''");
//                            country = enterprise_parts.get(i)[2];
//                            if (enterprise_parts.get(i)[3].length() == 0) {
//                                city = null;
//                            } else {
//                                city = enterprise_parts.get(i)[3];
//
//                            }
//                            supply_center = enterprise_parts.get(i)[4].replace("\"", "") + "," + enterprise_parts.get(i)[5].replace("\"", "");
//                            industry = enterprise_parts.get(i)[6];
//                        }
//
//                        load_enterprise_data(id, name, country, city, supply_center, industry);
//                    }
//                } catch (SQLException se) {
//                    System.err.println("SQL error: " + se.getMessage());
//                    try {
//                        con.rollback();
//                    } catch (Exception e2) {
//                    }
//                }
//            }
//            con.commit();
//        }
////stockIn////////////////////////////////////////////////////////////////
//        {
//            int id;
//            String center;
//            String product_model;
//            String supply_staff;
//            Date date;
//            int purchase_price;
//            int quantity;
//            for (int i = 0; i < task1_parts.size(); i++) {
//                try {
//                    if (i == 0) {
//                        continue;
//                    }
//                    cnt++;
//                    if (task1_parts.get(i).length > 1) {
//                        if (task1_parts.get(i).length == 7) {
//                            id = Integer.parseInt(task1_parts.get(i)[0]);
//                            center = task1_parts.get(i)[1];
//                            product_model = task1_parts.get(i)[2];
//                            supply_staff = task1_parts.get(i)[3];
//                            date = strToDate(task1_parts.get(i)[4]);
//                            purchase_price = Integer.parseInt(task1_parts.get(i)[5]);
//                            quantity = Integer.parseInt(task1_parts.get(i)[6]);
//                        } else {
//                            id = Integer.parseInt(task1_parts.get(i)[0]);
//                            center = task1_parts.get(i)[1].replace("\"", "") + "," + task1_parts.get(i)[2].replace("\"", "");
//                            product_model = task1_parts.get(i)[3];
//                            supply_staff = task1_parts.get(i)[4];
//                            date = strToDate(task1_parts.get(i)[5]);
//                            purchase_price = Integer.parseInt(task1_parts.get(i)[6]);
//                            quantity = Integer.parseInt(task1_parts.get(i)[7]);
//                        }
//
//
//                        stockIn(id, center, product_model, supply_staff, date, purchase_price, quantity);
//                    }
//                } catch (SQLException se) {
//                    System.err.println("SQL error: " + se.getMessage());
//                    try {
//                        con.rollback();
//                    } catch (Exception e2) {
//                    }
//                }
//            }
//            con.commit();
//        }
////placeOrder////////////////////////////////////////////////////
//        //todo
//
//        {
//            String contract_num;
//            String enterprise;
//            String product_model;
//            int quantity;
//            String contract_manager;
//            java.sql.Date contract_date;
//            java.sql.Date estimated_delivery_date;
//            java.sql.Date lodgement_date;
//            String salesman_num;
//            String contract_type;
//
//
//            for (int i = 0; i < task2_parts.size(); i++) {
//                try {
//                    if (i == 0) {
//                        continue;
//                    }
//                    cnt++;
//                    if (task2_parts.get(i).length > 1) {
//
//                        contract_num = task2_parts.get(i)[0];
//                        enterprise = task2_parts.get(i)[1];
//                        product_model = task2_parts.get(i)[2];
//                        quantity = Integer.parseInt(task2_parts.get(i)[3]);
//                        contract_manager = task2_parts.get(i)[4];
//                        contract_date = strToDate(task2_parts.get(i)[5]);
//                        estimated_delivery_date = strToDate(task2_parts.get(i)[6]);
//                        lodgement_date = strToDate(task2_parts.get(i)[7]);
//                        salesman_num = task2_parts.get(i)[8];
//                        contract_type = task2_parts.get(i)[9];
//
//
//                        load_contract_data(contract_num, enterprise, contract_manager, contract_date, contract_type);
//                        placeOrder(contract_num, enterprise, product_model, quantity, contract_manager, contract_date, estimated_delivery_date, lodgement_date, salesman_num, contract_type);
//
//                    }
//                } catch (SQLException se) {
//                    System.err.println("SQL error: " + se.getMessage());
//                    try {
//                        con.rollback();
//                    } catch (Exception e2) {
//                    }
//                }
//            }
//            con.commit();
//        }
////45////////////////////////////////////////////////////////////////////////////////
//
//
//        {
//            centerLength.put("center", 2);
//            centerLength.put("enterprise", 6);
//            centerLength.put("model", 5);
//            centerLength.put("staff", 8);
//            centerLength.put("contract", 6);
//            centerLength.put("orders", 6);
//            centerLength.put("inventory", 6);
//
//            String fileName = null;
//
/////////////////////////////////////////////////////////////////////////////
//
////todo
//
//            UpdateOrder(task3_parts);
//            deleteOrder(task4_parts);
//            con.commit();
//            stmt.close();
//
//
//        }
//

//get//////////////////////////////////////////////////
        {

//            Scanner in=new Scanner(System.in);
//            String q12_input=in.nextLine();
//            String q13_input_1=in.nextLine();
//            String q13_input_2=in.nextLine();
            String q12_input="WJ50817";
            String q13_input_1="CSE0000427";
//            String q13_input_2="CSE0000103";


//            getAllStaffCount();
//            getContractCount();
//            getOrderCount2();
//            getNeverSoldProductCount();
//            getFavoriteProductModel();
//            getAvgStockByCenter();
            getProductByNumber(q12_input);
            getContractInfo(q13_input_1);
//            getContractInfo(q13_input_2);

        }

        closeDB();

    }


    private static void load_center_data(int id, String name)
            throws SQLException {
        Statement stmt;
        if (con != null) {
            stmt = con.createStatement();


            stmt.execute("insert into center values('"
                    + id + "','"
                    + name
                    + "')");
        }
    }

    private static void load_enterprise_data(int id, String name, String country, String city, String supply_center, String industry)
            throws SQLException {
        Statement stmt;
        if (con != null) {
            stmt = con.createStatement();

            stmt.execute("insert into enterprise values('"
                    + id + "','"
                    + name + "','"
                    + country + "','"
                    + city + "','"
                    + supply_center + "','"
                    + industry
                    + "')");
        }
    }

    private static void load_staff_data(int id, String name, int age, String gender, String number, String supply_center, String mobile_number, String type)
            throws SQLException {
        Statement stmt;
        if (con != null) {
            stmt = con.createStatement();


            stmt.execute("insert into staff values('"
                    + id + "','"
                    + name + "','"
                    + age + "','"
                    + gender + "','"
                    + number + "','"
                    + supply_center + "','"
                    + mobile_number + "','"
                    + type
                    + "')");
        }
    }

    private static void load_model_data(int id, String number, String model, String name, int unit_price)
            throws SQLException {
        Statement stmt;
        if (con != null) {
            stmt = con.createStatement();


            stmt.execute("insert into model values('"
                    + id + "','"
                    + number + "','"
                    + model + "','"
                    + name + "','"
                    + unit_price
                    + "')");
        }
    }

    //stockIn////////////////////////////////////////////////////////////////
    public static boolean select_center_name(String supply_center) throws SQLException {
        //center供应中心是否存在
        PreparedStatement stmt = con.prepareStatement("select name from center where name='" + supply_center + "'");
        ResultSet rs = stmt.executeQuery();

        if (!rs.isBeforeFirst()) {
            return false;
        } else {

            return true;
        }

    }

    public static boolean select_model_name(String product_model) throws SQLException {
        //model产品是否存在
        PreparedStatement stmt = con.prepareStatement("select model from model where model='" + product_model + "'");
        ResultSet rs = stmt.executeQuery();

        if (!rs.isBeforeFirst()) {
            return false;
        } else {

            return true;
        }

    }

    public static boolean select_staff_all(String supply_center, String supply_staff) throws SQLException {
        //staff人员是否存在 供应中心是否一致 类型是否为supply_staff
        PreparedStatement stmt = con.prepareStatement("select supply_center,number,type from staff where supply_center='" + supply_center + "'" + "and number='" + supply_staff + "'");

        ResultSet rs = stmt.executeQuery();


        if (rs.isBeforeFirst()) {
            rs.next();

            if (rs.getString(3).equals("Supply Staff")) {

                return true;
            }

        }
        return false;
    }

    public static boolean select_inventory_exist(String supply_center, String product_model) throws SQLException {
        //在插入之前检查是否已经存在库存
        PreparedStatement stmt = con.prepareStatement("select center,product_model from inventory where center='" + supply_center + "'" + "and product_model='" + product_model + "'");

        ResultSet rs = stmt.executeQuery();
        if (!rs.isBeforeFirst()) {
            return false;
        } else {

            return true;
        }
    }

    public static void stockIn(int id, String supply_center, String product_model, String supply_staff, Date date, int purchase_price, int quantity) throws SQLException {
        if (select_center_name(supply_center) && select_model_name(product_model) && select_staff_all(supply_center, supply_staff)) {
            if (select_inventory_exist(supply_center, product_model)) {
                PreparedStatement stmt = con.prepareStatement("update inventory set quantity=quantity+" + quantity + ",total_purchase_money=total_purchase_money+" + purchase_price * quantity + "where center='" + supply_center + "' and product_model='" + product_model + "'");

                stmt.executeUpdate();

            } else {
                PreparedStatement stmt = con.prepareStatement("insert into inventory values (?,?,?,?,?,?)");
                stmt.setString(1, supply_center);
                stmt.setString(2, product_model);
                stmt.setInt(3, quantity);
                stmt.setInt(4, purchase_price * quantity);
                stmt.setInt(5, 0);
                stmt.setInt(6, 0);
                stmt.executeUpdate();

            }

        } else {
//            System.out.println("insert inventory fail");
        }


    }

    //placeOrder/////////////////////////////////////////////////////////////////
    public static String enterprise_center(String enterprise) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("select supply_center from enterprise where name='" + enterprise + "'");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public static boolean contract_exist(String contract_num) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("select contract_num from contract where contract_num='" + contract_num + "'");
        ResultSet rs = stmt.executeQuery();
        if (rs.isBeforeFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public static void load_contract_data(String contract_num, String enterprise, String contract_manager, java.sql.Date contract_date, String contract_type) throws SQLException {

        if (!contract_exist(contract_num)) {
            String supply_center = enterprise_center(enterprise);
            PreparedStatement stmt = con.prepareStatement("insert into contract values (?,?,?,?,?,?)");
            stmt.setString(1, contract_num);
            stmt.setString(2, enterprise);
            stmt.setString(3, contract_manager);
            stmt.setDate(4, contract_date);
            stmt.setString(5, contract_type);
            stmt.setString(6, supply_center);
            stmt.executeUpdate();
        }
    }

    static int cnnt = 0;

    public static boolean quantity_enougth(String contract_num, String product_model, int quantity) throws SQLException {

        PreparedStatement stmt = con.prepareStatement("select supply_center from contract where contract_num='" + contract_num + "'");
        ResultSet rs = stmt.executeQuery();
        if (!rs.isBeforeFirst()) {
            return false;
        }
        rs.next();
        String supply_center = rs.getString(1);

        PreparedStatement stmt2 = con.prepareStatement("select quantity from inventory where center='" + supply_center + "' and product_model='" + product_model + "'");
        ResultSet rs2 = stmt2.executeQuery();
        if (!rs2.isBeforeFirst()) {
            return false;
        }
        rs2.next();
        int q = Integer.parseInt(rs2.getString(1));

        PreparedStatement stmt3 = con.prepareStatement("select unit_price from model where model='" + product_model + "'");
        ResultSet rs3 = stmt3.executeQuery();
        if (!rs3.isBeforeFirst()) {
            return false;
        }
        rs3.next();
        int price = Integer.parseInt(rs3.getString(1));
        if (quantity > q) {
            return false;
        } else {
            cnnt++;
//            System.out.println(cnnt + "==============================================================");
            PreparedStatement stmt4 = con.prepareStatement("update inventory set quantity=quantity-" + quantity + ",profits=profits+" + price * quantity + ",sales_quantity=sales_quantity+" + quantity + " where center='" + supply_center + "' and product_model='" + product_model + "'");
            stmt4.executeUpdate();
            PreparedStatement stmt5 = con.prepareStatement("select center,product_model,quantity,sales_quantity from inventory " + " where center='" + supply_center + "' and product_model='" + product_model + "'");
            ResultSet rs5 = stmt5.executeQuery();
            while (rs5.next()){
                for (int i = 1; i <= 4; i++) {
//                    System.out.println(rs5.getString(i));
                }

            }
            return true;
        }
    }

    public static boolean staff_is_salesman(String salesman_num) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("select type from staff where number='" + salesman_num + "'");

        ResultSet rs = stmt.executeQuery();


        if (rs.isBeforeFirst()) {
            rs.next();
            if (rs.getString(1).equals("Salesman")) {

                return true;
            }

        }
        return false;
    }

    public static void load_orders_data(String contract_num, String product_model, int quantity, String salesman_num, java.sql.Date estimated_delivery_date, java.sql.Date lodgement_date) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("insert into orders values (?,?,?,?,?,?)");
        stmt.setString(1, contract_num);
        stmt.setString(2, product_model);
        stmt.setInt(3, quantity);
        stmt.setString(4, salesman_num);
        stmt.setDate(5, estimated_delivery_date);
        stmt.setDate(6, lodgement_date);
        stmt.executeUpdate();
    }

    public static void placeOrder(String contract_num, String enterprise, String product_model, int quantity, String contract_manager, java.sql.Date contract_date, java.sql.Date estimated_delivery_date, java.sql.Date lodgement_date, String salesman_num, String contract_type) throws SQLException {
        if (staff_is_salesman(salesman_num) && quantity_enougth(contract_num, product_model, quantity)) {
            load_orders_data(contract_num, product_model, quantity, salesman_num, estimated_delivery_date, lodgement_date);

        } else {
//            System.out.println("insert orders fail");
        }
    }

    //getFavoriteProductModel//////////////////////////////////////
    public static void getFavoriteProductModel() throws SQLException {
        System.out.println("Q10");
        PreparedStatement stmt = con.prepareStatement("select product_model,sum(quantity)s from orders group by product_model order by s desc limit 1;");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.print(rs.getString(1) + " ");
            System.out.println(rs.getString(2));

        }
    }

    //getAvgStockByCenter//////////////////////////////////////
    public static void getAvgStockByCenter() throws SQLException {
        System.out.println("Q11");
        PreparedStatement stmt = con.prepareStatement("select sub1.center,round(1.00*sub1.sum/sub2.cnt,1) from(select center, sum(quantity) sum from inventory group by center)sub1 join(select center,count(product_model) cnt from inventory group by center)sub2 on sub1.center=sub2.center order by sub1.center;");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.printf("%-50s\t", rs.getString(1));
            System.out.printf("%-20s\t\n", rs.getString(2));


        }
    }

    //getProductByNumber//////////////////////////////////////
    public static void getProductByNumber(String number) throws SQLException {
        System.out.println("Q12");
        PreparedStatement stmt = con.prepareStatement("select center,number,product_model,total_purchase_money/(quantity+sales_quantity),quantity from model join inventory i on model.model = i.product_model where number=?");
        stmt.setString(1, number);
        ResultSet rs = stmt.executeQuery();
        System.out.printf("%-50s\t", "supply_center");
        System.out.printf("%-20s\t", "product_number");
        System.out.printf("%-50s\t", "product_mode");
        System.out.printf("%-20s\t", "purchase_prise");
        System.out.printf("%-20s\t\n", "quantity");


        while (rs.next()) {
            System.out.printf("%-50s\t", rs.getString(1));
            System.out.printf("%-20s\t", rs.getString(2));
            System.out.printf("%-50s\t", rs.getString(3));
            System.out.printf("%-20s\t", rs.getInt(4));
            System.out.printf("%-20s\t\n", rs.getInt(5));

        }
    }

    //getContractInfo//////////////////////////////////////
    public static void getContractInfo(String contract_num) throws SQLException {
        System.out.println("Q13");
        //contract
        PreparedStatement stmt = con.prepareStatement("select contract_num,name,enterprise,sub1.supply_center from (select * from contract where contract_num=?)sub1 join staff on number=sub1.contract_manager;");
        stmt.setString(1, contract_num);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        System.out.printf("%-50s\t", "contract_number:");
        System.out.printf("%-20s\t\n", rs.getString(1));
        System.out.printf("%-20s\t", "contract_manager_name:");
        System.out.printf("%-20s\t\n", rs.getString(2));
        System.out.printf("%-20s\t", "enterprise_name:");
        System.out.printf("%-20s\t\n", rs.getString(3));
        System.out.printf("%-20s\t", "supply_center:");
        System.out.printf("%-20s\t\n", rs.getString(4));


        //orders
        PreparedStatement stmt2 = con.prepareStatement("select model.model,staff.name,quantity,unit_price,estimated_delivery,lodgement_date from (select * from orders where contract_num=? )sub1 join model on product_model=model.model join staff on salesman_num=staff.number;");
        stmt2.setString(1, contract_num);
        ResultSet rs2 = stmt2.executeQuery();
        if (rs2.isBeforeFirst()) {
            System.out.printf("%-50s\t", "Product_model");
            System.out.printf("%-20s\t", "salesman_name");
            System.out.printf("%-20s\t", "quantity");
            System.out.printf("%-20s\t", "unit_price");
            System.out.printf("%-30s\t", "estimate_delivery_date");
            System.out.printf("%-20s\t\n", "lodgement_date");
        }

        while (rs2.next()) {
            System.out.printf("%-50s\t", rs2.getString(1));
            System.out.printf("%-20s\t", rs2.getString(2));
            System.out.printf("%-20s\t", rs2.getInt(3));
            System.out.printf("%-20s\t", rs2.getInt(4));
            System.out.printf("%-30s\t", rs2.getDate(5));
            System.out.printf("%-20s\t\n", rs2.getDate(6));

        }

    }

    /////////////////////////////////////////////////////////////////////////////////
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

    public static java.sql.Date strToDate2(String strDate) {
        if (strDate.equals("")) {
            return null;
        } else {
            String str = strDate;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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

    public static int getOrderCount() throws SQLException {
//        System.out.println("Q8");
        int fin = 0;
        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement("select count(*) from orders;");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {

            ResultSet rs1 = null;
            rs1 = stmt.executeQuery();
            while (rs1.next()) {
                fin = rs1.getInt(1);
//                System.out.println(fin);
            }
        }
        return fin;
    }

    public static int getOrderCount2() throws SQLException {
        System.out.println("Q8");
        int fin = 0;
        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement("select count(*) from orders;");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {

            ResultSet rs1 = null;
            rs1 = stmt.executeQuery();
            while (rs1.next()) {
                fin = rs1.getInt(1);
                System.out.println(fin);
            }
        }
        return fin;
    }
    public static void deleteOrder(ArrayList<String[]>parts) throws SQLException {
        ArrayList<String[]>p=new ArrayList<>();

        int length=getOrderCount() ;
        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement("select * ,rank()over (partition by contract_num,salesman_num order by estimated_delivery,product_model )  as seq from orders");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {
            ResultSet rs = null;
            rs = stmt.executeQuery();
            for (int j = 0; j <length ; j++) {
                if (rs.next()) {
                    String[]fin=new String[7];
                    for (int i = 1; i <= 7; i++) {


                        String s = rs.getString(i);
                        fin[i - 1] = s;
//                        System.out.print(s + " ");
                    }
//                    System.out.println();
                    p.add(fin);

                }
            }
        }





        ArrayList<String[]>p1=new ArrayList<>();
        for (int i = 0; i <parts.size() ; i++) {
            for (int j = 0; j < p.size(); j++) {
                if (parts.get(i)[0].equals(p.get(j)[0])){
                    if (parts.get(i)[1].equals(p.get(j)[3])){
                        if (parts.get(i)[2].equals(p.get(j)[6])){
                            p1.add(p.get(j));
                            break;
                        }
                    }
                }
            }
        }





//        String selectStatement ="select ";
//        String deleteStatement="delete from ";
//        String updateStatement;//�����
////        String table="order";
        ArrayList<String>Columns =new ArrayList<>();
        ArrayList<String> Data =new ArrayList<>();

        ArrayList<String[]>p11=new ArrayList<>();//��order��ѡ������ͬsalesman contract model�Ķ�Ӧ���У�����ǰ��
        for (int i = 0; i < p1.size(); i++) {
            String[]strings=new String[3];
            strings[0]=p1.get(i)[0];
            strings[1]=p1.get(i)[1];
            strings[2]=p1.get(i)[3];
            p11.add(strings);
        }
        Data.add("contract_num");
        Data.add("product_model");
        Data.add("salesman_num");

        ArrayList<String[]> beforeUpdate=SelectColums("orders",p11,Columns,Data,selectStatement);
        Columns.clear();
        Data.clear();




        selectStatement ="select ";
        ArrayList<String[]>p2=new ArrayList<>();
        for (int i = 0; i < beforeUpdate.size(); i++) {
            String[]strings1=new String[1];
            strings1[0]=beforeUpdate.get(i)[0];
            p2.add(strings1);
        }
        Data.add("contract_num");
        ArrayList<String[]>centers=SelectColums("contract",p2,Columns,Data,selectStatement);////////�Ҷ�Ӧ��center
        Columns.clear();
        Data.clear();




//        selectStatement ="select ";
//        ArrayList<String[]>p3=new ArrayList<>();
//        for (int i = 0; i <beforeUpdate.size() ; i++) {
//            String[]strings2=new String[2];
//            strings2[0]=centers.get(i)[5];
//            strings2[1]=beforeUpdate.get(i)[1];
//            p3.add(strings2);
//        }
//        Data.add("center");
//        Data.add("product_model");
//        ArrayList<String[]>inventory=SelectColums("inventory",p3,Columns,Data,selectStatement);///////////�ӹ���������Ӧ��quantity
//        Columns.clear();
//        Data.clear();


        selectStatement ="select ";
        ArrayList<String[]>p4=new ArrayList<>();
        for (int i = 0; i <beforeUpdate.size(); i++) {
            String[]strings1=new String[1];
            strings1[0]=beforeUpdate.get(i)[1];
            p4.add(strings1);
        }
//        Columns.add("model");
//        Columns.add("unit_price");
        Data.add("model");
        ArrayList<String[]>unit_price=SelectColums("model",p4,Columns,Data,selectStatement);////////�Ҷ�Ӧ��unit_price
        Columns.clear();
        Data.clear();




        updateStatement="update inventory set ";

        for (int i = 0; i <beforeUpdate.size() ; i++) {
            selectStatement ="select ";
            ArrayList<String[]>p3=new ArrayList<>();

                String[]strings2=new String[2];
                strings2[0]=centers.get(i)[5];
                strings2[1]=beforeUpdate.get(i)[1];
                p3.add(strings2);

            Data.add("center");
            Data.add("product_model");
            ArrayList<String[]>inventory=SelectColums("inventory",p3,Columns,Data,selectStatement);///////////�ӹ���������Ӧ��quantity
            Columns.clear();
            Data.clear();
            ArrayList<String[]>fin=new ArrayList<>();
            String[]strings3=new String[5];
            strings3[0]=String.valueOf(Integer.parseInt(inventory.get(0)[2])+Integer.parseInt(beforeUpdate.get(i)[2]));
            strings3[1]=String.valueOf(Integer.parseInt(inventory.get(0)[4])-Integer.parseInt(beforeUpdate.get(i)[2]));
            strings3[2]=String.valueOf(Integer.parseInt(inventory.get(0)[5])-Integer.parseInt(beforeUpdate.get(i)[2])*Integer.parseInt(unit_price.get(i)[4]));//////////profits-֮ǰ������*����+���ڵ�����*����;
            strings3[3]=inventory.get(0)[0];
            strings3[4]=inventory.get(0)[1];
            fin.add(strings3);
            Data.add("quantity");
            Data.add("sales_quantity");
            Data.add("profits");
            Columns.add("center");
            Columns.add("product_model");
            UpdateColumns("inventory",fin,Columns,Data,updateStatement);/////////////////����ǰ���ҵĵ��ۺ�center�͸���ǰ���quantity ���²ֿ�
            Columns.clear();
            Data.clear();

        }



        stmt= con.prepareStatement("delete from orders where contract_num=? and product_model=? and salesman_num=?");

        for (int i = 0; i <p1.size() ; i++) {

            stmt.setString(1,p1.get(i)[0]);
            stmt.setString(2,p1.get(i)[1]);
            stmt.setString(3,p1.get(i)[3]);
            stmt.executeUpdate();

        }

    }

    static ArrayList<Integer> nulls=new ArrayList<>();
    public static void UpdateOrder(ArrayList<String[]>parts) throws SQLException {
        parts.remove(0);
        String selectStatement ="select ";
        String deleteStatement="delete from ";
        String updateStatement;//�����
//        String table="order";
        ArrayList<String>Columns =new ArrayList<>();
        ArrayList<String> Data =new ArrayList<>();

        ArrayList<String[]>p1=new ArrayList<>();//��order��ѡ������ͬsalesman contract model�Ķ�Ӧ���У�����ǰ��
        for (int i = 0; i < parts.size(); i++) {
            String[]strings=new String[3];
            strings[0]=parts.get(i)[0];
            strings[1]=parts.get(i)[1];
            strings[2]=parts.get(i)[2];
            p1.add(strings);
        }
        Data.add("contract_num");
        Data.add("product_model");
        Data.add("salesman_num");

        ArrayList<String[]> beforeUpdate=SelectColums("orders",p1,Columns,Data,selectStatement);
        Columns.clear();
        Data.clear();

        for (int i = 0; i < nulls.size(); i++) {
            parts.remove(i);
        }

        updateStatement="update orders set ";
        Columns.add("contract_num");
        Columns.add("product_model");
        Columns.add("salesman_num");
        Data.add("quantity");
        Data.add("estimated_delivery");
        Data.add("lodgement_date");
        ArrayList<String[]> p=new ArrayList<>();
        for (String[] strings:
                parts) {
            String[] s=new String[strings.length];
            for (int j = 0; j < Data.size(); j++) {
                if (strings[j+Columns.size()].equals("")){
                    s[j]="null";
                }else {
                    s[j] = strings[j + Columns.size()];
                }
            }
            for (int j = Data.size(); j <strings.length ; j++) {
                if (strings[j-Data.size()].equals("")){
                    s[j]="null";
                }else {
                    s[j] = strings[j - Data.size()];
                }
            }
            p.add(s);
        }
        UpdateColumns("orders",p,Columns,Data,updateStatement);/////////////////////����(ȷ���˸��µ����Լ��� ���´��ڵ�
        Columns.clear();
        Data.clear();


        selectStatement ="select ";
        Data.add("contract_num");
        Data.add("product_model");
        Data.add("salesman_num");
        ArrayList<String[]> afterUpdate=SelectColums("orders",p1,Columns,Data,selectStatement);////////////���º��
        Columns.clear();
        Data.clear();

        try {
            stmt = con.prepareStatement("delete from orders where quantity=0");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {
            stmt.executeUpdate();

        }



        selectStatement ="select ";
        ArrayList<String[]>p2=new ArrayList<>();
        for (int i = 0; i < afterUpdate.size(); i++) {
            String[]strings1=new String[1];
            strings1[0]=afterUpdate.get(i)[0];
            p2.add(strings1);
        }
        Data.add("contract_num");
        ArrayList<String[]>centers=SelectColums("contract",p2,Columns,Data,selectStatement);////////�Ҷ�Ӧ��center
        Columns.clear();
        Data.clear();




//        selectStatement ="select ";
//        ArrayList<String[]>p3=new ArrayList<>();
//        for (int i = 0; i <afterUpdate.size() ; i++) {
//            String[]strings2=new String[2];
//            strings2[0]=centers.get(i)[5];
//            strings2[1]=afterUpdate.get(i)[1];
//            p3.add(strings2);
//        }
//        Data.add("center");
//        Data.add("product_model");
//        ArrayList<String[]>inventory=SelectColums("inventory",p3,Columns,Data,selectStatement);///////////�ӹ���������Ӧ��quantity
//        Columns.clear();
//        Data.clear();


        selectStatement ="select ";
        ArrayList<String[]>p4=new ArrayList<>();
        for (int i = 0; i <afterUpdate.size(); i++) {
            String[]strings1=new String[1];
            strings1[0]=afterUpdate.get(i)[1];
            p4.add(strings1);
        }
//        Columns.add("model");
//        Columns.add("unit_price");
        Data.add("model");
        ArrayList<String[]>unit_price=SelectColums("model",p4,Columns,Data,selectStatement);////////�Ҷ�Ӧ��unit_price
        Columns.clear();
        Data.clear();




        updateStatement="update inventory set ";

        for (int i = 0; i <afterUpdate.size() ; i++) {
            ArrayList<String[]>fin=new ArrayList<>();
            selectStatement ="select ";
            ArrayList<String[]>p3=new ArrayList<>();

                String[]strings2=new String[2];
                strings2[0]=centers.get(i)[5];
                strings2[1]=afterUpdate.get(i)[1];
                p3.add(strings2);

            Data.add("center");
            Data.add("product_model");
            ArrayList<String[]>inventory=SelectColums("inventory",p3,Columns,Data,selectStatement);///////////�ӹ���������Ӧ��quantity
            Columns.clear();
            Data.clear();

            String[]strings3=new String[5];

            strings3[0]=String.valueOf(Integer.parseInt(inventory.get(0)[2])+Integer.parseInt(beforeUpdate.get(i)[2])-Integer.parseInt(afterUpdate.get(i)[2]));
            strings3[1]=String.valueOf(Integer.parseInt(inventory.get(0)[4])-Integer.parseInt(beforeUpdate.get(i)[2])+Integer.parseInt(afterUpdate.get(i)[2]));
            strings3[2]=String.valueOf(Integer.parseInt(inventory.get(0)[5])-Integer.parseInt(beforeUpdate.get(i)[2])*Integer.parseInt(unit_price.get(i)[4])
                    +Integer.parseInt(afterUpdate.get(i)[2])*Integer.parseInt(unit_price.get(i)[4]));//////////profits-֮ǰ������*����+���ڵ�����*����;
            strings3[3]=inventory.get(0)[0];
            strings3[4]=inventory.get(0)[1];
            fin.add(strings3);
            Data.add("quantity");
            Data.add("sales_quantity");
            Data.add("profits");
            Columns.add("center");
            Columns.add("product_model");

            UpdateColumns("inventory",fin,Columns,Data,updateStatement);
            Columns.clear();
            Data.clear();
        }

       /////////////////����ǰ���ҵĵ��ۺ�center�͸���ǰ���quantity ���²ֿ�


    }



    public  static void UpdateColumns(String table, ArrayList<String[]> parts, ArrayList<String>Columns, ArrayList<String>Data,String updateStatement){
        ArrayList<Integer> INT =new ArrayList<>();
        ArrayList<Integer> DATE =new ArrayList<>();
        ArrayList<Integer> ColumnIndex =new ArrayList<>();
        if (Data.size()!=0){
            for (int i = 0; i < Data.size() ; i++) {
                if (i== Data.size()-1){
                    updateStatement = updateStatement + Trans(i,Data.get(i),INT,DATE)+" ";
                }else {
                    updateStatement = updateStatement + Trans(i,Data.get(i),INT,DATE) + ",";
                }
            }


        }
        updateStatement=updateStatement+"where ";
        if (Columns.size()!=0){
            for (int i = Data.size(); i < Columns.size()+Data.size() ; i++) {
                if (i== Columns.size()+Data.size()-1){
                    updateStatement = updateStatement + Trans(i, Columns.get(i-Data.size()),INT,DATE);
                }else {
                    updateStatement = updateStatement + Trans(i, Columns.get(i-Data.size()),INT,DATE) + " and ";
                }
            }
        }

        /////////////////////////////open DB
        try {
            stmt = con.prepareStatement(updateStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        String[]strings=new String[parts.get(0).length ];
        for (int i = 0; i < parts.size(); i++) {
            try {

                if (parts.get(i).length > 1) {
                    for (int j = 0; j < parts.get(0).length; j++) {
                        if (parts.get(i)[j].equals("")){
                            parts.get(i)[j]="null";
                        }
                        strings[j]= parts.get(i)[j];
                    }
                    load_update(strings,INT,DATE);
                    con.commit();
                }


            } catch (SQLException se) {
                System.err.println("SQL error: " + se.getMessage());
                try {
                    con.rollback();
                } catch (Exception e2) {
                }
            }
        }
//

//
//        ChooseTable(table,parts);
    }
//

//
//        ChooseTable(table,parts);


    //
    public static ArrayList<String[]> SelectColums(String table, ArrayList<String[]> parts, ArrayList<String> Columns, ArrayList<String> Data, String selectStatement) {
//        table table = null;
        ArrayList<Integer> INTData = new ArrayList<>();
        ArrayList<Integer> INTColumn = new ArrayList<>();
        ArrayList<Integer> DATEData = new ArrayList<>();
        ArrayList<Integer> DATEColumn = new ArrayList<>();
        ArrayList<String[]> p = new ArrayList<>();
        if (Columns.size() != 0) {
            for (int i = 0; i < Columns.size(); i++) {
                if (i == Columns.size() - 1) {
                    selectStatement = selectStatement + Trans(i, Columns.get(i), INTColumn, DATEColumn) + " ";
                } else {
                    selectStatement = selectStatement + Trans(i, Columns.get(i), INTColumn, DATEColumn) + ",";
                }
            }


        } else {
            selectStatement = selectStatement + "* ";
            switch (table) {
                case "center":
                    INTColumn.add(0);
                    break;
                case "enterprise":
                    INTColumn.add(0);
                    break;
                case "model":
                    INTColumn.add(0);
                    INTColumn.add(4);
                    break;
                case "staff":
                    INTColumn.add(0);
                    INTColumn.add(2);
                    break;
                case "contract":
                    DATEColumn.add(3);
                    break;
                case "orders":
                    INTColumn.add(2);
                    DATEColumn.add(4);
                    DATEColumn.add(5);
                    break;
                case "inventory":
                    INTColumn.add(2);
                    INTColumn.add(3);
                    INTColumn.add(4);
                    INTColumn.add(5);
                    break;
                case "temp":
                    INTColumn.add(2);
                    INTColumn.add(6);
                    break;
            }
        }


        selectStatement = selectStatement + "from " + table + " where ";
        for (int i = 0; i < Data.size(); i++) {
            if (i == 0) {
                selectStatement = selectStatement + Trans(i, Data.get(i), INTData, DATEData);
            } else {
                selectStatement = selectStatement + " and " + Trans(i, Data.get(i), INTData, DATEData);
            }
        }

        /////////////////////////////open DB
        try {
            stmt = con.prepareStatement(selectStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        String[] strings = new String[parts.get(0).length];

        for (int i = 0; i < parts.size(); i++) {
            try {

                if (parts.get(i).length >= 1) {
                    for (int j = 0; j < parts.get(0).length; j++) {
                        if (parts.get(i)[j].equals("")) {
                            parts.get(i)[j] = "null";
                        }
                        strings[j] = parts.get(i)[j];
                    }
                    String[] strings1 = load_select(strings, Columns, INTData, DATEData, table);
                    if (strings1 != null) {
                        p.add(strings1);
                    } else {
                        nulls.add(i);
                    }
                    con.commit();
                }
            } catch (SQLException se) {
                System.err.println("SQL error: " + se.getMessage());
                try {
                    con.rollback();
                } catch (Exception e2) {
                }
            }
        }
        return p;
    }


    private static String[] load_select(String[] strings, ArrayList<String> Columns, ArrayList<Integer> INT, ArrayList<Integer> DATE, String table)
            throws SQLException {

//        int index=0;
        String[] fin = new String[centerLength.get(table)];
        if (con != null) {
            int countINT = 0;
            int countDATE = 0;
            ResultSet rs = null;
            for (int i = 0; i < strings.length; i++) {
                if (INT.size() > countINT) {
                    if (INT.get(countINT) == i) {
                        stmt.setInt(i + 1, Integer.parseInt(strings[i]));
                        countINT++;
                        continue;
                    }
                }
                if (DATE.size() > countDATE) {
                    if (DATE.get(countDATE) == i) {
                        stmt.setDate(i + 1, strToDate(strings[i]));
                        countDATE++;
                        continue;
                    }
                }
                stmt.setString(i + 1, strings[i]);
            }
            countINT = 0;
            countDATE = 0;
            rs = stmt.executeQuery();

            if (rs.next()) {
                if (Columns.size() == 0) {
                    fin = new String[centerLength.get(table)];
                    for (int i = 1; i <= centerLength.get(table); i++) {
                        if (INT.size() > countINT) {
                            if (INT.get(countINT) + 1 == i) {
                                String s = String.valueOf(rs.getInt(i));
                                fin[i - 1] = s;
//
//                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (DATE.size() > countDATE) {
                            if (DATE.get(countDATE) + 1 == i) {
                                String s = String.valueOf(rs.getDate(i));
                                fin[i - 1] = s;

//                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s = rs.getString(i);
                        fin[i - 1] = s;
//                        System.out.print(s + " ");
                    }
//                    System.out.println();
                } else {
                    fin = new String[Columns.size()];
                    for (int i = 1; i <= Columns.size(); i++) {
                        if (INT.size() > countINT) {
                            if (INT.get(countINT) + 1 == i) {
                                String s = String.valueOf(rs.getInt(i));
                                fin[i - 1] = s;
//
//                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (DATE.size() > countDATE) {
                            if (DATE.get(countDATE) + 1 == i) {
                                String s = String.valueOf(rs.getDate(i));
                                fin[i - 1] = s;

//                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s = rs.getString(i);
                        fin[i - 1] = s;
//                        System.out.print(s + " ");
                    }
//                    System.out.println();
                }
            }
        }
        boolean isNull = true;
        for (int i = 0; i < fin.length; i++) {
            if (fin[i] != null) {
                isNull = false;
                break;
            }
        }
        return isNull ? null : fin;
    }

    public static void load_update(String[] strings, ArrayList<Integer> INT, ArrayList<Integer> DATE) throws SQLException {
        if (con != null) {
            int countINT = 0;
            int countDATE = 0;
            for (int i = 0; i < strings.length; i++) {
                if (INT.size() > countINT) {
                    if (INT.get(countINT) == i) {
                        stmt.setInt(i + 1, Integer.parseInt(strings[i]));
                        countINT++;
                        continue;
                    }
                }
                if (DATE.size() > countDATE) {
                    if (DATE.get(countDATE) == i) {
                        stmt.setDate(i + 1, strToDate(strings[i]));
                        countDATE++;
                        continue;
                    }
                }
                stmt.setString(i + 1, strings[i]);
            }


            stmt.executeUpdate();
        }
    }

    public static String Trans(int i, String s, ArrayList<Integer> INT, ArrayList<Integer> DATE) {
//        if (Columns.contains(s)){
//            ColumnIndex.add(i);
//        }
        if (s.toLowerCase(Locale.ROOT).equals("id") || s.toLowerCase().contains("seq") || s.toLowerCase(Locale.ROOT).equals("age") || s.toLowerCase(Locale.ROOT).equals("unit_price") ||
                s.toLowerCase(Locale.ROOT).contains("quantity") || s.toLowerCase(Locale.ROOT).contains("money") || s.toLowerCase(Locale.ROOT).contains("profits")) {
            INT.add(i);

        }
        if (s.toLowerCase(Locale.ROOT).contains("date") || s.toLowerCase().contains("estimated") || s.toLowerCase().contains("lodgement")) {
            DATE.add(i);

        }
        return s + "=? ";

    }

    public static void getAllStaffCount() throws SQLException {
        System.out.println("Q6");
        ArrayList<String> types = new ArrayList<>();
        int typeCount = 0;
        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement("select distinct type from staff");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {

            ResultSet rs1 = null;
            rs1 = stmt.executeQuery();
            while (rs1.next()) {
                typeCount++;
                types.add(rs1.getString(1));
            }
        }

        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement("select type, count(*) from staff where type=? group by type");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {
            for (int j = 0; j < typeCount; j++) {
                ResultSet rs2 = null;
                stmt.setString(1, types.get(j));
                rs2 = stmt.executeQuery();


                if (rs2.next()) {
                    System.out.printf("%-50s\t", rs2.getString(1));
                    System.out.printf("%-20s\t\n", rs2.getInt(2));

                }
            }

        }
    }

    public static int getContractCount() throws SQLException {
        System.out.println("Q7");
        int fin = 0;
        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement("select count(distinct contract_num) from contract");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {

            ResultSet rs1 = null;
            rs1 = stmt.executeQuery();
            while (rs1.next()) {
                fin = rs1.getInt(1);
                System.out.println(fin);
            }
        }
        return fin;
    }


    public static int getNeverSoldProductCount() throws SQLException {
        System.out.println("Q9");
        int fin = 0;
        try {
//            stmt = con.prepareStatement("select sum(id) from (select * from staff where gender='Female') Temp");
            stmt = con.prepareStatement("select  count(distinct product_model) from inventory");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {
            ResultSet rs1 = null;
            rs1 = stmt.executeQuery();
            while (rs1.next()) {
                fin = rs1.getInt(1);

            }
        }


        int temp = 0;
        try {
//            stmt = con.prepareStatement("select sum(id) from (select * from staff where gender='Female') Temp");
            stmt = con.prepareStatement("select  count(distinct product_model) from orders");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {
            ResultSet rs1 = null;
            rs1 = stmt.executeQuery();
            while (rs1.next()) {
                temp = rs1.getInt(1);

            }
        }
        System.out.println(fin - temp);
        return fin - temp;

    }


    public static void Select(ArrayList<String[]> parts) {
//        centerLength.put("center",2);
//        centerLength.put("enterprise",6);
//        centerLength.put("model",5);
//        centerLength.put("staff",8);
//        centerLength.put("contract",6);
//        centerLength.put("orders",6);
//        centerLength.put("inventory",6);
        String selectStatement = "select ";
        Scanner in = new Scanner(System.in);
        String table = in.next().toLowerCase(Locale.ROOT);
        ArrayList<String> Columns = new ArrayList<>();

        ArrayList<String> Data = new ArrayList<>();
        while (in.hasNext()) {
            String m = in.next();
            if (m.equals("\\")) break;
            Columns.add(m);
        }
        while (in.hasNext()) {
            String m = in.next();
            if (m.equals("\\")) break;
            Data.add(m);
        }
        SelectColums(table, parts, Columns, Data, selectStatement);
    }

    public static void Update(ArrayList<String[]> parts) {
        String updateStatement;//后面加
        Scanner in = new Scanner(System.in);
        String table = in.next().toLowerCase(Locale.ROOT);
        ArrayList<String> Columns = new ArrayList<>();

        ArrayList<String> Data = new ArrayList<>();
        updateStatement = "update " + table + " set ";
        while (in.hasNext()) {
            String m = in.next();
            if (m.equals("\\")) break;
            Columns.add(m);
        }
        while (in.hasNext()) {
            String m = in.next();
            if (m.equals("\\")) break;
            Data.add(m);
        }
        ArrayList<String[]> p = new ArrayList<>();
        for (String[] strings :
                parts) {
            String[] s = new String[strings.length];
            for (int j = 0; j < Data.size(); j++) {
                if (strings[j + Columns.size()].equals("")) {
                    s[j] = "null";
                } else {
                    s[j] = strings[j + Columns.size()];
                }
            }

            for (int j = Data.size(); j < strings.length; j++) {
                if (strings[j - Data.size()].equals("")) {
                    s[j] = "null";
                } else {
                    s[j] = strings[j - Data.size()];
                }
            }
            p.add(s);
        }
        UpdateColumns(table, p, Columns, Data, updateStatement);
    }

    public static void Delete(ArrayList<String[]> parts) {
        String deleteStatement = "delete from ";
        Scanner in = new Scanner(System.in);
        String table = in.next().toLowerCase(Locale.ROOT);
        ArrayList<String> Columns = new ArrayList<>();

        ArrayList<String> Data = new ArrayList<>();
        while (in.hasNext()) {
            String m = in.next();
            if (m.equals("\\")) break;
            Data.add(m);
        }
        DeleteColumns(table, parts, Columns, Data, deleteStatement);
    }


    public static void DeleteColumns(String table, ArrayList<String[]> parts, ArrayList<String> Columns, ArrayList<String> Data, String deleteStatement) {
        ArrayList<Integer> INT = new ArrayList<>();
        ArrayList<Integer> DATE = new ArrayList<>();
        ArrayList<Integer> ColumnIndex = new ArrayList<>();
        deleteStatement = deleteStatement + table + " where ";
        for (int i = 0; i < Data.size(); i++) {
            if (i == 0) {
                deleteStatement = deleteStatement + Trans(i, Data.get(i), Columns, ColumnIndex, INT, DATE);
            } else {
                deleteStatement = deleteStatement + " and " + Trans(i, Data.get(i), Columns, ColumnIndex, INT, DATE);
            }
        }
        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement(deleteStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        String[] strings = new String[parts.get(0).length];

        for (int i = 1; i < parts.size(); i++) {
            try {
                if (parts.get(i).length > 1) {
                    for (int j = 0; j < parts.get(0).length; j++) {
                        if (parts.get(i)[j].equals("")) {
                            parts.get(i)[j] = "null";
                        }
                        strings[j] = parts.get(i)[j];
                    }
                    load_delete(strings, INT, DATE);
                    con.commit();
                }


            } catch (SQLException se) {
                System.err.println("SQL error: " + se.getMessage());
                try {
                    con.rollback();
                } catch (Exception e2) {
                }
            }
        }
    }


    private static void load_delete(String[] strings, ArrayList<Integer> INT, ArrayList<Integer> DATE) throws SQLException {
        if (con != null) {
            int count = 0;

            for (int i = 0; i < strings.length; i++) {
                if (INT.size() > count) {
                    if (INT.get(count) == i) {
                        stmt.setInt(i + 1, Integer.parseInt(strings[i]));
                        count++;
                        continue;
                    }
                }
                stmt.setString(i + 1, strings[i]);
            }


            stmt.executeUpdate();
        }
    }

    private static String[] load_select(String[] strings, ArrayList<String> Columns, ArrayList<Integer> ColumnIndex, ArrayList<Integer> INT, ArrayList<Integer> DATE, String table)
            throws SQLException {

//        int index=0;
        String[] fin = new String[centerLength.get(table)];
        if (con != null) {
            int countINT = 0;
            int countDATE = 0;
            ResultSet rs = null;
            for (int i = 0; i < strings.length; i++) {
                if (INT.size() > countINT) {
                    if (INT.get(countINT) == i) {
                        stmt.setInt(i + 1, Integer.parseInt(strings[i]));
                        countINT++;
                        continue;
                    }
                }
                if (DATE.size() > countDATE) {
                    if (DATE.get(countDATE) == i) {
                        stmt.setDate(i + 1, strToDate(strings[i]));
                        countDATE++;
                        continue;
                    }
                }
                stmt.setString(i + 1, strings[i]);
            }
            countINT = 0;
            countDATE = 0;
            rs = stmt.executeQuery();

            if (rs.next()) {
                if (Columns.size() == 0) {
                    fin = new String[centerLength.get(table)];
                    for (int i = 1; i <= centerLength.get(table); i++) {
                        if (INT.size() > countINT) {
                            if (INT.get(countINT) + 1 == i) {
                                String s = String.valueOf(rs.getInt(i));
                                fin[i - 1] = s;
//todo

//                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (DATE.size() > countDATE) {
                            if (DATE.get(countDATE) + 1 == i) {
                                String s = String.valueOf(rs.getDate(i));
                                fin[i - 1] = s;

//                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s = rs.getString(i);
                        fin[i - 1] = s;
//                        System.out.print(s + " ");
                    }
//                    System.out.println();
                } else {
                    fin = new String[Columns.size()];
                    for (int i = 1; i <= ColumnIndex.size(); i++) {
                        if (INT.size() > countINT) {
                            if (INT.get(countINT) + 1 == i) {
                                String s = String.valueOf(rs.getInt(i));
                                fin[i - 1] = s;

//                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (DATE.size() > countDATE) {
                            if (DATE.get(countDATE) + 1 == i) {
                                String s = String.valueOf(rs.getDate(i));
                                fin[i - 1] = s;

//                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s = rs.getString(i);
                        fin[i - 1] = s;
//                        System.out.print(s + " ");
                    }
//                    System.out.println();
                }
            }
        }
        boolean isNull = true;
        for (int i = 0; i < fin.length; i++) {
            if (fin[i] != null) {
                isNull = false;
                break;
            }
        }
        return isNull ? null : fin;
    }

    public static String Trans(int i, String s, ArrayList<String> Columns, ArrayList<Integer> ColumnIndex, ArrayList<Integer> INT, ArrayList<Integer> DATE) {
        if (Columns.contains(s)) {
            ColumnIndex.add(i);
        }
        if (s.toLowerCase(Locale.ROOT).equals("id") || s.toLowerCase(Locale.ROOT).equals("age") || s.toLowerCase(Locale.ROOT).equals("unit_price") ||
                s.toLowerCase(Locale.ROOT).contains("quantity") || s.toLowerCase(Locale.ROOT).contains("money") || s.toLowerCase(Locale.ROOT).contains("profits")) {
            INT.add(i);

        }
        if (s.toLowerCase(Locale.ROOT).contains("date")) {
            DATE.add(i);

        }
        return s + "=? ";

    }


}

