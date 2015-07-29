package com.archbrey.letters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.archbrey.letters.Preferences.MainSettings;
import com.archbrey.letters.Preferences.SettingsActivity;


public class ClockOut {

    public static RelativeLayout clockoutBox;

    public static TextView clockView;
    public static TextView dateView;

    private static Context mainContext;
    private static Resources rTypeout;

    private GlobalHolder global;

    public ClockOut() {

        global = new GlobalHolder();
        mainContext = global.getMainContext();

        clockView = new TextView(mainContext);
        dateView = new TextView(mainContext);
        clockoutBox = new RelativeLayout(mainContext);


    } //public TypeOut(

    public RelativeLayout DrawBox(RelativeLayout getTypeoutBox,Resources getR){


        clockoutBox = getTypeoutBox;

        rTypeout = getR;
        int clockTextSize = 80;
        int dateTextSize = 20;

        int clockTop_margin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,200,
                getR.getDisplayMetrics());

        int dateTop_margin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 15,
                getR.getDisplayMetrics());

        clockView.setText("19:30");
        clockView.setGravity(Gravity.CENTER_HORIZONTAL);
        clockView.setTextSize(TypedValue.COMPLEX_UNIT_SP, clockTextSize);
        clockView.setTextColor(SettingsActivity.textColor);

        dateView.setText("29 Jul 2015 \n newline");
        dateView.setGravity(Gravity.CENTER_HORIZONTAL);
        dateView.setTextSize(TypedValue.COMPLEX_UNIT_SP, dateTextSize);
        dateView.setTextColor(SettingsActivity.textColor);

        clockView.setId(R.id.clockView);

        RelativeLayout.LayoutParams clockviewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, //width
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height

        clockviewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
       // clockviewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        clockviewParams.addRule(RelativeLayout.CENTER_VERTICAL);
        clockviewParams.setMargins(0, clockTop_margin, 0, 0);

        RelativeLayout.LayoutParams dateviewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, //width
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height
        dateviewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        dateviewParams.addRule(RelativeLayout.BELOW, clockView.getId());
        dateviewParams.setMargins(0, dateTop_margin, 0, 0);

        clockoutBox.setBackgroundColor(SettingsActivity.clockBack);
        clockoutBox.addView(clockView, clockviewParams);
        clockoutBox.addView(dateView, dateviewParams);

        return clockoutBox;

    } //public RelativeLayout DrawBox(RelativeLayout getTypeoutBox,Context c,Resources getR)


    public void setListener(){


        clockView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) { //perform action of click

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setClassName("com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.VoiceSearchActivity");
                        try {
                            mainContext.startActivity(intent);
                        } catch (ActivityNotFoundException anfe) {
                            // Log.d(TAG, "Google Voice Search is not found");
                        } //try

                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //findToggleView.Key.setOnClickListener



    } //

}
