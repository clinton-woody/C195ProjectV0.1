package model;

import java.sql.Timestamp;

public interface LocationReport {
    public void locationReport(String Location, String Title, String Contact_Name, String Customer_Name);

}

/*
            String locationReport = "SELECT appointments.Location, appointments.Title, contacts.Contact_Name, customers.Customer_Name " +
                    "FROM appointments " +
                    "JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID " +
                    "JOIN customers ON appointments.Customer_ID=cust
 */