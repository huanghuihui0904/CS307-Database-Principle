import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

public class t4_mysql_update {
    private static URL propertyURL = t4_mysql_update.class
            .getResource("/loader.cnf");

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static boolean verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Mysql driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url =  "jdbc:mysql://" + host + "/" + dbname;
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
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        try {
            stmt = con.prepareStatement("update contract_info set contract_number=?,client_enterprise=?,supply_center=?,country=?,city=?,industry=?,product_code=?,product_name=?,product_model=?,unit_price=?,quantity=?,contract_date=?,estimated_delivery_date=?,lodgement_date=?,director=?,salesman=?,salesman_number=?,gender=?,age=?,mobile_phone=? where contract_number=? and product_model=? and salesman_number=?");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    private static void loadData(String contract_number, String client_enterprise, String supply_center, String country, String city, String industry, String product_code, String product_name, String product_model, int unit_price, int quantity, Date contract_date, Date estimated_delivery_date, Date lodgement_date, String director, String salesman, String salesman_number, String gender, int age, String mobile_phone, String contract_number2, String product_model2, String salesman_number2)
            throws SQLException {
        if (con != null) {
            //对应set后面的很多个问号
            stmt.setString(1, contract_number);
            stmt.setString(2, client_enterprise);
            stmt.setString(3, supply_center);
            stmt.setString(4, country);
            stmt.setString(5, city);
            stmt.setString(6, industry);
            stmt.setString(7, product_code);
            stmt.setString(8, product_name);
            stmt.setString(9, product_model);
            stmt.setInt(10, unit_price);
            stmt.setInt(11, quantity);
            stmt.setDate(12, contract_date);
            stmt.setDate(13, estimated_delivery_date);
            stmt.setDate(14, lodgement_date);
            stmt.setString(15, director);
            stmt.setString(16, salesman);
            stmt.setString(17, salesman_number);
            stmt.setString(18, gender);
            stmt.setInt(19, age);
            stmt.setString(20, mobile_phone);
            //对应where后面的三个问号
            stmt.setString(21, contract_number2);
            stmt.setString(22, product_model2);
            stmt.setString(23, salesman_number2);
            stmt.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException {
        String fileName = null;
        String fileName1 = null;
        String fileName2 = null;
        boolean verbose = false;

        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 3:
                switch (args[0]) {
                    case "-v":
                        verbose = true;
                        break;
                    default:
                        System.err.println("Usage: java [-v] GoodLoader filename");
                        System.exit(1);
                }
                //更新后的数据文件名写在前面，要更新的写在后面 如-v update_after_normal.csv update_before.csv
                fileName1 = args[1];
                fileName2 = args[2];
                break;
            default:
                System.err.println("Usage: java [-v] GoodLoader filename");
                System.exit(1);
        }


        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "root");
        defprop.put("password", "123456");
        defprop.put("database", "db_proj1_t4");
        Properties prop = new Properties(defprop);
        String line;
        //更新后的数据存在parts1，即set后面的数据
        ArrayList<String[]> parts1 = new ArrayList<>();
        //要更新的数据存在parts2，即where后面的数据
        ArrayList<String[]> parts2 = new ArrayList<>();
        //读数据进parts1
        try (BufferedReader infile = new BufferedReader(new FileReader(fileName1));) {
            while ((line = infile.readLine()) != null) {
                parts1.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //读数据进parts2
        try (BufferedReader infile = new BufferedReader(new FileReader(fileName2));) {
            while ((line = infile.readLine()) != null) {
                parts2.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long start;
        long end;
        start = System.currentTimeMillis();
        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));
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
        Date contract_date;
        Date estimated_delivery_date;
        Date lodgement_date;
        String director;
        String salesman;
        String salesman_number;
        String gender;
        int age;
        String mobile_phone;
        int cnt = 0;

        for (int i = 0; i < parts1.size(); i++) {


            try {
                cnt++;

                if (parts1.get(i).length > 1) {
                    contract_number = parts1.get(i)[0];
                    client_enterprise = parts1.get(i)[1];
                    supply_center = parts1.get(i)[2];
                    country = parts1.get(i)[3];
                    if (parts1.get(i)[4].equals("NULL")) {
                        city = null;
                    } else {
                        city = parts1.get(i)[4];
                    }
                    industry = parts1.get(i)[5];
                    product_code = parts1.get(i)[6];
                    product_name = parts1.get(i)[7];
                    if (parts1.get(i)[8].equals("")) {
                        product_model = null;
                    } else {
                        product_model = parts1.get(i)[8];
                    }

                    unit_price = Integer.parseInt(parts1.get(i)[9]);
                    quantity = Integer.parseInt(parts1.get(i)[10]);
                    contract_date = strToDate(parts1.get(i)[11]);
                    estimated_delivery_date = strToDate(parts1.get(i)[12]);
                    lodgement_date = strToDate(parts1.get(i)[13]);
                    director = parts1.get(i)[14];
                    salesman = parts1.get(i)[15];
                    salesman_number = parts1.get(i)[16];
                    gender = parts1.get(i)[17];
                    age = Integer.parseInt(parts1.get(i)[18]);
                    mobile_phone = parts1.get(i)[19];
                    loadData(contract_number, client_enterprise, supply_center, country, city, industry, product_code, product_name, product_model, unit_price, quantity, contract_date, estimated_delivery_date, lodgement_date, director, salesman, salesman_number, gender, age, mobile_phone, parts2.get(i)[0], parts2.get(i)[8], parts2.get(i)[16]);
                    con.commit();

                }
//                System.out.println("haha");

            } catch (SQLException se) {
                System.err.println("SQL error: " + se.getMessage());
                try {
                    con.rollback();
                } catch (Exception e2) {
                }

//                se.printStackTrace();
            }
        }


        stmt.close();
        closeDB();
        end = System.currentTimeMillis();
        System.out.println(cnt + " records successfully loaded");
        System.out.println("Loading speed : "
                + (cnt * 1000) / (end - start)
                + " records/s");
        System.out.println(end - start);

    }

    public static Date strToDate(String strDate) {
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
            Date date = new Date(d.getTime());
            return date;
        }
    }


}

