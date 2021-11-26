package model;

import java.sql.Timestamp;
/**
 This is the AppointmentReport interface.  This interface is used by lambda 9.
 */
public interface AppointmentReport {
    public void appointmentReport(String Customer_Name, String Type, Timestamp Start);

}