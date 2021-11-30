package model;

import Interface.DBQuery;
import controller.AppointmentUpdateForm2;
import controller.ScheduleScreen;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
/**
 This is the DateTimeHandler class.  This class has methods that check the validity of appointmnet times and replaces
 the DateTimeConverter.within15Min method with within15.
 */
public class DateTimeHandler {
    //CLASS VARIABLES
    public static Timestamp eastCandidateStart;
    public static Timestamp eastCandidateEnd;
    public static DateTimeFormatter HMFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static boolean within15 = false;
    public static boolean overlap = false;
    public static boolean validTime = false;
    public static boolean startEndMismatch = false;
    public static boolean eastTimeValid = false;//local to eastern time; eastern is before 0800 or eastern is after 2200; make a static variable for 0800 and 2200 timestamp as datetime

    /**
     * This is the within15 method.  This method takes an appointment start time as apptTime and checks to see if it is
     * within 15 minutes of current local time.
     * @param apptTime Takes Timestamp apptTime.
     * @return
     */
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

    /**
     * This is the validTime method.  This method takes a start time, end time, customer id, and appointment id and
     * calls the checkOverlap method to test the provided times for validity against every applicable appointment in
     * the database.
     * @param candidateStart Takes Timestamp candidateStart
     * @param candidateEnd Takes Timestamp candidateEnd
     * @param customerId Takes int customerId
     * @param appointmentId Takes String appointmentId
     * @return
     */
    public static boolean validTime(Timestamp candidateStart, Timestamp candidateEnd, int customerId, String appointmentId) {
        overlap = false;
        validTime = true;
        System.out.println("valid time set to " + validTime + " before parsing through the validTime method." );
        System.out.println("validTime triggered");//works
        //System.out.println(customerId);//back as 0 on new
        //System.out.println(appointmentId);//is null on new
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
            System.out.println("valid time executed");
            ResultSet rsVT = psVT.getResultSet();
            while (rsVT.next()) {
                    Timestamp apptEnd;
                    Timestamp apptStart;
                    apptStart = rsVT.getTimestamp("Start");
                    apptEnd = rsVT.getTimestamp("End");
                    //System.out.println("Beginning Overlap: " + overlap);
                   //System.out.println("just before isvalid call;");

                    boolean isValid = overlapChecker(apptStart, apptEnd, candidateStart, candidateEnd);
                    validTime = isValid;//if isValid == false, valid time == false
            }
        }catch (SQLException e){
        }
        System.out.println("valid time set to " + validTime + " after parsing through the validTime method." );
        return validTime;
    }

    /**
     * This is the overlapChecker method.  This method is called by the validTime method to check provided apptStart
     * and apptEnd against provided candidateStart and candidateEnd times to see if the times overlap each other.
     * @param apptStart Takes Timestamp apptStart
     * @param apptEnd Takes Timestamp apptEnd
     * @param candidateStart Takes Timestamp candidateStart
     * @param candidateEnd Takes Timestamp candidateEnd
     * @return Returns overlap boolean.
     */
    public static boolean overlapChecker(Timestamp apptStart, Timestamp apptEnd, Timestamp candidateStart, Timestamp candidateEnd){//#not triggering on new
        System.out.println("Overlap flag set to " + overlap + "before parsing through the overlap checker.");
        System.out.println("overlapChecker triggered");//not on new
        LocalDateTime aStart = apptStart.toLocalDateTime();
        LocalDateTime aEnd = apptEnd.toLocalDateTime();
        LocalDateTime cStart = candidateStart.toLocalDateTime();
        LocalDateTime cEnd = candidateEnd.toLocalDateTime();
        //System.out.println("aStart: " + aStart);
        //System.out.println("aEnd: " + aEnd);
        //System.out.println("cStart: " + cStart);
        //System.out.println("cEnd: " + cEnd);
        //Need to verify this works
        if ((cStart.isEqual(aStart) || cStart.isAfter(aStart)) && (cStart.isEqual(aEnd) || cStart.isBefore(aEnd))){//overlap test 1
            System.out.println("Overlap test 1 triggered detected an overlap.");
            overlap = true;
        }
        else if ((cEnd.isEqual(aStart) || cEnd.isAfter(aStart)) && (cEnd.isEqual(aEnd) || cEnd.isBefore(aEnd))){//overlap test 2
            System.out.println("Overlap test 2 triggered detected an overlap.");
            overlap = true;
        }
        else if ((cStart.isEqual(aStart) || cStart.isBefore(aStart)) && (cEnd.isEqual(aEnd) || cEnd.isAfter(aEnd))){//overlap test 3
            System.out.println("Overlap test 3 triggered detected an overlap.");
            overlap = true;
        }
        else{
            System.out.println("No overlap detected.");
            overlap = false;
        }
        /*
        if ((cStart.isEqual(aStart) || cStart.isAfter(aStart)) && (cStart.isEqual(aEnd) || cStart.isBefore(aEnd)) ||
                ((cEnd.isEqual(aStart) || cEnd.isAfter(aStart)) && (cEnd.isEqual(aEnd) || cEnd.isBefore(aEnd)) ||
                        ((cStart.isBefore(aEnd)) && cEnd.isAfter(aStart)) || cEnd.isAfter(aStart) && cEnd.isBefore(aEnd))){
            overlap = true;
        } //Old just in case
        */
        if (overlap == true){
            System.out.println("Overlap flag set to " + overlap + "after parsing through the overlap checker.");
            validTime = false;
        }
        //System.out.println("End Overlap: " + overlap);//Testing only
        return validTime;
    }

    /**
     * This is the startEndMismatch method.  This method checks to ensure that the proposed start time is before the
     * proposed end time.
     * @param candidateStart Takes Timestamp candidateStart.
     * @param candidateEnd Takes Timestamp candidateEnd.
     * @return Returns startEndMismatch boolean.
     */
    public static boolean startEndMismatch (Timestamp candidateStart, Timestamp candidateEnd){
        startEndMismatch = false;
        LocalDateTime cStart = candidateStart.toLocalDateTime();
        LocalDateTime cEnd = candidateEnd.toLocalDateTime();
        if ((cStart.isEqual(cEnd)) || (cStart.isAfter(cEnd))) {
            startEndMismatch = true;
        }
        return startEndMismatch;
    }

    /**
     * This is the eastTimeValid method.  This method checks to ensure that the start time proposed is not before 0800
     * and proposed end time is not after 2200.
     * @param dateString Takes String dateString.
     * @param candidateStart Takes Timestamp candidateStart.
     * @param candidateEnd Takes Timestamp candidateEnd.
     * @return eastTimeValid boolean
     */
    public static boolean eastTimeValid(String dateString, Timestamp candidateStart, Timestamp candidateEnd){
        eastTimeValid = true;
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId ltZoneId = ZoneId.of(java.time.ZoneId.systemDefault().toString());
        String string0800 = dateString + " 08:00";//change to dateString
        String string2200 = dateString + " 22:00";//change to dateString
        ZonedDateTime MINTIME = ZonedDateTime.of(LocalDateTime.parse(string0800, HMFormatter), etZoneId);//CONSTANT MIN APPT TIME
        ZonedDateTime MAXTIME = ZonedDateTime.of(LocalDateTime.parse(string2200, HMFormatter), etZoneId);//CONSTANT MAX APPT TIME
        ZonedDateTime CONMIN = MINTIME.withZoneSameInstant(ltZoneId);//Convert MINTIME from EST to system time as CONMIN
        ZonedDateTime CONMAX = MAXTIME.withZoneSameInstant(ltZoneId);//Convert MAXTIME from EST to system time as CONMAX
        LocalDateTime LOCCONMIN = CONMIN.toLocalDateTime();//Converts CONMIN from ZonedDateTime to LocalDateTime as LOCCONMIN
        LocalDateTime LOCCONMAX = CONMAX.toLocalDateTime();//Converts CONMAX from ZonedDateTime to LocalDateTime as LOCCONMAX
        LocalDateTime cStart = candidateStart.toLocalDateTime();
        LocalDateTime cEnd = candidateEnd.toLocalDateTime();
        if ((cStart.isBefore(LOCCONMIN) || cStart.isAfter(LOCCONMAX)) || (cEnd.isBefore(LOCCONMIN) || cEnd.isAfter(LOCCONMAX))){
            eastTimeValid = false;
        }
        else{
            eastTimeValid = true;
        }
        //System.out.println(LOCCONMIN);
        //System.out.println(LOCCONMAX);
        //System.out.println("eastTimeValid " + eastTimeValid);
        return eastTimeValid;
    }

    /**
     * This is the eastCandidateStart method.  This method converts a start local time to eastern time.
     */
    public static void eastCandidateStart(){
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId ltZoneId = ZoneId.of(java.time.ZoneId.systemDefault().toString());
        LocalDateTime preConvert =  AppointmentUpdateForm2.candidateStart.toLocalDateTime();
        ZonedDateTime zConvert = preConvert.atZone(ltZoneId);
        //System.out.println("local" + zConvert);
        ZonedDateTime z2Convert = zConvert.withZoneSameInstant(etZoneId);
        eastCandidateStart = Timestamp.valueOf(z2Convert.toLocalDateTime());
        //System.out.println("eastern" + z2Convert);
        AppointmentUpdateForm2.candidateStart = eastCandidateStart;
    }

    /**
     * This is the eastCandidateEnd method.  This method converts a local time to eastern time.
     */
    public static void eastCandidateEnd(){
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId ltZoneId = ZoneId.of(java.time.ZoneId.systemDefault().toString());
        LocalDateTime preConvert = AppointmentUpdateForm2.candidateEnd.toLocalDateTime();
        ZonedDateTime zConvert = preConvert.atZone(ltZoneId);
        //System.out.println("local" + zConvert);
        ZonedDateTime z2Convert = zConvert.withZoneSameInstant(etZoneId);
        eastCandidateEnd = Timestamp.valueOf(z2Convert.toLocalDateTime());
        //System.out.println("eastern" + z2Convert);
        AppointmentUpdateForm2.candidateEnd = eastCandidateEnd;
    }

    /**
     * This is the eastCandidateEnd method.  This method converts a local time to eastern time.
     */
    public static void selectedDateStart(){
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId ltZoneId = ZoneId.of(java.time.ZoneId.systemDefault().toString());
        LocalDateTime preConvert = AppointmentUpdateForm2.selectedStart;
        ZonedDateTime zConvert = preConvert.atZone(etZoneId);
        ZonedDateTime z2Convert = zConvert.withZoneSameInstant(ltZoneId);
        AppointmentUpdateForm2.selectedStart = z2Convert.toLocalDateTime();
    }

    /**
     * This is the eastCandidateEnd method.  This method converts a local time to eastern time.
     */
    public static void selectedDateEnd(){
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId ltZoneId = ZoneId.of(java.time.ZoneId.systemDefault().toString());
        LocalDateTime preConvert = AppointmentUpdateForm2.selectedEnd;
        ZonedDateTime zConvert = preConvert.atZone(etZoneId);
        ZonedDateTime z2Convert = zConvert.withZoneSameInstant(ltZoneId);
        AppointmentUpdateForm2.selectedEnd = z2Convert.toLocalDateTime();
    }
}

/*
        if ((cStart.isEqual(aStart) || cStart.isAfter(aStart)) && (cStart.isEqual(aEnd) || cStart.isBefore(aEnd))
            ||
            (cEnd.isEqual(aStart) || cEnd.isAfter(aStart)) && (cEnd.isEqual(aEnd) || cEnd.isBefore(aEnd))
            ||
            (cStart.isEqual(aStart) || cStart.isBefore(aStart)) && (cEnd.isEqual(aEnd)) || cEnd.isAfter(aEnd)){
            overlap = true;

        }
        else{
            overlap = false;
        }
*/