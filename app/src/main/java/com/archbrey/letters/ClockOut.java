package com.archbrey.letters;

//import android.app.Activity;
//import android.app.Instrumentation;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Point;
//import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.MotionEventCompat;
//import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
//import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.archbrey.letters.Preferences.MainSettings;
import com.archbrey.letters.Preferences.SettingsActivity;

//import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.List;
import java.util.Locale;
//import java.util.Date;

public class ClockOut {

    public static GlobalHolder global;

    public static RelativeLayout clockoutBox;

    public static TextView clockView;
    public static TextView dateView;

    private static TextView leftGestures;
    private static TextView rightGestures;

    private static Context mainContext;
    private static Resources rClockout;


  //  private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
  //  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    private float initialTouchX;
    private float initialTouchY;
    private static int screenWidth;
    private static int screenHeight;
   // private static int screenHeight;

    private static Handler msghandler;
    private static Runnable lingerMsg;

    private static Handler autoBrighthandler;
    private static Runnable enableAutoBright;

    private static Handler popSelectHandler;
    private static Runnable triggerPopMenu;

    private static SimpleDateFormat dateFormat ;
    private static SimpleDateFormat timeFormat ;

    private final int TAP_CLOCK = 50;
    private final int UP_LEFTSIDE = 51;
    private final int DN_LEFTSIDE = 52;
    private final int UP_RIGHTSIDE = 53;
    private final int DN_RIGHTSIDE = 54;

    PackageManager pmForClock;

    public static int swipeType;
   // public static String gestureName;

    private static int touchOrientation; //0 for unset, 1 for vertical, 2 for horizontal

    Locale loc;


    private static AppItem[] allApps;
    public static GestureShortcut[] gestureShortcuts;

    public ClockOut() {

        global = new GlobalHolder();
        mainContext = global.getMainContext();
        pmForClock = global.getPackageManager();

        clockView = new TextView(mainContext);
        dateView = new TextView(mainContext);
        leftGestures = new TextView(mainContext);
        rightGestures = new TextView(mainContext);
        clockoutBox = new RelativeLayout(mainContext);

        rClockout = global.getResources();
        loc = rClockout.getConfiguration().locale;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", loc);
        timeFormat = new SimpleDateFormat("HH:mm", loc);

        swipeType = 0;

        gestureShortcuts = new GestureShortcut[55];
        for (int inc=50; inc<=54; inc++){
            gestureShortcuts[inc] = new GestureShortcut();
            switch (inc) {
                case TAP_CLOCK:
                    gestureShortcuts[inc].typeName= rClockout.getString(R.string.tap_clock);
                    break;
                case UP_LEFTSIDE:
                    gestureShortcuts[inc].typeName= String.valueOf(Character.toChars(8593)) + " " +rClockout.getString(R.string.left_side);
                    break;
                case DN_LEFTSIDE:
                    gestureShortcuts[inc].typeName=String.valueOf(Character.toChars(8595)) + " " +rClockout.getString(R.string.left_side);
                    break;
                case UP_RIGHTSIDE:
                    gestureShortcuts[inc].typeName=String.valueOf(Character.toChars(8593)) + " " + rClockout.getString(R.string.right_side);
                    break;
                case DN_RIGHTSIDE:
                    gestureShortcuts[inc].typeName=String.valueOf(Character.toChars(8595)) + " " + rClockout.getString(R.string.right_side);
                    break;
            } //switch (inc)
        } //for (int inc=50; inc<=54; inc++)

        msghandler = new Handler();
        lingerMsg = new Runnable() {
            public void run() {
                refreshClock();
            } // public void run()
        }; //lingerMsg = new Runnable()

        autoBrighthandler = new Handler();
        enableAutoBright = new Runnable() {
            public void run() {
                Settings.System.putInt(mainContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                clockView.setText(rClockout.getString(R.string.display));
            } // public void run()
        }; //lingerMsg = new Runnable()



        popSelectHandler = new Handler();
        triggerPopMenu = new Runnable(){
            public void run() {
                popSelectBox ();
            } // public void run()
        }; //triggerPopMenu = new Runnable()

    } //public TypeOut(

    public RelativeLayout DrawBox(RelativeLayout getTypeoutBox,Resources getR){


        clockoutBox = getTypeoutBox;

        //rClockout = getR;
        int clockTextSize = 80;
        int dateTextSize = 20;
        int gestureInfoSize = 10;

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

        rightGestures.setText("RIGHT");
        rightGestures.setGravity(Gravity.RIGHT);
        rightGestures.setTextSize(TypedValue.COMPLEX_UNIT_SP, gestureInfoSize);
        rightGestures.setTextColor(SettingsActivity.textColor);

        leftGestures.setText("LEFT");
        leftGestures.setGravity(Gravity.RIGHT);
        leftGestures.setTextSize(TypedValue.COMPLEX_UNIT_SP, gestureInfoSize);
        leftGestures.setTextColor(SettingsActivity.textColor);

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

        RelativeLayout.LayoutParams leftGestureParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, //width
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height
        leftGestureParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftGestureParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        RelativeLayout.LayoutParams rightGestureParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, //width
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height
        rightGestureParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightGestureParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        clockoutBox.setBackgroundColor(SettingsActivity.clockBack);
        clockoutBox.addView(clockView, clockviewParams);
        clockoutBox.addView(dateView, dateviewParams);
        clockoutBox.addView(leftGestures, leftGestureParams);
        clockoutBox.addView(rightGestures, rightGestureParams);

        refreshClock();

        Display display = LaunchpadActivity.mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        return clockoutBox;

    } //public RelativeLayout DrawBox(RelativeLayout getTypeoutBox,Context c,Resources getR)

    public void refreshClock(){

        Calendar c = Calendar.getInstance();

        clockView.setText(timeFormat.format(c.getTime()));
        dateView.setText(dateFormat.format(c.getTime()) + "\n");
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

       // DisplayMetrics metrics = new DisplayMetrics();


    } //public void refreshClock()


    public void setListener(){

        clockView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) { //perform action of click

                        Intent launchIntent = pmForClock.getLaunchIntentForPackage(gestureShortcuts[TAP_CLOCK].shortcutPackage);
                        if (launchIntent != null) {
                            global.getMainContext().startActivity(launchIntent);

                        } else //if (launchIntent.resolveActivity(pmForListener) != null)
                        {
                            shortcutPicker(TAP_CLOCK);
                        } //else of  if (launchIntent.resolveActivity(pmForGesture) != null)


                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //findToggleView.Key.setOnClickListener

        clockView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) { //perform action of click
                        shortcutPicker(TAP_CLOCK);
                        return true;
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

/*
        LaunchpadActivity.clockoutBox.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) { //perform action of click

                        // implement gesture selection
                        //popSelectBox ();
                        return true;
                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //findToggleView.Key.setOnClickListener
*/


        LaunchpadActivity.clockoutBox.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {

                        float currentX = event.getRawX();
                        float currentY = event.getRawY();

                        int action = MotionEventCompat.getActionMasked(event);
                        switch (action) {
                            case (MotionEvent.ACTION_DOWN):
                                initialTouchX = currentX;
                                initialTouchY = currentY;
                                msghandler.removeCallbacks(lingerMsg);
                                popSelectHandler.removeCallbacks(triggerPopMenu);
                                touchOrientation = 0; //reset to unknown touch orientation
                                popSelectHandler.postDelayed(triggerPopMenu, 500);
                                return true;
                            case (MotionEvent.ACTION_MOVE):
                                //if (touchOrientation==0) popSelectHandler.postDelayed(triggerPopMenu, 500);
                                if ((touchOrientation==0)||(touchOrientation==2)) determineBrightness(currentX);
                                if ((touchOrientation==0)||(touchOrientation==1)) determineVerticalGesture(currentY);
                                return true;
                            case (MotionEvent.ACTION_UP):
                                msghandler.postDelayed(lingerMsg, 500);
                                autoBrighthandler.removeCallbacks(enableAutoBright);
                                if (touchOrientation==1) evaluateGesture();
                                return true;
                            default:
                                return true;
                        }//switch(action)
                    } //public boolean onTouch(View v, MotionEvent event)
                } //new OnTouchListener()
        );//filterItems[inc].button.setOnTouchListener


    } //public void setListener()


    public void RetrieveSavedShortcuts (Context getContext) {

        allApps = global.getAllAppItems();
        mainContext = getContext;
        DBHelper dbHandler = new DBHelper(mainContext);

        for (int inc=50; inc<=54; inc++) {

            String shortcutFound = dbHandler.RetrievePackage(inc);

            //assign " " by default and fill this up if shortcut exists
            gestureShortcuts[inc].shortcutPackage = " ";
            gestureShortcuts[inc].shortcutLabel = " ";
            for (int appInc=0; appInc<allApps.length; appInc++) {

                if (shortcutFound.equals(allApps[appInc].pkgname)  ){
                    gestureShortcuts[inc].shortcutLabel = allApps[appInc].label;
                    gestureShortcuts[inc].shortcutPackage = allApps[appInc].pkgname;
                    break;
                } //if (DrawKeypadBox.keypadButton[inc].ShortcutPackage.equals(allApps[appInc].pkgname)  )

            } //(int appInc=0; appInc<allApps.length; appInc++)

        } //for (int inc=50; inc<=54; inc++)

    } // public class RetrieveSavedShortcuts ()

    private void evaluateGesture(){

        GestureShortcut launchGesture;

        //gestureName=" ";

        launchGesture = new GestureShortcut();
        launchGesture = gestureShortcuts[swipeType];

        Intent launchIntent = pmForClock.getLaunchIntentForPackage(launchGesture.shortcutPackage);
        if (launchIntent != null) {
            global.getMainContext().startActivity(launchIntent);

        } else //if (launchIntent.resolveActivity(pmForListener) != null)
        {
            shortcutPicker(swipeType);
        } //else of  if (launchIntent.resolveActivity(pmForGesture) != null)


    } //private void evaluateGesture()


    private void determineVerticalGesture(float getCurrentY){

    float movementY;
    movementY = (getCurrentY - initialTouchY)/screenHeight;

        if(Math.abs(movementY)>=0.05) {
            popSelectHandler.removeCallbacks(triggerPopMenu);
            touchOrientation = 1; //lock to only sense vertical movement

            if(initialTouchX>screenWidth/2) { //if on right side
                if (movementY>0) swipeType = DN_RIGHTSIDE;
                if (movementY<0) swipeType = UP_RIGHTSIDE;
            }else { //if on left side
                if (movementY>0) swipeType = DN_LEFTSIDE;
                if (movementY<0) swipeType = UP_LEFTSIDE;
            }

        } //if(Math.abs(movementY)>=0.05)

    } //private void determineVerticalGesture(float getCurrentY)


    private void determineBrightness(float getCurrentX) {

        int curBrightness = android.provider.Settings.System.getInt(mainContext.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS,-1);
        float movementX;
       // float screenPosition;
        float adjBrightness;
        int newBrightness;
        int newPercent;
       // int movPercent;

        //screenPosition = getCurrentX /screenWidth;
        movementX = (getCurrentX - initialTouchX)/screenWidth;
       // movPercent = (int)(movementX*100);

        float curPercent = ((float)curBrightness/255f);

        if(Math.abs(movementX)>=0.05) {

           // autoBrighthandler.removeCallbacks(enableAutoBright);
           // autoBrighthandler.postDelayed(enableAutoBright, 700);
            popSelectHandler.removeCallbacks(triggerPopMenu);
            touchOrientation = 2; //lock to only sense horizontal movement
            dateView.setText(rClockout.getString(R.string.display));
            adjBrightness = (curPercent+movementX);
            if (adjBrightness>=1) adjBrightness = 1;
            if (adjBrightness<=0) adjBrightness = 0;

            newBrightness = (int)(255*(adjBrightness));
            newPercent =(int)(100*(adjBrightness));

            clockView.setText(String.valueOf(newPercent) + "%");

            Settings.System.putInt(mainContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(mainContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, newBrightness);

            initialTouchX =  getCurrentX;

        } //if(Math.abs(movement)>=0.05)

    } //private void refreshBrightness(float getCurrentX)


    public void shortcutPicker(int getSwipeType){

        TypeOut.editMode = getSwipeType;
        LaunchpadActivity.hideDrawerAllApps = false;

        LaunchpadActivity.keypadBox.setVisibility(View.GONE);
        LaunchpadActivity.filterBox.setVisibility(View.INVISIBLE);
        LaunchpadActivity.clockoutBox.setVisibility(View.GONE);
        LaunchpadActivity.drawerBox.setVisibility(View.VISIBLE);
        TypeOut.typeoutBox.setVisibility(View.VISIBLE);
        TypeOut.findToggleView.setVisibility(View.GONE);
        TypeOut.editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        TypeOut.typeoutView.setText(gestureShortcuts[getSwipeType].typeName);
        if (gestureShortcuts[getSwipeType].shortcutPackage.length() >1) {
            TypeOut.editView.setVisibility(View.VISIBLE);
            TypeOut.typeoutView.append(" - ");
            TypeOut.typeoutView.append(gestureShortcuts[getSwipeType].shortcutLabel );
            TypeOut.editView.setText("  "); //make x button larger
            TypeOut.editView.append(String.valueOf(Character.toChars(215))); //x button
            TypeOut.editView.append(" "); //make x button larger
        }//if (DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel.length()>1)
        else {
            TypeOut.typeoutView.append(" - " + rClockout.getString(R.string.unassigned));
            TypeOut.editView.setText(" "); //x button not needed if unassigned
        } //else of if (DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel.length()>1)

        allApps = global.getAllAppItems();
        LaunchpadActivity.drawDrawerBox.DrawBox(allApps);

        LaunchpadActivity.appGridView.setOnItemClickListener(new ClickSelectListener(getSwipeType));

    } //private void shorcutPicker(Integer getGesture)


    private class ClickSelectListener implements AdapterView.OnItemClickListener{

        int clickSwipeType;

        public ClickSelectListener(int getSwipeType){
            clickSwipeType = getSwipeType;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            gestureShortcuts[clickSwipeType].shortcutPackage = allApps[position].pkgname;
            gestureShortcuts[clickSwipeType].shortcutLabel = allApps[position].label;
            shortcutPicker(clickSwipeType);

            DBHelper dbHandler = new DBHelper(mainContext);
            dbHandler.AssignShorcut(clickSwipeType, allApps[position].pkgname);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)


    } //private class ClickSelectListener implements AdapterView.OnItemClickListener


    public void popSelectBox () {

        AppItem[] menuItems;
        LaunchpadActivity.hideDrawerAllApps = false;

        LaunchpadActivity.keypadBox.setVisibility(View.GONE);
        LaunchpadActivity.filterBox.setVisibility(View.INVISIBLE);
        LaunchpadActivity.clockoutBox.setVisibility(View.GONE);
        LaunchpadActivity.drawerBox.setVisibility(View.VISIBLE);
        TypeOut.typeoutBox.setVisibility(View.VISIBLE);
        TypeOut.findToggleView.setVisibility(View.GONE);
        TypeOut.editView.setVisibility(View.GONE);
        TypeOut.typeoutView.setText(rClockout.getString(R.string.select_gesture));

        menuItems = new AppItem[4];

        for (int menuInc=0; menuInc<menuItems.length; menuInc++) menuItems[menuInc] = new AppItem();

        menuItems[0].label=gestureShortcuts[UP_LEFTSIDE].typeName;
        menuItems[1].label=gestureShortcuts[DN_LEFTSIDE].typeName;
        menuItems[2].label=gestureShortcuts[UP_RIGHTSIDE].typeName;
        menuItems[3].label=gestureShortcuts[DN_RIGHTSIDE].typeName;

        LaunchpadActivity.drawDrawerBox.DrawBox(menuItems);
        setPopMenuListener();

    } //public LinearLayout DrawBox ()

    public void setPopMenuListener() {LaunchpadActivity.appGridView.setOnItemClickListener(new PopMenuClickListener());}


    private class PopMenuClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            switch (position) {
                case 0:
                    shortcutPicker(UP_LEFTSIDE);
                    break;
                case 1:
                    shortcutPicker(DN_LEFTSIDE);
                    break;
                case 2:
                    shortcutPicker(UP_RIGHTSIDE);
                    break;
                case 3:
                    shortcutPicker(DN_RIGHTSIDE);
                    break;
                default:
                    break;
            } //switch (position)

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    } //private class PopMenuClickListener implements AdapterView.OnItemClickListener


} //public class ClockOut
