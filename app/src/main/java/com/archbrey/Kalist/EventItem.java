package com.archbrey.Kalist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EventItem {

    public Integer ID;
    public String Title;
    public String Description;
    public Calendar StartDate;
    public Calendar StopDate;

    private static final SimpleDateFormat dayOfWeekF = new SimpleDateFormat("EEE");
    private static final SimpleDateFormat dayOfMonthF = new SimpleDateFormat("dd");
    private static final SimpleDateFormat dayOfYearF = new SimpleDateFormat("D");
    private static final SimpleDateFormat MonthF = new SimpleDateFormat("M");
    private static final SimpleDateFormat YearF = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat WeekF = new SimpleDateFormat("w");
    private static final SimpleDateFormat HourF = new SimpleDateFormat("HH");
    private static final SimpleDateFormat MinuteF = new SimpleDateFormat("mm");


    public EventItem(String getID, String getTitle, String getDescription, String getStart, String getStop){

        StartDate = new GregorianCalendar();
        StopDate = new GregorianCalendar();

        Long startDate;
        Long stopDate;

        startDate = Long.parseLong(getStart);
        if (getStop!= null)  stopDate = Long.parseLong(getStop);
        else stopDate = null;

        StartDate.setTimeInMillis(startDate);
        if ( stopDate != null) StopDate.setTimeInMillis(stopDate);

        Title = getTitle;
        Description = getDescription;
        ID = Integer.valueOf(getID);

    } //public EventItem()

    /*
    public Integer Year(boolean isGetStart){

        Integer extractYear;
        String dateString;

        if (isGetStart) dateString = YearF.format(StartDate.getTime());
        else dateString = YearF.format(StopDate.getTime());

        extractYear = Integer.valueOf(dateString);

        return extractYear;
    } //public Integer Month()
    */

    public Integer Month(boolean isGetStart){

        Integer extractMonth;
        String dateString;

        if (isGetStart) dateString = MonthF.format(StartDate.getTime());
                else dateString = MonthF.format(StopDate.getTime());

        extractMonth = Integer.valueOf(dateString);

        return extractMonth;
    } //public Integer Month()

    public Integer Week(boolean isGetStart){

        Integer extractWeek;
        String dateString;

        if (isGetStart) dateString = WeekF.format(StartDate.getTime());
        else dateString = WeekF.format(StopDate.getTime());

        extractWeek = Integer.valueOf(dateString);

        return extractWeek;

    } //public Integer Week()

    public Integer dayOfMonth(boolean isGetStart){

        Integer extractdayOfMonth;
        String dateString;

        if (isGetStart) dateString = dayOfMonthF.format(StartDate.getTime());
        else dateString = dayOfMonthF.format(StopDate.getTime());

        extractdayOfMonth = Integer.valueOf(dateString);

        return extractdayOfMonth;
    } //public Integer dayOfMonth()

    public Integer dayOfYear(boolean isGetStart){

        Integer extractdayOfYear;
        String dateString;

        if (isGetStart) dateString = dayOfYearF.format(StartDate.getTime());
        else dateString = dayOfYearF.format(StopDate.getTime());

        extractdayOfYear = Integer.valueOf(dateString);

        return extractdayOfYear;
    } //public Integer dayOfMonth()


    public String dayOfWeek(boolean isGetStart){

        String extractdayOfWeek;

        if (isGetStart) extractdayOfWeek = dayOfWeekF.format(StartDate.getTime());
        else extractdayOfWeek = dayOfWeekF.format(StopDate.getTime());

        return extractdayOfWeek;

    } //public Integer dayOfWeek()

    public Integer Hour(boolean isGetStart){

        Integer extractHour;
        String dateString;

        if (isGetStart) dateString = HourF.format(StartDate.getTime());
        else dateString = HourF.format(StopDate.getTime());

        extractHour = Integer.valueOf(dateString);

        return extractHour;
    } //public Integer Hour()

    public Integer quarterDay (boolean isGetStart){

        Integer timeCategory;
        Integer intHour;

        if (isGetStart) intHour = Integer.valueOf(HourF.format(StartDate.getTime()));
        else intHour = Integer.valueOf(HourF.format(StopDate.getTime()));

        if (intHour<6) timeCategory = 0;
        else if (intHour<12) timeCategory = 1;
        else if (intHour<18) timeCategory = 2;
        else timeCategory = 3;

        return timeCategory;
    } //public Integer Hour()


    public String Minute(boolean isGetStart){

        String extractMinute;
        String dateString;

        if (isGetStart) dateString = MinuteF.format(StartDate.getTime());
        else dateString = MinuteF.format(StopDate.getTime());

      //  extractMinute = Integer.valueOf(dateString);

        return dateString;
    } //public Integer Hour()


} //public class EventItem
