package model;

import java.sql.Timestamp;

public interface AppointmentReport {
    public void appointmentReport(String Customer_Name, String Type, Timestamp Start);

}