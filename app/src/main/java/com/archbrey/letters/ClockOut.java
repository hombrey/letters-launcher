package com.archbrey.letters;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.support.v4.view.MotionEventCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.archbrey.letters.Preferences.MainSettings;
import com.archbrey.letters.Preferences.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClockOut {

    public static RelativeLayout clockoutBox;

    public static TextView clockView;
    public static TextView dateView;

    private static Context mainContext;
    private static Resources rClockout;

    private GlobalHolder global;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    private float initialTouchX;
    private static int screenWidth;
    private static int screenHeight;

    private static Handler msghandler;
    private static Runnable lingerMsg;

    public ClockOut() {

        global = new GlobalHolder();
        mainContext = global.getMainContext();

        clockView = new TextView(mainContext);
        dateView = new TextView(mainContext);
        clockoutBox = new RelativeLayout(mainContext);

        msghandler = new Handler();
        lingerMsg = new Runnable() {
            public void run() {
                refreshClock();
            } // public void run()
        }; //lingerMsg = new Runnable()



    } //public TypeOut(

    public RelativeLayout DrawBox(RelativeLayout getTypeoutBox,Resources getR){


        clockoutBox = getTypeoutBox;

        rClockout = getR;
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

        refreshClock();

        Display display = LaunchpadActivity.mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        int height = size.y;

        return clockoutBox;

    } //public RelativeLayout DrawBox(RelativeLayout getTypeoutBox,Context c,Resources getR)

    public void refreshClock(){

        Calendar c = Calendar.getInstance();

        clockView.setText(timeFormat.format(c.getTime()));
        dateView.setText(dateFormat.format(c.getTime()));
        dateView.append("\n");
        int day = c.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY: dateView.append(rClockout.getString(R.string.sun)); break;
            case Calendar.MONDAY: dateView.append(rClockout.getString(R.string.mon)); break;
            case Calendar.TUESDAY: dateView.append(rClockout.getString(R.string.tue)); break;
            case Calendar.WEDNESDAY: dateView.append(rClockout.getString(R.string.wed)); break;
            case Calendar.THURSDAY: dateView.append(rClockout.getString(R.string.thu)); break;
            case Calendar.FRIDAY: dateView.append(rClockout.getString(R.string.fri)); break;
            case Calendar.SATURDAY: dateView.append(rClockout.getString(R.string.sat)); break;
        } //switch (day)

    } //public void refreshClock()



    public void setListener(){

        clockView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) { //perform action of click

                        Intent openClockIntent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                        //  openClockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try {
                            mainContext.startActivity(openClockIntent);
                        } catch (ActivityNotFoundException anfe) {
                            // Log.d(TAG, "Google Voice Search is not found");
                        } //try

                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //findToggleView.Key.setOnClickListener

        dateView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) { //perform action of click

                        Intent calendarIntent = new Intent(Intent.ACTION_MAIN);
                        calendarIntent.addCategory(Intent.CATEGORY_APP_CALENDAR);
                        try {
                            mainContext.startActivity(calendarIntent);
                        } catch (ActivityNotFoundException anfe) {
                            // Log.d(TAG, "Google Voice Search is not found");
                        } //try - catch

                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //findToggleView.Key.setOnClickListener


        LaunchpadActivity.clockoutBox.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) { //perform action of click

                        /*
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setClassName("com.google.android.googlequicksearchbox",
                                "com.google.android.googlequicksearchbox.VoiceSearchActivity");
                        try {
                            mainContext.startActivity(intent);
                        } catch (ActivityNotFoundException anfe) {
                            // Log.d(TAG, "Google Voice Search is not found");
                        } //try*/


                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //findToggleView.Key.setOnClickListener


        View.OnTouchListener clockScreenListener;
        LaunchpadActivity.clockoutBox.setOnTouchListener(
                clockScreenListener = new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {

                        float currentX = event.getRawX();
                        //float currentY = event.getRawY();



                        int action = MotionEventCompat.getActionMasked(event);
                        switch (action) {
                            case (MotionEvent.ACTION_DOWN):
                                initialTouchX = currentX;
                                return false;
                            case (MotionEvent.ACTION_MOVE):
                                refreshBrightness(currentX);
                                return false;
                            case (MotionEvent.ACTION_UP):
                                msghandler.postDelayed(lingerMsg, 500);
                                return false;
                            default:
                                return true;
                        }//switch(action)
                    } //public boolean onTouch(View v, MotionEvent event)
                } //new OnTouchListener()
        );//filterItems[inc].button.setOnTouchListener


    } //public void setListener()


    private void refreshBrightness(float getCurrentX) {

        int curBrightness = android.provider.Settings.System.getInt(mainContext.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS,-1);
        float movement = (getCurrentX - initialTouchX)/screenWidth;
        float screenPosition = getCurrentX /screenWidth;
        float adjBrightness;
        int newBrightness;
        int newPercent;
        int movPercent = (int)(movement*100);

        float curPercent = ((float)curBrightness/255f);

        if(Math.abs(movement)>=0.05) {

            newPercent =(int)(100*screenPosition);
            clockView.setText(String.valueOf(newPercent));
            clockView.append("%");
            dateView.setText(rClockout.getString(R.string.display));
            newBrightness = (int)(255*screenPosition);

            Settings.System.putInt(mainContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(mainContext.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, newBrightness);

        } //if(Math.abs(movement)>=0.05)


       // newBrightness = (int)(curBrightness+10);
       // if (newBrightness>=255) newBrightness= 255;
       // if (newBrightness<=1) newBrightness= 1;
      //  LaunchpadActivity.mainActivity.getWindow().setAttributes(lp);
        //Settings.System.putInt(resolver, "screen_brightness", 30);
      //  Settings.System.putInt(mainContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
      //  Settings.System.putInt(mainContext.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, newBrightness);
    }



}
