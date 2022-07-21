import com.csvreader.CsvReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;

public class csvIn {
    static char separate =',';
    public static ArrayList<obIn> getInsert(String filePath) throws IOException {



        String encode = "GBK";
        CsvReader r = new CsvReader(filePath, separate, Charset.forName(encode));



        ArrayList<obIn> arrayList=new ArrayList<>();
        r.readRecord();
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
//            for (int i = 0; i < head.length; i++) {
//                System.out.println(head[i] + ":" + r.get(head[i]));
//            }
            arrayList.add(ob);

        }
        r.close();

        return arrayList;

    }
}
