package model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHandler {
    public static boolean within15 = false;

    public static boolean within15(Timestamp apptTime){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDateTime now15 = now.plusMinutes(15);
        System.out.println(now15);
        LocalDateTime aTime = apptTime.toLocalDateTime();
        System.out.println(aTime);
        if (now.isEqual(aTime) || now.isBefore(aTime)){
            if (now15.isEqual(aTime) || now15.isAfter(aTime)){
                within15 = true;
            }

        }
        else{
            within15 = false;
        }
System.out.println(within15);
        return within15;

    }



}
/*
                            Timestamp apptTime = rs2.getTimestamp("Start");
                            //Timestamp convApptTime = apptTime.valueOf(apptTime.toLocalDateTime().plusMinutes(15));
                            LocalDateTime now = LocalDateTime.now();
                            Timestamp localTime = Timestamp.valueOf(now);
                            Timestamp localTime15 = localTime.valueOf(localTime.toLocalDateTime().plusMinutes(15));
 */