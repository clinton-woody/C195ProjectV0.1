package model;

import java.sql.Timestamp;
/**
 This is the ContactReport interface.  This interface is used by lambda 10.
 */
public interface ContactReport {
    public void contactReport(String Contact_Name, String Appointment_ID, String Title, String Type, String Description, Timestamp Start,Timestamp End);

}
