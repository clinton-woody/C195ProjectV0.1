package model;

import java.sql.Timestamp;

public interface ContactReport {
    public void contactReport(String Contact_Name, String Appointment_ID, String Title, String Type, String Description, Timestamp Start,Timestamp End);

}

/*
                            rsA.getString("Appointment_ID"),
                            rsA.getString("Title"),
                            rsA.getString("Type"),
                            rsA.getString("Description"),
                            rsA.getTimestamp("Start"),
                            rsA.getTimestamp("End")
 */