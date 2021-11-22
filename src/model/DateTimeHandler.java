package model;

import Interface.DBQuery;
import controller.ScheduleScreen;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHandler {
    public static boolean within15 = false;
    public static boolean overlap = false;
    public static boolean validTime = true;
    public static boolean startEndMismatch = false;
    public static boolean eastTimeValid = false;//local to eastern time; eastern is before 0800 or eastern is after 2200; make a static variable for 0800 and 2200 timestamp as datetime

    public static boolean within15(Timestamp apptTime) {
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(now);
        LocalDateTime now15 = now.plusMinutes(15);
        //System.out.println(now15);
        LocalDateTime aTime = apptTime.toLocalDateTime();
        //System.out.println(aTime);
        if (now.isEqual(aTime) || now.isBefore(aTime)) {
            if (now15.isEqual(aTime) || now15.isAfter(aTime)) {
                within15 = true;
            }

        } else {
            within15 = false;
        }
        //System.out.println(within15);
        return within15;

    }

    public static boolean validTime(Timestamp candidateStart, Timestamp candidateEnd, int customerId, String appointmentId) {
        overlap = false;
        validTime = true;

        try {
            String validTimeQuery = "SELECT Start, End " +
                                    "FROM appointments " +
                                    "WHERE Customer_ID = ? " +
                                    "AND Appointment_Id <> ?";

            DBQuery.setPreparedStatement(Interface.JDBC.conn, validTimeQuery);
            PreparedStatement psVT = DBQuery.getPreparedStatement();
            psVT.setInt(1, customerId);
            psVT.setString(2 ,appointmentId);
            psVT.execute();
            ResultSet rsVT = psVT.getResultSet();
            while (rsVT.next()) {
                Timestamp apptEnd;
                Timestamp apptStart;
                apptStart = rsVT.getTimestamp("Start");
                apptEnd = rsVT.getTimestamp("End");
                //System.out.println("Beginning Overlap: " + overlap);
                boolean isValid = overlapChecker(apptStart, apptEnd, candidateStart, candidateEnd);
                validTime = isValid;
            }
        }catch (SQLException e){
        }
        return validTime;
    }

    public static boolean overlapChecker(Timestamp apptStart, Timestamp apptEnd, Timestamp candidateStart, Timestamp candidateEnd){
        LocalDateTime aStart = apptStart.toLocalDateTime();
        LocalDateTime aEnd = apptEnd.toLocalDateTime();
        LocalDateTime cStart = candidateStart.toLocalDateTime();
        LocalDateTime cEnd = candidateEnd.toLocalDateTime();
        //System.out.println(aStart);
        //System.out.println(aEnd);
        //System.out.println(cStart);
        //System.out.println(cEnd);
        if ((cStart.isEqual(aStart) || cStart.isAfter(aStart)) && (cStart.isEqual(aEnd) || cStart.isBefore(aEnd)) || ((cEnd.isEqual(aStart) || cEnd.isAfter(aStart)) && (cEnd.isEqual(aEnd) || cEnd.isBefore(aEnd)) || ((cStart.isBefore(aStart)) && cEnd.isAfter(aEnd)))){
                overlap = true;
        }
        if (overlap == true){
            validTime = false;
        }
        //System.out.println("End Overlap: " + overlap);//Testing only
        return validTime;
    }

    public static boolean startEndMismatch (Timestamp candidateStart, Timestamp candidateEnd){
        startEndMismatch = false;
        LocalDateTime cStart = candidateStart.toLocalDateTime();
        LocalDateTime cEnd = candidateEnd.toLocalDateTime();
        if ((cStart.isEqual(cEnd)) || (cStart.isAfter(cEnd))) {
            startEndMismatch = true;
        }
        return startEndMismatch;
    }

}
