package model;

import javax.swing.*;
import controller.*;

public class Messages{

    public static int deleteAppointmentId; //CAN DELETE?

    //Messages

    public static void messageOne(String stringIDDateTime){
        JOptionPane.showMessageDialog(null, "User: " + LoginScreen.userNameInput + " has an appointment within 15 minutes.  " + stringIDDateTime, "Message-1", JOptionPane.WARNING_MESSAGE);
    }

    public static void messageOneFrench(String stringIDDateTime){
        JOptionPane.showMessageDialog(null, "Utilisateur: " + LoginScreen.userNameInput + " a un rendez-vous dans les 15 minutes.  " + stringIDDateTime, "Un message-1", JOptionPane.WARNING_MESSAGE);
    }


    public static void messageTwo(){
        JOptionPane.showMessageDialog(null,"You are about to delete all records associated with appointment ID: " + ScheduleScreen.deleteCandidateId + ", appointment type: " + ScheduleScreen.selectedType + ".  Press the delete button to finalize this transaction. ", "MESSAGE-2", JOptionPane.WARNING_MESSAGE);
    }

    public static void messageThree(){
        JOptionPane.showMessageDialog(null,"All records associated with appointment ID: " + ScheduleScreen.deleteCandidateId + ", appointment type: " + ScheduleScreen.selectedType + " have been deleted. ", "MESSAGE-3", JOptionPane.WARNING_MESSAGE);
    }

    public static void messageFour(){
        JOptionPane.showMessageDialog(null,"You are about to delete all records associated with Customer ID: " + CustomerScreen.deleteCandidateId + ".  Press the delete button to finalize this transaction. ", "MESSAGE-4", JOptionPane.WARNING_MESSAGE);
    }

    public static void messageFive(){
        JOptionPane.showMessageDialog(null,"All records associated with customer ID: " + CustomerScreen.deleteCandidateId + " have been deleted. ", "MESSAGE-5", JOptionPane.WARNING_MESSAGE);
    }

    public static void messageSix(){
        JOptionPane.showMessageDialog(null,"Selected customer has appointments that need to be deleted before this customer can be deleted.", "MESSAGE-6", JOptionPane.WARNING_MESSAGE);
    }

    public static void messageSeven(){
        JOptionPane.showMessageDialog(null, "User: " + LoginScreen.userNameInput + " does not have an appointment starting within 15 minutes.  ", "Message-7", JOptionPane.WARNING_MESSAGE);
    }

    public static void messageSevenFrench(){
        JOptionPane.showMessageDialog(null, "Utilisateur: " + LoginScreen.userNameInput + " n'a pas de rendez-vous à partir de 15 minutes.  ", "Un message-7", JOptionPane.WARNING_MESSAGE);
    }

    //Errors

    public static void errorOne(){//Working, in use in LoginScreen
        JOptionPane.showMessageDialog(null,"UNKNOWN USERNAME/PASSWORD COMBINATION!", "ERROR-1", JOptionPane.WARNING_MESSAGE);
    }

    public static void errorOneFrench(){//Working, in use in LoginScreen
        JOptionPane.showMessageDialog(null,"COMBINAISON NOM D'UTILISATEUR/MOT DE PASSE INCONNU !", "ERREUR-1", JOptionPane.WARNING_MESSAGE);
    }

    public static void errorTwo(){//Currently not working, AppointmentScreen
        JOptionPane.showMessageDialog(null,"No appointment selected for update.  Please select one appointment and press update.", "ERROR-2", JOptionPane.WARNING_MESSAGE);
    }

    public static void errorThree(){//Currently not working, CustomerScreen
        JOptionPane.showMessageDialog(null,"Only one customer can be updated at a time.  Please select one customer and press update.", "ERROR-3", JOptionPane.WARNING_MESSAGE);
    }

    public static void errorFour(){//Working, in use in CustomerScreen
        JOptionPane.showMessageDialog(null,"When deleting a customer all appointments assigned to that customer must be deleted first.", "ERROR-4", JOptionPane.WARNING_MESSAGE);
    }

    public static void errorFive(){//Working, in use in LoginScreen
        JOptionPane.showMessageDialog(null,"Unsupported language: Please change system language to English.  Veuillez changer la langue du systeme en francais. ", "ERROR-5", JOptionPane.WARNING_MESSAGE);
    }

    public static void errorSix(){
        JOptionPane.showMessageDialog(null,"Required fields left blank.", "ERROR-6", JOptionPane.WARNING_MESSAGE);
    }

    public static void errorSeven(){
        JOptionPane.showMessageDialog(null,"Start time must be before end time.", "ERROR-7", JOptionPane.WARNING_MESSAGE);
    }

    public static void errorEight(){
        JOptionPane.showMessageDialog(null,"This appointment overlaps with at least one other appointment assigned to the selected customer.", "ERROR-8", JOptionPane.WARNING_MESSAGE);
    }

    public static void errorNine(){
        JOptionPane.showMessageDialog(null,"This appointment Start or End time does not fall between 0800 EST and 2200 EST.", "ERROR-9", JOptionPane.WARNING_MESSAGE);
    }

    public static void infoBox(String infoMessage, String titleBar)
    {
        infoBox(infoMessage, titleBar);
    }
}
