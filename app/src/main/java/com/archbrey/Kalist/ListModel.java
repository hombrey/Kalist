package com.archbrey.Kalist;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class ListModel {

    public static ScrollView listBox;
    private static Context mainContext;

    public static ArrayList<EventItem> eventArrayList;

    private static Calendar lookupStart;
    private static Calendar lookupStop;

    private static LinearLayout.LayoutParams eventParams;
    private static LinearLayout.LayoutParams subGroupParams;

    private static LinearLayout listLayout;

    //private static TextView[] MonthLabel;
    //private static TextView[] DayofWeekLabel;

    private static ArrayList<TextView> TextViewList;


    private static ArrayList<LinearLayout[]> SubEventView;
    private static ArrayList<TextView> SubGroupText;
    private static ArrayList<ArrayList<TextView>> SubEventList;

    public static ArrayList<LinearLayout> EventView;
    private static ArrayList<TextView> EventHeaderText;
    //private static ArrayList<TextView> EventViewText;


public ListModel(){


    mainContext = CalActivity.c;
    Resources rMain = CalActivity.r;

    listBox = new ScrollView(mainContext);
    listLayout = new LinearLayout(mainContext);
    listLayout.setOrientation(LinearLayout.VERTICAL);

    lookupStart = Calendar.getInstance();
    lookupStop = Calendar.getInstance();

    eventParams = new LinearLayout.LayoutParams (
            LinearLayout.LayoutParams.WRAP_CONTENT, //width
            LinearLayout.LayoutParams.WRAP_CONTENT); //height
    eventParams.gravity=Gravity.TOP;

    int sub_width = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 60,
            rMain.getDisplayMetrics());

    subGroupParams = new LinearLayout.LayoutParams (
            sub_width,  //width
            LinearLayout.LayoutParams.WRAP_CONTENT);  //height
    subGroupParams.gravity=Gravity.TOP;

    /*
    MonthLabel = new TextView[12];
    for (int monthInc=0; monthInc<=11; monthInc++) {
        MonthLabel[monthInc] = new TextView (mainContext);
        MonthLabel[monthInc].setText(NavModel.fullMonthStr[monthInc]);
    } // for (int monthInc=0; monthInc<=11; monthInc++)

    DayofWeekLabel = new TextView[8];
    for (int weekInc=1; weekInc<=7; weekInc++) {
        MonthLabel[weekInc] = new TextView (mainContext);
        MonthLabel[weekInc].setText(NavModel.dayofWeek[weekInc]);
    } // for (int weekInc=1; weekInc<=7; weekInc++)
    */

    TextViewList = new ArrayList<>();
  //  EventViewText = new ArrayList<>();
    EventHeaderText = new ArrayList<>();
    EventView = new ArrayList<>();
    SubEventView = new ArrayList<>();
    SubGroupText = new ArrayList<>();
    SubEventList = new ArrayList<ArrayList<TextView>>();

} //public ListModel()


    public void drawBox() {

    listBox.setBackgroundColor(SettingsActivity.backColor);

    LinearLayout.LayoutParams sampleParams = new LinearLayout.LayoutParams (
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    sampleParams.gravity=Gravity.BOTTOM;


} //public static void drawBox()


    public void drawList(String listType){

        int SocketStart = 0, SocketStop = 0;
        if (listType.equals("Month")){

           // lookupStart.set(NavModel.currentYear, NavModel.currentMonth, 1, 0, 0);
           // lookupStop.set(NavModel.currentYear, NavModel.currentMonth + 1, 1, 0, 0);
            lookupStart.set(NavModel.currentYear, NavModel.currentMonth, 1);
            lookupStop.set(NavModel.currentYear, NavModel.currentMonth + 1, 1);
            SocketStart = 1;
            SocketStop = NavModel.navMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        } //if (listType.equals("Month"))

        if (listType.equals("Year")){

            lookupStart.set(NavModel.currentYear, 0, 1);
            lookupStop.set(NavModel.currentYear+1, 0, 1);
            SocketStart = 1;
            SocketStop = NavModel.navMonthCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        } //if (listType.equals("Year"))

        if (listType.equals("Day")){

          //  lookupStart.set(NavModel.currentYear, NavModel.currentMonth, NavModel.currentDate);
          //  lookupStop.set(NavModel.currentYear, NavModel.currentMonth, NavModel.currentDate+1);

            lookupStart.set(NavModel.currentYear, NavModel.currentMonth, NavModel.currentDate, 0, 0);
            lookupStop.set(NavModel.currentYear, NavModel.currentMonth, NavModel.currentDate+1, 0,0);

            SocketStart = 0;
            SocketStop = 23;

        } //if (listType.equals("Day"))


        fetchList();

        listBox.removeAllViews(); //remove contents of layout
        listLayout.removeAllViews();
        EventHeaderText.clear(); // remove contents of Text view list
        for (int clearInc=0; clearInc<SubEventView.size(); clearInc++) {
            SubEventView.get(clearInc)[0].removeAllViews();

        } //for (int clearInc=0; clearInc<SubEventView.size(); clearInc++)
        SubEventList.clear();
        SubEventView.clear();
        EventView.clear();

        Collections.sort(eventArrayList, new CalStartCompare());

        int ViewCount = -1;
        int SubViewCount = -1;
        int arrayListIndex = 0;

        for (int incSocket = SocketStart ; incSocket <= SocketStop; incSocket++) { //main loop. Go through every possible value of the time value socket

            int testDate = 0;

            if (arrayListIndex<eventArrayList.size()) {//make sure that arrayListIndex does not access any element beyond eventArrayList size

                if (listType.equals("Month")) {
                    if (eventArrayList.get(arrayListIndex).StartDate.before(lookupStart))
                        {testDate = -1; arrayListIndex++; incSocket--; } //discard events that began outside the scope. The list does not work with them
                    else testDate = eventArrayList.get(arrayListIndex).dayOfMonth(true);

                } //if (listType.equals("Month"))

                if (listType.equals("Year")) {
                    if (eventArrayList.get(arrayListIndex).StartDate.before(lookupStart))
                    {testDate = -2; arrayListIndex++; incSocket--; } //discard events that began outside the scope. The list does not work with them
                    else testDate = eventArrayList.get(arrayListIndex).dayOfYear(true);

                } //if (listType.equals("Year"))


                if (listType.equals("Day")||listType.equals("Week")) {
                    if (eventArrayList.get(arrayListIndex).StartDate.before(lookupStart))
                    {testDate = -2; arrayListIndex++; incSocket--; } //discard events that began outside the scope. The list does not work with them
                    else testDate = eventArrayList.get(arrayListIndex).Hour(true);

                } //(listType.equals("Day")||listType.equals("Week"))


            } //if (arrayListIndex<eventArrayList.size())
            else {
                incSocket = SocketStop + 1; //exit major loop if contents eventArrayList are exhausted
                testDate = SocketStop + 2; //just make sure testDate does not ever match incrementer index
            } //else of if (arrayListIndex<eventArrayList.size())

            if (testDate == incSocket) { //one time action per subgroup

            //add group header

                if (listType.equals("Month")) {

                    Integer eventWeek = eventArrayList.get(arrayListIndex).Week(true);
                    if (ViewCount == -1)
                        addGroupHeaderView("Week " + String.valueOf(eventWeek), ++ViewCount);
                    else if (arrayListIndex > 0) //check to add to group header only if actual events have been added to the list
                        if (!eventWeek.equals(eventArrayList.get(arrayListIndex - 1).Week(true)))
                            addGroupHeaderView("Week " + String.valueOf(eventWeek), ++ViewCount);

                } //if (listType.equals("Month"))


                if (listType.equals("Year")) {

                    Integer eventMonth = eventArrayList.get(arrayListIndex).Month(true);
                    if (ViewCount == -1)
                        addGroupHeaderView( NavModel.fullMonthStr[eventMonth -1] , ++ViewCount);
                    else if (arrayListIndex > 0) //check to add to group header only if actual events have been added to the list
                        if (!eventMonth.equals(eventArrayList.get(arrayListIndex - 1).Month(true)))
                            addGroupHeaderView(NavModel.fullMonthStr[eventMonth-1], ++ViewCount);

                } //if (listType.equals("Year"))

                if (listType.equals("Day")) {

                    Integer eventQuarterDay = eventArrayList.get(arrayListIndex).quarterDay(true);
                    if (ViewCount == -1)
                        addGroupHeaderView(NavModel.quarterDayStr[eventQuarterDay], ++ViewCount);
                    else if (arrayListIndex > 0) //check to add to group header only if actual events have been added to the list
                        if (!eventQuarterDay.equals(eventArrayList.get(arrayListIndex - 1).quarterDay(true)))
                            addGroupHeaderView(NavModel.quarterDayStr[eventQuarterDay], ++ViewCount);

                } //if (listType.equals("Day"))


                EventView.add(new LinearLayout(mainContext));
                ViewCount++;
                EventHeaderText.add(new TextView(mainContext)); //need to add EventHeaderText even if not used to keep alignment with EventView index

                EventView.get(ViewCount).setOrientation(LinearLayout.HORIZONTAL);

                SubEventView.add(new LinearLayout[2]);
                SubViewCount++;
                SubEventView.get(SubViewCount)[0] = new LinearLayout(mainContext);
                SubEventView.get(SubViewCount)[1] = new LinearLayout(mainContext);
                SubEventView.get(SubViewCount)[1].setOrientation(LinearLayout.VERTICAL);

                SubEventList.add(new ArrayList<TextView>());

                //fill up sub event Text using the first item that matches the time increment socket
                SubGroupText.add(new TextView(mainContext));

                String subGroupLabel;

                if (listType.equals("Month")){
                    subGroupLabel = String.valueOf(eventArrayList.get(arrayListIndex).dayOfMonth(true)) + "|" +
                            eventArrayList.get(arrayListIndex).dayOfWeek(true);
                    SubGroupText.get(SubViewCount).setText(subGroupLabel);
                    SubEventView.get(SubViewCount)[0].addView(SubGroupText.get(SubViewCount), eventParams);
                    EventView.get(ViewCount).addView(SubEventView.get(SubViewCount)[0], subGroupParams);
                 } //if (listType.equals("Month"))

                if (listType.equals("Year")){
                    subGroupLabel ="    " + String.valueOf(eventArrayList.get(arrayListIndex).dayOfMonth(true));
                    SubGroupText.get(SubViewCount).setText(subGroupLabel);
                    SubEventView.get(SubViewCount)[0].addView(SubGroupText.get(SubViewCount), eventParams);
                    EventView.get(ViewCount).addView(SubEventView.get(SubViewCount)[0], subGroupParams);
                } //if (listType.equals("Month"))

                int SubEventListCount = -1;

                while  (testDate == incSocket) { //for cases when multiple events match subgroup

                    if (arrayListIndex<eventArrayList.size()) {

                        if (listType.equals("Month")) testDate = eventArrayList.get(arrayListIndex).dayOfMonth(true);

                        if (listType.equals("Year")) testDate = eventArrayList.get(arrayListIndex).dayOfYear(true);
                        if (listType.equals("Day")) testDate = eventArrayList.get(arrayListIndex).Hour(true);

                        if  (testDate == incSocket) { //add to list only if current event item in array matches time socket criteria

                            SubEventList.get(SubViewCount).add(new TextView(mainContext)); SubEventListCount++;

                            SubEventList.get(SubViewCount).get(SubEventListCount).setText(""); //use this so the rest of the text can just be appended
                            if (listType.equals("Day")||listType.equals("Week")) SubEventList.get(SubViewCount).get(SubEventListCount).append(eventArrayList.get(arrayListIndex).Hour(true) + ":" + eventArrayList.get(arrayListIndex).Minute(true) +" " );
                            SubEventList.get(SubViewCount).get(SubEventListCount).append( eventArrayList.get(arrayListIndex).Title );

                            SubEventView.get(SubViewCount)[1].addView(SubEventList.get(SubViewCount).get(SubEventListCount), eventParams);
                            arrayListIndex++;
                        } // if  (testDate == incMonth)

                    } //if (arrayListIndex<eventArrayList.size())

                    else testDate = NavModel.navMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1;

                } //while ( (eventArrayList.get(arrayListIndex).dayOfMonth(true) == incMonth) || (KeepInLoop) )

                EventView.get(ViewCount).addView(SubEventView.get(SubViewCount)[1], eventParams);
                listLayout.addView(EventView.get(ViewCount), eventParams);

            }// if (testDate == incMonth)

        } //for (int inc = 0; inc <= NavModel.navMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); inc++)

        listBox.addView(listLayout, eventParams); //add assembled Linear layout into scrollview


    }//public void drawList(String listType)


    public void listWeek() {

        lookupStart.set(Calendar.DAY_OF_WEEK, 1);
        lookupStart.set(Calendar.WEEK_OF_YEAR, NavModel.currentWeek);
        lookupStart.set(Calendar.YEAR, NavModel.currentYear);

        lookupStop.set(Calendar.DAY_OF_WEEK, 1);
        lookupStop.set(Calendar.WEEK_OF_YEAR, NavModel.currentWeek + 1);
        lookupStop.set(Calendar.YEAR, NavModel.currentYear);

        fetchList();
        Collections.sort(eventArrayList, new CalStartCompare());

        listBox.removeAllViews();
        listLayout.removeAllViews();
        TextViewList.clear();

        listBox.removeAllViews(); //remove contents of layout
        EventHeaderText.clear(); // remove contents of Text view list
        EventView.clear();

        int ViewCount = -1;
        for (int inc = 0; inc < eventArrayList.size(); inc++)
        {
            String dayOfWeek = eventArrayList.get(inc).dayOfWeek(true) + " (" +
                                String.valueOf(eventArrayList.get(inc).Month(true)) + "/" +
                                String.valueOf(eventArrayList.get(inc).dayOfMonth(true)) + ")" ;

            if (ViewCount == -1)
                addGroupHeaderView(dayOfWeek, ++ViewCount);
            else if (inc > 0) //check to add to group header only if actual events have been added to the list
                 if (!dayOfWeek.equals(eventArrayList.get(inc - 1).dayOfWeek(true)))
                addGroupHeaderView(dayOfWeek, ++ViewCount);

            TextViewList.add(new TextView(mainContext));
            TextViewList.get(inc).setGravity(Gravity.START);
            TextViewList.get(inc).setText("   " + eventArrayList.get(inc).Hour(true) + ":" + eventArrayList.get(inc).Minute(true) +" " +
                    eventArrayList.get(inc).Title );

            listLayout.addView(TextViewList.get(inc), eventParams);
        }

        listBox.addView(listLayout, eventParams);

    } //public void listWeek()

    private void addGroupHeaderView(String GroupHeader,int ViewCount){

        EventView.add(new LinearLayout(mainContext));
        EventHeaderText.add(new TextView(mainContext));
        EventHeaderText.get(ViewCount).setTextColor(SettingsActivity.textColor);
        EventHeaderText.get(ViewCount).setGravity(Gravity.START);
        EventHeaderText.get(ViewCount).setText(GroupHeader);

        EventView.get(ViewCount).addView(EventHeaderText.get(ViewCount), eventParams);

        listLayout.addView(EventView.get(ViewCount), eventParams);


    } //private void addWeekView()



    private void fetchList(){

        String[] projection = new String[]{"calendar_id",
                                            "title",
                                            "description",
                                            CalendarContract.Instances.BEGIN,
                                            CalendarContract.Instances.END,
                                            "eventLocation"};

        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        ContentUris.appendId(eventsUriBuilder, lookupStart.getTimeInMillis());
        ContentUris.appendId(eventsUriBuilder, lookupStop.getTimeInMillis());
        Uri eventsUri = eventsUriBuilder.build();
        Cursor cursor;
        cursor = mainContext.getContentResolver().query(eventsUri, projection, null, null, CalendarContract.Instances.DTSTART + " ASC");

        cursor.moveToFirst();

        eventArrayList = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++) {

            eventArrayList.add(new EventItem(cursor.getString(0),
                                            cursor.getString(1),
                                            cursor.getString(2),
                                            cursor.getString(3),
                                            cursor.getString(4)) );


                     cursor.moveToNext();

        } //for (int i = 0; i < cursor.getCount(); i++)

        cursor.close();

    } //private void fetchList()



} //public class ListModel
