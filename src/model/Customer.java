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
/**
 This is the Customer class.  This class sets the parameters for customers.
 */
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

    /**
     * This is the customer constructor method.
     * @param id customer id
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postalCode
     * @param phone customer phone
     * @param createDate customer createDate
     * @param createdBy customer createdBy
     * @param lastUpdated customer lastUpdated
     * @param lastUpdatedBy customer lastUpdatedBy
     * @param divisionID customer divisionId
     */
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

    /**
     * This is the overloaded Customer constructor.
     * @param id customer id
     */
    public Customer(int id)
    {
        setId(id);
    }

    /**
     * This is the getAllCustomers method.  This method sends a SELECT statement to the database and returns a list of
     * all customers as an ObservableList.
     * @return Returns customerList as an ObservableList.
     */
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

    /**
     * This is the getSelectedCustomer method.  This method sends a SELECT statemetn to the database that returns the
     * customer that matches a provided customer Id.
     * @return Returns Customer selectedCustomer.
     * @throws SQLException
     */
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

    /**
     * This is the insertCustomer method.  This method sends an INSERT INTO statement to the database that creates a
     * customer based on user provided variables.
     * @throws SQLException
     */
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

    /**
     * This is the customerUpdate method.  This method sends an UPDATE statement into the database that changes the
     * last update field to a current Timestamp.
     * @throws SQLException
     */
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

    /**
     * This is the deleteCustomer method.  This method sends a DELETE FROM statemetn to the database that deleted the
     * selected customer based on a user provided customer id.
     * @throws SQLException
     */
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

    /**
     * This is the canInsert method.  This method checks for null or 0 values in the variables for the user inputs in
     * the customer update form.  It returns canInsert of true if no null/0 values.
     * @return Returns boolean value for canInsert.
     */
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

    /**
     * This is the getCustomerIds method.  This method sends a SELECT statement to the database that returns all
     * customer ids in ascending order.
     * @return Returns a list of customer ids as customerIds.
     */
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

    /**
     * This is the getCustomerID method.  This is the getter for customer id.
     * @return Returns id as int.
     */
    public int getCustomerID(){
        return id;
    }

    /**
     *This is the getCustomerName method.  This is the getter for customer name.
     * @return Returns name as String
     */
    public String getCustomerName(){
        return name;
    }

    /**
     *This is the getAddress method.  This is the getter method for customer address.
     * @return Returns address as String.
     */
    public String getAddress(){
        return address;
    }

    /**
     *This is the getPostalCode method.  This is the getter method for customer postalCode.
     * @return Returns postalCode as String.
     */
    public String getPostalCode(){
        return postalCode;
    }

    /**
     *This is the getPhone method.  This is the getter method for customer phone.
     * @return Returns phone as String.
     */
    public String getPhone(){
        return phone;
    }

    /**
     *This is the getCreateDate method.  This is the getter method for customer create date.
     * @return Returns createDate as String.
     */
    public String getCreateDate(){
        return createDate;
    }

    /**
     *This is the getCreatedBy method.  Ths is the getter method for customer created by.
     * @return Returns createdBy as String.
     */
    public String getCreatedBy(){
        return createdBy;
    }

    /**
     *This is the getLastUpdated method.  This is the getter method for customer last updated.
     * @return Returns lastUpdated as String.
     */
    public String getLastUpdated(){
        return lastUpdated;
    }

    /**
     *This is the getLastUpdatedBy method.  This is the getter method for customer last updated by.
     * @return Returns lastUpdatedBy as String.
     */
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    /**
     *This is the getDivisionId method.  This is the getter method for customer division id.
     * @return Returns division id as int.
     */
    public int getDivisionID(){
        return divisionID;
    }

    /**
     * This is the setName method.  This is the setter for name.
     * @param newName Takes String newName.
     */
    public void setName (String newName){
        this.name = newName;
    }

    /**
     * This is the setAddress method.  This is the setter for address.
     * @param newAddress Takes String newAddress.
     */
    public void setAddress(String newAddress){
        this.address = newAddress;
    }

    /**
     * This is the setPostalCode method.  This is the setter for postal code.
     * @param newPostalCode Takes String newPostalCode.
     */
    public void setPostalCode (String newPostalCode){
        this.postalCode = newPostalCode;
    }

    /**
     * This is the setPhone method.  This is the setter method for phone.
     * @param newPhone Takes String newPhone.
     */
    public void setPhone (String newPhone){
        this.phone = newPhone;
    }

    /**
     * This is the setCreateDate method.  This is the setter method for createDate.
     * @param newCreateDate Takes String newCreateDate.
     */
    public void setCreateDate (String newCreateDate){
        this.createDate = newCreateDate;
    }

    /**
     *This is the setCreatedBy method.  This is the setter method for createdBy.
     * @param newCreatedBy Takes String newCreatedBy
     */
    public void setCreatedBy (String newCreatedBy){
        this.createdBy = newCreatedBy;
    }

    /**
     *This is the setLastUpdated method.  This is the setter method for lastUpdated.
     * @param newLastUpdated Takes String newLastUpdated.
     */
    public void setLastUpdated (String newLastUpdated){
        this.lastUpdated = newLastUpdated;
    }

    /**
     *This is the setLastUpdatedBy method.  This is the setter method for lastUpdatedBy.
     * @param newLastUpdatedBy Takes String newLastUpdatedBy.
     */
    public void setLastUpdatedBy (String newLastUpdatedBy){
        this.lastUpdatedBy = newLastUpdatedBy;
    }

    /**
     *This is the setId method.  This is the setter method for id.
     * @param newId Takes int newId.
     */
    public void setId (int newId){
        this.id = newId;
    }

    /**
     *This is the setDivisionID method.  This is the setter method for divisionID.
     * @param newDivisionID Takes int newDivisionID
     */
    public void setDivisionID (int newDivisionID){
        this.divisionID = newDivisionID;
    }

    /**
     * This is the toString method.  This method overrides the normat toString method.
     * @return Returns a String.
     */
    public String toString(){
        if(customerUpdate == false){
            return id+" "+name;
        }
        else{
            return id+" "+name;
        }
    }
}