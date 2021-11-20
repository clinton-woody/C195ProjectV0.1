//STILL NEED WORK
package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import java.util.ResourceBundle;

public class AppointmentUpdateForm2 implements Initializable {
    @FXML
    private Label labelAppointment;
    @FXML
    public ComboBox<Contact> comboBoxContact;
    @FXML
    public ComboBox<User> comboBoxUser;
    @FXML
    public ComboBox<Customer> comboBoxCustomer;
    @FXML
    public ComboBox<String> comboBoxStartTimeHour;
    @FXML
    public ComboBox<String> comboBoxStartTimeMinute;
    @FXML
    public ComboBox<String> comboBoxEndTimeHour;
    @FXML
    public ComboBox<String> comboBoxEndTimeMinute;
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
    public static String newStartHour;
    public static String newStartMinute;
    public static String newEndHour;
    public static String newEndMinute;
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
    private ObservableList<String> hourList = FXCollections.observableArrayList("01","02","03","04", "05","06",
            "07","08","09","10","11","12","13","14","15","16", "17","18","19","20","21","22","23","00");
    private ObservableList<String> minuteList = FXCollections.observableArrayList("00","01","02","03","04","05",
            "06","07","08", "09","10","11","12","13","14","15","16", "17","18","19","20", "21","22","23","24","25","26",
            "27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47",
            "48","49","50","51","52","53","54","55","56","57","58","59");
    ObservableList<String> startHourList = hourList;
    ObservableList<String> endHourList = hourList;
    ObservableList<String> startMinuteList = minuteList;
    ObservableList<String> endMinuteList = minuteList;



    public void submitButton(ActionEvent event) throws IOException, SQLException {
        if (update == false){
            title = textFieldTitle.getText();
            description = textFieldDescription.getText();
            location = textFieldLocation.getText();
            type = textFieldType.getText();
            //parsedStart = date + " " + newStartTime;
            //parsedEnd = date + " " + newEndTime;
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

        root = FXMLLoader.load(getClass().getResource( "/view/AppointmentUpdateForm2.fxml"));
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

        comboBoxStartTimeHour.setItems(startHourList);//8am to 955pm
        comboBoxStartTimeHour.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                newStartHour = t1;
                System.out.println(newStartHour);
                startFlag = true;
            }
        });

        comboBoxStartTimeMinute.setItems(startMinuteList);//8am to 955pm
        comboBoxStartTimeMinute.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                newStartMinute = t1;
                System.out.println(newStartMinute);
                startFlag = true;
            }
        });

        comboBoxEndTimeHour.setItems(endHourList);//805am to 1000pm, Create exception not allowing an appointment end time before the start time
        comboBoxEndTimeHour.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                newEndHour = t1;
                System.out.println(newEndHour);
                endFlag = true;
            }
        });

        comboBoxEndTimeMinute.setItems(endMinuteList);//805am to 1000pm, Create exception not allowing an appointment end time before the start time
        comboBoxEndTimeMinute.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                newEndMinute = t1;
                System.out.println(newEndMinute);
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

