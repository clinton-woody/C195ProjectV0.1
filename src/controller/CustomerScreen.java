package controller;

//IMPORT STATEMENTS
import javafx.collections.ObservableList; //CanDelete?
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Messages;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
This is the CustomerScreen class.  This class is the controller of the Customer Screen.
 */
public class CustomerScreen implements Initializable {
    //CLASS VARIABLES
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> createDateColumn;
    @FXML
    private TableColumn<Customer, String> createdByColumn;
    @FXML
    private TableColumn<Customer, String> lastUpdatedColumn;
    @FXML
    private TableColumn<Customer, String> lastUpdatedByColumn;
    @FXML
    private TableColumn<Customer, Integer> divisionIDColumn;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static Customer selectedCustomer;
    public static int selectedCustomerId;
    public static int deleteCandidateId;
    public static int deleteConfirmId;
    public static int confirmDeleteId;
    public static int updateCustomerId;
    public static boolean canDeleteCustomer = false;
    public static boolean hasAppointment = false;

    /**
     This is the toScheduleScreen method.  This class switches the program from the Customer Screen to the Schedule
     Screen.
     @param event This method is executed based on the action of pressing the Schedule button.
     */
    public void toScheduleScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/ScheduleScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     This is the toNewCustomerForm method.  This class switches the program from the Customer Screen to the Customer
     Update Form in it's new customer configuration.
     @param event This method is executed based on the action of pressing the New button.
     */
    public void toNewCustomerForm(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/CustomerUpdateForm.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     This is the toUpdateCustomerForm method.  This class takes the selected appointment id and passes it to the
     customer update form in it's customer update configuration.
     @param event This method is executed based on the action of pressing the Update button.
     */
    public void toUpdateCustomerForm(ActionEvent event) throws IOException, SQLException {
        Customer.customerUpdate = true;
        Customer selectedCustomerCells = customerTableView.getSelectionModel().getSelectedItem();
        int updateCustomerId = selectedCustomerCells.getCustomerID();
        Customer.updateCustomerId = updateCustomerId;
        CustomerUpdateForm.updateCustomerId = updateCustomerId;
        selectedCustomer = Customer.getSelectedCustomer();
        root = FXMLLoader.load(getClass().getResource("/view/CustomerUpdateForm.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     This is the deleteButton method.  This class is used to delete customers.  When first initiated the customer
     selected in the customer table view is grabbed and the customer id is searched for in the appointment database
     table to see if any appointments are assigned to the selected customer.  If there is no appointment associated with
     the customer id then the customer selected is marked safe to delete. When initiated a second time if the customer
     id selected is the same customer id that was selected during the first button initiation the selected customer is
     deleted.
     @param event This method is executed based on the action of pressing the Delete button.
     */
    public void deleteButton(ActionEvent event) throws IOException, SQLException {
        if(canDeleteCustomer == false){
            Customer selectedCustomerCells = customerTableView.getSelectionModel().getSelectedItem();
            deleteCandidateId = selectedCustomerCells.getCustomerID();
            Appointment.hasAppointment();
            if(hasAppointment == true){
                //System.out.println(hasAppointment);
                //System.out.println("hasAppointment works");
                canDeleteCustomer = false;
                Messages.messageSix();
                hasAppointment = false;
                //System.out.println(hasAppointment);
            }
            else{
                canDeleteCustomer = true;
                Messages.messageFour();
                deleteConfirmId = deleteCandidateId;
            }
        }
        else{
            if(deleteCandidateId == deleteConfirmId) {
                Customer selectedCustomerCells = customerTableView.getSelectionModel().getSelectedItem();
                confirmDeleteId = selectedCustomerCells.getCustomerID();
                if (deleteCandidateId == confirmDeleteId) {
                    Customer.deleteCustomer();
                    Messages.messageFive();
                    deleteCandidateId = 0;
                    canDeleteCustomer = false;
                    customerTableView.setItems(Customer.getAllCustomers());
                }
                else {
                    deleteCandidateId = confirmDeleteId;
                    Messages.messageFour();
                }
            }
            else{
                canDeleteCustomer = false;
            }
        }
    }

    /**
     This is the initialize method.  This class imports the contents of the database customer table into the customer
     table view.
     @param resourceBundle Store texts and components that are locale sensitive.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerTableView.setItems(Customer.getAllCustomers());
    }
}