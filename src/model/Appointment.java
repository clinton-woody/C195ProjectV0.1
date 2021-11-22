//Fix all the ID to Id
package model;

import Interface.DBQuery;
import controller.AppointmentUpdateForm2;
import controller.CustomerScreen;
import controller.ScheduleScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Appointment {

    //Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID

    //Variables

    public int appointmentID;
    public int customerId;
    public int userId;
    public int contactId;
    public String title;
    public String description;
    public String location;
    public String contact;
    public String type;
    public Timestamp start;
    public Timestamp end;
    public String customerName;
    public static int updateAppointmentID;
    public static Appointment selectedAppointment;
//    public static Customer customerToUpdate;
//    public static boolean selectedAppointmentDeletable = false;
    public static boolean appointmentUpdate = false;


    //DELETE Variables
//    public static int haId = 0;
    public static boolean hasAppointmentResult = false;
//    public static String parseName;

    //Constructor

    public Appointment(int appointmentID, String title, String description, String location, String contact, String type, Timestamp start, Timestamp end, String customerName )
    {
        setAppointmentID(appointmentID);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setContact(contact);
        setType(type);
        setStart(start);
        setEnd(end);
        setCustomerName(customerName);
    }

    //OVERLOADED CONSTRUCTOR
    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId )
    {
        setAppointmentID(appointmentID);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setStart(start);
        setEnd(end);
        setCustomerId(customerId);
        setUserId(userId);
        setContactId(contactId);
    }

    public static void insertAppointment() throws SQLException{
        System.out.println(AppointmentUpdateForm2.candidateStart);
        System.out.println(AppointmentUpdateForm2.candidateEnd);

        try{
            String insert = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";//get rid of the datetime converter
            DBQuery.setPreparedStatement(Interface.JDBC.conn, insert);
            PreparedStatement psIA = DBQuery.getPreparedStatement();
            psIA.setString(1, AppointmentUpdateForm2.title);
            psIA.setString(2, AppointmentUpdateForm2.description);
            psIA.setString(3, AppointmentUpdateForm2.location);
            psIA.setString(4, AppointmentUpdateForm2.type);
            psIA.setTimestamp(5, AppointmentUpdateForm2.candidateStart);
            psIA.setTimestamp(6, AppointmentUpdateForm2.candidateEnd);
            psIA.setTimestamp(7, DateTimeConverter.easternDateTimeStamp() );
            psIA.setString(8, User.currentUser);
            psIA.setTimestamp(9, DateTimeConverter.easternDateTimeStamp() );
            psIA.setString(10, User.currentUser);
            psIA.setInt(11, AppointmentUpdateForm2.newCustomerId);
            psIA.setInt(12, AppointmentUpdateForm2.newUserId);
            psIA.setInt(13, AppointmentUpdateForm2.newContactId);
            psIA.execute(); //Execute PreparedStatement
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Check your SQL statement or variables");}
    }


    public static Appointment getSelectedAppointment() throws SQLException {
        try {
            String selectedCustomerGrab = "SELECT * " +
                                          "FROM appointments " +
                                          "WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, selectedCustomerGrab);
            PreparedStatement psSA = DBQuery.getPreparedStatement();
            psSA.setInt(1, ScheduleScreen.selectedAppointmentID);
            psSA.execute();
            ResultSet rsSA = psSA.getResultSet();
            while (rsSA.next()) {
                Appointment appointment = new Appointment(
                        rsSA.getInt("Appointment_ID"),
                        rsSA.getString("Title"),
                        rsSA.getString("Description"),
                        rsSA.getString("Location"),
                        rsSA.getString("Type"),
                        rsSA.getTimestamp("Start"),
                        rsSA.getTimestamp("End"),
                        rsSA.getInt("Customer_ID"),
                        rsSA.getInt("User_ID"),
                        rsSA.getInt("Contact_ID"));
                //int=appointmentID, String=title, String=description, String=location, String contact, String type, String start, String end, String customerName
                selectedAppointment = appointment;
            }
            return selectedAppointment;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }

    public static void deleteAppointment() throws SQLException {
        try{
            String delete = "DELETE FROM appointments " +
                            "WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, delete);
            PreparedStatement psDA = DBQuery.getPreparedStatement();
            psDA.setInt(1, ScheduleScreen.deleteCandidateId);
            psDA.execute(); //Execute PreparedStatement
        } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error on Building Data");
        }
    }

    public static ObservableList<Appointment> getAllAppointments(){
        try{
            String appointmentGrab = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description," +
                    " appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, customers.Customer_Name" +
                    " FROM appointments" +
                    " JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID" +
                    " JOIN customers ON appointments.Customer_ID=customers.Customer_ID" +
                    " ORDER BY appointments.Start desc";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, appointmentGrab);
            PreparedStatement psMA = DBQuery.getPreparedStatement();
            psMA.execute();
            ResultSet rsA = psMA.getResultSet();
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            while (rsA.next()) {
                Appointment nextAppointment = new Appointment(
                        rsA.getInt("Appointment_ID"),
                        rsA.getString("Title"),
                        rsA.getString("Description"),
                        rsA.getString("Location"),
                        rsA.getString("Contact_Name"),
                        rsA.getString("Type"),
                        rsA.getTimestamp("Start"),
                        rsA.getTimestamp("End"),
                        rsA.getString("Customer_Name"));
                appointmentList.add(nextAppointment);
            }
            return appointmentList;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }

    public static ObservableList<Appointment> getWeeklyAppointments(){
        try{
            DateTimeConverter.currentWeekParser();
            //System.out.println("Begin currentWeekParser()");
            String weeklyAppointmentGrab = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description," +
                    " appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, customers.Customer_Name" +
                    " FROM appointments" +
                    " JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID" +
                    " JOIN customers ON appointments.Customer_ID=customers.Customer_ID" +
                    " WHERE appointments.Start BETWEEN ? AND ?" +
                    " ORDER BY appointments.Start desc";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, weeklyAppointmentGrab);
            PreparedStatement psMA = DBQuery.getPreparedStatement();
            psMA.setString(1, DateTimeConverter.currentSunString);
            psMA.setString(2, DateTimeConverter.nextSunString);
            psMA.execute(); //Execute PreparedStatement
            ResultSet rsA = psMA.getResultSet();
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            while (rsA.next()) {
                //System.out.println(DateTimeConverter.currentSunString);
                //System.out.println(DateTimeConverter.nextSunString);
                Appointment nextAppointment = new Appointment(
                        rsA.getInt("Appointment_ID"),
                        rsA.getString("Title"),
                        rsA.getString("Description"),
                        rsA.getString("Location"),
                        rsA.getString("Contact_Name"),
                        rsA.getString("Type"),
                        rsA.getTimestamp("Start"),
                        rsA.getTimestamp("End"),
                        rsA.getString("Customer_Name"));
                appointmentList.add(nextAppointment);
                //System.out.println(DateTimeConverter.currentSunString);
                //System.out.println(DateTimeConverter.nextSunString);
            }
            //System.out.println("End currentWeekParser()");
            //System.out.println(appointmentList);
            return appointmentList;
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error on Building Data");
            return null;
        }
    }

    public static ObservableList<Appointment> getMonthlyAppointments(){
        try{
            DateTimeConverter.currentMonthParser();
            String appointmentGrab = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description," +
                    " appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, customers.Customer_Name" +
                    " FROM appointments" +
                    " JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID" +
                    " JOIN customers ON appointments.Customer_ID=customers.Customer_ID" +
                    " WHERE appointments.Start BETWEEN ? AND ?" +
                    " ORDER BY appointments.Start desc";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, appointmentGrab);
            PreparedStatement psMA = DBQuery.getPreparedStatement();
            psMA.setString(1, DateTimeConverter.firstDayString);
            psMA.setString(2, DateTimeConverter.afterLastDayString);
            psMA.execute();
            ResultSet rsA = psMA.getResultSet();
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            while (rsA.next()) {
                Appointment nextAppointment = new Appointment(
                        rsA.getInt("Appointment_ID"),
                        rsA.getString("Title"),
                        rsA.getString("Description"),
                        rsA.getString("Location"),
                        rsA.getString("Contact_Name"),
                        rsA.getString("Type"),
                        rsA.getTimestamp("Start"),
                        rsA.getTimestamp("End"),
                        rsA.getString("Customer_Name"));
                appointmentList.add(nextAppointment);
            }
            return appointmentList;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }

    public static void hasAppointment() throws SQLException {
        hasAppointmentResult = false;
        CustomerScreen.hasAppointment = hasAppointmentResult;
        try {
            String hasAppointment = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description," +
                    " appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, customers.Customer_Name" +
                    " FROM appointments" +
                    " JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID" +
                    " JOIN customers ON appointments.Customer_ID=customers.Customer_ID" +
                    " WHERE appointments.Customer_ID = ?" +
                    " LIMIT 1";
            DBQuery.setPreparedStatement(Interface.JDBC.conn, hasAppointment);
            PreparedStatement psHA = DBQuery.getPreparedStatement();
            psHA.setInt(1, CustomerScreen.deleteCandidateId);
            psHA.execute();
            ResultSet rsHA = psHA.getResultSet();
            if (rsHA.next()) {
                hasAppointmentResult = true;
                CustomerScreen.hasAppointment = Appointment.hasAppointmentResult;
            } else {
                hasAppointmentResult = false;
                CustomerScreen.hasAppointment = Appointment.hasAppointmentResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

            /*
            Press Delete Button
            on runHasAppointment()
                SQL statement: determine whether CustomerScreen.deleteCandidateId has an appointment
                    while there is a result get parseName from Name of delete Candidate
                run nameIdParse()
                    SQL Statement: get customerId from parseName
                        while there is a result get haId from parsedId --haId is 0 by default
                if haId is greater than 0
                    set hasAppointmentResult to true --hasAppointmentResult is false by default
                    get CustomerScreen.hasAppointment from Appointment.hasAppointmentResult
                else
                    set hasAppointmentResult to false
                    set CustomerScreen.hasAppointment from Appointment.hasAppointmentResult;
            */

    //Getters

    public int getAppointmentId(){
        return appointmentID;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation(){
        return location;
    }

    public String getContact(){
        return contact;
    }

    public String getType(){
        return type;
    }

    public Timestamp getStart(){
        return start;
    }

    public Timestamp getEnd(){
        return end;
    }

    public String getCustomerName(){
        return customerName;
    }

    public int getContactId(){
        return contactId;
    }

    public int getCustomerId(){
        return customerId;
    }

    public int getUserId(){
        return userId;
    }

    // Setters

    public void setAppointmentID(int newAppointmentID) {
        this.appointmentID = newAppointmentID;
    }

    public void setCustomerId(int newCustomerId) {
        this.customerId = newCustomerId;
    }

    public void setUserId(int newUserId) {
        this.userId = newUserId;
    }

    public void setContactId(int newContactId) {
        this.contactId = newContactId;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    public void setContact(String newContact) {
        this.contact = newContact;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    public void setStart(Timestamp newStartDateAndTime) {
        this.start = newStartDateAndTime;
    }

    public void setEnd(Timestamp newEndDateAndTime) {
        this.end = newEndDateAndTime;
    }

    public void setCustomerName(String newCustomerName) {
        this.customerName = newCustomerName;
    }

}
