package com.archbrey.kalist;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NavModel {

    public static TextView[][] dateHolder;
    public static TextView[] weekHolder;

    public static TextView yearHolder;
    public static TextView[] monthHolder;

    private static Resources rMain;
    private static LinearLayout[] navWeek;
    public static LinearLayout  navMonthBox;
    public static LinearLayout navYearBox;
    public static LinearLayout[] navYearRow;
    public static LinearLayout topYearRow;
    public static RelativeLayout navBox;


    public static Calendar navMonthCalendar;



    public NavModel(){

        Context mainContext;
        int Week,Weekday;
        mainContext = CalActivity.c;
        rMain = CalActivity.r;

        navBox = new RelativeLayout(mainContext);
        navMonthBox = new LinearLayout(mainContext);

        navMonthCalendar = new GregorianCalendar();
        navYearBox = new LinearLayout(mainContext);

        monthHolder = new TextView[12];

        navYearRow = new LinearLayout[6];

        topYearRow = new LinearLayout(mainContext);
        yearHolder = new TextView(mainContext);

        for (int incMonth=0;incMonth<=11;incMonth++){
            monthHolder[incMonth] = new TextView(mainContext);
            float halfValue = incMonth/2;
            int Row = (int) halfValue;
            navYearRow[Row] = new LinearLayout(mainContext);
        }

        dateHolder =  new TextView[7][8];
        weekHolder = new TextView[7];
        navWeek = new LinearLayout[7];
        for (Week=0;Week<=6;Week++){

            navWeek[Week] = new LinearLayout(mainContext);
            weekHolder[Week] = new TextView(mainContext);
            for (Weekday=0;Weekday<=7;Weekday++) {

                dateHolder[Week][Weekday] = new TextView(mainContext);


            } //for (Weekday=0;Weekday<=6;Week+)

        }//for (Week=0;Week<=7;Week++)

    }// public NavMonth()

    public void drawBox(){

        int keypad_key_width = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0,
                rMain.getDisplayMetrics());
        int keypad_key_height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SettingsActivity.navHeight,
                rMain.getDisplayMetrics());


        //set default view by showing current month
        navMonthCalendar.getTime();
        navMonthCalendar.set(navMonthCalendar.get(Calendar.YEAR), navMonthCalendar.get(Calendar.MONTH), 1);

        //---------- NAV MONTH ----------------------------------------------------------------------------------------------------------------------

        navMonthBox.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams navWeekParams = new LinearLayout.LayoutParams (
                LinearLayout.LayoutParams.MATCH_PARENT, //width
                LinearLayout.LayoutParams.WRAP_CONTENT); //height

        LinearLayout.LayoutParams navHolderParams = new LinearLayout.LayoutParams (
                keypad_key_width,
                keypad_key_height);
        navHolderParams.setMargins(0,0,0,0);
        navHolderParams.weight=0.1f;


        boolean StartCounting;
        boolean StopCounting;

        int date = 1;
        StartCounting = false;
        StopCounting = false;

        String dayofWeek[];
        dayofWeek = new String[8];
        dayofWeek[0] = rMain.getString(R.string.week_2);
        dayofWeek[1] = rMain.getString(R.string.sun_2);
        dayofWeek[2] = rMain.getString(R.string.mon_2);
        dayofWeek[3] = rMain.getString(R.string.tue_2);
        dayofWeek[4] = rMain.getString(R.string.wed_2);
        dayofWeek[5] = rMain.getString(R.string.thu_2);
        dayofWeek[6] = rMain.getString(R.string.fri_2);
        dayofWeek[7] = rMain.getString(R.string.sat_2);
        for (int Weekday=0;Weekday<=7;Weekday++) {

            dateHolder[0][Weekday].setTextColor(SettingsActivity.textColor);
            dateHolder[0][Weekday].setBackgroundColor(SettingsActivity.backerColor);
            dateHolder[0][Weekday].setGravity(Gravity.CENTER);
            dateHolder[0][Weekday].setText(dayofWeek[Weekday]);
            navWeek[0].addView(dateHolder[0][Weekday], navHolderParams);

        } //for (int Weekday=0;Weekday<=7;Weekday++)
        navMonthBox.addView(navWeek[0], navWeekParams);

        int MaxDate;
        MaxDate = navMonthCalendar.get(Calendar.DAY_OF_WEEK);

        for (int Week=1;Week<=6;Week++){

            navWeek[Week].setOrientation(LinearLayout.HORIZONTAL);

            weekHolder[Week].setTextColor(SettingsActivity.textColor);
            weekHolder[Week].setBackgroundColor(SettingsActivity.backColor);
            weekHolder[Week].setGravity(Gravity.CENTER);
            weekHolder[Week].setText(String.valueOf(navMonthCalendar.get(Calendar.WEEK_OF_YEAR) + (Week - 1)));
            weekHolder[Week].setTypeface(null, Typeface.BOLD);

            navWeek[Week].addView(weekHolder[Week], navHolderParams);

            for (int Weekday=1;Weekday<=7;Weekday++) {

                dateHolder[Week][Weekday].setTextColor(SettingsActivity.textColor);
                dateHolder[Week][Weekday].setBackgroundColor(SettingsActivity.backColor);
                dateHolder[Week][Weekday].setGravity(Gravity.CENTER);
                navWeek[Week].addView(dateHolder[Week][Weekday], navHolderParams);
                //dateHolder[Week][Weekday].setText(String.valueOf(date));

                if (!StartCounting&&( (MaxDate) == Weekday ) ) {
                    StartCounting=true;
                } //if (!StartCounting&&( (navMonthCalendar.get(Calendar.DAY_OF_WEEK)) == Weekday ) )

                if ( (StartCounting)&&(!StopCounting) ) {
                    if (date == navMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) StopCounting = true;
                    dateHolder[Week][Weekday].setText(String.valueOf(date));
                    date++;
                }

            } //for (Weekday=1;Weekday<=7;Week+)

            navMonthBox.addView(navWeek[Week], navWeekParams);

        }//for (Week=1;Week<=6;Week++)

        //---------- NAV YEAR ----------------------------------------------------------------------------------------------------------------------
        navYearBox.setOrientation(LinearLayout.VERTICAL);


        LinearLayout.LayoutParams navYearParams = new LinearLayout.LayoutParams (
                LinearLayout.LayoutParams.MATCH_PARENT, //width
                LinearLayout.LayoutParams.WRAP_CONTENT); //height

        LinearLayout.LayoutParams topYearParams = new LinearLayout.LayoutParams (
                keypad_key_width,
                keypad_key_height);
        topYearParams.setMargins(0,0,0,0);
        topYearParams.weight=0.1f;

        LinearLayout.LayoutParams navMonthParams = new LinearLayout.LayoutParams (
                keypad_key_width,
                keypad_key_height);
        navMonthParams.setMargins(0,0,0,0);
        navMonthParams.weight=0.1f;

        yearHolder.setTextColor(SettingsActivity.textColor);
        yearHolder.setBackgroundColor(SettingsActivity.backerColor);
        yearHolder.setGravity(Gravity.CENTER);
        yearHolder.setText(String.valueOf(navMonthCalendar.get(Calendar.YEAR)));
        //yearHolder.setText("hello");
        topYearRow.addView(yearHolder,topYearParams);
        navYearBox.addView(topYearRow, navYearParams);


        String[] monthOfYear;
        monthOfYear = new String[12];
        monthOfYear[0] = rMain.getString(R.string.jan_2);
        monthOfYear[1] = rMain.getString(R.string.feb_2);
        monthOfYear[2] = rMain.getString(R.string.mar_2);
        monthOfYear[3] = rMain.getString(R.string.apr_2);
        monthOfYear[4] = rMain.getString(R.string.may_2);
        monthOfYear[5] = rMain.getString(R.string.jun_2);
        monthOfYear[6] = rMain.getString(R.string.jul_2);
        monthOfYear[7] = rMain.getString(R.string.aug_2);
        monthOfYear[8] = rMain.getString(R.string.sep_2);
        monthOfYear[9] = rMain.getString(R.string.oct_2);
        monthOfYear[10] = rMain.getString(R.string.nov_2);
        monthOfYear[11] = rMain.getString(R.string.dec_2);

        for (int incMonth=0;incMonth<=11;incMonth++){

            monthHolder[incMonth].setTextColor(SettingsActivity.textColor);
            monthHolder[incMonth].setBackgroundColor(SettingsActivity.backColor);
            monthHolder[incMonth].setGravity(Gravity.CENTER);
            monthHolder[incMonth].setText(monthOfYear[incMonth]);

            float halfValue = incMonth/2;
            int Row = (int) halfValue;
            navYearRow[Row].setOrientation(LinearLayout.HORIZONTAL);
            navYearRow[Row].addView(monthHolder[incMonth], navMonthParams);

        }

        for (int incRow=0;incRow<=5;incRow++) {
            navYearBox.addView(navYearRow[incRow], navYearParams);
        }

// ------- assemble navBox ---------------------------------------------------------------------------------

        int screenWidth;
        Display display = CalActivity.mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        navMonthBox.setId(R.id.navMonthBox);
        navYearBox.setId(R.id.navYearBox);

        int navMonthSpace;
        float computeNavSpace;
        computeNavSpace = screenWidth*0.7f;
        navMonthSpace = (int) computeNavSpace;

        RelativeLayout.LayoutParams monthBoxParams = new RelativeLayout.LayoutParams(
                //RelativeLayout.LayoutParams.WRAP_CONTENT, //width
                navMonthSpace,
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height
        monthBoxParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        monthBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        RelativeLayout.LayoutParams yearBoxParams = new RelativeLayout.LayoutParams(
                //RelativeLayout.LayoutParams.WRAP_CONTENT, //width
                navMonthSpace,
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height
        yearBoxParams.addRule(RelativeLayout.LEFT_OF, navMonthBox.getId());
        yearBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        navBox.addView(navMonthBox, monthBoxParams);
        navBox.addView(navYearBox, yearBoxParams);

    } //public void drawBox()


} //public class NavMonth