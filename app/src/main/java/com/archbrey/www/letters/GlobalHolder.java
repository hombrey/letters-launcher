package com.archbrey.www.letters;


import android.content.Context;
import android.widget.GridView;

public class GlobalHolder {

    private static AppItem[] currentAppItems;
    private static Context mainContext;
    private static GridView appGridView;


    public void setGridView (GridView getGridView){ appGridView = getGridView;
    }//public void setGridView (GridView getGridView)

    public GridView getGridView (){ return appGridView;
    }//public void setGridView (GridView getGridView)

    public void setMainContext (Context getContext){ mainContext = getContext;
    } //public void getMainContext (Context getContext)

    public Context getMainContext (){ return mainContext;
    } //public void getMainContext (Context getContext)


    public void setAppItem(AppItem[] appItems) {

        int ArraySize = appItems.length;

        currentAppItems = new AppItem[ArraySize];

        for (int inc = 0; inc<ArraySize; inc++){
            currentAppItems[inc] = new AppItem();
            currentAppItems[inc] = appItems[inc];
        } //for (int inc = 0; inc<ArraySize; inc++)

    } // public void setAppItem(AppItem[] appItems)


    public int getAppItemSize() {

        int appItemSize = currentAppItems.length;
        return appItemSize;

    } //public AppItem[] getAppItem()

    public AppItem[] getAppItem() {
            return currentAppItems;
    } //public AppItem[] getAppItem()



} //public class GlobalHolder
