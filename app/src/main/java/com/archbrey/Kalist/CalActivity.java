package com.archbrey.Kalist;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;


public class CalActivity extends Activity {

    public static RelativeLayout mainScreen;

    public static Resources r;
    public static Context c;
    public static Activity mainActivity;

    public static NavModel navHandle;
    public static NavListener navListener;

    public static ListModel listHandle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        r = getResources();
        c = this;
        mainActivity = this;

        AutoRescaleFonts();
        getPreferences();


        drawBoxes();
        assembleVertical();
        setContentView(mainScreen);

        navListener = new NavListener();
        navListener.setListener();


    }

    @Override
    protected void onStart(){
        super.onStart();



    } //protected void onStart()

    @Override
    protected void onResume() {
        super.onResume();

    } //protected void onResume()

    private void drawBoxes(){

        navHandle = new NavModel();
        navHandle.drawBox();

        listHandle = new ListModel();
        listHandle.drawBox();


    } //private void drawBoxes()

    private void assembleVertical(){

        mainScreen = new RelativeLayout(this);
        //mainScreen.setGravity(Gravity.BOTTOM);


        RelativeLayout.LayoutParams navBoxParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, //width
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height
        navBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);


        RelativeLayout.LayoutParams listBoxParams = new RelativeLayout.LayoutParams (
                RelativeLayout.LayoutParams.MATCH_PARENT, //width
                RelativeLayout.LayoutParams.MATCH_PARENT); //height
        listBoxParams.addRule(RelativeLayout.ABOVE, NavModel.navBox.getId());


        mainScreen.addView(ListModel.listBox, listBoxParams);
        mainScreen.addView(NavModel.navBox, navBoxParams);


    } //private void assembleVertical()

    private void getPreferences(){

        SettingsActivity.textColor = r.getColor(R.color.white);
        SettingsActivity.backColor = r.getColor(R.color.Black_transparent);
        SettingsActivity.backerColor = r.getColor(R.color.Blacker_transparent);
        SettingsActivity.backSelectColor = r.getColor(R.color.grey50);
        SettingsActivity.transparent = r.getColor(R.color.transparent);
        SettingsActivity.navHeight = 38;

    } // private void getPreferences()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void AutoRescaleFonts(){
        Configuration configuration = r.getConfiguration();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if ((metrics.xdpi>=310)||(metrics.ydpi>=310)){
            configuration.fontScale=(float) 0.9;
        } // if ((metrics.xdpi>=310)||(metrics.ydpi>=310))

        if ( configuration.fontScale > 1) {
            configuration.fontScale=(float) 1;
        } //if ( configuration.fontScale > 1)

        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    } //private void AutoRescaleFonts()

}
