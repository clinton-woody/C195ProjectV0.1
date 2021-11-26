package model;

import javax.swing.*;
import controller.*;
/**
 This is the Messages class.  This class handles system messages and errors.
 */
public class Messages{
    public static int deleteAppointmentId; //CAN DELETE?

    /**
     * This is the messageOne method.  This message outputs that an appointment exists whithin 15 minutes when called as a popup.
     * @param stringIDDateTime Injects stringIDDateTime into the outputted message string.
     */
    public static void messageOne(String stringIDDateTime){
        JOptionPane.showMessageDialog(null, "User: " + LoginScreen.userNameInput + " has an appointment within 15 minutes.  " + stringIDDateTime, "Message-1", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This is the messageOneFrench method.  This message outputs that an appointment exists whithin 15 minutes when called as a popup in french.
     * @param stringIDDateTime Injects stringIDDateTime into the outputted message string.
     */
    public static void messageOneFrench(String stringIDDateTime){
        JOptionPane.showMessageDialog(null, "Utilisateur: " + LoginScreen.userNameInput + " a un rendez-vous dans les 15 minutes.  " + stringIDDateTime, "Un message-1", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the messageTwo method.  This message outputs that all records associated with an appointment id are about
     *to be deleted.  The id is called from ScheduleScreen.deleteCandidateId and the type ScheduleScreen.selectedType.
     */
    public static void messageTwo(){
        JOptionPane.showMessageDialog(null,"You are about to delete all records associated with appointment ID: " + ScheduleScreen.deleteCandidateId + ", appointment type: " + ScheduleScreen.selectedType + ".  Press the delete button to finalize this transaction. ", "MESSAGE-2", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the messageThree method.  This message outputs that all records associated with a given appointment id and
     *appointment type have been deleted.  The id is called from ScheduleScreen.deleteCandidateId and the type
     *ScheduleScreen.selectedType.
     */
    public static void messageThree(){
        JOptionPane.showMessageDialog(null,"All records associated with appointment ID: " + ScheduleScreen.deleteCandidateId + ", appointment type: " + ScheduleScreen.selectedType + " have been deleted. ", "MESSAGE-3", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the messageFour method.  This message outputs that all records associated with a given customer id are
     *about to be deleted.  The id is called from CustomerScreen.deleteCandidateId.
     */
    public static void messageFour(){
        JOptionPane.showMessageDialog(null,"You are about to delete all records associated with Customer ID: " + CustomerScreen.deleteCandidateId + ".  Press the delete button to finalize this transaction. ", "MESSAGE-4", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the messageFive method.  This message outputs that all records associated with a given customer id have
     * been deleted.  The id is called from CustomerScreen.deleteCandidateId.
     */
    public static void messageFive(){
        JOptionPane.showMessageDialog(null,"All records associated with customer ID: " + CustomerScreen.deleteCandidateId + " have been deleted. ", "MESSAGE-5", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the messageSix method.  This message outputs that the selected customer has appointments that need to be
     *deleted before the customer can be deleted.
     */
    public static void messageSix(){
        JOptionPane.showMessageDialog(null,"Selected customer has appointments that need to be deleted before this customer can be deleted.", "MESSAGE-6", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the messageSeven method.  This message outputs that the user does not have an appointment within 15
     *minutes.  The name is called from LoginScreen.userNameInput.
     */
    public static void messageSeven(){
        JOptionPane.showMessageDialog(null, "User: " + LoginScreen.userNameInput + " does not have an appointment starting within 15 minutes.  ", "Message-7", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the messageSevenFrench method.  This message outputs that the user does not have an appointment within 15
     *minutes in french.  The name is called from LoginScreen.userNameInput.
     */
    public static void messageSevenFrench(){
        JOptionPane.showMessageDialog(null, "Utilisateur: " + LoginScreen.userNameInput + " n'a pas de rendez-vous à partir de 15 minutes.  ", "Un message-7", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorOne method.  This error outputs that an unknown user name password combination has been provided.
     */
    public static void errorOne(){//Working, in use in LoginScreen
        JOptionPane.showMessageDialog(null,"UNKNOWN USERNAME/PASSWORD COMBINATION!", "ERROR-1", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorOneFrench method.  This error outputs that an unknown user name password combination has been
     *provided in french.
     */
    public static void errorOneFrench(){//Working, in use in LoginScreen
        JOptionPane.showMessageDialog(null,"COMBINAISON NOM D'UTILISATEUR/MOT DE PASSE INCONNU !", "ERREUR-1", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorTwo method.  This error states that no appointment has been selected for update.
     */
    public static void errorTwo(){//Currently not working, AppointmentScreen
        JOptionPane.showMessageDialog(null,"No appointment selected for update.  Please select one appointment and press update.", "ERROR-2", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorThree method.  This error states that only one customer can be updated at a time.
     */
    public static void errorThree(){//Currently not working, CustomerScreen
        JOptionPane.showMessageDialog(null,"Only one customer can be updated at a time.  Please select one customer and press update.", "ERROR-3", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorFour method.  This error states that when deleting a customer all appointments assigned to that
     *customer must be deleted first.
     */
    public static void errorFour(){//Working, in use in CustomerScreen
        JOptionPane.showMessageDialog(null,"When deleting a customer all appointments assigned to that customer must be deleted first.", "ERROR-4", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorFive method.  This error states that english or french must be selected as a system language.
     */
    public static void errorFive(){//Working, in use in LoginScreen
        JOptionPane.showMessageDialog(null,"Unsupported language: Please change system language to English.  Veuillez changer la langue du systeme en francais. ", "ERROR-5", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorSix method.  This error states that required fields are left blank.
     */
    public static void errorSix(){
        JOptionPane.showMessageDialog(null,"Required fields left blank.", "ERROR-6", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorSeven method.  This error states that start time must be before end time.
     */
    public static void errorSeven(){
        JOptionPane.showMessageDialog(null,"Start time must be before end time.", "ERROR-7", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorEight method.  This error states that an appointment overlaps another appointment
     *
     */
    public static void errorEight(){
        JOptionPane.showMessageDialog(null,"This appointment overlaps with at least one other appointment assigned to the selected customer.", "ERROR-8", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *This is the errorNine method.  This error states that an appointment falls outside allowable times.
     */
    public static void errorNine(){
        JOptionPane.showMessageDialog(null,"This appointment Start or End time does not fall between 0800 EST and 2200 EST.", "ERROR-9", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This is the infoBox method.  This is the constructor for infoBoxes.
     * @param infoMessage This is the body of the message or error.
     * @param titleBar This is the titleBar of the message.
     */
    public static void infoBox(String infoMessage, String titleBar)
    {
        infoBox(infoMessage, titleBar);
    }
}
