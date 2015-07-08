package com.archbrey.www.letters;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

public class GlobalHolder {

    private static AppItem[] currentAppItems;
    private static Context mainContext;
    private static GridView appGridView;
    private static PackageManager pkgMgr;
    private static String findString;
    private static View drawerBox;
    private static LinearLayout typeoutBox;


    public void setTypeoutBox (LinearLayout getTypeoutBox) {typeoutBox = getTypeoutBox;
    }//public void setDrawerBox (View getDrawerBox)

    public LinearLayout getTypeoutBox () {return typeoutBox;
    }//public void setDrawerBox (View getDrawerBox)


    public void setDrawerBox (View getDrawerBox) {drawerBox = getDrawerBox;
    }//public void setDrawerBox (View getDrawerBox)

    public View getDrawerBox () {return drawerBox;
    }//public void setDrawerBox (View getDrawerBox)

    public void setFindString (String getFindString) { findString = getFindString;
    } // public void String setFindString (String getFindString)

    public String getFindString () { return findString;
    } // public void String setFindString (String getFindString)

    public void setPackageManager (PackageManager getPkgMgr){ pkgMgr = getPkgMgr;
    }//public void setGridView (GridView getGridView)

    public PackageManager getPackageManager (){ return pkgMgr;
    }//public void setGridView (GridView getGridView)

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
