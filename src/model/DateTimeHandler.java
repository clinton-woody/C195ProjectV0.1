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

public class DateTimeHandler {
    public static Timestamp eastCandidateStart;
    public static Timestamp eastCandidateEnd;
    public static DateTimeFormatter HMFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
/* Maybe ZoneOffset*/
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
        System.out.println(LOCCONMIN);
        System.out.println(LOCCONMAX);
        System.out.println("eastTimeValid " + eastTimeValid);
        return eastTimeValid;
    }

    public static void eastCandidateStart(){
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId ltZoneId = ZoneId.of(java.time.ZoneId.systemDefault().toString());
        LocalDateTime preConvert =  AppointmentUpdateForm2.candidateStart.toLocalDateTime();
        ZonedDateTime zConvert = preConvert.atZone(ltZoneId);
        System.out.println("local" + zConvert);
        ZonedDateTime z2Convert = zConvert.withZoneSameInstant(etZoneId);
        eastCandidateStart = Timestamp.valueOf(z2Convert.toLocalDateTime());
        System.out.println("eastern" + z2Convert);
        AppointmentUpdateForm2.candidateStart = eastCandidateStart;
    }

    public static void eastCandidateEnd(){
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId ltZoneId = ZoneId.of(java.time.ZoneId.systemDefault().toString());
        LocalDateTime preConvert = AppointmentUpdateForm2.candidateEnd.toLocalDateTime();
        ZonedDateTime zConvert = preConvert.atZone(ltZoneId);
        System.out.println("local" + zConvert);
        ZonedDateTime z2Convert = zConvert.withZoneSameInstant(etZoneId);
        eastCandidateEnd = Timestamp.valueOf(z2Convert.toLocalDateTime());
        System.out.println("eastern" + z2Convert);
        AppointmentUpdateForm2.candidateEnd = eastCandidateEnd;
    }

    public static void selectedDateStart(){
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId ltZoneId = ZoneId.of(java.time.ZoneId.systemDefault().toString());
        LocalDateTime preConvert = AppointmentUpdateForm2.selectedStart;
        ZonedDateTime zConvert = preConvert.atZone(etZoneId);
        ZonedDateTime z2Convert = zConvert.withZoneSameInstant(ltZoneId);
        AppointmentUpdateForm2.selectedStart = z2Convert.toLocalDateTime();
    }

    public static void selectedDateEnd(){
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZoneId ltZoneId = ZoneId.of(java.time.ZoneId.systemDefault().toString());
        LocalDateTime preConvert = AppointmentUpdateForm2.selectedEnd;
        ZonedDateTime zConvert = preConvert.atZone(etZoneId);
        ZonedDateTime z2Convert = zConvert.withZoneSameInstant(ltZoneId);
        AppointmentUpdateForm2.selectedEnd = z2Convert.toLocalDateTime();
    }




}
