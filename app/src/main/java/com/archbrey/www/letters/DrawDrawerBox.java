package com.archbrey.www.letters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class DrawDrawerBox extends LaunchpadActivity {

    private Context getViewContext;
    private AppItem[] showItem;
    private AppDrawerAdapter AppDrawerAdapterObject;
    private GridView appGridView;

    public DrawDrawerBox (Context c, GridView getAppGridView, AppItem[] appItem)  {

        getViewContext = c;
        showItem = appItem;
        appGridView = getAppGridView;


        AppDrawerAdapterObject = new AppDrawerAdapter(getViewContext, showItem);
        appGridView.setAdapter(AppDrawerAdapterObject);

       // appGridView.setOnItemClickListener(new DrawerClickListener(getViewContext, appItem, basicPkgMgr));
       // appGridView.setOnItemLongClickListener(new DrawerLongClickListener(getViewContext, appItem, basicPkgMgr));

    } // public DrawDrawerBox (Context c, AppItem[] appItem)


    public void setListener() {

        appGridView.setOnItemClickListener(new DrawerClickListener(getViewContext, showItem, basicPkgMgr));
        appGridView.setOnItemLongClickListener(new DrawerLongClickListener(getViewContext, showItem, basicPkgMgr));

    }

} //public class DrawDrawerBox
