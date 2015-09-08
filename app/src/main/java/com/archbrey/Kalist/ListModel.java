package com.archbrey.Kalist;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
//import java.util.Date;
//import java.util.GregorianCalendar;



public class ListModel {

    public static ScrollView listBox;
    private static Context mainContext;
    private static String CNames[];

    public static ArrayList<EventItem> eventArrayList;

    private static Calendar lookupStart;
    private static Calendar lookupStop;

    private static LinearLayout.LayoutParams eventParams;

    private static LinearLayout listLayout;

    private static TextView[] MonthLabel;
    private static TextView[] DayofWeekLabel;

    private static ArrayList<TextView> TextViewList;
    private static ArrayList<TextView> EventViewList;
    public static ArrayList<LinearLayout> EventView;



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


    MonthLabel = new TextView[12];
    for (int monthInc=0; monthInc<=11; monthInc++) {
        MonthLabel[monthInc] = new TextView (mainContext);
        MonthLabel[monthInc].setText(CalActivity.navHandle.fullMonthStr[monthInc]);
    } // for (int monthInc=0; monthInc<=11; monthInc++)

    DayofWeekLabel = new TextView[8];
    for (int weekInc=1; weekInc<=7; weekInc++) {
        MonthLabel[weekInc] = new TextView (mainContext);
        MonthLabel[weekInc].setText(CalActivity.navHandle.dayofWeek[weekInc]);
    } // for (int weekInc=1; weekInc<=7; weekInc++)


    TextViewList = new ArrayList<>();
    EventViewList = new ArrayList<>();
    EventView = new ArrayList<>();

} //public ListModel()




    public void drawBox() {

    listBox.setBackgroundColor(SettingsActivity.backerColor);

    TextView[] sampleText;

    LinearLayout.LayoutParams sampleParams = new LinearLayout.LayoutParams (
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    sampleParams.gravity=Gravity.BOTTOM;


} //public static void drawBox()

    public void drawMonthList(){

        lookupStart.set(NavModel.currentYear, NavModel.currentMonth, 1, 0,0);
        lookupStop.set(NavModel.currentYear, NavModel.currentMonth + 1, 1, 0, 0);
        fetchList();

        listBox.removeAllViews();
        listLayout.removeAllViews();
        EventViewList.clear();

        Collections.sort(eventArrayList, new CalStartCompare());

        int ViewCount = -1;
        for (int inc = 0; inc < eventArrayList.size(); inc++)
        {
            if (inc==0) addGroupHeaderView("Week "+String.valueOf(eventArrayList.get(inc).Week(true)), ++ViewCount);
            else if (eventArrayList.get(inc).Week(true) != eventArrayList.get(inc-1).Week(true))  addGroupHeaderView("Week "+String.valueOf(eventArrayList.get(inc).Week(true)), ++ViewCount);

            EventViewList.add(new TextView(mainContext)); ViewCount++;
            EventViewList.get(ViewCount).setTextColor(SettingsActivity.textColor);
            EventViewList.get(ViewCount).setGravity(Gravity.LEFT);
            EventViewList.get(ViewCount).setText(eventArrayList.get(inc).Title + " " +
                    String.valueOf(eventArrayList.get(inc).Month(true)) + "/" +
                    String.valueOf(eventArrayList.get(inc).dayOfMonth(true)) +
                    "\n");

            listLayout.addView(EventViewList.get(ViewCount), eventParams);
        }

        listBox.addView(listLayout, eventParams);

    }//public void drawMonthList()


    private void addGroupHeaderView(String GroupHeader,int ViewCount){

        EventViewList.add(new TextView(mainContext));
        EventViewList.get(ViewCount).setTextColor(SettingsActivity.textColor);
        EventViewList.get(ViewCount).setGravity(Gravity.LEFT);
        EventViewList.get(ViewCount).setText(GroupHeader);
        listLayout.addView(EventViewList.get(ViewCount), eventParams);

    } //private void addWeekView()

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

            String selection = "((dtstart >= " + lookupStart.getTimeInMillis() + ") AND (dtstart < " + lookupStop.getTimeInMillis() + "))";
            String[] projection = new String[]{"calendar_id", "title", "description",
                    "dtstart", "dtend", "eventLocation"};

            Cursor cursor = mainContext.getContentResolver().query(Uri.parse("content://com.android.calendar/events"),
                    projection,
                    selection,
                    null,
                    null);

        cursor.moveToFirst();

        eventArrayList = new ArrayList<>();

        //for (int i = 0; i < CNames.length; i++) {
        for (int i = 0; i < cursor.getCount(); i++) {

            eventArrayList.add(new EventItem(cursor.getString(0),
                                            cursor.getString(1),
                                            cursor.getString(2),
                                            cursor.getString(3),
                                            cursor.getString(4)) );


                     cursor.moveToNext();

        } //for (int i = 0; i < CNames.length; i++)

        cursor.close();

    } //private void fetchList()


    public void updateBox(){


      //  if (listBox.getChildCount()>=0) listBox.removeAllViews();
      //  if (listLayout.getChildCount()>=0) listLayout.removeAllViews();

        listBox.removeAllViews();
        listLayout.removeAllViews();
        TextViewList.clear();

        for (int inc = 0; inc < eventArrayList.size(); inc++)
        {

            TextViewList.add(new TextView(mainContext));
            TextViewList.get(inc).setTextColor(SettingsActivity.textColor);
            TextViewList.get(inc).setGravity(Gravity.LEFT);
            TextViewList.get(inc).setText(eventArrayList.get(inc).Title + " " +
                    String.valueOf(eventArrayList.get(inc).Month(true)) + "/" +
                    String.valueOf(eventArrayList.get(inc).dayOfMonth(true)) +
                    "\n");

            listLayout.addView(TextViewList.get(inc), eventParams);
        }

        listBox.addView(listLayout,eventParams);

    } //public void updateBox()





} //public class ListModel
