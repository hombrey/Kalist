package com.archbrey.Kalist;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class TopOut {

    public static RelativeLayout topOutBox;
    public static TextView dateInfo;
    public static TextView dayInfo;



    public TopOut(){

        topOutBox = new RelativeLayout(CalActivity.c);
        dateInfo = new TextView(CalActivity.c);
        dayInfo = new TextView(CalActivity.c);

    } //public TopOut()

    public void drawBox(){

        //dateInfo.setGravity(Gravity.LEFT);
        dateInfo.setText("date");
        dateInfo.setTextColor(SettingsActivity.textColor);

        //dayInfo.setGravity(Gravity.LEFT);
        dayInfo.setText("Today");
        dayInfo.setTextColor(SettingsActivity.textColor);

        dateInfo.setId(R.id.dateInfo);
        dayInfo.setId(R.id.dayInfo);

        RelativeLayout.LayoutParams dateInfoParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        dateInfoParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        dateInfoParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        RelativeLayout.LayoutParams dayInfoParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        dayInfoParams.addRule(RelativeLayout.BELOW,dateInfo.getId());
        dayInfoParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        topOutBox.setBackgroundColor(SettingsActivity.backerColor);
        topOutBox.addView(dateInfo,dateInfoParams);
        topOutBox.addView(dayInfo, dayInfoParams);

    }//public void DrawBox()

    public void showMonth(){
        TopOut.dateInfo.setText(NavModel.fullMonthStr[NavListener.NavID]);
    }//public void showMonth()

    public void showWeek(){
        TopOut.dateInfo.setText("Week " + String.valueOf(NavListener.NavID));
    }//public void showWeek()

    public void showDate(){
        String getMonth;
        String getYear;
        String getDate;

        getMonth =NavModel.fullMonthStr[NavModel.navMonthCalendar.get(Calendar.MONTH)];
        getYear = String.valueOf(NavModel.navMonthCalendar.get(Calendar.YEAR));
        getDate = String.valueOf(NavListener.NavID);

        TopOut.dateInfo.setText(getMonth+" "+getDate+", "+getYear);


    }//public void showWeek()

} //public class TopOut
