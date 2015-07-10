package com.archbrey.www.letters;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
//import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//import android.graphics.Color;
//import android.util.TypedValue;
import android.content.res.Resources;
import android.view.Gravity;
//import android.view.View.OnTouchListener;
//import android.view.MotionEvent;
//import android.support.v4.view.MotionEventCompat;
import android.content.pm.ActivityInfo;

//import android.util.Log;
//import android.widget.EditText;

public class LaunchpadActivity extends Activity {

    private RelativeLayout mainScreen;
    private LinearLayout keypadBox;
    private LinearLayout typeoutBox;
    private LinearLayout filterBox;
    private TextView typeoutView;

    private int typeoutTextSize;

    private View drawerBox ;


    private LinearLayout fillerBox;
    private TextView fillerView;

   // private ScrollView drawerView;
    PackageManager basicPkgMgr;
    AppDrawerAdapter AppDrawerAdapterObject;
    GridView appGridView;

    private Resources r;
    DrawKeypadBox KeypadBoxHandle;
    DrawFilterBox filterBoxHandle;
    private  KeypadButton[] keypadButtons ;
    private FilterItem[] filterItems;
    private  SideButton menuButton;
    private  SideButton delButton;
    private GlobalHolder global;
    private DrawDrawerBox drawDrawerBox;

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

        drawTypeoutBox();
        drawFillerBox();

        //draw app drawer
        drawerBox = LayoutInflater.from(this).inflate(R.layout.drawerbox, null);

        drawerBox.setId(R.id.drawerBox);
        fillerBox.setId(R.id.fillerBox);
        typeoutBox.setId(R.id.typeoutBox);
        keypadBox.setId(R.id.keypadBox);
        filterBox.setId(R.id.filterBox);

        RelativeLayout.LayoutParams fillerBoxParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        fillerBoxParams.addRule(RelativeLayout.ABOVE, drawerBox.getId());

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
        mainScreen.addView(fillerBox, fillerBoxParams);


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
        //global.setResources(r);

        //setup initial app list
        appItems = new GetAppList().all_appItems(basicPkgMgr, appItems);

        drawDrawerBox = new DrawDrawerBox (this, appGridView, appItems);
        drawDrawerBox.setListener();
        //appGridView.setBackgroundColor(r.getColor(R.color.Black_transparent));

        //setup listeners
        new KeypadTouchListener(keypadButtons,delButton, typeoutView);
        new FilterBoxTouchListener(filterItems,typeoutView);

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

    private void drawFillerBox(){

        fillerView = new TextView(this);
        fillerView.setBackgroundColor(r.getColor(R.color.Black_transparent));

        LinearLayout.LayoutParams fillerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        fillerBox = new LinearLayout(this);
        fillerBox.addView(fillerView, fillerParams);

    } //drawFillerBox


    private void drawTypeoutBox(){

        typeoutTextSize = 24;
        typeoutView = new TextView(this);
        typeoutView.setText("Hello there");
        typeoutView.setGravity(Gravity.CENTER_HORIZONTAL);
        typeoutView.setTextSize(TypedValue.COMPLEX_UNIT_SP, typeoutTextSize);
        typeoutView.setTextColor(Color.WHITE);

        LinearLayout.LayoutParams typeoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
            //typeoutParams.gravity=Gravity.CENTER;


        typeoutBox = new LinearLayout(this);
        typeoutBox.setOrientation(LinearLayout.HORIZONTAL);
        typeoutBox.setBackgroundColor(r.getColor(R.color.Blacker_transparent));
        //typeoutBox.setGravity(Gravity.CENTER_HORIZONTAL);

        typeoutBox.addView(typeoutView, typeoutParams);

    } //drawTypeoutBox;

    public class RefreshAppItemReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            appItems = new GetAppList().all_appItems(basicPkgMgr, appItems);
            appGridView = (GridView) findViewById(R.id.drawer_content);
            new DrawDrawerBox (context, appGridView, appItems);

        }

    } //public class PacReceiver extends BroadcastReceiver


} //public class LaunchpadActivity
