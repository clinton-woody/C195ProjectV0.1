package controller;

import Interface.DBQuery;
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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 This is the AppointmentUpdateForm2 class.  This class is the controller for the Appointment Update Form.
 */
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
    @FXML
    public DatePicker datePickerDate;
    //FLAGS
    public static boolean contactFlag = false;
    public static boolean userFlag = false;
    public static boolean customerFlag = false;
    public static boolean startFlag = false;
    public static boolean endFlag = false;
    public static boolean dateFlag = false;
    public static boolean update = false;
    public static boolean isValid = true;
    public static boolean startEndMismatch = false;
    //INITIAL VARIABLES
    public static String appointmentId = "0";
    public static String type;
    public static String title;
    public static String description;
    public static String location;
    public static LocalDate date;
    public static LocalDateTime selectedStart;
    public static LocalDateTime selectedEnd;
    public static int contactId;
    public static int userId;
    public static int customerId;
    public static final String ZEROSEC = "00";
    public static String startHr;
    public static String startMn;
    public static String endHr;
    public static String endMn;
//    public static Timestamp initialStart = Timestamp.valueOf(date.toString() + " " + startHr + startMn + ZEROSEC);
//    public static Timestamp initialEnd = Timestamp.valueOf(date.toString() + " " + endHr + endMn + ZEROSEC);
    //UPDATED VARIABLES
    public static String dbType;
    public static String dbTitle;
    public static String dbLocation;
    public static String dbDescription;
    public static LocalDate dbDate;
    public static String dbDateString;
    public static int dbContactId;
    public static int dbUserId;
    public static int dbCustomerId;
    public static String dbStartHr;
    public static String dbStartMn;
    public static String dbEndHr;
    public static String dbEndMn;
    public static Timestamp dbStart;
    public static Timestamp dbEnd;
    public static int newContactId;
    public static int newUserId;
    public static int newCustomerId;
    public static String dateString;
    public static String parsedStartTime;
    public static String parsedEndTime;
    public static String parsedStartDateTime;
    public static String parsedEndDateTime;
    public static Timestamp candidateStart;
    public static Timestamp candidateEnd;
    public static User user;
    public static Customer customer;
    public static Contact contact;
    public static String updateAppointmentID;
    public static String stichStartHr;
    public static String stichStartMn;
    public static String stichEndHr;
    public static String stichEndMn;
    public static String dbParsedStart;
    public static String dbParsedEnd;
    public static boolean canInsert = false;
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

    /**
     This is the submitButton method.  If in new appointment configuration initiating this button inserts a new
     appointment into the appointment table.  If in the update appointment configuration initiating this button updates
     the selected appointment information that is changed within this form.
     @param event This method is executed based on the action of pressing the submit button.
     */
    public void submitButton(ActionEvent event) throws IOException, SQLException {//only new isnt working
        parsedStartTime = stichStartHr + stichStartMn + ZEROSEC;//put if check for null of stichStartHr,stichStartMn,stichEndHr,stichEndMn,dateString here that encompases all of Submit() method
        parsedEndTime = stichEndHr + stichEndMn + ZEROSEC;//Make else of above output to blank error
        parsedStartDateTime = dateString + " " + parsedStartTime;
        parsedEndDateTime = dateString + " " + parsedEndTime;
        //System.out.println(parsedStartDateTime);//Testing only
        //System.out.println(parsedEndDateTime);//Testing only
        candidateStart = Timestamp.valueOf(parsedStartDateTime); //#error
        candidateEnd = Timestamp.valueOf(parsedEndDateTime);
        isValid = DateTimeHandler.validTime(candidateStart, candidateEnd, newCustomerId, appointmentId);//#need version for new and update because new doesn't have an appointment id
        startEndMismatch = DateTimeHandler.startEndMismatch(candidateStart, candidateEnd);
        dbTitle = textFieldTitle.getText();
        dbDescription = textFieldDescription.getText();
        dbLocation = textFieldLocation.getText();
        dbType = textFieldType.getText();
        dbContactId = newContactId;
        dbUserId = newUserId;
        dbCustomerId = newCustomerId;
        dbStart = Timestamp.valueOf(dateString + " " + stichStartHr + stichStartMn + ZEROSEC);
        dbEnd = Timestamp.valueOf(dateString + " " + stichEndHr + stichEndMn + ZEROSEC);
        canInsert = Appointment.canInsert();
        //System.out.println(canInsert);
        if(DateTimeHandler.eastTimeValid(dateString, candidateStart, candidateEnd) == true){//line 177, 385-387
        if(canInsert == true) {
            if (isValid == true && startEndMismatch == false) {
                if (update == true) {
                    //System.out.println(dbCustomerId + "" + customerId);
                    if (dbTitle != title) {
                        try {
                            String insertTitle = "UPDATE appointments " + //AAD
                                    "SET Title = ? " + //AAD
                                    "WHERE Appointment_ID = ?"; //AAD
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertTitle); //AAD
                            PreparedStatement psTU = DBQuery.getPreparedStatement(); //AAD
                            psTU.setString(1, dbTitle); //AAD
                            psTU.setString(2, appointmentId); //AAD
                            psTU.execute(); //AAD
                        } catch (Exception e) {
                            e.printStackTrace(); //AAD
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Appointment.appointmentUpdate = false;
                    }
                    if (dbDescription != description) {
                        try {
                            String insertDescription = "UPDATE appointments " + //AAD
                                    "SET Description = ? " + //AAD
                                    "WHERE Appointment_ID = ?"; //AAD
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertDescription); //AAD
                            PreparedStatement psTU = DBQuery.getPreparedStatement(); //AAD
                            psTU.setString(1, dbDescription); //AAD
                            psTU.setString(2, appointmentId); //AAD
                            psTU.execute(); //AAD
                        } catch (Exception e) {
                            e.printStackTrace(); //AAD
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Appointment.appointmentUpdate = false;
                    }
                    if (dbLocation != location) {
                        try {
                            String insertLocation = "UPDATE appointments " + //AAD
                                    "SET Location = ? " + //AAD
                                    "WHERE Appointment_ID = ?"; //AAD
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertLocation); //AAD
                            PreparedStatement psTU = DBQuery.getPreparedStatement(); //AAD
                            psTU.setString(1, dbLocation); //AAD
                            psTU.setString(2, appointmentId); //AAD
                            psTU.execute(); //AAD
                        } catch (Exception e) {
                            e.printStackTrace(); //AAD
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Appointment.appointmentUpdate = false;
                    }
                    if (dbType != type) {
                        try {
                            String insertType = "UPDATE appointments " + //AAD
                                    "SET Type = ? " + //AAD
                                    "WHERE Appointment_ID = ?"; //AAD
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertType); //AAD
                            PreparedStatement psTU = DBQuery.getPreparedStatement(); //AAD
                            psTU.setString(1, dbType); //AAD
                            psTU.setString(2, appointmentId); //AAD
                            psTU.execute(); //AAD
                        } catch (Exception e) {
                            e.printStackTrace(); //AAD
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Appointment.appointmentUpdate = false;
                    }
                    DateTimeHandler.eastCandidateStart();
                    try {//update start date
                        String insertStart = "UPDATE appointments " +
                                "SET Start = ? " +
                                "WHERE Appointment_ID = ?";
                        DBQuery.setPreparedStatement(Interface.JDBC.conn, insertStart); //AAD
                        PreparedStatement psSU = DBQuery.getPreparedStatement(); //AAD
                        psSU.setTimestamp(1, candidateStart); //AAD
                        psSU.setString(2, appointmentId); //AAD
                        psSU.execute(); //AAD
                    } catch (Exception e) {

                    }
                    DateTimeHandler.eastCandidateEnd();
                    try {//update end date
                        String insertEnd = "UPDATE appointments " +
                                "SET End = ? " +
                                "WHERE Appointment_ID = ?";
                        DBQuery.setPreparedStatement(Interface.JDBC.conn, insertEnd); //AAD
                        PreparedStatement psSU = DBQuery.getPreparedStatement(); //AAD
                        psSU.setTimestamp(1, candidateEnd); //AAD
                        psSU.setString(2, appointmentId); //AAD
                        psSU.execute(); //AAD

                    } catch (Exception e) {

                    }
                    if (dbContactId != contactId) {//not working
                        try {
                            String insertContact = "UPDATE appointments " + //AAD
                                    "SET Contact_ID = ? " + //AAD
                                    "WHERE Appointment_ID = ?"; //AAD
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertContact); //AAD
                            PreparedStatement psCU = DBQuery.getPreparedStatement(); //AAD
                            psCU.setInt(1, dbContactId); //AAD
                            psCU.setString(2, appointmentId); //AAD
                            psCU.execute(); //AAD
                        } catch (Exception e) {
                            e.printStackTrace(); //AAD
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Appointment.appointmentUpdate = false;
                    }
                    if (dbUserId != userId) {//not working
                        try {
                            String insertUser = "UPDATE appointments " + //AAD
                                    "SET User_ID = ? " + //AAD
                                    "WHERE Appointment_ID = ?"; //AAD
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertUser); //AAD
                            PreparedStatement psUU = DBQuery.getPreparedStatement(); //AAD
                            psUU.setInt(1, dbUserId); //AAD
                            psUU.setString(2, appointmentId); //AAD
                            psUU.execute(); //AAD
                        } catch (Exception e) {
                            e.printStackTrace(); //AAD
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Appointment.appointmentUpdate = false;
                    }
                    if (dbCustomerId != customerId) {//not working
                        try {
                            String insertCustomer = "UPDATE appointments " + //AAD
                                    "SET Customer_ID = ? " + //AAD
                                    "WHERE Appointment_ID = ?"; //AAD
                            DBQuery.setPreparedStatement(Interface.JDBC.conn, insertCustomer); //AAD
                            PreparedStatement psCU = DBQuery.getPreparedStatement(); //AAD
                            psCU.setInt(1, dbCustomerId); //AAD
                            psCU.setString(2, appointmentId); //AAD
                            psCU.execute(); //AAD
                        } catch (Exception e) {
                            e.printStackTrace(); //AAD
                            System.out.println("Check your SQL statement or variables");
                        }
                        Customer.customerUpdated();
                        Appointment.appointmentUpdate = false;
                    }
                    isValid = false;//#true
                    update = false;
                    startEndMismatch = false;
                    root = FXMLLoader.load(getClass().getResource("/view/ScheduleScreen.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else {//not getting to here
                    title = textFieldTitle.getText();
                    description = textFieldDescription.getText();
                    location = textFieldLocation.getText();
                    type = textFieldType.getText();
                    //System.out.println(candidateStart);
                    //System.out.println(candidateEnd);
                    DateTimeHandler.eastCandidateStart();
                    DateTimeHandler.eastCandidateEnd();
                    if((newContactId * newUserId * newCustomerId) == 0){
                        Messages.errorSix();
                    }
                    else {
                        Appointment.insertAppointment();
                        newContactId = 0;
                        newUserId = 0;
                        newCustomerId = 0;
                        isValid = false;//#true
                        update = false;
                        startEndMismatch = false;
                        root = FXMLLoader.load(getClass().getResource("/view/ScheduleScreen.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }
            else if (isValid == true && startEndMismatch == true) {
                Messages.errorSeven();
            }
            else {
                Messages.errorEight();
            }
            isValid = false;//#true
        }
        else{
            Messages.errorSix();
        }
        }
        else{
            Messages.errorNine();
        }
    }

    /**
     This is the resetButton method.  This method resets the appointment form to the state it was in when initially
     changed from the appointment screen to the appointment update form.
     @param event This method is executed based on the action of pressing the reset button.
     */
    public void resetButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource( "/view/AppointmentUpdateForm2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     This is the initialize method.  This method builds the combo boxes and other user input mechanisims.  If the update
     button was pressed to launch this form then the selected customer information is set into the proper combo boxes
     and text fields.
     @param resourceBundle Store texts and components that are locale sensitive.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * lambda3: If the selected contact changes this lambda takes the last contact and next contact.  The lambda
         * then creates the variable newContactId from the next FirstLevelDivision.
         */
        comboBoxContact.setItems(Contact.getAllContacts());
        comboBoxContact.valueProperty().addListener((observableValue, contact, t1) -> {
            newContactId = t1.getContactId();
            //contactFlag = true;
        });

        /**
         * lambda4: If the selected user changes this lambda takes the last user and
         * next user.  The lambda then creates the variable newUserId from the next
         * user.
         */
        comboBoxUser.setItems(User.getAllUsers());
        comboBoxUser.valueProperty().addListener((observableValue, user, t1) -> {
            newUserId = t1.getId();
            //userFlag = true;
        });

        /**
         * lambda5: If the selected customer changes this lambda takes the last customer and next customer.  The lambda
         * then creates the variable newCustomerId from the next customer.
         */
        comboBoxCustomer.setItems(Customer.getAllCustomers());
        comboBoxCustomer.valueProperty().addListener((observableValue, customer, t1) -> {
            newCustomerId = t1.getCustomerID();
            //customerFlag = true;
        });

        /**
         * lambda6: If the selected startHour changes this lambda takes the last startHour and next startHour.  The
         * lambda then creates the variable stichStartHr from the next startHour.
         */
        comboBoxStartTimeHour.setItems(startHourList);//8am to 955pm
        comboBoxStartTimeHour.valueProperty().addListener((observableValue, s, t1) -> {
            stichStartHr = t1 + ":";
            //System.out.println(stichStartHr);
            //startFlag = true;
        });

        /**
         * lambda7: If the selected start minute changes this lambda takes the last start minute and next start minute.
         * The lambda then creates the variable stichStartMn from the next startMinute.
         */
        comboBoxStartTimeMinute.setItems(startMinuteList);//8am to 955pm
        comboBoxStartTimeMinute.valueProperty().addListener((observableValue, s, t1) -> {
            stichStartMn = t1 + ":";
            //System.out.println(stichStartMn);
            //startFlag = true;
        });

        /**
         * lambda8: If the selected endHour changes this lambda takes the last endHour and next endHour.  The
         * lambda then creates the variable stichEndHr from the next endHour.
         */
        comboBoxEndTimeHour.setItems(endHourList);//805am to 1000pm, Create exception not allowing an appointment end time before the start time
        comboBoxEndTimeHour.valueProperty().addListener((observableValue, s, t1) -> {
            stichEndHr = t1 + ":";
            //System.out.println(stichEndHr);
            //endFlag = true;
        });

        /**
         * lambda7: If the selected end minute changes this lambda takes the last end minute and next end minute.
         * The lambda then creates the variable stichEndMn from the next endMinute.
         */
        comboBoxEndTimeMinute.setItems(endMinuteList);//805am to 1000pm, Create exception not allowing an appointment end time before the start time
        comboBoxEndTimeMinute.valueProperty().addListener((observableValue, s, t1) -> {
            stichEndMn = t1 + ":";
            //System.out.println(stichEndMn);
            //endFlag = true;
        });

        /**
         * lambda8: If the selected date changes this lambda takes the last date and next date.  The lambda then creates
         * the variable dateString from the next date.
         */
        datePickerDate.valueProperty().addListener((observableValue, localDate, t1) -> {
            dateString = t1.toString();
            //dateFlag = true;
        });

        if (update == true){//new
            labelAppointment.setText("APPOINTMENT UPDATE FORM");
            appointmentId = String.valueOf(selectedAppointment.getAppointmentId());
            description = selectedAppointment.getDescription();
            title = selectedAppointment.getTitle();
            type = selectedAppointment.getType();
            location = selectedAppointment.getLocation();
            selectedStart = selectedAppointment.getStart().toLocalDateTime();
            selectedEnd = selectedAppointment.getEnd().toLocalDateTime();
            DateTimeHandler.selectedDateStart();
            DateTimeHandler.selectedDateEnd();
            //System.out.println("IIIII" + selectedStart);
            date = LocalDate.parse(selectedStart.toString().substring(0, 4) + "-" + selectedAppointment.getStart().toString().substring(5,7) + "-" + selectedAppointment.getStart().toString().substring(8, 10));
            startHr = selectedStart.toString().substring(11, 13);
            startMn = selectedStart.toString().substring(14, 16);
            endHr = selectedEnd.toString().substring(11, 13);
            endMn = selectedEnd.toString().substring(14, 16);
            contactId = selectedAppointment.getContactId();
            userId = selectedAppointment.getUserId();
            customerId = selectedAppointment.getCustomerId();
            int customerIndex = Customer.getCustomerIds().indexOf(customerId);
            int userIndex = User.getUserIds().indexOf(userId);
            int contactIndex = Contact.getContactIds().indexOf(contactId);
            comboBoxContact.getSelectionModel().select(contactIndex);
            comboBoxCustomer.getSelectionModel().select(customerIndex);
            comboBoxUser.getSelectionModel().select(userIndex);
            textFieldAppointmentId.setText(appointmentId); //Appointment ID
            textFieldDescription.setText(description); //Description
            textFieldTitle.setText(title); //Title
            textFieldType.setText(type); //Type
            textFieldLocation.setText(location); //Location
            datePickerDate.setValue(date); //Date
            comboBoxStartTimeHour.setValue(startHr);
            comboBoxStartTimeMinute.setValue(startMn);
            comboBoxEndTimeHour.setValue(endHr);
            comboBoxEndTimeMinute.setValue(endMn);
        }
        else{
            labelAppointment.setText("NEW APPOINTMENT FORM");
        }
    }
}

