package com.archbrey.Kalist;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;

public class ListModel {

    public static ScrollView listBox;
    private static Context mainContext;
    private static String CNames[];
    public static EventItem eventItems[];

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

    public void listWeek() {

        lookupStart.set(Calendar.DAY_OF_WEEK, 1);
        lookupStart.set(Calendar.WEEK_OF_YEAR, NavModel.currentWeek);
        lookupStart.set(Calendar.YEAR, NavModel.currentYear);

        lookupStop.set(Calendar.DAY_OF_WEEK, 1);
        lookupStop.set(Calendar.WEEK_OF_YEAR, NavModel.currentWeek+1);
        lookupStop.set(Calendar.YEAR, NavModel.currentYear);

        fetchList();
        updateBox();

    } //public void listWeek()


    public void listDate() {

        lookupStart.set(NavModel.currentYear, NavModel.currentMonth, NavModel.currentDate, 0,0);
        lookupStop.set(NavModel.currentYear, NavModel.currentMonth, NavModel.currentDate+1, 0,0);

        fetchList();
        updateBox();

    } //public void listWeek()

    private void fetchList(){


        String selection = "((dtstart >= "+lookupStart.getTimeInMillis()+") AND (dtstart < "+lookupStop.getTimeInMillis()+"))";
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
        eventItems = new EventItem[cursor.getCount()];

        for (int i = 0; i < CNames.length; i++) {

            eventItems[i] = new EventItem();

            Long startDate;
            Long stopDate;

            startDate = Long.parseLong(cursor.getString(3));
            if (cursor.getString(4)!= null)  stopDate = Long.parseLong(cursor.getString(4));
               else stopDate = null;

            eventItems[i].Title = cursor.getString(1);
            eventItems[i].Description = cursor.getString(2);
            eventItems[i].ID = Integer.valueOf(cursor.getString(0));
            eventItems[i].StartDate.setTimeInMillis(startDate);
            if ( stopDate != null) eventItems[i].StopDate.setTimeInMillis(stopDate);

            // CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        } //for (int i = 0; i < CNames.length; i++)

    } //private void fetchList()


    public void updateBox(){

        TextView[] sampleText;

        if (listBox.getChildCount()>0) listBox.removeAllViews();
        if (listLayout.getChildCount()>0) listLayout.removeAllViews();

        sampleText = new TextView[eventItems.length+1];

        for (int inc = 0; inc < eventItems.length; inc++) {

            sampleText[inc] = new TextView(mainContext);
            sampleText[inc].setTextColor(SettingsActivity.textColor);
            sampleText[inc].setGravity(Gravity.LEFT);
            sampleText[inc].setText(eventItems[inc].Title+" "+
                                    String.valueOf(eventItems[inc].Month(true)) + "/" +
                                    String.valueOf(eventItems[inc].dayOfMonth(true)) +
                                    "\n");

            listLayout.addView(sampleText[inc], eventParams);
        }

        listBox.addView(listLayout,eventParams);

    } //public void updateBox()




} //public class ListModel
