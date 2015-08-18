package com.archbrey.kalist;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

public class NavListener {

    private static int yearHeight;
    private static int yearWidth;
    private static class YearMap {
        int X;
        int Y;
    }

    private static int datesHeight;
    private static int datesWidth;
    private static int datesLeftBoundary;
    public static int navMaxDate;
    private static class DatesMap {
        int X;
        int Y;
    } //private static class DatesMap

    private static int weeksHeight;
    private static int weeksWidth;
    private static int weeksLeftBoundary;
    private static class WeeksMap {
        Integer value;
        int X;
        int Y;
    } //private static class WeeksMap

    private static int monthsHeight;
    private static int monthsWidth;
    private static class MonthsMap {
        int X;
        int Y;
    } //private static class MonthsMap

    private static DatesMap[] datesMap;
    private static MonthsMap[] monthsMap;
    private static WeeksMap[] weeksMap;
    private static YearMap yearMap;

    public static int touchMode;
    public final int YEARMODE =100;
    public final int MONTHMODE = 101;
    public final int WEEKMODE = 102;
    public final int DATEMODE = 103;


    public static int NavID;

    public NavListener() {

        datesMap = new DatesMap[32];
        monthsMap = new MonthsMap[12];
        weeksMap = new WeeksMap[7];
        monthsWidth = 0; //ensure that value is initiated as 0
        yearMap = new YearMap();

    } //public NavListener()

    public void setListener(){

       // View.OnTouchListener NavListener;

        NavModel.navBox.setOnTouchListener(
                //NavListener =
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        float currentX = event.getRawX();
                        float currentY = event.getRawY();
                        if (touchMode>0) determineNavID(currentX, currentY);
                        int action = MotionEventCompat.getActionMasked(event);
                        switch (action) {
                            case (MotionEvent.ACTION_DOWN):
                                touchMode = 1;
                                if (monthsWidth < 1) refreshNavPadLocations(); //only perform one time
                                determineTouchMode(currentX, currentY);
                                break;
                            case (MotionEvent.ACTION_UP):
                                touchMode = 0;
                                break;
                        }//switch(action)
                        return true;
                    } //public boolean onTouch(View v, MotionEvent event)
                } //NavListener = new View.OnTouchListener(
        );//NavModel.navBox.setOnTouchListener

    } //public void setListener()

    private void determineNavID(float getX, float getY){

        int TouchX=(int)getX;
        int TouchY=(int)getY;

        switch (touchMode) {
            case MONTHMODE:
                for (int monthInc = 0; monthInc <= 11; monthInc++) {

                    if( ( TouchX >= monthsMap[monthInc].X) && (TouchX < (monthsMap[monthInc].X+monthsWidth) ) )
                        if( ( TouchY >= monthsMap[monthInc].Y) && (TouchY < (monthsMap[monthInc].Y+monthsHeight) ) )
                        {NavID=monthInc; }

                     TopOut.dateInfo.setText(String.valueOf(NavID));
                } //for (int monthInc = 0; monthInc <= 11; monthInc++)
                break;
            case WEEKMODE:
                for (int weekInc = 1; weekInc <= 6; weekInc++) {

                    if( ( TouchX >= weeksMap[weekInc].X) && (TouchX < (weeksMap[weekInc].X+weeksWidth) ) )
                        if( ( TouchY >= weeksMap[weekInc].Y) && (TouchY < (weeksMap[weekInc].Y+weeksHeight) ) )
                        {NavID=weekInc; }

                    TopOut.dateInfo.setText(String.valueOf(NavID));
                } //for (int monthInc = 0; monthInc <= 11; monthInc++)
                break;
            case DATEMODE:
                for (int dateInc = 1; dateInc <= navMaxDate; dateInc++) {

                    if( ( TouchX >= datesMap[dateInc].X) && (TouchX < (datesMap[dateInc].X+datesWidth) ) )
                        if( ( TouchY >= datesMap[dateInc].Y) && (TouchY < (datesMap[dateInc].Y+datesHeight) ) )
                        {NavID=dateInc; }

                    TopOut.dateInfo.setText(String.valueOf(NavID));
                } //for (int monthInc = 0; monthInc <= 11; monthInc++)
                break;
            case YEARMODE:
                break;
            default:
                break;
        } //switch (touchMode)

    } //private void determineNavId(float getX, float getY)




    private void determineTouchMode(float getX, float getY){

        int IntTouchX=(int)getX;
        int IntTouchY=(int)getY;

        if ( (IntTouchY>yearMap.Y) && (IntTouchY<(monthsMap[11].Y+monthsHeight)) ){ //within navpad region

            if (IntTouchX > datesLeftBoundary) touchMode = DATEMODE;
            if ((IntTouchX < datesLeftBoundary) && (IntTouchX > weeksLeftBoundary))
                touchMode = WEEKMODE;
            if ((IntTouchX < weeksLeftBoundary) && (IntTouchY > (yearMap.Y + yearHeight)))
                touchMode = MONTHMODE;
            if ((IntTouchX < weeksLeftBoundary) && (IntTouchY < (yearMap.Y + yearHeight)))
                touchMode = YEARMODE;

            TopOut.dateInfo.setText(String.valueOf(touchMode));

        } //if (IntTouchY>yearMap.Y)

    } //private void determineMode()


    public void refreshNavPadLocations() {

        int[] instanceLocation;
        String stringValue;
        instanceLocation = new int[2];


         if (monthsWidth < 1) { //perform only if this hasn't been done before

        //get Year
        yearWidth = NavModel.topYearRow.getWidth();
        yearHeight =NavModel.topYearRow.getHeight();
        NavModel.topYearRow.getLocationOnScreen(instanceLocation);
        yearMap.X = instanceLocation[0];
        yearMap.Y = instanceLocation[1];

        //get Months
        monthsWidth = NavModel.monthHolder[0].getWidth(); //it's enough to get the first instance of a holder to represent all the rest
        monthsHeight = NavModel.monthHolder[0].getHeight();
        for (int monthInc = 0; monthInc <= 11; monthInc++) {
            monthsMap[monthInc] = new MonthsMap();
            NavModel.monthHolder[monthInc].getLocationOnScreen(instanceLocation);
            monthsMap[monthInc].X = instanceLocation[0];
            monthsMap[monthInc].Y = instanceLocation[1];
        } //for (int monthInc=1; monthInc<=12; monthInc++)

        //get Weeks
        weeksWidth = NavModel.weekHolder[1].getWidth(); //it's enough to get the first instance of a holder to represent all the rest
        weeksHeight = NavModel.weekHolder[1].getHeight();
        for (int weekInc = 1; weekInc <= 6; weekInc++) {
            weeksMap[weekInc] = new WeeksMap();
            NavModel.weekHolder[weekInc].getLocationOnScreen(instanceLocation);
            weeksMap[weekInc].X = instanceLocation[0];
            weeksMap[weekInc].Y = instanceLocation[1];
            //stringValue = NavModel.weekHolder[weekInc].getText().toString();
            //weeksMap[weekInc].value = Integer.parseInt(stringValue);
        } //for (int monthInc=1; monthInc<=12; monthInc++)
        weeksLeftBoundary = weeksMap[1].X; //location of any week instance is the left boundary for "weeks" zone
        datesLeftBoundary = weeksMap[1].X + weeksWidth; //right side of weeks zone is the left boundary of "days" zone

         } // if (monthsWidth < 0)


        //get days
        datesWidth = NavModel.dateHolder[1][1].getWidth(); //it's enough to get the first instance of a holder to represent all the rest
        datesHeight = NavModel.dateHolder[1][1].getHeight();
        int datesInc = 1;
        for (int Week=1;Week<=6;Week++){
            for (int Weekday=1;Weekday<=7;Weekday++) {
                stringValue = NavModel.dateHolder[Week][Weekday].getText().toString();
                if (stringValue.length()>0) {
                    datesMap[datesInc] = new DatesMap();
                    NavModel.dateHolder[Week][Weekday].getLocationOnScreen(instanceLocation);
                    datesMap[datesInc].X=instanceLocation[0];
                    datesMap[datesInc].Y=instanceLocation[1];
                    navMaxDate = datesInc;
                    datesInc++;
                } //if (stringValue.length()>0)
            } //for (Weekday=1;Weekday<=7;Week+)
        }//for (Week=0;Week<=7;Week++)


    } //private void getNavpadLocations()


} //public class NavListener
