//CURRENTLY WORKING
package controller;

/*
 USED IMPORT STATEMENTS
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import Interface.DBQuery;
import java.util.Locale;
import java.util.ResourceBundle;
import model.*;
import Interface.JDBC;
/*
 UNUSED IMPORT STATEMENTS
 */
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
/**
This is the LoginScreen class.
*/
public class LoginScreen implements Initializable {
    /*
    USED CLASS VARIABLES
    */
    @FXML
    public TextField textFieldLoginFormUser;
    @FXML
    private TextField textFieldLoginFormPassword;
    @FXML
    private Label labelForm;
    @FXML
    private Label labelUser;
    @FXML
    private Label labelPassword;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonReset;
    @FXML
    private Label labelLocation;
    @FXML
    private Label labelLanguage;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String userNameInput;
    public static String userPasswordInput;
    public static boolean isFrench = false;
    public final String FRENCH = "fr";
    public final String ENGLISH = "en";
    public static String language = Locale.getDefault().getLanguage();
    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;
    /*
    UNUSED CLASS VARIABLES
     */
    public static LocalDate convertedTime;
    public static LocalDate convertedTimePlus15;
    public static int timeUntilAppointment;

    /**
      This is the toCustomerScreen method.  This class tests a user/password combination against the database and, if true,
      launches the CustomerScreen.  If the user/password combination has a match in the database then the user id is tested
      against the next appointment start time to see if an appointment starts within 15 minutes.  If so a message is
      displayed stating this. Each time this method is initiated the login attempt is appended to the login_activity.txt file.

      @param event This method is executed based on the action of pressing the login button
     */
    public void toCustomerScreen(ActionEvent event) throws IOException, SQLException, ParseException {
        Appointment.getMonthlyAppointments();
        userNameInput = textFieldLoginFormUser.getText();
        userPasswordInput = textFieldLoginFormPassword.getText();
        String passwordCheck =  "SELECT * " +
                                "FROM users " +
                                "WHERE User_Name=? AND Password=?";
        DBQuery.setPreparedStatement(JDBC.conn, passwordCheck);
        PreparedStatement ps1 = DBQuery.getPreparedStatement();
        ps1.setString(1, userNameInput);
        ps1.setString(2, userPasswordInput);
        ps1.execute();
        ResultSet rs1 = ps1.getResultSet();
        try {
            if (rs1.next()) {
                try {
                    String appointmentCheck = "SELECT * " +
                            "FROM appointments " +
                            "JOIN users ON appointments.User_ID = users.User_ID " +
                            "WHERE User_Name=? AND CURRENT_TIMESTAMP < Start " +
                            "ORDER BY Start Asc " +
                            "LIMIT 1";
                    DBQuery.setPreparedStatement(JDBC.conn, appointmentCheck);
                    PreparedStatement ps2 = DBQuery.getPreparedStatement();
                    ps2.setString(1, userNameInput);
                    ps2.execute();
                    ResultSet rs2 = ps2.getResultSet();
                    String stringID;
                    if (rs2.next()) {
                        stringID = rs2.getString("Appointment_ID");
                        Timestamp apptTime = rs2.getTimestamp("Start");
                        if (DateTimeHandler.within15(apptTime) == true) {
                            if (language == ENGLISH) {
                                User.currentUser = userNameInput;
                                String stringIDDateTime = " Appointment ID: " + stringID + " Appointment Date/Time: " + apptTime;
                                model.Messages.messageOne(stringIDDateTime);
                                root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } else {
                                User.currentUser = userNameInput;
                                String stringIDDateTime = " Identifiant de rendez-vous : " + stringID + " Date/Heure de rendez-vous: " + apptTime;
                                model.Messages.messageOneFrench(stringIDDateTime);
                                root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            }
                        }
                        else if(language == ENGLISH){
                            User.currentUser = userNameInput;
                            Messages.messageSeven();
                            root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }
                        else {
                            User.currentUser = userNameInput;
                            Messages.messageSevenFrench();
                            root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }
                        try {
                            fw = new FileWriter("login_activity.txt", true);
                            bw = new BufferedWriter(fw);
                            pw = new PrintWriter(bw);
                            Timestamp timestamp = Timestamp.valueOf(DateTimeConverter.easternDateTime().toLocalDateTime());
                            pw.println(timestamp + " User: " + userNameInput + " successfully logged in.");
                            System.out.println("Data Successfully appended into file");
                            pw.flush();
                        } finally {
                            try {
                                pw.close();
                                bw.close();
                                fw.close();
                            } catch (IOException io) {
                            }
                        }
                    }
                    else{
                        root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }

                } catch (Exception e) {
                }
            }
            else {

                System.out.println("Good to here");
                        FileWriter fw = null;
                        BufferedWriter bw = null;
                        PrintWriter pw = null;
                        try {
                            fw = new FileWriter("login_activity.txt", true);
                            bw = new BufferedWriter(fw);
                            pw = new PrintWriter(bw);
                            Timestamp timestamp = Timestamp.valueOf(DateTimeConverter.easternDateTime().toLocalDateTime());
                            pw.println(timestamp + " User: " + userNameInput + " unsuccessfully attempted to log in.");
                            System.out.println("Data Successfully appended into file");
                            pw.flush();
                        } finally {
                            try {
                                pw.close();
                                bw.close();
                                fw.close();
                            } catch (IOException io) {
                            }
                        }
                if (language == ENGLISH) {
                    model.Messages.errorOne();
                } else {
                    model.Messages.errorOneFrench();
                }
            }
        } catch (SQLException e) {
        }
    }
    /**
     * This is the resetLoginScreen method.  It resets the LoginScreen to the original state.
     *
     * @param event This method is executed based on the action of pressing the reset button
     * @throws IOException
     */
    public void resetLoginScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This is the initialize method.  This method determines the starting condition of the Login
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (language == FRENCH) {
            isFrench = true;
            labelForm.setText("FORMULAIRE DE CONNEXION");
            labelUser.setText("Utilisateur:");
            labelPassword.setText("le Mot de Passe:");
            buttonLogin.setText("Connexion");
            buttonReset.setText("Réinitialiser");
            labelLocation.setText("Fuseau Horaire: " + java.time.ZoneId.systemDefault().toString());
            labelLanguage.setText("Langue: Français");
        } else if (language == ENGLISH) {
            isFrench = false;
            labelForm.setText("LOGIN FORM");
            labelUser.setText("User:");
            labelPassword.setText("Password:");
            buttonLogin.setText("Login");
            buttonReset.setText("Reset");
            labelLocation.setText("Time Zone: " + java.time.ZoneId.systemDefault().toString());
            labelLanguage.setText("Language: English");
        } else {
            Messages.errorFive();
            labelForm.setText("LOGIN FORM");
            labelUser.setText("User:");
            labelPassword.setText("Password:");
            buttonLogin.setText("Login");
            buttonReset.setText("Reset");
            labelLocation.setText("Time Zone: " + java.time.ZoneId.systemDefault().toString());
            labelLanguage.setText("Default Language: English");
        }
    }
}





