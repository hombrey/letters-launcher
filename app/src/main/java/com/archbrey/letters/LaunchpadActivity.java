package com.archbrey.letters;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.res.Resources;
import android.view.Gravity;
import android.content.pm.ActivityInfo;


//import com.archbrey.letters.Preferences.ClockSettings;
import com.archbrey.letters.Preferences.SettingsActivity;

//import android.util.Log;

public class LaunchpadActivity extends Activity {

    public static RelativeLayout mainScreen;
    public static  LinearLayout keypadBox;
    public static RelativeLayout typeoutBox;
    public static LinearLayout filterBox;
    public static RelativeLayout clockoutBox;
    private static LinearLayout filler;

    public static View drawerBox ;
    public static ClockOut clockoutHandle;

    public static OptionsCall optionsHandle;

   // private ScrollView drawerView;
    private static PackageManager basicPkgMgr;
    public static GridView appGridView;

    private Resources r;
    DrawKeypadBox KeypadBoxHandle;
    DrawFilterBox filterBoxHandle;
    TypeOut typeoutBoxHandle;

    private FilterItem[] filterItems;
    private GlobalHolder global;
    public static DrawDrawerBox drawDrawerBox;

    private TextView typeoutView;

    private static RefreshAppItemReceiver appUpdater;
    private static AppItem[] allAppItems;

    public static SharedPreferences prefs;
    public static String prefName = "LettersPrefs";

    public static boolean hideDrawerAllApps;
    private static boolean isForeground;

    public static Activity mainActivity;

    public static String colorScheme;

    public static boolean isSetAsHome;
    private static boolean setAsHomeChanged;
    private static boolean prepareToStop;
    private static Bundle reuseBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        reuseBundle = savedInstanceState;

       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //keep layout in portrait
        global = new GlobalHolder();
        r = getResources();
        basicPkgMgr = getPackageManager();
        hideDrawerAllApps = true;
        mainActivity = this;
        setAsHomeChanged = false;
        prepareToStop = false;

        AutoRescaleFonts();

        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        getPreferences();

        mainScreen = new RelativeLayout(this);
        mainScreen.setGravity(Gravity.BOTTOM);

        global.setMainContext(this);
        drawBoxes();

        if (getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
            assembleHorizontal();
        else
            assembleVertical();


        setContentView(mainScreen);

        //set variables to be used by other classes
        appGridView = (GridView) findViewById(R.id.drawer_content);

        global.setDrawerBox(drawerBox);
        global.setTypeoutBox(typeoutBox);
        global.setGridView(appGridView);
        global.setPackageManager(basicPkgMgr);
        global.setFindString("");
        global.setResources(r);

        //setup initial app list
        //new GetAppList().initialize(); //needed only when "recents" feature is enabled
        allAppItems = new GetAppList().all_appItems(basicPkgMgr);
        filterBoxHandle.refreshFilterItems(allAppItems);

        drawDrawerBox = new DrawDrawerBox (this, appGridView, allAppItems);
        drawDrawerBox.setListener();

        //setup listeners
        new KeypadTouchListener(typeoutView);
        new FilterBoxTouchListener(filterItems,typeoutView);
        typeoutBoxHandle.setListener();
        clockoutHandle.setListener();


        //setup receivers
        IntentFilter Package_update_filter = new IntentFilter();
        Package_update_filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        Package_update_filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        Package_update_filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        Package_update_filter.addDataScheme("package");

        appUpdater = new RefreshAppItemReceiver();
        //registerReceiver(new RefreshAppItemReceiver(), Package_update_filter);
        registerReceiver(appUpdater, Package_update_filter);

        new KeypadShortcuts().RetrieveSavedShortcuts(this);

        optionsHandle = new OptionsCall();

    }// protected void onCreate(Bundle savedInstanceState)


    @Override
    public void onBackPressed() {

            if (TypeOut.editMode > 10) {
                keypadBox.setVisibility(View.VISIBLE);
                filterBox.setVisibility(View.VISIBLE);
                TypeOut.findToggleView.setVisibility(View.VISIBLE);
                TypeOut.editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TypeOut.TextSize);
                TypeOut.editView.setText("  "); //spacer to make the tap target larger
                TypeOut.editView.append(String.valueOf(Character.toChars(177))); //plus minus button
                TypeOut.editView.append("  "); //x button
                drawerBox.setVisibility(View.INVISIBLE);
                TypeOut.typeoutBox.setVisibility(View.GONE);
                //  TypeOut.typeoutView.setText("");
            } //if (TypeOut.editMode > 10)

        if (isSetAsHome) {
            clockoutBox.setVisibility(View.VISIBLE);
            clockoutHandle.refreshClock();
            drawerBox.setVisibility(View.INVISIBLE);
            TypeOut.typeoutBox.setVisibility(View.GONE);
            typeoutBoxHandle.setFindStatus(false);
            TypeOut.typeoutView.setText("");
            hideDrawerAllApps = true; //this standardizes behavior when pressing home button
        }

        if (!isSetAsHome) {
            drawerBox.setVisibility(View.VISIBLE);
           // TypeOut.typeoutBox.setVisibility(View.VISIBLE);
            clockoutBox.setVisibility(View.GONE);
           // if (TypeOut.editMode <= 10)  super.onBackPressed();
        } //if (!isSetAsHome)

    } //public void onBackPressed()


    @Override
    protected void onResume() {
        super.onResume();

        boolean tempReadStatus;
        tempReadStatus=IsHome();

        if (tempReadStatus!=isSetAsHome) {
            isSetAsHome = tempReadStatus;
            setAsHomeChanged = true;
        }

        if (!isSetAsHome) {
            TypeOut.typeoutView.setText("");
            hideDrawerAllApps = false;
            if (setAsHomeChanged) {
                appGridView.clearTextFilter();
                appGridView.setAdapter(null);
                setAsHomeChanged = false;
                onCreate(reuseBundle);
                drawDrawerBox = new DrawDrawerBox (this, appGridView, allAppItems);
                drawDrawerBox.setListener();
                prepareToStop = true;
            } //if (setAsHomeChanged)
            typeoutBox.setVisibility(View.VISIBLE);
            drawerBox.setVisibility(View.VISIBLE);
            clockoutBox.setVisibility(View.GONE);
            TypeOut.editView.setVisibility(View.GONE);
            drawDrawerBox.DrawBox(allAppItems);
        } //if (!isSetAsHome)
        else {
            if (setAsHomeChanged) {
                onCreate(reuseBundle);
            }//if (setAsHomeChanged)
        } //else of if (!isSetAsHome)


        if (hideDrawerAllApps) {
            drawerBox.setVisibility(View.INVISIBLE);
            typeoutBox.setVisibility(View.GONE);
        } // if (hideDrawerAllApps)

        global.setFindString("");
        typeoutBoxHandle.setFindStatus(false); //stop search mode if length = 0;
        // filterBoxHandle.refreshRecentItems();

        if (SettingsActivity.SettingChanged) {
            onCreate(reuseBundle);
            if (!isSetAsHome) clockoutBox.setVisibility(View.GONE);
            SettingsActivity.SettingChanged = false;
        }
        clockoutHandle.refreshClock();


    } //protected void onResume()

    @Override
    protected void onStart(){
        super.onStart();

        isForeground = true;
    } //protected void onStart()


    @Override
    protected void onPause() {
        super.onPause();

        if (prepareToStop) {
            //finish();
            appGridView.clearTextFilter();
            appGridView.setAdapter(null);
           onCreate(reuseBundle);
            drawDrawerBox = new DrawDrawerBox (this, appGridView, allAppItems);
            drawDrawerBox.setListener();

        }

    } //protected void onRestart()


    @Override
    protected void onStop(){
        super.onStop();
        isForeground = false;
    } //protected void onStart()


    @Override
    protected void onDestroy(){
        super.onDestroy();

        appGridView.clearTextFilter();
        appGridView.setAdapter(null);

        unregisterReceiver(appUpdater);

    } //protected void onStart()


    private void disassembleScreen(){
        //setContentView(null);
        mainScreen.removeView(filterBox);
        mainScreen.removeView(keypadBox);
        mainScreen.removeView(typeoutBox);
        mainScreen.removeView(drawerBox);
        if (SettingsActivity.clockVisibility != 0) mainScreen.removeView(clockoutBox);
    } //private void disassembleScreen()


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        drawerBox.setVisibility(View.VISIBLE);
        typeoutBox.setVisibility(View.GONE);
        clockoutBox.setVisibility(View.GONE);
        optionsHandle.DrawBox(appGridView, this, r);

        return false;
    }//public boolean onCreateOptionsMenu(Menu menu)


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    } //protected void onActivityResult(int requestCode, int resultCode, Intent data)

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (isForeground) {

            if (Intent.ACTION_MAIN.equals(intent.getAction()) && isSetAsHome) {
                // Log.i("MyLauncher", "onNewIntent: HOME Key");

                LaunchpadActivity.keypadBox.setVisibility(View.VISIBLE);
                LaunchpadActivity.filterBox.setVisibility(View.VISIBLE);
                TypeOut.findToggleView.setVisibility(View.VISIBLE);
                TypeOut.editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TypeOut.TextSize);
                TypeOut.editView.setText("  "); //spacer to make the tap target larger
                TypeOut.editView.append(String.valueOf(Character.toChars(177))); //plus minus button
                TypeOut.editView.append("  "); //spacer to make the tap target larger
                typeoutBoxHandle.setFindStatus(false);
                TypeOut.typeoutView.setText("");
                toggleHideAllApps();

                SettingsActivity.menuLevel = 0;
                // SettingsActivity.settingsScreen.removeView(SettingsActivity.viewpadBox);

            } //if (Intent.ACTION_MAIN.equals(intent.getAction()))

        } //if isForeground;

    } //protected void onNewIntent(Intent intent)

    private void toggleHideAllApps(){

        if (hideDrawerAllApps) { hideDrawerAllApps = false;}
        else {
           hideDrawerAllApps = true;
        }//if (hideDrawerAllApps) { hideDrawerAllApps = false;}

        if (!hideDrawerAllApps) {

            LaunchpadActivity.typeoutBox.setVisibility(View.VISIBLE);
            LaunchpadActivity.drawerBox.setVisibility(View.VISIBLE);
            LaunchpadActivity.clockoutBox.setVisibility(View.GONE);
            TypeOut.editView.setVisibility(View.GONE);

            drawDrawerBox.DrawBox(allAppItems);
            drawDrawerBox.setListener();

        } //if (hideDrawerAllApps=false)
        else {
            LaunchpadActivity.typeoutBox.setVisibility(View.GONE);
            LaunchpadActivity.drawerBox.setVisibility(View.GONE);
            LaunchpadActivity.clockoutBox.setVisibility(View.VISIBLE);
            clockoutHandle.refreshClock();

        } //else of if (hideDrawerAllApps=false)


    } //private void toggleHideAllApps()



    private void getPreferences(){

        colorScheme = prefs.getString("colorscheme", "black");

        Integer clockVisibility = prefs.getInt("clockVisibility",1);

        Integer columns = prefs.getInt("column_num", 4);
        Integer textSize = prefs.getInt("drawerTextSize", 17);

        Integer keyHeight = prefs.getInt("keyboardHeight",38);
        Integer fltHeight = prefs.getInt("filterHeight",7);



        SettingsActivity.filterCodes = new String[6];

        SettingsActivity.filterCodes[0] = prefs.getString("filter0", "Www");
        SettingsActivity.filterCodes[1] = prefs.getString("filter1", "Com");
        SettingsActivity.filterCodes[2] = prefs.getString("filter2", "Fav");
        SettingsActivity.filterCodes[3] = prefs.getString("filter3", "Calc");
        SettingsActivity.filterCodes[4] = prefs.getString("filter4", "Fun");
        SettingsActivity.filterCodes[5] = prefs.getString("filter5", "Sys");

        SettingsActivity.textColor = r.getColor(R.color.white);
        SettingsActivity.backColor = r.getColor(R.color.Black_transparent);
        SettingsActivity.backerColor = r.getColor(R.color.Blacker_transparent);
        SettingsActivity.backSelectColor = r.getColor(R.color.grey50);
        SettingsActivity.transparent = r.getColor(R.color.transparent);

        //SettingsActivity.clockBack = r.getColor(R.color.White_transparent);

        if (colorScheme.equals("white")) {
            SettingsActivity.textColor = r.getColor(R.color.black);
            SettingsActivity.backColor = r.getColor(R.color.White_transparent);
            SettingsActivity.backerColor = r.getColor(R.color.Whiter_transparent);
            //SettingsActivity.backSelectColor = r.getColor(R.color.grey50);
        } //if if colorScheme.equals("white")

        SettingsActivity.clockVisibility = clockVisibility;
        //new ClockSettings().setBackground();
        setClockBackground();

        SettingsActivity.drawerColumns = columns;
        SettingsActivity.drawerTextSize = textSize;
        SettingsActivity.keyboardHeight = keyHeight;
        SettingsActivity.filterHeight = fltHeight;

    } //private void setColorTheme()

    private void drawBoxes(){

        keypadBox = new LinearLayout(this);
        KeypadBoxHandle = new DrawKeypadBox(keypadBox,this, r);
        KeypadBoxHandle.drawKeypadBox();
        keypadBox = KeypadBoxHandle.getLayout();

        filterBox = new LinearLayout(this);
        filterBoxHandle = new DrawFilterBox(filterBox,this,r);
        filterBoxHandle.drawFilterBox();
        filterBox = filterBoxHandle.getLayout();
        filterItems = filterBoxHandle.getFilterItems();

        typeoutBox = new RelativeLayout(this);
        typeoutBoxHandle = new TypeOut();
        typeoutBox = typeoutBoxHandle.DrawBox(typeoutBox, this, r);
        typeoutView = typeoutBoxHandle.getTypeoutView();

        clockoutBox = new RelativeLayout(this);
        clockoutHandle = new ClockOut();
        clockoutBox = clockoutHandle.DrawBox(clockoutBox, r);

        //draw app drawer
        drawerBox = LayoutInflater.from(this).inflate(R.layout.drawerbox, null);

    } //private void drawBoxes()



    private void assembleVertical(){

        drawerBox.setId(R.id.drawerBox);
        typeoutBox.setId(R.id.typeoutBox);
        keypadBox.setId(R.id.keypadBox);
        filterBox.setId(R.id.filterBox);
        clockoutBox.setId(R.id.clockoutBox);

        RelativeLayout.LayoutParams clockoutBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        clockoutBoxParams.addRule(RelativeLayout.ABOVE, keypadBox.getId());

        RelativeLayout.LayoutParams drawerBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        drawerBoxParams.addRule(RelativeLayout.ABOVE, typeoutBox.getId());

        RelativeLayout.LayoutParams typeoutBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        typeoutBoxParams.addRule(RelativeLayout.ABOVE, keypadBox.getId());

        RelativeLayout.LayoutParams keypadBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        keypadBoxParams.addRule(RelativeLayout.ABOVE, filterBox.getId());

        RelativeLayout.LayoutParams filterBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        filterBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);


        mainScreen.addView(filterBox, filterBoxParams);
        mainScreen.addView(keypadBox, keypadBoxParams);
        mainScreen.addView(typeoutBox, typeoutBoxParams);
        mainScreen.addView(drawerBox, drawerBoxParams);
        if (SettingsActivity.clockVisibility != 0) mainScreen.addView(clockoutBox, clockoutBoxParams);

    } //private void assembleVertical()


    private void assembleHorizontal(){

        int screenWidth;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.y;
       // int height = size.y;


        drawerBox.setId(R.id.drawerBox);
        typeoutBox.setId(R.id.typeoutBox);
        keypadBox.setId(R.id.keypadBox);
        filterBox.setId(R.id.filterBox);
        clockoutBox.setId(R.id.clockoutBox);

        RelativeLayout.LayoutParams clockoutBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        clockoutBoxParams.addRule(RelativeLayout.LEFT_OF, filterBox.getId());
        clockoutBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        RelativeLayout.LayoutParams drawerBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        drawerBoxParams.addRule(RelativeLayout.LEFT_OF, filterBox.getId());
        drawerBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        RelativeLayout.LayoutParams typeoutBoxParams = new RelativeLayout.LayoutParams(
                screenWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        typeoutBoxParams.addRule(RelativeLayout.ABOVE, keypadBox.getId());
        typeoutBoxParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        RelativeLayout.LayoutParams keypadBoxParams = new RelativeLayout.LayoutParams(
                screenWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        keypadBoxParams.addRule(RelativeLayout.ABOVE, filterBox.getId());
        keypadBoxParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        RelativeLayout.LayoutParams filterBoxParams = new RelativeLayout.LayoutParams(
                screenWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        filterBoxParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        filterBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);


        filler = new LinearLayout(this);
        filler.setBackgroundColor(SettingsActivity.backerColor);

        RelativeLayout.LayoutParams fillerParams = new RelativeLayout.LayoutParams(
                screenWidth,
                LinearLayout.LayoutParams.MATCH_PARENT);
        fillerParams.addRule(RelativeLayout.ABOVE, typeoutBox.getId());
        fillerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);


        mainScreen.addView(filterBox, filterBoxParams);
        mainScreen.addView(keypadBox, keypadBoxParams);
        mainScreen.addView(typeoutBox, typeoutBoxParams);
        mainScreen.addView(drawerBox, drawerBoxParams);
        mainScreen.addView(filler,fillerParams);
        if (SettingsActivity.clockVisibility != 0) mainScreen.addView(clockoutBox, clockoutBoxParams);

    } //private void assembleHorizontal()


    public void setClockBackground() {

        SettingsActivity.clockBack = r.getColor(R.color.transparent);

        if (LaunchpadActivity.colorScheme.equals("black")) {
            switch (SettingsActivity.clockVisibility) {
                case 2: SettingsActivity.clockBack = r.getColor(R.color.Blackless_transparent);break;
                case 3: SettingsActivity.clockBack = r.getColor(R.color.Black_transparent);break;
                case 4: SettingsActivity.clockBack = r.getColor(R.color.Blacker_transparent);break;
                default: break;
            } //switch (SettingsActivity.clockVisibility)
        } // if (LaunchpadActivity.colorScheme.equals("black"))
        else {
            switch (SettingsActivity.clockVisibility) {
                case 2: SettingsActivity.clockBack = r.getColor(R.color.Whiteless_transparent);break;
                case 3: SettingsActivity.clockBack = r.getColor(R.color.White_transparent);break;
                case 4: SettingsActivity.clockBack = r.getColor(R.color.Whiter_transparent);break;
                default: break;
            } //switch (SettingsActivity.clockVisibility)

        } //else of if (LaunchpadActivity.colorScheme.equals("black"))

    } //public void setBackground()

    private boolean IsHome(){

        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null) {
            // should not happen. A home is always installed, isn't it?
        } if ("com.archbrey.letters".equals(res.activityInfo.packageName)) {
            return true;
        } else {
            return false;
        }

    } //private boolean IsHome()

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

    public class RefreshAppItemReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            allAppItems = new GetAppList().all_appItems(basicPkgMgr);
            typeoutBox.setVisibility(View.GONE);
            drawerBox.setVisibility(View.GONE);
            new KeypadShortcuts().RetrieveSavedShortcuts(context);
            filterBoxHandle.refreshFilterItems(allAppItems);

        }

    } //public class PacReceiver extends BroadcastReceiver


} //public class LaunchpadActivity
