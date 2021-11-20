/*
//STILL NEED WORK
package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentUpdateForm implements Initializable {
    @FXML
    private Label labelAppointment;
    @FXML
    public ComboBox<Contact> comboBoxContact;
    @FXML
    public ComboBox<User> comboBoxUser;
    @FXML
    public ComboBox<Customer> comboBoxCustomer;
    @FXML
    public ComboBox<String> comboBoxStartTime;
    @FXML
    public ComboBox<String> comboBoxEndTime;
    @FXML
    public TextField textFieldAppointmentId;
    @FXML
    public TextField textFieldTitle;
    @FXML
    public TextField textFieldDescription;
    @FXML
    public TextField textFieldLocation;
    @FXML
    public TextField textFieldType;
    @FXML
    public static Appointment selectedAppointment;
    public DatePicker datePickerDate;
    public static int newContactId;
    public static int newUserId;
    public static int newCustomerId;
    public static int contactId;
    public static int userId;
    public static int customerId;
    public static String newStartTime;
    public static String newEndTime;
    public static String date;
    public static String parsedStart;
    public static String parsedEnd;
    public static String appointmentId;
    public static String type;
    public static String title;
    public static String description;
    public static String location;
    public static User user;
    public static Customer customer;
    public static Contact contact;

    public static String updateAppointmentID;//going to need to run a query for a different type of appointment that matches the DB
    public static String dbType;
    public static String dbTitle;
    public static String dbLocation;
    public static String dbDescription;
    public static String dbParsedStart;
    public static String dbParsedEnd;
    public static String dbContactId;
    public static String dbCustomerId;
    public static String dbUserId;




    public static boolean contactFlag = false;
    public static boolean userFlag = false;
    public static boolean customerFlag = false;
    public static boolean startFlag = false;
    public static boolean endFlag = false;
    public static boolean dateFlag = false;
    public static boolean update = false;

    private Stage stage;
    private Scene scene;
    private Parent root;
    ObservableList<String> startList = FXCollections.observableArrayList("08:00:00","08:05:00","08:10:00","08:15:00", "08:20:00","08:25:00","08:30:00","08:35:00", "08:40:00","08:45:00","08:50:00","08:55:00",
            "09:00:00","09:05:00","09:10:00","09:15:00", "09:20:00","09:25:00","09:30:00","09:35:00", "09:40:00","09:45:00","09:50:00","09:55:00",
            "10:00:00","10:05:00","10:10:00","10:15:00", "10:20:00","10:25:00","10:30:00","10:35:00", "10:40:00","10:45:00","10:50:00","10:55:00",
            "11:00:00","11:05:00","11:10:00","11:15:00", "11:20:00","11:25:00","11:30:00","11:35:00", "11:40:00","11:45:00","11:50:00","11:55:00",
            "12:00:00","12:05:00","12:10:00","12:15:00", "12:20:00","12:25:00","12:30:00","12:35:00", "12:40:00","12:45:00","12:50:00","12:55:00",
            "13:00:00","13:05:00","13:10:00","13:15:00", "13:20:00","13:25:00","13:30:00","13:35:00", "13:40:00","13:45:00","13:50:00","13:55:00",
            "14:00:00","14:05:00","14:10:00","14:15:00", "14:20:00","14:25:00","14:30:00","14:35:00", "14:40:00","14:45:00","14:50:00","14:55:00",
            "15:00:00","15:05:00","15:10:00","15:15:00", "15:20:00","15:25:00","15:30:00","15:35:00", "15:40:00","15:45:00","15:50:00","15:55:00",
            "16:00:00","16:05:00","16:10:00","16:15:00", "16:20:00","16:25:00","16:30:00","16:35:00", "16:40:00","16:45:00","16:50:00","16:55:00",
            "17:00:00","17:05:00","17:10:00","17:15:00", "17:20:00","17:25:00","17:30:00","17:35:00", "17:40:00","17:45:00","17:50:00","17:55:00",
            "18:00:00","18:05:00","18:10:00","18:15:00", "18:20:00","18:25:00","18:30:00","18:35:00", "18:40:00","18:45:00","18:50:00","18:55:00",
            "19:00:00","19:05:00","19:10:00","19:15:00", "19:20:00","19:25:00","19:30:00","19:35:00", "19:40:00","19:45:00","19:50:00","19:55:00",
            "20:00:00","20:05:00","20:10:00","20:15:00", "20:20:00","20:25:00","20:30:00","20:35:00", "20:40:00","20:45:00","20:50:00","20:55:00",
            "21:00:00","21:05:00","21:10:00","21:15:00", "21:20:00","21:25:00","21:30:00","21:35:00", "21:40:00","21:45:00","21:50:00","21:55:00");
    ObservableList<String> endList = FXCollections.observableArrayList("08:05:00","08:10:00","08:15:00", "08:20:00","08:25:00","08:30:00","08:35:00", "08:40:00","08:45:00","08:50:00","08:55:00",
            "09:00:00","09:05:00","09:10:00","09:15:00", "09:20:00","09:25:00","09:30:00","09:35:00", "09:40:00","09:45:00","09:50:00","09:55:00",
            "10:00:00","10:05:00","10:10:00","10:15:00", "10:20:00","10:25:00","10:30:00","10:35:00", "10:40:00","10:45:00","10:50:00","10:55:00",
            "11:00:00","11:05:00","11:10:00","11:15:00", "11:20:00","11:25:00","11:30:00","11:35:00", "11:40:00","11:45:00","11:50:00","11:55:00",
            "12:00:00","12:05:00","12:10:00","12:15:00", "12:20:00","12:25:00","12:30:00","12:35:00", "12:40:00","12:45:00","12:50:00","12:55:00",
            "13:00:00","13:05:00","13:10:00","13:15:00", "13:20:00","13:25:00","13:30:00","13:35:00", "13:40:00","13:45:00","13:50:00","13:55:00",
            "14:00:00","14:05:00","14:10:00","14:15:00", "14:20:00","14:25:00","14:30:00","14:35:00", "14:40:00","14:45:00","14:50:00","14:55:00",
            "15:00:00","15:05:00","15:10:00","15:15:00", "15:20:00","15:25:00","15:30:00","15:35:00", "15:40:00","15:45:00","15:50:00","15:55:00",
            "16:00:00","16:05:00","16:10:00","16:15:00", "16:20:00","16:25:00","16:30:00","16:35:00", "16:40:00","16:45:00","16:50:00","16:55:00",
            "17:00:00","17:05:00","17:10:00","17:15:00", "17:20:00","17:25:00","17:30:00","17:35:00", "17:40:00","17:45:00","17:50:00","17:55:00",
            "18:00:00","18:05:00","18:10:00","18:15:00", "18:20:00","18:25:00","18:30:00","18:35:00", "18:40:00","18:45:00","18:50:00","18:55:00",
            "19:00:00","19:05:00","19:10:00","19:15:00", "19:20:00","19:25:00","19:30:00","19:35:00", "19:40:00","19:45:00","19:50:00","19:55:00",
            "20:00:00","20:05:00","20:10:00","20:15:00", "20:20:00","20:25:00","20:30:00","20:35:00", "20:40:00","20:45:00","20:50:00","20:55:00",
            "21:00:00","21:05:00","21:10:00","21:15:00", "21:20:00","21:25:00","21:30:00","21:35:00", "21:40:00","21:45:00","21:50:00","21:55:00", "22:00:00");

    public void submitButton(ActionEvent event) throws IOException, SQLException {
        if (update == false){
            title = textFieldTitle.getText();
            description = textFieldDescription.getText();
            location = textFieldLocation.getText();
            type = textFieldType.getText();
            parsedStart = date + " " + newStartTime;
            parsedEnd = date + " " + newEndTime;
            Appointment.insertAppointment();

            root = FXMLLoader.load(getClass().getResource( "/view/ScheduleScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
        //
        }

        root = FXMLLoader.load(getClass().getResource( "/view/ScheduleScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void resetButton(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource( "/view/AppointmentUpdateForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //Should work as is
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBoxContact.setItems(Contact.getAllContacts());
        comboBoxContact.valueProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observableValue, Contact contact, Contact t1) {
                newContactId = t1.getContactId();
                contactFlag = true;
            }
        });

        comboBoxUser.setItems(User.getAllUsers());
        comboBoxUser.valueProperty().addListener(new ChangeListener<User>() {
             @Override
             public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {
                 newUserId = t1.getId();
                 userFlag = true;
             }
        });

        comboBoxCustomer.setItems(Customer.getAllCustomers());
        comboBoxCustomer.valueProperty().addListener(new ChangeListener<Customer>(){
            @Override
            public void changed(ObservableValue<? extends Customer> observableValue, Customer customer, Customer t1) {
                newCustomerId = t1.getCustomerID();
                customerFlag = true;
            }
        });

        comboBoxStartTime.setItems(startList);//8am to 955pm
        comboBoxStartTime.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                newStartTime = t1;
                System.out.println(newStartTime);
                startFlag = true;
            }
        });

        comboBoxEndTime.setItems(endList);//805am to 1000pm, Create exception not allowing an appointment end time before the start time
        comboBoxEndTime.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                newEndTime = t1;
                System.out.println(newEndTime);
                endFlag = true;
            }
        });
        datePickerDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                date = t1.toString();
                dateFlag = true;
            }
        });

        if (update == true){//new
            labelAppointment.setText("APPOINTMENT UPDATE FORM");
            //selectedAppointment = ScheduleScreen.selectedAppointment;//Good
            //appointmentId = String.valueOf(selectedAppointment.getAppointmentId());
            appointmentId = String.valueOf(selectedAppointment.getAppointmentId());
            description = selectedAppointment.getDescription();
            title = selectedAppointment.getTitle();
            type = selectedAppointment.getType();
            location = selectedAppointment.getLocation();
            LocalDate date = LocalDate.parse(selectedAppointment.getStart().toString().substring(0, 4) + "-" + selectedAppointment.getStart().toString().substring(5,7) + "-" + selectedAppointment.getStart().toString().substring(8, 10));
            String start = selectedAppointment.getStart().toString().substring(11, 19);//   //
            String end = selectedAppointment.getEnd().toString().substring(11, 19);
            textFieldAppointmentId.setText(appointmentId); //Appointment ID
            textFieldDescription.setText(description); //Description
            textFieldTitle.setText(title); //Title
            textFieldType.setText(type); //Type
            textFieldLocation.setText(location); //Location
            datePickerDate.setValue(date); //Date

            contactId = selectedAppointment.getContactId();
            int contactIndex = Contact.getContactIds().indexOf(contactId);
            userId = selectedAppointment.getUserId();
            int userIndex = User.getUserIds().indexOf(userId);//problem here, null
            customerId = selectedAppointment.getCustomerId();
            int customerIndex = Customer.getCustomerIds().indexOf(customerId);
            comboBoxContact.getSelectionModel().select(contactIndex);//get all ID as list, get index of selected id, set combobox to that index
            comboBoxCustomer.getSelectionModel().select(customerIndex);
            comboBoxUser.getSelectionModel().select(userIndex);


        }else{
            labelAppointment.setText("NEW APPOINTMENT FORM");
        }

    }

}
*/
