package model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * This is the DateTimeConverter class.  This class contains methods relating to checking time measurements in relation
 * to the current date and time.
 */
public class DateTimeConverter {
    public static String ZEROTIME = " 00:00:00";  //NEEDED
    public static LocalDateTime currentSun;  //NEEDED
    public static LocalDateTime nextSun;  //NEEDED
    public static LocalDateTime firstDay;  //NEEDED
    public static LocalDateTime afterLastDay;  //NEEDED
    public static LocalDateTime currentDay = LocalDateTime.now();  //NEEDED
    public static int currentDayInt = getDayNumberOld(DateTimeConverter.currentDay);  //NEEDED
    public static int currentDayOfMonth = LocalDateTime.now().getDayOfMonth();  //NEEDED
    public static int currentMonth = LocalDateTime.now().getMonthValue();  //NEEDED
    public static int currentSunInt = currentDayInt - 1;  //NEEDED
    private static final String DATE_FORMATTER= "yyyy-MM-dd";  //NEEDED
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);  //NEEDED
    public static String nextSunString = "unchanged";  //NEEDED
    public static String currentSunString =  "unchanged";  //NEEDED
    public static String firstDayString = "unchanged";  //NEEDED
    public static String afterLastDayString =  "unchanged";  //NEEDED
//    public static Timestamp currentSunTS;
//    public static Timestamp nextSunTS;
    public static Timestamp firstDayTS;  //NEEDED
    public static Timestamp afterLastDayTS;  //NEEDED

    /**
     * This is the currentWeekParser method.  This method takes the local day and compares it against the currentSunInt
     * to determine what date the current weeks Sunday is.  It then creates nextSun as 7 days from currentSun.  It sets
     * variables as nextSun, currentSun, nextSunString, and currentSunString.
     *
     * @return Returns currentSun as LocalDateTime variable.
     */
    public static LocalDateTime currentWeekParser(){//works
//    public static String currentWeekParser(){
        if (currentSunInt == 0){
            currentSun = currentDay;
        }else if(currentSunInt == 1){
            currentSun = currentDay.minusDays(1);
        }else if(currentSunInt == 2){
            currentSun = currentDay.minusDays(2);
        }else if(currentSunInt == 3){
            currentSun = currentDay.minusDays(3);
        }else if(currentSunInt == 4){
            currentSun = currentDay.minusDays(4);
        }else if(currentSunInt == 5){
            currentSun = currentDay.minusDays(5);
        }else if(currentSunInt == 6){
            currentSun = currentDay.minusDays(6);
        }
        nextSun = currentSun.plusDays(7);
        nextSunString = nextSun.format(formatter) + ZEROTIME;
        currentSunString = currentSun.format(formatter) + ZEROTIME;
 //       nextSunTS = Timestamp.valueOf(nextSunString);
 //       currentSunTS = Timestamp.valueOf(currentSunString);
        System.out.println(currentSun);
        return currentSun;

    }

    /**
     * This is the currentMonthParser method.  This method takes the currentDayOfMonth and, based on the current month,
     * determines first and last day of that month.  It sets variables as afterLastDayString, firstDayString,
     * firstDayTS, afterLastDayTS.
     *
     * @return Returns firstDay as LocalDateTime variable.
     */
    public static LocalDateTime currentMonthParser(){//works
        int firstDayOfMonthModifier = currentDayOfMonth - 1;
        long currentDayOfMonthLong = firstDayOfMonthModifier;
        System.out.println(currentDayOfMonthLong);
        firstDay = currentDay.minusDays(firstDayOfMonthModifier);
        if (currentMonth == 1 || currentMonth == 3 ||currentMonth == 5 || currentMonth == 7 || currentMonth == 8 ){
            afterLastDay = firstDay.plusDays(31);
        }else if(currentMonth == 4 || currentMonth == 6 ||currentMonth == 9 || currentMonth == 10 || currentMonth == 11 || currentMonth == 12 ){
            afterLastDay = firstDay.plusDays(30);
        }else if (LocalDateTime.now().getYear()%4 == 0){
            afterLastDay = firstDay.plusDays(29);
        }else{
            afterLastDay = firstDay.plusDays(28);
        }
        afterLastDayString = afterLastDay.format(formatter) + ZEROTIME;
        firstDayString = firstDay.format(formatter) + ZEROTIME;
        firstDayTS =  Timestamp.valueOf(firstDayString);
        afterLastDayTS =  Timestamp.valueOf(afterLastDayString);
        return firstDay;
    }


    /**
     * This is the getDayNumberOld method.  This method returns the current weekday as an int for use by other methods
     * within this class.
     *
     * @param date
     *
     * @return  Returns int that represents the current day of the week.
     */
    public static int getDayNumberOld(LocalDateTime date) {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * This is the easternDateTime method.  This method returns eastern time for use by other methods.
     *
     * @return Returns ZonedDateTime that represents what eastern time is from current local time.
     */
    public static ZonedDateTime easternDateTime()//provides eastern time at tile method is called
    {
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZonedDateTime easternDateTime = ZonedDateTime.now().withZoneSameInstant(etZoneId);
        return easternDateTime;
        //Works; returns eastern time as ZonedDateTime
    }

    /**
     * This is the easternDateTimeStamp method.    This method returns eastern time as a Timestamp for use by other
     * methods.
     *
     * @return Returns Timestamp that represents easternDateTime
     */
    public static Timestamp easternDateTimeStamp()//COMPLETE AND WORKING WITH CUSTOMER UPDATE FORM NEW
    {
        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZonedDateTime easternDateTime = ZonedDateTime.now().withZoneSameInstant(etZoneId);
        Timestamp timestamp = Timestamp.valueOf(easternDateTime.toLocalDateTime());
        return timestamp;
        //Works; returns eastern time as timestamp
    }

    /**
     * This is the within15Min method.  This method checks if a time is within 15 minutes of current eastern standard
     * time and returns a boolean.
     *
     * @param inputDate
     *
     * @return Returns a boolean to be used by other methods.
     */
    public static boolean within15Min(String inputDate)
    {
        boolean within15Minutes = false;
        ZonedDateTime easternDateTime = DateTimeConverter.easternDateTime();
        String stringInputTime = inputDate;
        String stringEasternTime = easternDateTime.toString();
        String newStringInputTime = stringInputTime.substring(14, 16);
        String newStringEasternTime = stringEasternTime.substring(14, 16);
        int w = Integer.parseInt(newStringInputTime);
        int x = Integer.parseInt(newStringEasternTime);
        int y = x + 15;
        int z = 0;
        if (w + 59 < 75)
        {
            z = w + 59;
        }
        else
        {
            z = 100;
        }
        for (x = x; x <= y; x++)
        {
            if (x == w || w == z)
            {
                within15Minutes = true;
            }
//            System.out.print(w + "|");//test only
//            System.out.print(x + "|");//test only
//            System.out.println(y);//test only
//            System.out.println(within15Minutes);//test only
        }
        return within15Minutes;
    }
/*  #check. This is commented out during javadoc commenting.
    public static ZonedDateTime testOnly()//provides eastern time at tile method is called
    {

        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZonedDateTime easternDateTime = ZonedDateTime.now().withZoneSameInstant(etZoneId);
        ZonedDateTime e10 = easternDateTime.plusMinutes(10);
        ZonedDateTime e15 = easternDateTime.plusMinutes(15);
        ZonedDateTime e20 = easternDateTime.plusMinutes(20);
        return e10;
    }
*/
}

