
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;


public class t4_highCon4 {
    //static long cnt=0;
    public static void main(String[] args) {
        String fileName = null;
        boolean verbose = false;
        String line;
        ArrayList<String[]> parts = new ArrayList<>();

        long start = System.currentTimeMillis();
        int threadCount = 10;
        int total = 100;
        int every = total / threadCount;
        final CountDownLatch latch = new CountDownLatch(threadCount);
//		ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < threadCount; i++) {

            new Thread(new Worker_test(latch, i * every, (i + 1) * every)).start();
        }
        try {
            latch.await();
            long end = System.currentTimeMillis();
           // System.out.println(cnt + " records successfully loaded");
//            System.out.println("Loading speed : "
//                    + (cnt * 1000) / (end - start)
//                    + " records/s");
            System.out.println(end - start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Worker_test implements Runnable {
    static String sql1 = "update counter set count=count-1 where id=1;";
    static String sql2 = "update counter set count=count+1 where id=2;";

    int start = 0;
    int end = 0;

    CountDownLatch latch;
    ArrayList<String[]> parts;

    public Worker_test(CountDownLatch latch, int start, int end) {
        this.start = start;
        this.end = end;

        this.latch = latch;

    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {

            JdbcUtils2.insert(sql1, sql2);
        }
        latch.countDown();
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


class JdbcUtils2 {
    static Savepoint sp = null;
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
        return null;}

    static {
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "test4_highCon");
        Properties prop = new Properties(defprop);
        int i = 0;
        while (i < 10) {
            Connection con=openDB(prop.getProperty("host"), prop.getProperty("database"), prop.getProperty("user"), prop.getProperty("password"));
            try {
                con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pools.add(con);
            i++;
        }

    }

    public static synchronized Connection getPool() {
        if (pools != null && pools.size() > 0) {
            int last_ind = pools.size() - 1;
            return (Connection) pools.remove(last_ind);
        } else {
            Properties defprop = new Properties();
            defprop.put("host", "localhost");
            defprop.put("user", "checker");
            defprop.put("password", "123456");
            defprop.put("database", "test4_highCon");
            Properties prop = new Properties(defprop);
            return openDB(prop.getProperty("host"), prop.getProperty("database"), prop.getProperty("user"), prop.getProperty("password"));

        }
    }

    public static void insert(String sql1, String sql2) {

        Connection con = getPool();

        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        try {
          // t4_highCon4.cnt++;
//            sp = con.setSavepoint();//在这里设置事务回滚点

            stmt1 = con.prepareStatement(sql1);

//             loadData(contract_number,client_enterprise,supply_center,country,city,industry,product_code,product_name,product_model,unit_price, quantity,contract_date,estimated_delivery_date,lodgement_date,director, salesman, salesman_number,  gender,  age,  mobile_phone);

            stmt1.executeUpdate();
            con.commit();


            int a = con.getTransactionIsolation();
            System.out.println(a);

       Thread.currentThread().sleep(100);
            sp = con.setSavepoint();//在这里设置事务回滚点



            stmt2 = con.prepareStatement(sql2);
            stmt2.executeUpdate();

            con.commit();
//            sp = con.setSavepoint();//在这里设置事务回滚点
        } catch (SQLException e) {
            try {
                con.rollback(sp);//回滚到该事务点，即该点之前的会正常执行（sql1）
                con.commit();  //回滚了要记得提交,如果没有提交sql1将会自动回滚
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        finally {
//            if(stmt != null){
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }

            if (con != null) {

                pools.add(con);
            }
        }

    }
}


