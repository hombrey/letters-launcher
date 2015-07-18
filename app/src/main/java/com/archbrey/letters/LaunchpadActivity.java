package com.archbrey.letters;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.res.Resources;
import android.view.Gravity;
import android.content.pm.ActivityInfo;

import com.archbrey.letters.Preferences.SettingsActivity;

//import android.util.Log;

public class LaunchpadActivity extends Activity {

    private RelativeLayout mainScreen;
    private LinearLayout keypadBox;
    private RelativeLayout typeoutBox;
    private LinearLayout filterBox;


    private View drawerBox ;

   // private ScrollView drawerView;
    PackageManager basicPkgMgr;
    public static GridView appGridView;

    private Resources r;
    DrawKeypadBox KeypadBoxHandle;
    DrawFilterBox filterBoxHandle;
    TypeOut typeoutBoxHandle;

    private  KeypadButton[] keypadButtons ;
    private FilterItem[] filterItems;
    private  SideButton menuButton;
    private  SideButton delButton;
    private GlobalHolder global;
    private DrawDrawerBox drawDrawerBox;

    private TextView typeoutView;

    public static int textColor;
    public static int backColor;
    public static int backerColor;

    public static Context Cfirst;

    private static RefreshAppItemReceiver appUpdater;
    AppItem[] appItems;

    public static SharedPreferences prefs;
    public static String prefName = "LettersPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //keep layout in portrait
        global = new GlobalHolder();
        r = getResources();
        basicPkgMgr = getPackageManager();
        Cfirst = this;

        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        getPreferences();

        mainScreen = new RelativeLayout(this);
        mainScreen.setGravity(Gravity.BOTTOM);

        drawBoxes();
        assembleScreen();

        setContentView(mainScreen);


        //set variables to be used by other classes
        appGridView = (GridView) findViewById(R.id.drawer_content);
        global.setDrawerBox(drawerBox);
        global.setTypeoutBox(typeoutBox);
        global.setGridView(appGridView);
        global.setMainContext(this);
        global.setPackageManager(basicPkgMgr);
        global.setFindString("");
        global.setDelButton(delButton);
        global.setResources(r);

        //setup initial app list
        new GetAppList().initialize();
        appItems = new GetAppList().all_appItems(basicPkgMgr);
        filterBoxHandle.refreshFilterItems(appItems);

        drawDrawerBox = new DrawDrawerBox (this, appGridView, appItems);
        drawDrawerBox.setListener();
        //appGridView.setBackgroundColor(r.getColor(R.color.Black_transparent));

        //setup listeners
        new KeypadTouchListener(keypadButtons,delButton, typeoutView);
        new FilterBoxTouchListener(filterItems,typeoutView);
        typeoutBoxHandle.setListener();

        menuButton.Key.setOnClickListener( new Button.OnClickListener() {
                    public void onClick(View v) {openOptionsMenu(); }
                } //new Button.OnClickListener()
        );// menuButton.Key.setOnClickListener


        //setup intents
        IntentFilter Package_update_filter = new IntentFilter();
        Package_update_filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        Package_update_filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        Package_update_filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        Package_update_filter.addDataScheme("package");

        appUpdater = new RefreshAppItemReceiver();
        //registerReceiver(new RefreshAppItemReceiver(), Package_update_filter);
        registerReceiver(appUpdater, Package_update_filter);

    }// protected void onCreate(Bundle savedInstanceState)


    @Override
    protected void onResume() {
        super.onResume();
        drawerBox.setVisibility(View.INVISIBLE);
        typeoutBox.setVisibility(View.INVISIBLE);
        global.setFindString("");
        typeoutBoxHandle.setFindStatus(false); //stop search mode if length = 0;
        filterBoxHandle.refreshRecentItems();

    } //protected void onResume()

    @Override
    protected void onStart(){
        super.onStart();

    } //protected void onStart()

    @Override
    protected void onDestroy(){
        super.onDestroy();

        unregisterReceiver(appUpdater);

    } //protected void onStart()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launchpad, menu);

        return true;
    }//public boolean onCreateOptionsMenu(Menu menu)

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_sys_settings:
                    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS) );
                        return true;
            case R.id.action_launcher_settings:
                    SettingsActivity.menuArea="MainSettings";
                    final Intent launcherSettings = new Intent("com.archbrey.letters.Preferences.SettingsActivity");
                  startActivityForResult(launcherSettings, 100);
                  //  startActivity(launcherSettings);
                        return true;
            default:
                    return super.onOptionsItemSelected(item);
        } //switch (id)

    } //public boolean onOptionsItemSelected(MenuItem item)

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //force on destroy
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //on create at portrait
        //startActivityForResult(new Intent(this, LaunchpadActivity.class), 0);

    } //protected void onActivityResult(int requestCode, int resultCode, Intent data)



    private void getPreferences(){

        String colorScheme = prefs.getString("colorscheme","");
        Integer columns = prefs.getInt("column_num", 4);
        Integer textSize = prefs.getInt ("drawerTextSize",17);


        SettingsActivity.textColor = r.getColor(R.color.white);
        SettingsActivity.backColor = r.getColor(R.color.Black_transparent);
        SettingsActivity.backerColor = r.getColor(R.color.Blacker_transparent);

        if (colorScheme.equals("white")) {
            SettingsActivity.textColor = r.getColor(R.color.black);
            SettingsActivity.backColor = r.getColor(R.color.White_transparent);
            SettingsActivity.backerColor = r.getColor(R.color.Whiter_transparent);
        } //if if colorScheme.equals("white")

        SettingsActivity.drawerColumns = columns;
        SettingsActivity.drawerTextSize = textSize;

    } //private void setColorTheme()

    private void drawBoxes(){

        keypadBox = new LinearLayout(this);
        KeypadBoxHandle = new DrawKeypadBox(keypadBox,this, r);
        keypadBox = KeypadBoxHandle.getLayout();
        keypadButtons = KeypadBoxHandle.getKeypadButton();
        menuButton = KeypadBoxHandle.getmenuButton();
        delButton = KeypadBoxHandle.getdelButton();

        filterBox = new LinearLayout(this);
        filterBoxHandle = new DrawFilterBox(filterBox,this,r);
        filterBox = filterBoxHandle.getLayout();
        filterItems = filterBoxHandle.getFilterItems();

        typeoutBox = new RelativeLayout(this);
        typeoutBoxHandle = new TypeOut();
        typeoutBox = typeoutBoxHandle.DrawBox(typeoutBox, this, r);
        typeoutView = typeoutBoxHandle.getTypeoutView();


        //draw app drawer
        drawerBox = LayoutInflater.from(this).inflate(R.layout.drawerbox, null);

    } //private void drawBoxes()

    private void assembleScreen(){

        drawerBox.setId(R.id.drawerBox);
        typeoutBox.setId(R.id.typeoutBox);
        keypadBox.setId(R.id.keypadBox);
        filterBox.setId(R.id.filterBox);


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
        // keypadBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        keypadBoxParams.addRule(RelativeLayout.ABOVE, filterBox.getId());

        RelativeLayout.LayoutParams filterBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        filterBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        mainScreen.addView(filterBox, filterBoxParams);
        mainScreen.addView(keypadBox, keypadBoxParams);
        mainScreen.addView(typeoutBox, typeoutBoxParams);
        mainScreen.addView(drawerBox, drawerBoxParams);

    } //private void assembleScreen()

    public class RefreshAppItemReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            appItems = new GetAppList().all_appItems(basicPkgMgr);
            appGridView = (GridView) findViewById(R.id.drawer_content);
            new DrawDrawerBox (context, appGridView, appItems);

        }

    } //public class PacReceiver extends BroadcastReceiver


} //public class LaunchpadActivity
