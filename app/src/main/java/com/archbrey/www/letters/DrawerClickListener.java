package com.archbrey.www.letters;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class DrawerClickListener implements OnItemClickListener  {

    Context clickListenerContext;
    AppItem[] appItemForListener;
    PackageManager pmForListener;

    public DrawerClickListener (Context c, AppItem[] appItem, PackageManager pm){
        clickListenerContext = c;
        appItemForListener=appItem;
        pmForListener = pm;
    } //public DrawerClickListener(Context c, MainActivity.AppItem[] appItem, PackageManager pm)


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //if (MainActivity.appLaunchable)
        {
            /*
            Intent launchIntent = pmForListener.getLaunchIntentForPackage(appItemForListener[position].name);
            clickListenerContext.startActivity(launchIntent);*/

            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(appItemForListener[position].pkgname, appItemForListener[position].name);
            launchIntent.setComponent(cp);

            clickListenerContext.startActivity(launchIntent);

        }//if (MainActivity.appLaunchable)
    }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)

} //public class DrawerClicklistener implements onItemClickListener