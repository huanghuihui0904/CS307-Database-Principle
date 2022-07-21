import com.csvreader.CSVUtil;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;

public class Select {
    static char separate =',';
    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<obIn>Insert=csvIn.getInsert("src/Data/select3.csv");

        String filePath = "E:\\IdeaProjects\\cs307_pro1\\src\\Data\\contract_info.csv";
//        String filePathout = "E:\\IdeaProjects\\cs307_pro1\\src\\Data\\contract_info1.csv";
        String encode = "GBK";
        long start=System.currentTimeMillis();
        CsvReader r = new CsvReader(filePath, separate, Charset.forName(encode));

        r.readHeaders();


        ArrayList<obIn> arrayList=new ArrayList<>();
        String[] head = r.getHeaders(); //获取表头
        int count=0;
        while (r.readRecord()) {
            obIn ob=new obIn();
            ob.setContract_number(r.get(0));
            ob.setEnterprise(r.get(1));
            ob.setSupply_center(r.get(2));
            ob.setCountry(r.get(3));
            ob.setCity(r.get(4));
            ob.setIndustry(r.get(5));
            ob.setProduct_code(r.get(6));
            ob.setProduct_name(r.get(7));
            ob.setProduct_model(r.get(8));
            ob.setUnit_price(r.get(9));
            ob.setQuantity(r.get(10));
            ob.setContract_date(r.get(11));
            ob.setEstimated_date(r.get(12));
            ob.setLodgement_date(r.get(13));
            ob.setDirector(r.get(14));
            ob.setSalesman(r.get(15));
            ob.setSalesman_number(r.get(16));
            ob.setGender(r.get(17));
            ob.setAge(r.get(18));
            ob.setPhone(r.get(19));
            for (obIn o:Insert) {
                if (ob.equals(o)){
                    count++;
                    arrayList.add(o);
                    break;
                }
            }

        }
        r.close();
        while (count< Insert.size()) {
            try {

                if (count < Insert.size()){
                    count++;
                    throw new MyException();
                }

            } catch (MyException e) {
                System.out.println("Error!");
            }
        }


//        CsvWriter w = null;

//        FileOutputStream out = null;
        try {
//            out = new FileOutputStream(filePathout, true);
//            w = new CsvWriter(filePathout, separate, Charset.forName(encode));
//            w.writeRecord(head);
            for (obIn ob : arrayList) {

                System.out.println(ob.getContract_number()+" "+ ob.getEnterprise()+" "+ob.getCountry()+" "+ ob.getCity()+" "+ ob.getIndustry()+" "+ ob.getProduct_code()+" "+
                        ob.getProduct_name() +" "+ob.getProduct_model()+" "+ ob.getUnit_price()+" "+ ob.getQuantity()+" "+ ob.getContract_date()+" "+ ob.getEstimated_date()+" "+
                        ob.getLodgement_date()+" "+ ob.getDirector()+" "+ ob.getSalesman()+" "+ ob.getSalesman_number()+" "+ ob.getGender()+" "+ ob.getAge()+" "+ ob.getPhone());
            }

        } catch (Exception e) {
            System.out.println("生成CSV出错..." + e);
            throw e;
        }
//        finally {
//            w.close();
//            //            if (null != out) {
////                try {
////                    out.close();
////                } catch (IOException e) {
////                    System.out.println("exportCsv close Exception: " + e);
////                    throw e;
////                }
////            }
//        }
        long end=System.currentTimeMillis();
        System.out.println();
        System.out.println(Insert.size()+"records successfully loaded");
        System.out.println("Loading speed : " + (Insert.size() * 1000L)/(end - start) + " records/s");
        System.out.println(end-start);



    }
}
