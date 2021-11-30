package controller;

import Interface.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import model.Report;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 This is the ScheduleScreen class.  This class is the controller for the Schedule Screen.
 */
public class ScheduleScreen implements Initializable {
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;
    @FXML
    private TableColumn<Appointment, String> userColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> startColumn;//datetime from string
    @FXML
    private TableColumn<Appointment, String> endColumn;//datetime from string
    @FXML
    private TableColumn<Appointment, String> customerColumn;
    @FXML
    private RadioButton radioButtonMonth;//CanDelete?
    @FXML
    public TextArea textArea;//CanDelete?
    @FXML
    private RadioButton radioButtonWeek;//CanDelete?
    @FXML
    private RadioButton radioButtonAll;//CanDelete?
    @FXML
    private RadioButton customerRadio;//CanDelete?
    @FXML
    private RadioButton contactRadio;//CanDelete?
    @FXML
    private RadioButton locationRadio;//CanDelete?
    //public static String deleteType;
    public static boolean canDeleteAppointment = false;
    public static int selectedAppointmentID;
    public static int deleteCandidateId;
    public static int confirmDeleteId;
    public static boolean isWeek = false;
    public static boolean isMonth = true;
    public static boolean isAll = false;
    public static Appointment selectedAppointment;
    public static Appointment newSelectedAppointment;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static int updateAppointmentID;
    public static int deleteAppointmentID = 0;//CanDelete?
    public static String selectedType;
    public static String activeMonth;
    public static String activeType;
    public static String activeMonthStart;
    public static String activeMonthEnd;
    public static String activeHRM;
    public static int activeYear = 2021;
    public static int nextYear = activeYear + 1;
    public static int parsedYear;

    /**
     This is the customerButton method.  This method switches the program from the Schedule Screen to the Customer
     Screen.
     @param event This method is executed based on the action of pressing the Customer Button.
     */
    public void customerButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource( "/view/CustomerScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     This is the newButton method.  This method switches the program from the Schedule Screen to the Appointment Update
     Form in its new appointment configuration.
     @param event This method is executed based on the action of pressing the New Button.
     */
    public void newButton(ActionEvent event) throws IOException {
        Appointment.appointmentUpdate = false;
        root = FXMLLoader.load(getClass().getResource( "/view/AppointmentUpdateForm2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     This is the newButton method.  This method takes the selected appointment id and switches the program from the
     Schedule Screen to the Appointment Update Form in its update selected appointment id configuration.
     @param event This method is executed based on the action of pressing the Update Button.
     */
    public void updateButton(ActionEvent event) throws IOException, SQLException {
        Appointment.appointmentUpdate = true;
        AppointmentUpdateForm2.update = true;
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        selectedAppointmentID = selectedAppointment.getAppointmentId();
        Appointment.updateAppointmentID = selectedAppointmentID;
        AppointmentUpdateForm2.selectedAppointment = Appointment.getSelectedAppointment();
        //AppointmentUpdateForm.updateAppointmentID = selectedAppointmentID;
        root = FXMLLoader.load(getClass().getResource("/view/AppointmentUpdateForm2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     This is the deleteButton method.  This method is used to delete appointments.  When first initiated the appointment
     selected is marked safe to delete. When initiated a second time if the appointment id selected is the same
     appointment id that was selected during the first button initiation the selected appointment is deleted.
     @param event This method is executed based on the action of pressing the Delete button.
     */
    public void deleteButton(ActionEvent event) throws IOException, SQLException {
        if(canDeleteAppointment == false){
            Appointment selectedAppointmentCells = appointmentTableView.getSelectionModel().getSelectedItem();
            selectedAppointmentID = selectedAppointmentCells.getAppointmentId();
            selectedType = selectedAppointmentCells.getType();
            deleteCandidateId = selectedAppointmentID;
            canDeleteAppointment = true;
            Messages.messageTwo();
        }
        else{
            Appointment selectedAppointmentCells = appointmentTableView.getSelectionModel().getSelectedItem();
            selectedAppointmentID = selectedAppointmentCells.getAppointmentId();
            selectedType = selectedAppointmentCells.getType();
            confirmDeleteId = selectedAppointmentID;
            if (deleteCandidateId==confirmDeleteId){
                //deleteType = Appointment.getSelectedAppointment().getType();
                Appointment.deleteAppointment();
                Messages.messageThree();
                canDeleteAppointment = false;
                deleteCandidateId = 0;
                if (isMonth == true){
                    appointmentTableView.setItems(Appointment.getMonthlyAppointments());
                }
                else if(isWeek == true){
                    appointmentTableView.setItems(Appointment.getWeeklyAppointments());
                }
                else{
                    appointmentTableView.setItems(Appointment.getAllAppointments());
                }
            }
            else{
                deleteCandidateId = confirmDeleteId;
                Messages.messageTwo();
            }
        }
    }

    /**
     This is the radioButtonMonth method.  This method is used to set the isMonth boolean flag to true and pass a query
     that pulls all appointments between the 1st and last day of the month and passes them to the table view.
     @param event This method is executed based on the action of toggling the monthly radio button.
     */
    public void radioButtonMonth(ActionEvent event) throws IOException {
        appointmentTableView.setItems(Appointment.getMonthlyAppointments());//Works
        isMonth = true;
        isWeek = false;
        isAll = false;
    }

    /**
     This is the radioButtonWeek method.  This method is used to set the isWeek boolean flag to true and pass a query
     that pulls all appointments between the 1st and last day of the Week and passes them to the table view.
     @param event This method is executed based on the action of toggling the weekly radio button.
     */
    public void radioButtonWeek(ActionEvent event) throws IOException {
        appointmentTableView.setItems(Appointment.getWeeklyAppointments());//no work
        isMonth = false;
        isWeek = true;
        isAll = false;
    }

    /**
     This is the radioButtonAll method.  This method is used to set the isAll boolean flag to true and pass a query
     that pulls all appointments  and passes them to the table view.
     @param event This method is executed based on the action of toggling the all radio button.
     */
    public void radioButtonAll(ActionEvent event) throws IOException {
        appointmentTableView.setItems(Appointment.getAllAppointments());//no work throws DAY_OF_MONTH error
        isMonth = false;
        isWeek = false;
        isAll = true;
    }

    /**
     This is the reportButton method.  This method is used to pull the report determined by the report radio button
     toggled and print it in the text area.  If the customer radio button is toggled run the customer report.  If the
     appointment radio button is toggled run the appointment report.  If the location radio button is toggled run the
     location report.
     @param event This method is executed based on the action of pressing the report button.
     */
    public void reportButton(ActionEvent event) throws SQLException {
        if (customerRadio.isSelected()){
            try{
                textArea.clear();
                textArea.setText("MONTH  |  TYPE  |  APPOINTMENT QUANTITY" + '\n');
                String ZERO = " 00:00:00";
                List<String> firstStart = Arrays.asList("01-01", "02-01", "03-01", "04-01", "05-01", "06-01", "07-01", "08-01", "09-01", "10-01", "11-01", "12-01");
                List<String> lastStart = Arrays.asList("02-01", "03-01", "04-01", "05-01", "06-01", "07-01", "08-01", "09-01", "10-01", "11-01", "12-01", "01-01");
                List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
                List<String> typesRaw = new ArrayList<>();
                List<String> typesParsed = new ArrayList<>();
                List<String> startRaw = new ArrayList<>();
                List<String> startParsed = new ArrayList<>();
                List<String> monthParsed = new ArrayList<>();

                try{
                    String typeCall = "SELECT Type FROM appointments";
                    DBQuery.setPreparedStatement(Interface.JDBC.conn, typeCall);
                    PreparedStatement psCT = DBQuery.getPreparedStatement();
                    psCT.execute(); //Execute PreparedStatement
                    ResultSet rsCT = psCT.getResultSet();
                    while (rsCT.next()) {
                        String nextType = rsCT.getString("Type");
                        typesRaw.add(nextType);
                    }
                    for (int i = 0; i < typesRaw.size(); i++) {
                        Object element = typesRaw.get(i);
                        if(!typesParsed.contains(element)){
                            typesParsed.add(element.toString());
                        }
                    }
                    String startCall = "SELECT Start FROM appointments";
                    DBQuery.setPreparedStatement(Interface.JDBC.conn, startCall);
                    PreparedStatement psCS = DBQuery.getPreparedStatement();
                    psCS.execute(); //Execute PreparedStatement
                    ResultSet rsCS = psCS.getResultSet();
                    while (rsCS.next()) {
                        String nextStart = rsCS.getTimestamp("Start").toString().substring(5, 7);
                        startRaw.add(nextStart);
                    }
                    for (int i = 0; i < startRaw.size(); i++) {
                        Object element = startRaw.get(i);
                        if(!startParsed.contains(element)){
                            startParsed.add(element.toString());
                        }
                    }
                    for (int i1 = 0; i1 < startParsed.size(); i1++) {
                        Object element1 = startParsed.get(i1);
                        if(element1 != null){//basically a while not null

                            String indexString = element1.toString();//object to string
                            int index = Integer.valueOf(indexString)-1;//string to int for use as index
                            String convertedMonth = months.get(index);
                            LocalDate date = java.time.LocalDate.now();
                            activeHRM = convertedMonth;
                            activeMonthStart = firstStart.get(index);
                            activeMonthEnd = lastStart.get(index);
                            activeYear = Integer.parseInt(java.time.LocalDate.now().toString().substring(0, 4));//Works to here
                            //int ams = Integer.valueOf(activeMonthEnd);
                            int ams = Integer.valueOf(activeMonthStart.substring(0,2));
                            System.out.println("ams: " + ams);
                            int ame = Integer.valueOf(activeMonthEnd.substring(0,2));

                            if (ams < ame){

                                parsedYear = activeYear;

                            }
                            else{
                                parsedYear = nextYear;
                            }

                            System.out.println("Parsed Year: " + parsedYear);


                            System.out.println(activeMonthEnd);//null
                            for (int i2 = 0; i2 < typesParsed.size(); i2++) {
                                Object element2 = typesParsed.get(i2);
                                activeType = element2.toString();
                                try{
                                    System.out.println("Works3");//not getting to here
                                    String report = "SELECT COUNT(Type) AS total " +
                                            "FROM appointments " +
                                            "WHERE Start >+ ? " +
                                            "AND Start < ? " +
                                            "AND Type = ?";
                                    DBQuery.setPreparedStatement(Interface.JDBC.conn, report);
                                    PreparedStatement psR = DBQuery.getPreparedStatement();
                                    psR.setString(1, activeYear + "-" + activeMonthStart + ZERO);
                                    psR.setString(2, parsedYear + "-" + activeMonthEnd + ZERO);
                                    psR.setString(3, activeType);
                                    psR.execute();
                                    ResultSet rsR = psR.getResultSet();
                                    System.out.println("Works2");

                                    while (rsR.next()) {
                                        System.out.println("Works1");//works to here
                                        int count = rsR.getInt("total");//try changing total to 1
                                        System.out.println("count" + count);
                                        if (count != 0){
                                            textArea.appendText(activeHRM + "   |   " + activeType + "   |   " + count + '\n');
                                        }


                                    }


                                }catch(Exception e) {
                                }
                            }

                            System.out.println(convertedMonth);
                            monthParsed.add(convertedMonth);//not needed
                            //Works above here


                        }
                    }

                    for (int i = 0; i < startParsed.size(); i++) {//The big bad iteration machine

                    }

                }
                catch (Exception e){
                }
            }
            catch(Exception e){

            }

        }
        else if(contactRadio.isSelected()){
            try{
                textArea.clear();
                String customerReport = "SELECT contacts.Contact_Name, appointments.appointment_ID, appointments.Title, appointments.Type, appointments.Description, appointments.Start, appointments.End " +
                                        "FROM appointments " +
                                        "INNER JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID";
                DBQuery.setPreparedStatement(Interface.JDBC.conn, customerReport);
                PreparedStatement psMA = DBQuery.getPreparedStatement();
                textArea.setText("Contact Name" + "  |  " + "Appointment ID  " + "   |   " + "Title   " + "   |   " + "Type" + "   |   " + "Description" + "   |   "  + "Start" + "   |   "  + "End" + '\n');
                psMA.execute();

                /**
                 * lambda10: Takes the output of the contact report and passes it to the text area.  This lambda
                 * uses the ContactReport interface.
                 */
                ContactReport contactReport = (Contact_Name, Appointment_ID, Title, Type, Description, Start, End) -> textArea.appendText( (Contact_Name = Contact_Name + "                        ").substring(0, 20) + "   |   " + (Appointment_ID = Appointment_ID + "                         ").substring(0, 15) + "   |   " + (Title = Title + " ") + "   |   " +  (Type = Type + " ") + "   |   " +  (Description = Description + " ") + "   |   " + Start + "   |   " + End + '\n');

                ResultSet rsA = psMA.getResultSet();
                while (rsA.next()) {
                    contactReport.contactReport(
                            rsA.getString("Contact_Name"),
                            rsA.getString("Appointment_ID"),
                            rsA.getString("Title"),
                            rsA.getString("Type"),
                            rsA.getString("Description"),
                            rsA.getTimestamp("Start"),
                            rsA.getTimestamp("End")
                    );
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }
        }
        else if(locationRadio.isSelected()){
        try{
            textArea.clear();
            String report = "SELECT appointments.Location, appointments.Title, contacts.Contact_Name, customers.Customer_Name " +
                    "FROM appointments " +
                    "JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID " +
                    "JOIN customers ON appointments.Customer_ID=customers.Customer_ID " +
                    "ORDER BY appointments.Location desc";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, report);
            PreparedStatement psMA = DBQuery.getPreparedStatement();
            textArea.setText("Location  " + "|" + "Appointment Title   " + "|" + "Contact Name" + "|" + "Customer Name" + '\n');
            psMA.execute();

            /**
             * lambda11: Takes the output of the location report and passes it to the text area.  This lambda
             * uses the LocalReport interface.
             */
            LocationReport locationReport = (location, appointmentTitle, contactName, customerName) -> textArea.appendText( (location = location + "    " ) + "   |   "  +  (appointmentTitle = appointmentTitle + "    ") + "   |   " + (contactName = contactName + "    ") + "   |   " + (customerName = customerName + "    ")  + '\n');
            ResultSet rsA = psMA.getResultSet();
            while (rsA.next()) {
                String customerName = null;
                locationReport.locationReport(
                        rsA.getString("Location"),
                        rsA.getString("Title"),
                        rsA.getString("Contact_Name"),
                        rsA.getString("Customer_Name")
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
        else{
            //make a message directing the selection of a redio button here//this should never be seen as the radio buttons are part of a toggle group
        }
}

    /**
     This is the initialize method.  This method imports the contents of the database appointment table into the
     appointment table view.
     @param resourceBundle Store texts and components that are locale sensitive.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
            userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            appointmentTableView.setItems(Appointment.getMonthlyAppointments());

    }
}

