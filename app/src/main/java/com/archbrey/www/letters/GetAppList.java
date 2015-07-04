package com.archbrey.www.letters;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;


import java.util.List;


public class GetAppList {

    //AppItem[] appItem;

    public AppItem[] all_appItems(PackageManager mainPkgMgr, AppItem[] appItem){

        PackageManager PkgMgr;
        PkgMgr = mainPkgMgr;

        final Intent pkgIntent = new Intent (Intent.ACTION_MAIN,null);
        pkgIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appPkgList = PkgMgr.queryIntentActivities(pkgIntent, 0);

        appItem = new AppItem[appPkgList.size()];

        for (int inc = 0; inc<appPkgList.size(); inc++){
            appItem[inc] = new AppItem();
            // appItem[inc].icon = appPkgList.get(inc).loadIcon(basicPkgMgr);
            appItem[inc].name = appPkgList.get(inc).activityInfo.packageName;
            appItem[inc].label = appPkgList.get(inc).loadLabel(PkgMgr).toString();

        } //for (int inc = 0; inc<appPkgList.size(); inc++)

        new SortApps().exchange_sort(appItem);

        return appItem;

    } //public AppItem[] all_appItems(PackageManager mainPkgMgr, AppItem[] appItem)


    public AppItem[] filterByFirstChar (PackageManager mainPkgMgr, AppItem[] appItem, String Search) {


        return appItem;
    } // filterByFirstChar (PackageManager mainPkgMgr, AppItem[] appItem, String Search)


} //public class GetAppList
