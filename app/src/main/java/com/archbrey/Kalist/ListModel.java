package com.archbrey.Kalist;

import android.content.Context;
import android.database.Cursor;
//import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
//import android.widget.HorizontalScrollView;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ListModel {

    public static ScrollView listBox;
    private static Context mainContext;
    private static String CNames[];


    private static Calendar lookupStart;
    private static Calendar lookupStop;

    private static LinearLayout.LayoutParams eventParams;

    private static LinearLayout listLayout;

public ListModel(){

    mainContext = CalActivity.c;
    listBox = new ScrollView(mainContext);
    listLayout = new LinearLayout(mainContext);
    listLayout.setOrientation(LinearLayout.VERTICAL);

    lookupStart = Calendar.getInstance();
    lookupStop = Calendar.getInstance();

    eventParams = new LinearLayout.LayoutParams (
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    eventParams.gravity=Gravity.BOTTOM;

} //public ListModel()


public void drawBox() {

    listBox.setBackgroundColor(SettingsActivity.backerColor);

    TextView[] sampleText;

    LinearLayout.LayoutParams sampleParams = new LinearLayout.LayoutParams (
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    sampleParams.gravity=Gravity.BOTTOM;

    listEvents();

    sampleText = new TextView[CNames.length+1];

    for (int inc = 0; inc < CNames.length; inc++) {

        sampleText[inc] = new TextView(mainContext);
        sampleText[inc].setTextColor(SettingsActivity.textColor);
        sampleText[inc].setGravity(Gravity.START);
        sampleText[inc].setText(CNames[inc]+"\n");

        listLayout.addView(sampleText[inc], sampleParams);
    }


    listBox.addView(listLayout,sampleParams);

} //public static void drawBox()


    public void listYear() {

        lookupStart.set(NavModel.currentYear, 0, 1, 0,0);
        lookupStop.set(NavModel.currentYear+1, 0, 1, 0,0);
        fetchList();
        updateBox();

    } //public void listYear()

    public void listMonth() {

        lookupStart.set(NavModel.currentYear, NavModel.currentMonth, 1, 0,0);
        lookupStop.set(NavModel.currentYear, NavModel.currentMonth+1, 1, 0,0);
        fetchList();
        updateBox();

    } //public void listMonth()

    public void listWeek(int getWeek) {

    } //public void listWeek(int getWeek)


    public void listDate(int getDate) {

    } //public void listWeek(int getWeek)

    private void fetchList(){

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
        Calendar calendar = new GregorianCalendar();
        String dateString;

        String selection = "((dtstart >= "+lookupStart.getTimeInMillis()+") AND (dtend < "+lookupStop.getTimeInMillis()+"))";
        String[] projection = new String[] { "calendar_id", "title", "description",
                "dtstart", "dtend", "eventLocation" };

        Cursor cursor = mainContext.getContentResolver().query(Uri.parse("content://com.android.calendar/events"),
                projection,
                selection,
                null,
                null);

        cursor.moveToFirst();
        // fetching calendars name
        CNames = new String[cursor.getCount()];

        for (int i = 0; i < CNames.length; i++) {

            ArrayList<String> nameOfEvent = new ArrayList<>();
            // ArrayList<String> startDates = new ArrayList<>();
            // ArrayList<String> endDates = new ArrayList<>();
            ArrayList<String> descriptions = new ArrayList<>();

            nameOfEvent.add(cursor.getString(1));
            // startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            // endDates.add(getDate(Long.parseLong(cursor.getString(4))));

            descriptions.add(cursor.getString(2));
            //calendar.setTimeInMillis(Long.parseLong(cursor.getString(4)));

            Long enddate;
            //if (i<70)
            enddate = Long.parseLong(cursor.getString(3));
            //else enddate = Long.parseLong("1010111");

            calendar.setTimeInMillis(enddate);
            dateString = formatter.format(calendar.getTime());

            //if (i==70) CNames[i] =  cursor.getString(1)+" "+cursor.getString(3);
            //else
            CNames[i] = cursor.getString(1)+" "+ dateString ;
            // CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        } //for (int i = 0; i < CNames.length; i++)

    } //private void fetchList()


    public void updateBox(){

        TextView[] sampleText;

        if (listBox.getChildCount()>0) listBox.removeAllViews();
        if (listLayout.getChildCount()>0) listLayout.removeAllViews();

        sampleText = new TextView[CNames.length+1];

        for (int inc = 0; inc < CNames.length; inc++) {

            sampleText[inc] = new TextView(mainContext);
            sampleText[inc].setTextColor(SettingsActivity.textColor);
            sampleText[inc].setGravity(Gravity.LEFT);
            sampleText[inc].setText(CNames[inc]+"\n");

            listLayout.addView(sampleText[inc], eventParams);
        }

        listBox.addView(listLayout,eventParams);

    } //public void updateBox()

    public void listEvents(){

        ArrayList<String> nameOfEvent = new ArrayList<>();
       // ArrayList<String> startDates = new ArrayList<>();
       // ArrayList<String> endDates = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();

        //SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("dd (HH:mm)");
        Calendar calendar = new GregorianCalendar();
        String dateString;

        String[] projection = new String[] { "calendar_id", "title", "description",
                "dtstart", "dtend", "eventLocation" };

        Calendar c_start= Calendar.getInstance();
        c_start.set(2015,2,4,0,0); //Note that months start from 0 (January)
        Calendar c_end= Calendar.getInstance();
        c_end.set(2015,3,1,0,0); //Note that months start from 0 (January)

        String selection = "((dtstart >= "+c_start.getTimeInMillis()+") AND (dtend < "+c_end.getTimeInMillis()+"))";


        Cursor cursor = mainContext.getContentResolver().query(Uri.parse("content://com.android.calendar/events"),
                projection,
                selection,
                null,
                null);

        cursor.moveToFirst();
        // fetching calendars name
        CNames = new String[cursor.getCount()];

        for (int i = 0; i < CNames.length; i++) {

            nameOfEvent.add(cursor.getString(1));
           // startDates.add(getDate(Long.parseLong(cursor.getString(3))));
           // endDates.add(getDate(Long.parseLong(cursor.getString(4))));

            descriptions.add(cursor.getString(2));
            //calendar.setTimeInMillis(Long.parseLong(cursor.getString(4)));
            Long enddate;
            //if (i<70)
                enddate = Long.parseLong(cursor.getString(3));
            //else enddate = Long.parseLong("1010111");

            calendar.setTimeInMillis(enddate);
            dateString = formatter.format(calendar.getTime());

            //if (i==70) CNames[i] =  cursor.getString(1)+" "+cursor.getString(3);
            //else
                CNames[i] = cursor.getString(1)+" "+ dateString ;
           // CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        }



    } //public void listEvents()



} //public class ListModel
