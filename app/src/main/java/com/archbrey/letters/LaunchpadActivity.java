package com.archbrey.letters;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import android.graphics.Color;
//import android.util.TypedValue;
import android.content.res.Resources;
import android.view.Gravity;
import android.content.pm.ActivityInfo;

//import android.util.Log;

public class LaunchpadActivity extends Activity {

    private RelativeLayout mainScreen;
    private LinearLayout keypadBox;
    private RelativeLayout typeoutBox;
    private LinearLayout filterBox;
    private LinearLayout fillerBox;

    private View drawerBox ;


   // private ScrollView drawerView;
    PackageManager basicPkgMgr;
    GridView appGridView;

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

    AppItem[] appItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //keep layout in portrait

        r = getResources();
        basicPkgMgr = getPackageManager();

        mainScreen = new RelativeLayout(this);
       // mainScreen.setOrientation(LinearLayout.VERTICAL);
        mainScreen.setGravity(Gravity.BOTTOM);

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
                typeoutBox = typeoutBoxHandle.DrawBox(typeoutBox,this,r);
                typeoutView = typeoutBoxHandle.getTypeoutView();


        //draw app drawer
        drawerBox = LayoutInflater.from(this).inflate(R.layout.drawerbox, null);

        assembleScreen();

        setContentView(mainScreen);

        //set variables to be used by other classes
        global = new GlobalHolder();
        appGridView = (GridView) findViewById(R.id.drawer_content);
        global.setDrawerBox(drawerBox);
        global.setTypeoutBox(typeoutBox);
        global.setGridView(appGridView);
        global.setMainContext(this);
        global.setPackageManager(basicPkgMgr);
        global.setFindString("");
        global.setDelButton(delButton);
        //global.setResources(r);

        //setup initial app list
        new GetAppList().initialize();
        appItems = new GetAppList().all_appItems(basicPkgMgr, appItems);
        filterBoxHandle.refreshFilterItems(appItems);

        drawDrawerBox = new DrawDrawerBox (this, appGridView, appItems);
        drawDrawerBox.setListener();
        //appGridView.setBackgroundColor(r.getColor(R.color.Black_transparent));

        //setup listeners
        new KeypadTouchListener(keypadButtons,delButton, typeoutView);
        new FilterBoxTouchListener(filterItems,typeoutView);
        typeoutBoxHandle.setListener();

        //setup intents
        IntentFilter Package_update_filter = new IntentFilter();
        Package_update_filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        Package_update_filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        Package_update_filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        Package_update_filter.addDataScheme("package");
        registerReceiver(new RefreshAppItemReceiver(), Package_update_filter);

    }// protected void onCreate(Bundle savedInstanceState)


    @Override
    protected void onResume(){
        super.onResume();
        filterBoxHandle.refreshRecentItems();

    } //protected void onResume()

    @Override
    protected void onStart(){
        super.onStart();

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } //public boolean onOptionsItemSelected(MenuItem item)


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

            appItems = new GetAppList().all_appItems(basicPkgMgr, appItems);
            appGridView = (GridView) findViewById(R.id.drawer_content);
            new DrawDrawerBox (context, appGridView, appItems);

        }

    } //public class PacReceiver extends BroadcastReceiver


} //public class LaunchpadActivity
