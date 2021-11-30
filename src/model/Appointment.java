//Fix all the ID to Id
package model;

import Interface.DBQuery;
import controller.AppointmentUpdateForm2;
import controller.CustomerScreen;
import controller.CustomerUpdateForm;
import controller.ScheduleScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 This is the Appointment class
 */
public class Appointment {
    //CLASS VARIABLES
    public int appointmentID;
    public int customerId;
    public int userId;
    public int contactId;
    public String title;
    public String description;
    public String location;
    public String contact;
    public String user;
    public String type;
    public Timestamp start;
    public Timestamp end;
    public String customerName;
    public static int updateAppointmentID;
    public static Appointment selectedAppointment;
    public static boolean appointmentUpdate = false;
    public static boolean hasAppointmentResult = false;
    public static String activeMonth;
    public static String activeType;
    public static String activeMonthStart;
    public static String activeMonthEnd;
    public static String activeHRM;
    public static int activeYear;
    public static int nextYear = activeYear + 1;
    public static int parsedYear;

    /**
     * This is the Appointment constructor method.
     * @param appointmentID appointment appointmentId
     * @param title appointment Title
     * @param description appointment description
     * @param location appointment location
     * @param contact appointment contact
     * @param user appointment user
     * @param type appointment type
     * @param start appointmant start
     * @param end appointment end
     * @param customerName appointmant customerName
     */
    public Appointment(int appointmentID, String title, String description, String location, String contact, String user, String type, Timestamp start, Timestamp end, String customerName )
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
        setUser(user);
    }

    /**
     * This is the overloaded Appointment constructor method.
     * @param appointmentID appointment appointmentID
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start
     * @param end appointment end
     * @param customerId appointment customerId
     * @param userId appointment userId
     * @param contactId appointment contactId
     */
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

    /**
     * This is the canInsert method.  This method checks for null or 0 values in the variables for the user inputs in
     * the appointment update form.  It returns canInsert of true if no null/0 values.
     * @return Returns boolean value for canInsert.
     */
    public static boolean canInsert(){
        boolean canInsert = true;
        System.out.println("Working");
        if (AppointmentUpdateForm2.dbType == null || AppointmentUpdateForm2.dbTitle == null || AppointmentUpdateForm2.dbDescription == null || AppointmentUpdateForm2.dbLocation == null || AppointmentUpdateForm2.parsedStartDateTime == null || AppointmentUpdateForm2.parsedEndDateTime == null) {
            canInsert = false;
            System.out.println(AppointmentUpdateForm2.dbType + AppointmentUpdateForm2.dbTitle + AppointmentUpdateForm2.dbDescription + AppointmentUpdateForm2.dbLocation + AppointmentUpdateForm2.parsedStartDateTime + AppointmentUpdateForm2.parsedEndDateTime);
            System.out.println("Working1");
        }
        else if (AppointmentUpdateForm2.dbType.isEmpty() || AppointmentUpdateForm2.dbTitle.isEmpty() || AppointmentUpdateForm2.dbDescription.isEmpty() || AppointmentUpdateForm2.dbLocation.isEmpty()) {
            canInsert = false;
            System.out.println("Working2");
        }
        else{
            System.out.println("Working3");
        }
        return canInsert;
    }

    /**
     * This is the insertAppointment method.  This method sends an INSERT INTO statement into the database to create
     * appointments in the database.
     * @throws SQLException
     */
    public static void insertAppointment() throws SQLException{
        System.out.println(AppointmentUpdateForm2.candidateStart);
        System.out.println(AppointmentUpdateForm2.candidateEnd);
        try{//need to convert candidate start and candidateEnd to eastern
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

    /**
     * This is the getSelectedAppointment method.  This method sends a SELECT statement to the database that returns an
     * appointment from a user specified appointment id.
     * @return Returns an Appointment as appointment.
     * @throws SQLException
     */
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

    /**
     * This is the deleteAppointment method.  This method sends a DELETE statement to the database that deletes an
     * appointment that matches a user specified appointment id.
     * @throws SQLException
     */
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

    /**
     * This is the getAllAppointments method.  This method sends a SELECT statement that gets all appointments in the
     * format of the first, non-overloaded, appointment constructor and returns appointmentList as an ObservableList.
     * @return Returns appointmentList as an ObservableList.
     */
    public static ObservableList<Appointment> getAllAppointments(){
        try{
            String appointmentGrab = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description," +
                    " appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, customers.Customer_Name, users.User_Name" +
                    " FROM appointments" +
                    " JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID" +
                    " JOIN customers ON appointments.Customer_ID=customers.Customer_ID" +
                    " JOIN users ON appointments.User_ID=users.User_ID" +
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
                        rsA.getString("User_Name"),
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

    /**
     * This is the getWeeklyAppointments method.  This method sends a SELECT statement that gets all appointments in the
     * format of the first, non-overloaded, appointment constructor that fall within the current week and returns
     * appointmentList as an ObservableList.
     * @return Returns appointmentList as an ObservableList.
     */
    public static ObservableList<Appointment> getWeeklyAppointments(){
        try{
            DateTimeConverter.currentWeekParser();
            //System.out.println("Begin currentWeekParser()");
            String weeklyAppointmentGrab = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description," +
                    " appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, customers.Customer_Name, users.User_Name" +
                    " FROM appointments" +
                    " JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID" +
                    " JOIN customers ON appointments.Customer_ID=customers.Customer_ID" +
                    " JOIN users ON appointments.User_ID=users.User_ID" +
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
                        rsA.getString("User_Name"),
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

    /**
     * This is the getMonthlyAppointments method.  This method sends a SELECT statement that gets all appointments in the
     * format of the first, non-overloaded, appointment constructor that fall within the current month and returns
     * appointmentList as an ObservableList.
     * @return Returns appointmentList as an ObservableList.
     */
    public static ObservableList<Appointment> getMonthlyAppointments(){
        try{
            DateTimeConverter.currentMonthParser();
            String appointmentGrab = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description," +
                    " appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, customers.Customer_Name, users.User_Name" +
                    " FROM appointments" +
                    " JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID" +
                    " JOIN customers ON appointments.Customer_ID=customers.Customer_ID" +
                    " JOIN users ON appointments.User_ID=users.User_ID" +
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
                        rsA.getString("User_Name"),
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

    /**
     * This is the hasAppointment method.  This method sends a SELECT statement that gets an appointment if it exists
     * for a user specified customer id.  If ther eis an appointment then the CustomerScreen.hasAppointment variable is
     * set to true, otherwise its set to false.
     * @throws SQLException
     */
    public static void hasAppointment() throws SQLException {
        hasAppointmentResult = false;
        CustomerScreen.hasAppointment = hasAppointmentResult;
        try {
            String hasAppointment = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description," +
                    " appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, customers.Customer_Name, users.User_Name" +
                    " FROM appointments" +
                    " JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID" +
                    " JOIN customers ON appointments.Customer_ID=customers.Customer_ID" +
                    " JOIN users ON appointments.User_ID=users.User_ID" +
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


    /**
     * This is the getAppointmentId method.  This is the getter method for appointmnetID.
     * @return Returns appointmnetId as an int.
     */
    public int getAppointmentId(){
        return appointmentID;
    }

    /**
     *This is the getTitle method.  This is the getter method for title.
     * @return Returns title as a String.
     */
    public String getTitle(){
        return title;
    }

    /**
     *This is the getDescription method.  This is the getter method for description
     * @return Returns description as a String.
     */
    public String getDescription(){
        return description;
    }

    /**
     *This is the getLocation method.  This is the getter method for location.
     * @return Returns location as a String.
     */
    public String getLocation(){
        return location;
    }

    /**
     *
     * @return
     */
    public String getContact(){
        return contact;
    }

    /**
     *This is the getUser method.  This is the getter method for user.
     * @return Returns user as a String.
     */
    public String getUser(){
        return user;
    }

    /**
     *This is the getType method.  This is the getter method for type.
     * @return Returns type as a String.
     */
    public String getType(){
        return type;
    }

    /**
     *This is the getStart method.  This is the getter method for start.
     * @return Returns start as a Timestamp.
     */
    public Timestamp getStart(){
        return start;
    }

    /**
     *This is the getEnd method.  This is the getter method for end.
     * @return Returns end as a Timestamp.
     */
    public Timestamp getEnd(){
        return end;
    }

    /**
     *This is the getCustomerName method.  This is the getter method for customerName.
     * @return Returns customerName as a String.
     */
    public String getCustomerName(){
        return customerName;
    }

    /**
     *This is the getContctId method.  This is the getter method for contactId.
     * @return Returns contactId as an int.
     */
    public int getContactId(){
        return contactId;
    }

    /**
     *This is the getCustomerId method.  This is the getter method for customerId.
     * @return Returns customerId as an int.
     */
    public int getCustomerId(){
        return customerId;
    }

    /**
     *This is the getUserId method.  This is the getter method for userId.
     * @return Returns userId as an int.
     */
    public int getUserId(){
        return userId;
    }

    /**
     * This is the setAppointmentID method.  This is the setter method for appointmentId.
     * @param newAppointmentID Takes int newAppointmentID.
     */
    public void setAppointmentID(int newAppointmentID) {
        this.appointmentID = newAppointmentID;
    }

    /**
     * This is the setCustomerId method.  This is the setter method for customerId.
     * @param newCustomerId Takes int newCustomerId.
     */
    public void setCustomerId(int newCustomerId) {
        this.customerId = newCustomerId;
    }

    /**
     * This is the setUserId method.  This is the setter method for userId.
     * @param newUserId Takes int newUserId.
     */
    public void setUserId(int newUserId) {
        this.userId = newUserId;
    }

    /**
     * This is the setContactId method.  This is the setter method for contactId.
     * @param newContactId Takes int newContactId.
     */
    public void setContactId(int newContactId) {
        this.contactId = newContactId;
    }

    /**
     * This is the setTitle method.  This is the setter method for title.
     * @param newTitle Takes int newTitle.
     */
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    /**
     * This is the setDescription method.  This is the setter method for description.
     * @param newDescription Takes int newDescription.
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * This is the setLocation method.  This is the setter method for location.
     * @param newLocation Takes int newLocation.
     */
    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    /**
     * This is the setContact method.  This is the setter method for contact.
     * @param newContact Takes int newContact.
     */
    public void setContact(String newContact) {
        this.contact = newContact;
    }

    /**
     * This is the setUser method.  This is the setter method for user.
     * @param newUser Takes int newUser.
     */
    public void setUser(String newUser) {
        this.user = newUser;
    }

    /**
     * This is the setType method.  This is the setter method for type.
     * @param newType Takes int newType.
     */
    public void setType(String newType) {
        this.type = newType;
    }

    /**
     * This is the setStart method.  This is the setter method for start.
     * @param newStartDateAndTime Takes int newStartDateAndTime.
     */
    public void setStart(Timestamp newStartDateAndTime) {
        this.start = newStartDateAndTime;
    }

    /**
     * This is the setEnd method.  This is the setter method for end.
     * @param newEndDateAndTime Takes int newEndDateAndTime.
     */
    public void setEnd(Timestamp newEndDateAndTime) {
        this.end = newEndDateAndTime;
    }

    /**
     * This is the setCustomerName method.  This is the setter method for customerName.
     * @param newCustomerName Takes int newCustomerName.
     */
    public void setCustomerName(String newCustomerName) {
        this.customerName = newCustomerName;
    }
}
