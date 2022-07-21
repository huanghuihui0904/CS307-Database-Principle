import com.csvreader.CSVUtil;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;

public class Update {
    static char separate =',';
    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<obIn>Update_before=csvIn.getInsert("src/Data/select3.csv");
        ArrayList<obIn>Update_after=csvIn.getInsert("src/Data/update3.csv");

        String filePath = "E:\\IdeaProjects\\cs307_pro1\\src\\Data\\contract_info.csv";
        String filePathout = "E:\\IdeaProjects\\cs307_pro1\\src\\Data\\contract_info1.csv";
        String encode = "GBK";

        long start=System.currentTimeMillis();
        CsvReader r = new CsvReader(filePath, separate, Charset.forName(encode));

        r.readHeaders();


        ArrayList<obIn> arrayList=new ArrayList<>();
        String[] head = r.getHeaders(); //获取表头

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
            arrayList.add(ob);

        }
        r.close();
//        obIn ob1=arrayList.get(0);
        ArrayList<obIn> fin=arrayList;

        for (int i = 0; i <Update_before.size() ; i++) {
            try {
                obIn ob=Update_before.get(i);
                boolean isFind=false;
                for (obIn o:arrayList) {
                    if (ob.equals(o)){
                        isFind=true;
                        obIn ob1=Update_after.get(i);
                        for (obIn o1:
                             arrayList) {
                            if (ob1.equalsTotal(o1))throw new MyException();
                        }
                    if (!ob1.checkContractNum())throw new MyException();
                    if (!ob1.checkSalesmanNumber())throw new MyException();
                    if (!ob1.checkNull())throw new MyException();
                    if (!ob1.checkPhone())throw new MyException();
                    if (!ob1.checkProductCode())throw new MyException();
                    if (!ob1.checkType())throw new MyException();
                     arrayList.remove(o);
                     arrayList.add(1,ob1);
                     break;

                    }
                }
                if (!isFind)throw new MyException();
            }
            catch (MyException e){
                System.out.println("Error!");
            }
        }

        CsvWriter w = null;

//        FileOutputStream out = null;
        try {
//            out = new FileOutputStream(filePathout, true);
            w = new CsvWriter(filePathout, separate, Charset.forName(encode));
            w.writeRecord(new String[]{"contract number", "client enterprise", "supply center", "country,city", "industry",
                    "product code", "product name", "product model", "unit price", "quantity", "contract date",
                    "estimated delivery date", "lodgement date", "director,salesman", "salesman number", "gender", "age", "mobile phone"});
            for (obIn ob : arrayList) {

                w.writeRecord(new String[]{ob.getContract_number(), ob.getEnterprise(), ob.getCountry(), ob.getCity(), ob.getIndustry(), ob.getProduct_code(),
                        ob.getProduct_name(), ob.getProduct_model(), ob.getUnit_price(), ob.getQuantity(), ob.getContract_date(), ob.getEstimated_date(),
                        ob.getLodgement_date(), ob.getDirector(), ob.getSalesman(), ob.getSalesman_number(), ob.getGender(), ob.getAge(), ob.getPhone()});
            }

        } catch (Exception e) {
            System.out.println("生成CSV出错..." + e);
            throw e;
        } finally {
            w.close();
        }
        long end=System.currentTimeMillis();
        System.out.println();
        System.out.println(Update_before.size()+"records successfully loaded");
        System.out.println("Loading speed : " + (Update_before.size() * 1000L)/(end - start) + " records/s");
        System.out.println(end-start);


    }
}
