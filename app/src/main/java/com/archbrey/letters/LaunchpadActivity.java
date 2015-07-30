package com.archbrey.letters;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

    private static RelativeLayout mainScreen;
    public static  LinearLayout keypadBox;
    public static RelativeLayout typeoutBox;
    public static LinearLayout filterBox;
    public static RelativeLayout clockoutBox;

    public static View drawerBox ;
    public static ClockOut clockoutHandle;

   // private ScrollView drawerView;
    PackageManager basicPkgMgr;
    public static GridView appGridView;

    private Resources r;
    DrawKeypadBox KeypadBoxHandle;
    DrawFilterBox filterBoxHandle;
    TypeOut typeoutBoxHandle;

   // private  KeypadButton[] keypadButtons ;
    private FilterItem[] filterItems;
    //private  SideButton menuButton;
    private GlobalHolder global;
    public static DrawDrawerBox drawDrawerBox;

    private TextView typeoutView;

    private static RefreshAppItemReceiver appUpdater;
    private static AppItem[] allAppItems;

    public static SharedPreferences prefs;
    public static String prefName = "LettersPrefs";

    public static boolean hideDrawerAllApps;
   // private static boolean isForeground;

    public static Activity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //keep layout in portrait
        global = new GlobalHolder();
        r = getResources();
        basicPkgMgr = getPackageManager();
        hideDrawerAllApps = true;
        mainActivity = this;

        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        getPreferences();

        mainScreen = new RelativeLayout(this);
        mainScreen.setGravity(Gravity.BOTTOM);

        global.setMainContext(this);
        drawBoxes();
        assembleScreen();

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
        new GetAppList().initialize();
        allAppItems = new GetAppList().all_appItems(basicPkgMgr);
        filterBoxHandle.refreshFilterItems(allAppItems);

        drawDrawerBox = new DrawDrawerBox (this, appGridView, allAppItems);
        drawDrawerBox.setListener();

        //setup listeners
        new KeypadTouchListener(typeoutView);
        new FilterBoxTouchListener(filterItems,typeoutView);
        typeoutBoxHandle.setListener();
        clockoutHandle.setListener();

        DrawKeypadBox.menuButton.Key.setOnClickListener( new Button.OnClickListener() {
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

        new KeypadShortcuts().RetrieveSavedShortcuts(this);



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
            TypeOut.typeoutBox.setVisibility(View.INVISIBLE);
          //  TypeOut.typeoutView.setText("");
        } //if (TypeOut.editMode > 10)


        LaunchpadActivity.clockoutBox.setVisibility(View.VISIBLE);
        clockoutHandle.refreshClock();
        drawerBox.setVisibility(View.INVISIBLE);
        TypeOut.typeoutBox.setVisibility(View.INVISIBLE);
        typeoutBoxHandle.setFindStatus(false);
        TypeOut.typeoutView.setText("");
        LaunchpadActivity.hideDrawerAllApps = true; //this standardizes behavior when pressing home button
    }



    @Override
    protected void onResume() {
        super.onResume();

        if (hideDrawerAllApps) {
            drawerBox.setVisibility(View.INVISIBLE);
            typeoutBox.setVisibility(View.INVISIBLE);
        } // if (hideDrawerAllApps)

        global.setFindString("");
        typeoutBoxHandle.setFindStatus(false); //stop search mode if length = 0;
        // filterBoxHandle.refreshRecentItems();

        if (SettingsActivity.SettingChanged) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            SettingsActivity.SettingChanged = false;
        }

        clockoutHandle.refreshClock();

    } //protected void onResume()

    @Override
    protected void onStart(){
        super.onStart();

    } //protected void onStart()

    @Override
    protected void onStop(){
        super.onStop();

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
                   //startActivityForResult(launcherSettings, 421);
                    startActivity(launcherSettings);
                    return true;
            case R.id.action_wallpaper_settings:
                final Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
                startActivity(Intent.createChooser(pickWallpaper, getString(R.string.chooser_wallpaper)));
            default:
                    return super.onOptionsItemSelected(item);
        } //switch (id)

    } //public boolean onOptionsItemSelected(MenuItem item)


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    } //protected void onActivityResult(int requestCode, int resultCode, Intent data)

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (Intent.ACTION_MAIN.equals(intent.getAction()) ) {
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

            SettingsActivity.menuLevel=0;
           // SettingsActivity.settingsScreen.removeView(SettingsActivity.viewpadBox);

        } //if (Intent.ACTION_MAIN.equals(intent.getAction()))


    } //protected void onNewIntent(Intent intent)

    private void toggleHideAllApps(){

        if (hideDrawerAllApps) { hideDrawerAllApps = false;}
        else {hideDrawerAllApps = true; }

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

        String colorScheme = prefs.getString("colorscheme", "black");

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
        SettingsActivity.clockBack = r.getColor(R.color.White_transparent);

        if (colorScheme.equals("white")) {
            SettingsActivity.textColor = r.getColor(R.color.black);
            SettingsActivity.backColor = r.getColor(R.color.White_transparent);
            SettingsActivity.backerColor = r.getColor(R.color.Whiter_transparent);
            //SettingsActivity.backSelectColor = r.getColor(R.color.grey50);
        } //if if colorScheme.equals("white")

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




    private void assembleScreen(){

        drawerBox.setId(R.id.drawerBox);
        typeoutBox.setId(R.id.typeoutBox);
        keypadBox.setId(R.id.keypadBox);
        filterBox.setId(R.id.filterBox);
        clockoutBox.setId(R.id.clockoutBox);

        RelativeLayout.LayoutParams clockoutBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        clockoutBoxParams.addRule(RelativeLayout.ABOVE, keypadBox.getId());
        //clockoutBoxParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

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
        mainScreen.addView(clockoutBox, clockoutBoxParams);

    } //private void assembleScreen()

    public class RefreshAppItemReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            allAppItems = new GetAppList().all_appItems(basicPkgMgr);
            new KeypadShortcuts().RetrieveSavedShortcuts(context);
            filterBoxHandle.refreshFilterItems(allAppItems);

        }

    } //public class PacReceiver extends BroadcastReceiver


} //public class LaunchpadActivity
