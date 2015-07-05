package com.archbrey.www.letters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.LinearLayout;


public class DrawDrawerBox extends LaunchpadActivity {

    Context getViewContext;
    AppItem[] showItem;
    AppDrawerAdapter AppDrawerAdapterObject;


    public DrawDrawerBox (Context c, GridView appGridView, AppItem[] appItem)  {

        getViewContext = c;
        showItem = appItem;
        AppDrawerAdapterObject = new AppDrawerAdapter(getViewContext, showItem);
        appGridView.setAdapter(AppDrawerAdapterObject);

        appGridView.setOnItemClickListener(new DrawerClickListener(getViewContext, appItem, basicPkgMgr));
        //appGridView.setOnItemLongClickListener(new DrawerLongClicklistener(getViewContext, slidingDrawer, mainScreen));

    } // public DrawDrawerBox (Context c, AppItem[] appItem)


} //public class DrawDrawerBox
