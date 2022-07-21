import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class obIn {
    String contract_number;
    String enterprise;
    String supply_center;
    String country;
    String city;
    String industry;
    String product_code;
    String product_name;
    String product_model;
    String unit_price;
    String quantity;
    String contract_date;
    String estimated_date;
    String lodgement_date;
    String director;

    public String getContract_number() {
        return contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getSupply_center() {
        return supply_center;
    }

    public void setSupply_center(String supply_center) {
        this.supply_center = supply_center;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getContract_date() {
        return contract_date;
    }

    public void setContract_date(String contract_date) {
        this.contract_date = contract_date;
    }

    public String getEstimated_date() {
        return estimated_date;
    }

    public void setEstimated_date(String estimated_date) {
        this.estimated_date = estimated_date;
    }

    public String getLodgement_date() {
        return lodgement_date;
    }

    public void setLodgement_date(String lodgement_date) {
        this.lodgement_date = lodgement_date;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getSalesman_number() {
        return salesman_number;
    }

    public void setSalesman_number(String salesman_number) {
        this.salesman_number = salesman_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String salesman;
    String salesman_number;
    String gender;
    String age;
    String phone;
    public obIn(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        obIn ob = (obIn) o;
        return contract_number.equals(ob.contract_number) &&  product_model.equals(ob.product_model) &&salesman_number.equals(ob.salesman_number);
    }

    public boolean equalsTotal(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        obIn ob = (obIn) o;
        return contract_number.equals(ob.contract_number) &&  product_model.equals(ob.product_model) &&salesman_number.equals(ob.salesman_number)&&

                enterprise.equals(ob.enterprise)&&
                supply_center.equals(ob.enterprise)&&
                country.equals(ob.country)&&
                city.equals(ob.city)&&
                industry.equals(ob.industry)&&
                product_code.equals(ob.industry)&&
                product_name.equals(ob.product_name)&&
                unit_price.equals(ob.unit_price)&&
                quantity.equals(ob.quantity)&&
                contract_date.equals(ob.contract_date)&&
                estimated_date.equals(ob.estimated_date)&&
                lodgement_date.equals(ob.lodgement_date)&&
                director.equals(ob.director)&&
                salesman.equals(ob.salesman)&&
                gender.equals(ob.gender)&&
                age.equals(ob.age)&&
                phone.equals(ob.phone);
    }
    @Override
    public int hashCode() {
        return Objects.hash(contract_number, enterprise, supply_center, country, city, industry, product_code, product_name, product_model, unit_price, quantity, contract_date, estimated_date, lodgement_date, director, salesman, salesman_number, gender, age, phone);
    }
    public  boolean checkNull(){
        if (age==null)return false;
        if (country==null)return false;
        if (contract_date==null)return false;
        if (director==null)return false;
        if (contract_number==null)return false;
        if (enterprise==null)return false;
        if (estimated_date==null)return false;
        if (gender==null)return false;
        if (phone==null)return false;
        if (product_code==null)return false;
        if (industry==null) return false;
        if (product_model==null)return false;
        if (product_name==null)return false;
        if (quantity==null)return false;
        if (salesman==null)return false;
        if (salesman_number==null)return false;
        if (supply_center==null)return false;
        if (unit_price==null)return false;
        return true;
    }
    public  boolean checkContractNum(){
        return contract_number.length() == 10;
    }
//    public  boolean checkDate() throws ParseException {
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
//
//
//        if (ft.parse(contract_date).compareTo(ft.parse(estimated_date))>0){
//            return false;
//        }
//        if (ft.parse(contract_date).compareTo(ft.parse(estimated_date))>0){
//            return false;
//        }
//        return true;
//    }
    public  boolean checkProductCode(){
        return product_code.length() == 7;

    }
    public  boolean checkSalesmanNumber(){
        return salesman_number.length() == 8;
    }
    public  boolean checkPhone(){
        return phone.length() == 11;
    }
    public boolean checkType(){
        return unit_price.matches("[0-9]{1,}")&&quantity.matches("[0-9]{1,}")&&salesman_number.matches("[0-9]{1,}")
                &&age.matches("[0-9]{1,}")&&phone.matches("[0-9]{1,}")&&gender.matches("([a-zA-Z_]*)");

    }
}
