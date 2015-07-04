package com.archbrey.www.letters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
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
    private TextView typeoutView;

    private View drawerBox ;


    private LinearLayout fillerBox;
    private TextView fillerView;

   // private ScrollView drawerView;
    PackageManager basicPkgMgr;
    AppDrawerAdapter AppDrawerAdapterObject;
    GridView appGridView;

    private Resources r;
    DrawKeypadBox KeypadBoxHandle;
    private  KeypadButton[] keypadButtons ;
    private  SideButton menuButton;
    private  SideButton delButton;




    AppItem[] appItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //keep layout in portrait

        r = getResources();
        basicPkgMgr = getPackageManager();

        mainScreen = new RelativeLayout(this);
       // mainScreen.setOrientation(LinearLayout.VERTICAL);
        mainScreen.setGravity(Gravity.BOTTOM);


        keypadButtons = new KeypadButton[36];
        for (int inc=0; inc<=35; inc++) {
            keypadButtons[inc] = new KeypadButton();
        } //for (inc=0; inc<=35; inc++)


        keypadBox = new LinearLayout(this);

        KeypadBoxHandle = new DrawKeypadBox(keypadBox,this, r);
                typeoutBox = KeypadBoxHandle.getLayout();
                keypadButtons = KeypadBoxHandle.getKeypadButton();
                menuButton = KeypadBoxHandle.getmenuButton();
                delButton = KeypadBoxHandle.getdelButton();

        drawTypeoutBox();
        drawFillerBox();

        //draw app drawer
        drawerBox = LayoutInflater.from(this).inflate(R.layout.drawerbox, null);

        //drawerView = (ScrollView) findViewById(R.id.letter_drawer);

        typeoutBox.setId(R.id.typeoutBox);
        keypadBox.setId(R.id.keypadBox);
        drawerBox.setId(R.id.drawerBox);
        fillerBox.setId(R.id.fillerBox);

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
        keypadBoxParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        mainScreen.addView(fillerBox, fillerBoxParams);
        mainScreen.addView(drawerBox, drawerBoxParams);
        mainScreen.addView(typeoutBox, typeoutBoxParams);
        mainScreen.addView(keypadBox, keypadBoxParams);

        setContentView(mainScreen);

        get_appItems();

        new KeypadTouchListener(keypadButtons, typeoutView);

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

        typeoutView = new TextView(this);
        typeoutView.setText("Hello there");
        typeoutView.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams typeoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
            //typeoutParams.gravity=Gravity.CENTER;


        typeoutBox = new LinearLayout(this);
        typeoutBox.setOrientation(LinearLayout.HORIZONTAL);
        //keypadTopRow.setBackgroundColor(Color.BLUE);
        //typeoutBox.setGravity(Gravity.CENTER_HORIZONTAL);

        typeoutBox.addView(typeoutView, typeoutParams);

    } //drawTypeoutBox;

    public void get_appItems(){
        final Intent pkgIntent = new Intent (Intent.ACTION_MAIN,null);
        pkgIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appPkgList = basicPkgMgr.queryIntentActivities(pkgIntent, 0);


        appItem = new AppItem[appPkgList.size()];

        for (int inc = 0; inc<appPkgList.size(); inc++){
            appItem[inc] = new AppItem();
            // appItem[inc].icon = appPkgList.get(inc).loadIcon(basicPkgMgr);
            appItem[inc].name = appPkgList.get(inc).activityInfo.packageName;
            appItem[inc].label = appPkgList.get(inc).loadLabel(basicPkgMgr).toString();

        } //for (int inc = 0; inc<appPkgList.size(); inc++)


        /*
        appItem = new AppItem[5];

        for (int inc = 0; inc<5; inc++){
            appItem[inc] = new AppItem();
            // appItem[inc].icon = appPkgList.get(inc).loadIcon(basicPkgMgr);
            appItem[inc].name = appPkgList.get(inc).activityInfo.packageName;
            appItem[inc].label = appPkgList.get(inc).loadLabel(basicPkgMgr).toString();

        } //for (int inc = 0; inc<appPkgList.size(); inc++)*/

        new SortApps().exchange_sort(appItem);

        AppDrawerAdapterObject = new AppDrawerAdapter(this, appItem);

        appGridView = (GridView) findViewById(R.id.drawer_content);
        appGridView.setAdapter(AppDrawerAdapterObject);
        //appGridView.setOnItemClickListener(new DrawerClicklistener(this, appItem, basicPkgMgr));
        //appGridView.setOnItemLongClickListener(new DrawerLongClicklistener(this, slidingDrawer, mainScreen));

    } //public void set_appItem()

} //public class LaunchpadActivity
