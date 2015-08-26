package com.archbrey.Kalist;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
//import android.widget.HorizontalScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListModel {

    public static ScrollView listBox;
    private static Context mainContext;



public ListModel(){

    mainContext = CalActivity.c;
    listBox = new ScrollView(mainContext);

} //public ListModel()


public void drawBox() {

    listBox.setBackgroundColor(SettingsActivity.backerColor);
   // listBox.setOrientation(LinearLayout.VERTICAL);

    LinearLayout[] sampledView;
    TextView[] sampleText;

    LinearLayout mainLayout;


    LinearLayout.LayoutParams sampleParams = new LinearLayout.LayoutParams (
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    mainLayout = new LinearLayout(mainContext);
    sampledView = new LinearLayout[31];
    sampleText = new TextView[31];

    mainLayout.setOrientation(LinearLayout.VERTICAL);

    for (int inc=0; inc<=30; inc++) {

        sampleText[inc] = new TextView(mainContext);
        sampleText[inc].setTextColor(SettingsActivity.textColor);
        sampleText[inc].setGravity(Gravity.CENTER);
        sampleText[inc].setText("Hello \n");


        sampledView[inc] = new LinearLayout(mainContext);
        sampledView[inc].addView(sampleText[inc]);
        mainLayout.addView(sampledView[inc], sampleParams);

    }


    listBox.addView(mainLayout,sampleParams);

} //public static void drawBox()



} //public class ListModel
