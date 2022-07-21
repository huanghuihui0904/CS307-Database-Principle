import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

public class t4_select {
    private static URL propertyURL = t4_select.class
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
            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
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

    private static void loadData(String contract_number,String product_model,  String salesman_number)
            throws SQLException {
        if (con != null) {

            ResultSet rs = null;
            stmt.setString(1, contract_number);

            stmt.setString(2, product_model);

            stmt.setString(3, salesman_number);

              rs=  stmt.executeQuery();
              if(rs.next()){
                  for (int i = 1; i <=20 ; i++) {
                      System.out.print(rs.getString(i)+" ");
                  }
                  System.out.println();
              }
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

        String product_model;

        String salesman_number;
        int cnt =0;
        for (int i = 0; i < parts.size(); i++) {


            try {
                    cnt++;

                    if (parts.get(i).length > 1) {
                        contract_number = parts.get(i)[0];


                            product_model = parts.get(i)[8];

                        salesman_number = parts.get(i)[16];
                        loadData(contract_number,product_model , salesman_number);

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

