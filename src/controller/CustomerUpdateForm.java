//WORKING
package controller;

import Interface.DBQuery;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Stream; //CanDelete?
import java.util.List; //CanDelete?
import java.util.Optional; //CanDelete?
import java.util.OptionalInt;import static java.lang.Integer.parseInt; //CanDelete?

import javafx.beans.value.ObservableListValue; //CanDelete?

public class CustomerUpdateForm implements Initializable {
    /*
    VARIABLES
     */
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label labelSceneTitle;
    @FXML
    private TextField textFieldCustomerName;
    @FXML
    private TextField textFieldAddress;
    @FXML
    private TextField textFieldPostalCode;
    @FXML
    private TextField textFieldPhoneNumber;
    @FXML
    private TextField textFieldCustomerId;
    @FXML
    private ComboBox comboBoxFirstLevelDivision;
    @FXML
    private ComboBox comboBoxCountry;
    public static int selectedID = 99;//may be able to combine with selectedCountryId
    public static String dbName;
    public static String dbAddress;
    public static String dbPostalCode;
    public static String dbPhone;
    public static String name;
    public static String address;
    public static String postalCode;
    public static String phone;
    public static int updateCustomerId;
    public static int selectedDivisionId;
    public static int selectedCountryId;//Can Delete?
    public static ObservableList<model.Country> selectedCountry;//Can Delete?
    public static FirstLevelDivision selectedDivision;//Can Delete?
    public static ObservableList allCountries = Country.getAllCountries();//Can Delete?
    public static boolean divisionUpdate = false;
    public static boolean canInsert = false;
    public static int dbDivisionInt;

    /*
    METHODS
     */
    public void submitButton(ActionEvent event) throws IOException, SQLException { //A
        if (Customer.customerUpdate == true) { //AA

                dbName = textFieldCustomerName.getText(); //AAA
                dbAddress = textFieldAddress.getText(); //AAA
                dbPostalCode = textFieldPostalCode.getText(); //AAA
                dbPhone = textFieldPhoneNumber.getText(); //AAA
                canInsert = false;
                canInsert = Customer.canInsert();
                if (canInsert == true) {
                    if (dbName != name) { //AAC
                        try {
                            String insertCustomer = "UPDATE customers " + //AAC
                                                    "SET Customer_Name = ? " + //AAC
                                                    "WHERE Customer_ID = ?"; //AAC

                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertCustomer); //AAC
                            PreparedStatement psCU = DBQuery.getPreparedStatement(); //AAC
                            psCU.setString(1, dbName); //AAC
                            psCU.setInt(2, updateCustomerId); //AAC
                            psCU.execute(); //AAC
                        } catch (Exception e) {
                            e.printStackTrace(); //AAC
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Customer.customerUpdate = false;
                    }
                    if (dbAddress != address) { //AAD
                        try {
                            String insertCustomer = "UPDATE customers " + //AAD
                                                    "SET Address = ? " + //AAD
                                                    "WHERE Customer_ID = ?"; //AAD
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertCustomer); //AAD
                            PreparedStatement psCU = DBQuery.getPreparedStatement(); //AAD
                            psCU.setString(1, dbAddress); //AAD
                            psCU.setInt(2, updateCustomerId); //AAD
                            psCU.execute(); //AAD
                        } catch (Exception e) {
                            e.printStackTrace(); //AAD
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Customer.customerUpdate = false;
                    }
                    if (dbPostalCode != postalCode) { //AAE
                        try {
                            String insertCustomer = "UPDATE customers " + //AAE
                                                    "SET Postal_Code = ? " + //AAE
                                                    "WHERE Customer_ID = ?"; //AAE
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertCustomer); //AAE
                            PreparedStatement psCU = DBQuery.getPreparedStatement(); //AAE
                            psCU.setString(1, dbPostalCode); //AAE
                            psCU.setInt(2, updateCustomerId); //AAE
                            psCU.execute(); //AAE
                        } catch (Exception e) {
                            e.printStackTrace(); //AAE
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Customer.customerUpdate = false;
                    }
                    if (dbPhone != phone) { //AAF
                        try {
                            String insertCustomer = "UPDATE customers " + //AAF
                                                    "SET Phone = ? " + //AAF
                                                    "WHERE Customer_ID = ?"; //AAF
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertCustomer); //AAF
                            PreparedStatement psCU = DBQuery.getPreparedStatement(); //AAF
                            psCU.setString(1, dbPhone); //AAF
                            psCU.setInt(2, updateCustomerId); //AAF
                            psCU.execute(); //AAF
                        } catch (Exception e) {
                            e.printStackTrace(); //AAF
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Customer.customerUpdate = false;
                    }
                    if (FirstLevelDivision.divisionId != dbDivisionInt) { //AAG
                        try {
                            String insertCustomer = "UPDATE customers " +  //AAG
                                                    "SET Division_ID = ? " + //AAG
                                                    "WHERE Customer_ID = ?"; //AAG
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertCustomer); //AAG
                            PreparedStatement psCU = DBQuery.getPreparedStatement(); //AAG
                            psCU.setInt(1, dbDivisionInt); //AAG
                            psCU.setInt(2, updateCustomerId); //AAG
                            psCU.execute(); //AAG
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace(); //AAG
                            System.out.println("Check your SQL statement or variables"); //AAG
                        }
                        Customer.customerUpdated();
                        Customer.customerUpdate = false;
                        canInsert = false;
                        root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                    else
                    {
                        Customer.customerUpdate = false;
                        canInsert = false;
                        root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
                else
                {
                    Messages.errorSix();
                    canInsert = false;
                }
        }
        else //if (Customer.customerUpdate == false)
        {
            dbName = textFieldCustomerName.getText();
            dbAddress = textFieldAddress.getText();
            dbPostalCode = textFieldPostalCode.getText();
            dbPhone = textFieldPhoneNumber.getText();
            canInsert = false;
            canInsert = Customer.canInsert();

            if (canInsert == true){
                Customer.insertCustomer();
                Customer.customerUpdate = false;
                canInsert = false;
                root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                Messages.errorSix();

            }
        }
    }

    public void resetButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource( "/view/CustomerUpdateForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void cancelButton(ActionEvent event) throws IOException {
        Customer.customerUpdate = false;
        root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
    INITIALIZE
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxCountry.setItems(Country.getAllCountries());
        comboBoxCountry.valueProperty().addListener((ChangeListener<Country>) (observableValue, lastCountry, currentCountry) -> {
            selectedID = currentCountry.getId();
            selectedDivision = null;
            comboBoxFirstLevelDivision.setItems(FirstLevelDivision.getAllDivision()); //Works to here
            comboBoxFirstLevelDivision.valueProperty().addListener((ChangeListener<FirstLevelDivision>) (observableValue1, firstLevelDivision, t1) -> {
                dbDivisionInt = t1.getId();//this goes null when the country changes
                selectedDivision = t1;
                divisionUpdate = true;
            });
        });
        if(Customer.customerUpdate==true) {
            labelSceneTitle.setText("CUSTOMER UPDATE FORM");
            Customer selectedCustomer = CustomerScreen.selectedCustomer;
            name = selectedCustomer.getCustomerName();
            textFieldCustomerName.setText(name);
            address = selectedCustomer.getAddress();
            textFieldAddress.setText(address);
            postalCode = selectedCustomer.getPostalCode();
            textFieldPostalCode.setText(postalCode);
            phone = selectedCustomer.getPhone();
            textFieldPhoneNumber.setText(phone);
            String customerId = String.valueOf(selectedCustomer.getCustomerID());
            textFieldCustomerId.setText(customerId);
            int divisionId = selectedCustomer.getDivisionID();
            FirstLevelDivision.divisionId = divisionId;
            int countryId = FirstLevelDivision.getCountryID();
            int c1 = 1;//US: maybe dont need
            int c2 = 2;//UK: maybe dont need
            int c3 = 3;//Canada: maybe dont need
            int divIndexUs = divisionId - 1;
            int divIndexCan = divisionId - 60;
            int divIndexUk = divisionId - 101;
            if (countryId == 1){
                comboBoxCountry.getSelectionModel().select(0);
            }
            if (countryId == 2){
                comboBoxCountry.getSelectionModel().select(1);
            }
            if (countryId == 3){
                comboBoxCountry.getSelectionModel().select(2);
            }
            if (countryId == 1){
                comboBoxFirstLevelDivision.getSelectionModel().select(divIndexUs);
            }else if(countryId == 2){
                comboBoxFirstLevelDivision.getSelectionModel().select(divIndexUk);
            }else{
                comboBoxFirstLevelDivision.getSelectionModel().select(divIndexCan);
            }
        }else{
            labelSceneTitle.setText("NEW CUSTOMER FORM");
        }
    }
}