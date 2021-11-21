//WORKING
package model;

import Interface.DBQuery;
import controller.CustomerScreen;
import controller.CustomerUpdateForm;
import controller.ScheduleScreen; //CanDelete?
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    //Variables
    public int id;
    public String name;
    public String address;
    public String postalCode;
    public String phone;
    public String createDate;
    public String createdBy;
    public String lastUpdated;
    public String lastUpdatedBy;
    public int divisionID;
    public static boolean selectedCustomerDeletable = false; //CanDelete?
    public static boolean customerUpdate = false;
    public static Customer selectedCustomer;
    public static int updateCustomerId;
    public static boolean hasAppointmentVar = false; //CanDelete?
    public static int haId = 0; //CanDelete?
    public static boolean canInsert = false;
   // public static boolean isValid;
    public static String empty = "";
    public static List<Integer> customerIds = new ArrayList<>();


    //Constructor
    public Customer(int id, String name, String address, String postalCode, String phone, String createDate, String createdBy, String lastUpdated, String lastUpdatedBy, int divisionID )
    {
        setId(id);
        setName(name);
        setAddress(address);
        setPostalCode(postalCode);
        setPhone(phone);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdated(lastUpdated);
        setLastUpdatedBy(lastUpdatedBy);
        setDivisionID(divisionID);
    }

    public Customer(int id)
    {
        setId(id);
    }

    //SQL Statements
    public static ObservableList<Customer> getAllCustomers() {

        try {
            String customerGrab = "SELECT * FROM customers";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, customerGrab);
            PreparedStatement psC = DBQuery.getPreparedStatement();
            psC.execute();
            ResultSet rsC = psC.getResultSet();
            ObservableList<Customer> customerList = FXCollections.observableArrayList();
            while (rsC.next()) {
                Customer nextCustomer = new Customer(
                        rsC.getInt("Customer_ID"),
                        rsC.getString("Customer_Name"),
                        rsC.getString("Address"),
                        rsC.getString("Postal_Code"),
                        rsC.getString("Phone"),
                        rsC.getString("Create_Date"),
                        rsC.getString("Created_By"),
                        rsC.getString("Last_Update"),
                        rsC.getString("Last_Updated_By"),
                        rsC.getInt("Division_ID"));
                customerList.add(nextCustomer);
            }
            return customerList;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }

    public static Customer getSelectedCustomer() throws SQLException {
        try {
            //System.out.println(updateCustomerId);
            String selectedCustomerGrab = "SELECT * " +
                                          "FROM customers " +
                                          "WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, selectedCustomerGrab);
            PreparedStatement psSC = DBQuery.getPreparedStatement();
            psSC.setInt(1, updateCustomerId);
            psSC.execute();
            ResultSet rsSC = psSC.getResultSet();
            while (rsSC.next()) {
                Customer customer = new Customer(
                        rsSC.getInt("Customer_ID"),
                        rsSC.getString("Customer_Name"),
                        rsSC.getString("Address"),
                        rsSC.getString("Postal_Code"),
                        rsSC.getString("Phone"),
                        rsSC.getString("Create_Date"),
                        rsSC.getString("Created_By"),
                        rsSC.getString("Last_Update"),
                        rsSC.getString("Last_Updated_By"),
                        rsSC.getInt("Division_ID"));
                selectedCustomer = customer;
            }
            return selectedCustomer;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }

    public static void insertCustomer() throws SQLException {
        try {
            //System.out.println(CustomerUpdateForm.dbName +  CustomerUpdateForm.dbAddress +  CustomerUpdateForm.dbPostalCode +  CustomerUpdateForm.dbPhone  + CustomerUpdateForm.dbDivisionInt);
            String insertCustomer = "INSERT INTO customers ( Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_Id) " +
                                    "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertCustomer);
            PreparedStatement psSC = DBQuery.getPreparedStatement();
            psSC.setString(1, CustomerUpdateForm.dbName);
            psSC.setString(2, CustomerUpdateForm.dbAddress);
            psSC.setString(3, CustomerUpdateForm.dbPostalCode);
            psSC.setString(4, CustomerUpdateForm.dbPhone);
            psSC.setTimestamp(5, DateTimeConverter.easternDateTimeStamp() );
            psSC.setString(6, User.currentUser );
            psSC.setTimestamp(7, DateTimeConverter.easternDateTimeStamp() );
            psSC.setString(8, User.currentUser );
            psSC.setInt(9, CustomerUpdateForm.dbDivisionInt);
            psSC.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Check your SQL statement or variables");
        }
    }

    public static void customerUpdated() throws SQLException {
        try {
            String insertCustomer = "UPDATE customers " +
                                    "SET Last_Update = ? " +
                                    "WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertCustomer);
            PreparedStatement psCU = DBQuery.getPreparedStatement();
            psCU.setTimestamp(1, DateTimeConverter.easternDateTimeStamp() );
            psCU.setInt(2, CustomerScreen.updateCustomerId );
            psCU.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Check your SQL statement or variables");
        }
    }

    public static void deleteCustomer() throws SQLException {
        try{
            String delete = "DELETE FROM customers " +
                            "WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, delete);
            PreparedStatement psDA = DBQuery.getPreparedStatement();
            psDA.setInt(1, CustomerScreen.deleteCandidateId);
            psDA.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public static boolean canInsert(){

        if (CustomerUpdateForm.dbName == null || CustomerUpdateForm.dbAddress == null || CustomerUpdateForm.dbPostalCode == null || CustomerUpdateForm.dbPhone == null || CustomerUpdateForm.dbDivisionInt == 0 || CustomerUpdateForm.selectedDivision == null) {
            canInsert = false;
            //System.out.println("Working");
        }
        else if (CustomerUpdateForm.dbName.isEmpty() || CustomerUpdateForm.dbAddress.isEmpty() || CustomerUpdateForm.dbPostalCode.isEmpty() || CustomerUpdateForm.dbPhone.isEmpty()) {

        }
        else{
            canInsert = true;
            //System.out.println("Working");
        }
        return canInsert;

    }

    public static List getCustomerIds() {
        try {
            String idGrab = "SELECT Customer_ID FROM Customers ORDER BY Customer_ID ASC";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, idGrab);
            PreparedStatement psCI = DBQuery.getPreparedStatement();
            psCI.execute(); //Execute PreparedStatement
            ResultSet rsCI = psCI.getResultSet();
            ObservableList<User> allUsers = FXCollections.observableArrayList();
            while (rsCI.next()) {
                int nextId = rsCI.getInt("Customer_ID");
                customerIds.add(nextId);
            }
            return customerIds;
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }

    //Getters
    public int getCustomerID(){
        return id;
    }

    public String getCustomerName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public String getPhone(){
        return phone;
    }

    public String getCreateDate(){
        return createDate;
    }

    public String getCreatedBy(){
        return createdBy;
    }

    public String getLastUpdated(){
        return lastUpdated;
    }

    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    public int getDivisionID(){
        return divisionID;
    }

    // Setters
    public void setName (String newName){
        this.name = newName;
    }

    public void setAddress(String newAddress){
        this.address = newAddress;
    }

    public void setPostalCode (String newPostalCode){
        this.postalCode = newPostalCode;
    }

    public void setPhone (String newPhone){
        this.phone = newPhone;
    }

    public void setCreateDate (String newCreateDate){
        this.createDate = newCreateDate;
    }

    public void setCreatedBy (String newCreatedBy){
        this.createdBy = newCreatedBy;
    }

    public void setLastUpdated (String newLastUpdated){
        this.lastUpdated = newLastUpdated;
    }

    public void setLastUpdatedBy (String newLastUpdatedBy){
        this.lastUpdatedBy = newLastUpdatedBy;
    }

    public void setId (int newId){
        this.id = newId;
    }

    public void setDivisionID (int newDivisionID){
        this.divisionID = newDivisionID;
    }

    public String toString(){
        if(customerUpdate == false){
            return id+" "+name;
        }
        else{
            return id+" "+name;
        }
    }
}

/*
    public static boolean hasAppointment() throws SQLException {//this should be in appointment
        try{
            String hasAppointment = "SELECT Appointment_ID FROM appointments " +
                                    "WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, hasAppointment);
            PreparedStatement psHA = DBQuery.getPreparedStatement();
            //System.out.println(CustomerScreen.deleteCandidateId);
            psHA.setInt(1, CustomerScreen.deleteCandidateId);
            psHA.execute(); //Execute PreparedStatement
            System.out.println("Just inserted query");
            ResultSet rsHA = psHA.getResultSet();//This isn't working
            while (rsHA.next()) {//Make this output a Customer then getID() from that Customer
                while (rsHA.next()) {
                    Customer customer = new Customer(
                            rsHA.getInt("Customer_ID"),
                            rsHA.getString("Customer_Name"),
                            rsHA.getString("Address"),
                            rsHA.getString("Postal_Code"),
                            rsHA.getString("Phone"),
                            rsHA.getString("Create_Date"),
                            rsHA.getString("Created_By"),
                            rsHA.getString("Last_Update"),
                            rsHA.getString("Last_Updated_By"),
                            rsHA.getInt("Division_ID"));
                    int fromDB = customer.getCustomerID();
                    haId = fromDB;
                    System.out.println(fromDB);
                    System.out.println(haId);
                }

            }
            System.out.println(CustomerScreen.hasAppointmentVar);
            if (haId != 0){
                CustomerScreen.hasAppointmentVar = true;
                System.out.println(CustomerScreen.hasAppointmentVar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return hasAppointmentVar;
    }
*/

/*
    public static boolean hasAppointment() throws SQLException {
        try{
            String hasAppointment = "SELECT Appointment_ID FROM appointment " +
                                    "WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, hasAppointment);
            PreparedStatement psHA = DBQuery.getPreparedStatement();
            psHA.setInt(1, CustomerScreen.deleteCandidateId);
            psHA.execute(); //Execute PreparedStatement
            ResultSet rsHA = psHA.getResultSet();
            while (rsHA.next()) {
                Customer customer = new Customer(
                        rsHA.getInt("Customer_ID"),
                selectedCustomer = customer;


        }
        catch (Exception e) {
             e.printStackTrace();
             System.out.println("Error on Building Data");
             return hasAppointment;
    }
    }
    }
    */
