package com.archbrey.letters;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.View;
import android.widget.GridView;
//import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GlobalHolder {

    private static AppItem[] installedAppItems;
    private static Context mainContext;
    private static GridView appGridView;
    private static PackageManager pkgMgr;
    private static String findString;
    private static View drawerBox;
    private static RelativeLayout typeoutBox;
   // private static LinearLayout fillerBox;
    //private static SideButton menuButton;
    private static SideButton delButton;
    private static Resources rMain;


    public void setResources (Resources getR) {rMain = getR;
    }//public void setResources

    public Resources getResources () {return rMain;
    }//public void setDrawerBox (View getDrawerBox)


    public void setDelButton (SideButton getDelButton) {delButton = getDelButton;
    }//public void setDrawerBox (View getDrawerBox)

    public SideButton getDelButton () {return delButton;
    }//public void setDrawerBox (View getDrawerBox)

    public void setTypeoutBox (RelativeLayout getTypeoutBox) {typeoutBox = getTypeoutBox;
    }//public void setDrawerBox (View getDrawerBox)

    public RelativeLayout getTypeoutBox () {return typeoutBox;
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



    public void setAllAppItems(AppItem[] appItems) {

        int ArraySize = appItems.length;

        installedAppItems = new AppItem[ArraySize];

        for (int inc = 0; inc<ArraySize; inc++){
            installedAppItems[inc] = new AppItem();
            installedAppItems[inc] = appItems[inc];
        } //for (int inc = 0; inc<ArraySize; inc++)

    } // public void setAppItem(AppItem[] appItems)


    public int getAppItemSize() { return installedAppItems.length;
    } //public AppItem[] getAppItem()

    public AppItem[] getAllAppItems() { return installedAppItems;
    } //public AppItem[] getAppItem()



} //public class GlobalHolder
