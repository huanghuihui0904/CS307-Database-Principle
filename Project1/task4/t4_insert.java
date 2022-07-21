import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

public class t4_insert {
    private static URL propertyURL = t4_insert.class
            .getResource("/loader.cnf");

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static boolean verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {

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
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        try {
            stmt = con.prepareStatement("insert into contract_info"
                    + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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

    private static void loadData(String contract_number, String client_enterprise, String supply_center, String country, String city, String industry, String product_code, String product_name, String product_model, int unit_price, int quantity, java.sql.Date contract_date, java.sql.Date estimated_delivery_date, java.sql.Date lodgement_date, String director, String salesman, String salesman_number, String gender, int age, String mobile_phone)
            throws SQLException {
        if (con != null) {
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
            stmt.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException {
        String fileName = null;
        boolean verbose = false;

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
                        System.err.println("Usage: java [-v] GoodLoader filename");
                        System.exit(1);
                }
                fileName = args[1];
                break;
            default:
                System.err.println("Usage: java [-v] GoodLoader filename");
                System.exit(1);
        }


        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "proj_task4");
        Properties prop = new Properties(defprop);
        String line;
        ArrayList<String[]> parts=new ArrayList<>();
        try (BufferedReader infile = new BufferedReader(new FileReader(fileName));) {
            while ((line = infile.readLine()) != null) {
                parts.add(line.split(","));

            }} catch (FileNotFoundException e) {
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
        java.sql.Date contract_date;
        java.sql.Date estimated_delivery_date;
        java.sql.Date lodgement_date;
        String director;
        String salesman;
        String salesman_number;
        String gender;
        int age;
        String mobile_phone;
        int cnt =0;
        for (int i = 0; i < parts.size(); i++) {


            try {
                cnt++;

                if (parts.get(i).length > 1) {
                    contract_number = parts.get(i)[0];
                    client_enterprise = parts.get(i)[1];
                    supply_center = parts.get(i)[2];
                    country = parts.get(i)[3];
                    if (parts.get(i)[4].equals("NULL")) {
                        city = null;
                    } else {
                        city = parts.get(i)[4];
                    }
                    industry = parts.get(i)[5];
                    product_code = parts.get(i)[6];
                    product_name = parts.get(i)[7];
                    if(parts.get(i)[8].equals("")){
                        product_model=null;
                    }else {
                        product_model = parts.get(i)[8];
                    }

                    unit_price = Integer.parseInt(parts.get(i)[9]);
                    quantity = Integer.parseInt(parts.get(i)[10]);
                    contract_date = strToDate(parts.get(i)[11]);
                    estimated_delivery_date = strToDate(parts.get(i)[12]);
                    lodgement_date = strToDate(parts.get(i)[13]);
                    director = parts.get(i)[14];
                    salesman = parts.get(i)[15];
                    salesman_number = parts.get(i)[16];
                    gender = parts.get(i)[17];
                    age = Integer.parseInt(parts.get(i)[18]);
                    mobile_phone = parts.get(i)[19];
                    loadData(contract_number, client_enterprise, supply_center, country, city, industry, product_code, product_name, product_model, unit_price, quantity, contract_date, estimated_delivery_date, lodgement_date, director, salesman, salesman_number, gender, age, mobile_phone);
                    con.commit();

                }

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
                + (cnt * 1000)/(end - start)
                + " records/s");
        System.out.println(end - start);

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


}

