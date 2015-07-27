package com.archbrey.letters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.GridView;

import com.archbrey.letters.Preferences.SettingsActivity;
//import android.widget.RelativeLayout;


public class DrawDrawerBox  {

    private static Context getViewContext;
    private static AppItem[] showItem;
    private static AppDrawerAdapter AppDrawerAdapterObject;
    private static GridView appGridView;
    private static GlobalHolder global;

    public DrawDrawerBox (Context c, GridView getAppGridView, AppItem[] appItem)  {

        getViewContext = c;
        showItem = appItem;
        appGridView = getAppGridView;

        global = new GlobalHolder();

        AppDrawerAdapterObject = new AppDrawerAdapter(getViewContext, showItem);
        appGridView.setAdapter(AppDrawerAdapterObject);
        appGridView.setBackgroundColor(SettingsActivity.backerColor);
        appGridView.setNumColumns(SettingsActivity.drawerColumns);

    } // public DrawDrawerBox (Context c, AppItem[] appItem)


    public void DrawBox() {

        appGridView.setAdapter(AppDrawerAdapterObject);
        appGridView.setBackgroundColor(SettingsActivity.backerColor);
        appGridView.setNumColumns(SettingsActivity.drawerColumns);

    } //public void DrawBox()


    public void setListener() {

        PackageManager pm;

        pm=global.getPackageManager();
        appGridView.setOnItemClickListener(new DrawerClickListener(getViewContext, showItem, pm));
        appGridView.setOnItemLongClickListener(new DrawerLongClickListener(getViewContext, showItem, pm));

    }

} //public class DrawDrawerBox
