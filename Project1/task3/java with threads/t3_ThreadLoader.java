
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class t3_ThreadLoader {
    public static void main(String[] args) {
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
        String line;
        ArrayList<String[]> parts = new ArrayList<>();
        long cnt=-1;
        try (BufferedReader infile = new BufferedReader(new FileReader(fileName));) {
            while ((line = infile.readLine()) != null) {
                cnt++;
                if(cnt==0){
                    continue;
                }
                parts.add(line.split(","));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long start = System.currentTimeMillis();
        int threadCount = 60;
        int total = 50000;
        int every = total / threadCount;
        final CountDownLatch latch = new CountDownLatch(threadCount);
//		ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < threadCount; i++) {

            new Thread(new Worker(latch, i * every, (i + 1) * every, parts)).start();
        }
        try {
            latch.await();
            long end = System.currentTimeMillis();
            System.out.println(cnt + " records successfully loaded");
            System.out.println("Loading speed : "
                    + (cnt * 1000)/(end - start)
                    + " records/s");
            System.out.println(end - start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Worker implements Runnable {
static String sql="insert into contract_info values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    int start = 0;
    int end = 0;

    CountDownLatch latch;
    ArrayList<String[]> parts;

    public Worker(CountDownLatch latch, int start, int end, ArrayList<String[]> parts) {
        this.start = start;
        this.end = end;
        this.latch = latch;
        this.parts = parts;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
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
            if (parts.get(i)[8].equals("")) {
                product_model = null;
            } else {
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
            t3_ThreadLoader_JdbcUtils.insert(sql,contract_number, client_enterprise, supply_center, country, city, industry, product_code, product_name, product_model, unit_price, quantity, contract_date, estimated_delivery_date, lodgement_date, director, salesman, salesman_number, gender, age, mobile_phone);

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


