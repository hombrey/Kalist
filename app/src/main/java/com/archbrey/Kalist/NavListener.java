package com.archbrey.Kalist;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

public class NavListener {

    private static int yearHeight;
    public static int yearWidth;
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
    public static int navMaxWeek;
    private static class WeeksMap {
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
    public final int DATEMODE = 100;
    public final int WEEKMODE = 101;
    public final int MONTHMODE = 102;
    public final int YEARMODE =103;

    public static int NavID;

    private float initialTouchX;
    //private float initialTouchY;

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
                        if (touchMode>0) {
                            determineNavID(currentX, currentY);
                            evaluateAction();
                        } //if (touchMode>0)
                        int action = MotionEventCompat.getActionMasked(event);
                        switch (action) {
                            case (MotionEvent.ACTION_DOWN):
                                initialTouchX = currentX;
                                //initialTouchY = currentY;
                                touchMode = 1;
                                if (monthsWidth < 1) refreshNavPadLocations(); //only perform one time
                                determineTouchMode(currentX, currentY);
                                break;
                            case (MotionEvent.ACTION_UP):
                                if (touchMode==YEARMODE) {
                                    CalActivity.navHandle.reDrawYear(NavModel.currentYear + NavID);
                                    CalActivity.listHandle.drawList("Year");
                                    NavModel.listInfo.setText("Year View");
                                 }//if (touchMode==YEARMODE)
                                if (touchMode>=MONTHMODE) refreshNavPadLocations();
                                touchMode = 0;
                                break;
                        }//switch(action)
                        return true;
                    } //public boolean onTouch(View v, MotionEvent event)
                } //NavListener = new View.OnTouchListener(
        );//NavModel.navBox.setOnTouchListener

    } //public void setListener()

    private void evaluateAction(){

        switch (touchMode) {
            case MONTHMODE:
                CalActivity.navHandle.showMonth(NavID);
                CalActivity.navHandle.reDrawMonth(NavID);
                //CalActivity.listHandle.drawMonthList();
                CalActivity.listHandle.drawList("Month");
                NavModel.listInfo.setText("Month View");
                break;
            case WEEKMODE:

                CalActivity.navHandle.showWeek(NavID);
                CalActivity.listHandle.listWeek();
                NavModel.listInfo.setText("Week View");
                break;
            case DATEMODE:
                CalActivity.navHandle.showDate(NavID);
                CalActivity.listHandle.listDate();
                NavModel.listInfo.setText("Day View");
                break;
            case YEARMODE:
                NavModel.yearHolder.setText("<- " + String.valueOf(NavModel.currentYear + NavID) + " ->");
                break;
            default:
                break;
        } //switch (touchMode)
    } // private void evaluateAction()


    private void determineNavID(float getX, float getY){

        int TouchX=(int)getX;
        int TouchY=(int)getY;

        String stringValue;
        switch (touchMode) {
            case MONTHMODE:
                for (int monthInc = 0; monthInc <= 11; monthInc++) {

                    if( ( TouchX >= monthsMap[monthInc].X) && (TouchX < (monthsMap[monthInc].X+monthsWidth) ) )
                        if( ( TouchY >= monthsMap[monthInc].Y) && (TouchY < (monthsMap[monthInc].Y+monthsHeight) ) )
                        {NavID=monthInc; }

                } //for (int monthInc = 0; monthInc <= 11; monthInc++)
                break;
            case WEEKMODE:
                for (int weekInc = 1; weekInc <= navMaxWeek; weekInc++) {

                    if( ( TouchX >= weeksMap[weekInc].X) && (TouchX < (weeksMap[weekInc].X+weeksWidth) ) )
                        if( ( TouchY >= weeksMap[weekInc].Y) && (TouchY < (weeksMap[weekInc].Y+weeksHeight) ) )
                        {
                            stringValue = NavModel.weekHolder[weekInc].getText().toString();
                            if (stringValue.length()>0) NavID = Integer.parseInt(stringValue);
                        }

                } //for (int monthInc = 0; monthInc <= 11; monthInc++)
                break;
            case DATEMODE:
                for (int dateInc = 1; dateInc <= navMaxDate; dateInc++) {

                    if( ( TouchX >= datesMap[dateInc].X) && (TouchX < (datesMap[dateInc].X+datesWidth) ) )
                        if( ( TouchY >= datesMap[dateInc].Y) && (TouchY < (datesMap[dateInc].Y+datesHeight) ) )
                        {NavID=dateInc; }

                } //for (int monthInc = 0; monthInc <= 11; monthInc++)
                break;
            case YEARMODE:
                yearFling(getX);
                break;
            default:
                break;
        } //switch (touchMode)

    } //private void determineNavId(float getX, float getY)

    public void yearFling(float getCurrentX){

        float movementX;
        movementX = (getCurrentX - initialTouchX)/yearWidth;

        NavID = 0;
        if(movementX>0.25) NavID = 1;
        if(movementX<-0.25) NavID = -1;

    } //public void yearFling(float currentX)



    private void determineTouchMode(float getX, float getY){

        int IntTouchX=(int)getX;
        int IntTouchY=(int)getY;

        if ( (IntTouchY>yearMap.Y+yearHeight) && (IntTouchY<(monthsMap[11].Y+monthsHeight)) ){ //within lower navpad region

            if (IntTouchX > datesLeftBoundary) touchMode = DATEMODE;
            if ((IntTouchX < datesLeftBoundary) && (IntTouchX > weeksLeftBoundary))
                touchMode = WEEKMODE;
            if ((IntTouchX < weeksLeftBoundary) && (IntTouchY > (yearMap.Y + yearHeight)))
                touchMode = MONTHMODE;

        } //if (IntTouchY>yearMap.Y)

        if ( (IntTouchY<yearMap.Y+yearHeight)  ){ //within header navpad region

            if (IntTouchX < weeksLeftBoundary)
                touchMode = YEARMODE;

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


       } // if (monthsWidth < 0)


        //get Weeks
        weeksWidth = NavModel.weekHolder[1].getWidth(); //it's enough to get the first instance of a holder to represent all the rest
        weeksHeight = NavModel.weekHolder[1].getHeight();
        navMaxWeek = 1;
        for (int weekInc = 1; weekInc <= 6; weekInc++) {
            stringValue = NavModel.weekHolder[weekInc].getText().toString();
            if (stringValue.length()>0) {
                weeksMap[weekInc] = new WeeksMap();
                NavModel.weekHolder[weekInc].getLocationOnScreen(instanceLocation);
                weeksMap[weekInc].X = instanceLocation[0];
                weeksMap[weekInc].Y = instanceLocation[1];
                navMaxWeek = weekInc;
            } //if (stringValue.length()>0)
        } //for (int monthInc=1; monthInc<=12; monthInc++)

        weeksLeftBoundary = weeksMap[1].X; //location of any week instance is the left boundary for "weeks" zone
        datesLeftBoundary = weeksMap[1].X + weeksWidth; //right side of weeks zone is the left boundary of "days" zone


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
