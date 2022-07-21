import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class t3_ThreadLoader_JdbcUtils {

    private static URL propertyURL = t4_insert.class
            .getResource("/loader.cnf");
    //    private static Connection con = null;
//    private static PreparedStatement stmt = null;
    private static boolean verbose = false;
    static Vector pools = new Vector<>();
    private static Connection openDB(String host, String dbname,
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
            Connection con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
            return con;
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
//        try {
//            stmt = con.prepareStatement("insert into contract_info"
//                    + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
    }


//    private static void closeDB() {
//        if (con != null) {
//            try {
////                if (stmt != null) {
////                    stmt.close();
////                }
//                con.close();
//                con = null;
//            } catch (Exception e) {
//                // Forget about it
//            }
//        }
//    }

//    private static void loadData(String contract_number, String client_enterprise, String supply_center, String country, String city, String industry, String product_code, String product_name, String product_model, int unit_price, int quantity, java.sql.Date contract_date, java.sql.Date estimated_delivery_date, java.sql.Date lodgement_date, String director, String salesman, String salesman_number, String gender, int age, String mobile_phone)
//            throws SQLException {
//        if (con != null) {
//            stmt.setString(1, contract_number);
//            stmt.setString(2, client_enterprise);
//            stmt.setString(3, supply_center);
//            stmt.setString(4, country);
//            stmt.setString(5, city);
//            stmt.setString(6, industry);
//            stmt.setString(7, product_code);
//            stmt.setString(8, product_name);
//            stmt.setString(9, product_model);
//            stmt.setInt(10, unit_price);
//            stmt.setInt(11, quantity);
//            stmt.setDate(12, contract_date);
//            stmt.setDate(13, estimated_delivery_date);
//            stmt.setDate(14, lodgement_date);
//            stmt.setString(15, director);
//            stmt.setString(16, salesman);
//            stmt.setString(17, salesman_number);
//            stmt.setString(18, gender);
//            stmt.setInt(19, age);
//            stmt.setString(20, mobile_phone);
//            stmt.executeUpdate();
//        }
//    }

    static {
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "proj_task4");
        Properties prop = new Properties(defprop);
        int i = 0;
        while(i<10){
            pools.add(openDB(prop.getProperty("host"), prop.getProperty("database"), prop.getProperty("user"), prop.getProperty("password")));
            i++;
        }

    }
    public static synchronized Connection getPool(){
        if(pools != null && pools.size() > 0){
            int last_ind = pools.size() -1;
            return (Connection) pools.remove(last_ind);
        }else{
            Properties defprop = new Properties();
            defprop.put("host", "localhost");
            defprop.put("user", "checker");
            defprop.put("password", "123456");
            defprop.put("database", "proj_task4");
            Properties prop = new Properties(defprop);
            return  openDB(prop.getProperty("host"), prop.getProperty("database"), prop.getProperty("user"), prop.getProperty("password"));

        }
    }

    public static void insert(String sql,String contract_number, String client_enterprise, String supply_center, String country, String city, String industry, String product_code, String product_name, String product_model, int unit_price, int quantity, java.sql.Date contract_date, java.sql.Date estimated_delivery_date, java.sql.Date lodgement_date, String director, String salesman, String salesman_number, String gender, int age, String mobile_phone){

        Connection con = getPool();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);

//             loadData(contract_number,client_enterprise,supply_center,country,city,industry,product_code,product_name,product_model,unit_price, quantity,contract_date,estimated_delivery_date,lodgement_date,director, salesman, salesman_number,  gender,  age,  mobile_phone);
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
            con.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
//            if(stmt != null){
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }

            if(con != null){

                pools.add(con);
            }
        }

    }
}