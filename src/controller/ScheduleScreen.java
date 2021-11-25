//STILL NEED WORK
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
/**
 This is the ScheduleScreen class
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
    private TextArea textArea;//CanDelete?
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

    public void customerButton(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource( "/view/CustomerScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void newButton(ActionEvent event) throws IOException {

        Appointment.appointmentUpdate = false;
        root = FXMLLoader.load(getClass().getResource( "/view/AppointmentUpdateForm2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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



    public void deleteButton(ActionEvent event) throws IOException, SQLException {

        if(canDeleteAppointment == false){
            Appointment selectedAppointmentCells = appointmentTableView.getSelectionModel().getSelectedItem();
            selectedAppointmentID = selectedAppointmentCells.getAppointmentId();
            selectedType = selectedAppointmentCells.getType();
            deleteCandidateId = selectedAppointmentID;
            canDeleteAppointment = true;
            Messages.messageTwo();

        }else{
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
                }else if(isWeek == true){
                    appointmentTableView.setItems(Appointment.getWeeklyAppointments());
                }else{
                    appointmentTableView.setItems(Appointment.getAllAppointments());
                }
            }else{
                deleteCandidateId = confirmDeleteId;
                Messages.messageTwo();
            }

        }

    }


    public void radioButtonMonth(ActionEvent event) throws IOException {
        appointmentTableView.setItems(Appointment.getMonthlyAppointments());//Works
        isMonth = true;
        isWeek = false;
        isAll = false;
    }

    public void radioButtonWeek(ActionEvent event) throws IOException {
        appointmentTableView.setItems(Appointment.getWeeklyAppointments());//no work
        isMonth = false;
        isWeek = true;
        isAll = false;

    }

    public void radioButtonAll(ActionEvent event) throws IOException {
        appointmentTableView.setItems(Appointment.getAllAppointments());//no work throws DAY_OF_MONTH error
        isMonth = false;
        isWeek = false;
        isAll = true;

    }

    public void reportButton(ActionEvent event) throws SQLException {


        if (customerRadio.isSelected()){

            try{
                textArea.clear();
                String customerReport = "SELECT customers.Customer_Name, appointments.Type, appointments.Start " +
                                        "FROM appointments " +
                                        "INNER JOIN customers ON appointments.Customer_ID=customers.Customer_ID " +
                                        "ORDER BY appointments.Type desc, appointments.Start desc";
                DBQuery.setPreparedStatement(Interface.JDBC.conn, customerReport);
                PreparedStatement psMA = DBQuery.getPreparedStatement();
                textArea.setText("Customer Name  " + "|" + "Appointment Type   " + "|" + "Start Time" + '\n');
                psMA.execute();



                AppointmentReport appointmentReport = (customerName, appointmentType, appointmentStart) -> textArea.appendText( (customerName = customerName + "                    ").substring(0, 22) + "|" + (appointmentType = appointmentType + "                    ").substring(0, 24) + "|" + appointmentStart + '\n');

                ResultSet rsA = psMA.getResultSet();
                    while (rsA.next()) {
                        String customerName = null;
                        appointmentReport.appointmentReport(
                         rsA.getString("Customer_Name"),
                        rsA.getString("Type"),
                        rsA.getTimestamp("Start")

                        );

                    }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
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
//Delete Fully Works
