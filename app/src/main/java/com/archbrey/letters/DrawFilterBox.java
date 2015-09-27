package com.archbrey.letters;

import android.content.Context;
import android.content.res.Resources;
//import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
//import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.archbrey.letters.Preferences.SettingsActivity;

import java.util.ArrayList;


public class DrawFilterBox {

    private static GlobalHolder global;
    public static LinearLayout filterBox;
    private Resources rDrawBox;
    public static FilterItem[] filterItems;
    Context mainContext;
    int NumOfFilters;

    public DrawFilterBox(LinearLayout mainfilterBox,Context c, Resources r) {

        rDrawBox = r;
        mainContext = c;

        NumOfFilters = 6; //this may be dynamically assigned based on settings

        filterBox = new LinearLayout(mainContext);
        filterBox = mainfilterBox;

        assignFilterCodes();
        //drawFilterBox();

    } //public DrawFilterBox()

    public  LinearLayout getLayout(){return filterBox;
    }//public LinearLayout getLayout()

    public  FilterItem[] getFilterItems(){ return filterItems;
    }//public  KeypadButton[] getKeypadButton

    public void drawFilterBox() {

        int filterTextSize = 16;

        for (int inc=0; inc<NumOfFilters; inc++) {
            filterItems[inc].button = new TextView(mainContext);
            filterItems[inc].button.setGravity(Gravity.CENTER);
            filterItems[inc].button.setText(filterItems[inc].Code);
            filterItems[inc].button.setTextColor(SettingsActivity.textColor);
            filterItems[inc].button.setTextSize(TypedValue.COMPLEX_UNIT_SP, filterTextSize);
            filterItems[inc].button.setBackgroundColor(SettingsActivity.transparent);
        } //for (inc=0; inc<=NumOfFilters; inc++)

        int filter_button_width = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0,
                rDrawBox.getDisplayMetrics());

        int filter_button_height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SettingsActivity.filterHeight,
                rDrawBox.getDisplayMetrics());

        LinearLayout.LayoutParams filterButtonParams = new LinearLayout.LayoutParams (
                filter_button_width,
                ViewGroup.LayoutParams.WRAP_CONTENT);
               // filter_button_height);
        filterButtonParams.setMargins(0,filter_button_height,0,filter_button_height);

        filterButtonParams.gravity = Gravity.CENTER_VERTICAL;
        filterButtonParams.weight=0.1f;

        filterBox.setOrientation(LinearLayout.HORIZONTAL);
        filterBox.setBackgroundColor(SettingsActivity.backerColor);
        filterBox.setGravity(Gravity.BOTTOM);

        //allow user to remove filter bar from view via settings
        if (SettingsActivity.filterEnabled)
            for (int inc=0; inc<NumOfFilters; inc++) {filterBox.addView(filterItems[inc].button, filterButtonParams);}
        else filterBox.removeAllViews();

    } //private void drawFilerBox()

    private void assignFilterCodes(){

        filterItems = new FilterItem[NumOfFilters];

        for (int inc=0; inc<NumOfFilters; inc++) {
            filterItems[inc] = new FilterItem();
        } //for (int inc=0; inc<NumOfFilters; inc++)


        filterItems[0].Code=SettingsActivity.filterCodes[0];filterItems[0].Name="Recents";
        filterItems[1].Code=SettingsActivity.filterCodes[1];filterItems[1].Name="CalcTools";
        filterItems[2].Code=SettingsActivity.filterCodes[2];filterItems[2].Name="Favorites";
        filterItems[3].Code=SettingsActivity.filterCodes[3];filterItems[3].Name="Fun";
        filterItems[4].Code=SettingsActivity.filterCodes[4];filterItems[3].Name="System";
        filterItems[5].Code=SettingsActivity.filterCodes[5];filterItems[3].Name="Webapps";


    } //private void getFilterCodes()


    public void refreshFilterItems(AppItem[] masterAppItems){

        int ArraySize = masterAppItems.length;
        global = new GlobalHolder();

        AppItem[] RecentApps;
        GetAppList getAppListHandle;
        getAppListHandle = new GetAppList();

        RecentApps = new AppItem[10];
        RecentApps = getAppListHandle.getRecentApps();

        ArrayList<String> packagesRetrieved;
        packagesRetrieved = new ArrayList<String>();

        DBHelper dbHelperHandle;
        dbHelperHandle = new DBHelper(mainContext);

       // AppItem[] allAppItems;
       // allAppItems = global.getAllAppItems();

        for (int inc=0; inc<NumOfFilters; inc++) {

            filterItems[inc].filteredPkgs = new AppItem[ArraySize];

            /*if (inc == 0) { //assign recent launchable apps to this special filter

                filterItems[inc].filteredPkgs = new AppItem[ArraySize];
                filterItems[inc].filteredPkgs = RecentApps;
                filterItems[inc].CountofPackages = getAppListHandle.recentAppCount;

            } else //else if (filterItems[inc].Code.equals("Rec"))*/

            {

                packagesRetrieved = dbHelperHandle.RetrievePackagesOfFilter(inc);

                filterItems[inc].filteredPkgs = new AppItem[masterAppItems.length];
                int foundCount = 0;
                for (int pkgInc=0; pkgInc<packagesRetrieved.size(); pkgInc++ ){

                    String packageFound = packagesRetrieved.get(pkgInc);
                    filterItems[inc].filteredPkgs[pkgInc] = new AppItem();
                    

                        filterItems[inc].filteredPkgs[pkgInc].pkgname = " ";
                        filterItems[inc].filteredPkgs[pkgInc].label = " ";
                        for (int appInc=0; appInc<masterAppItems.length; appInc++) {

                            if (packageFound.equals(masterAppItems[appInc].pkgname)  ){
                                filterItems[inc].filteredPkgs[foundCount].label = masterAppItems[appInc].label;
                                filterItems[inc].filteredPkgs[foundCount].pkgname = masterAppItems[appInc].pkgname;
                                foundCount++;
                                break;
                            } //if (DrawKeypadBox.keypadButton[inc].ShortcutPackage.equals(allApps[appInc].pkgname)  )

                        } //for (int appInc=0; appInc<=35; appInc++)

                } //for (int pkgInc=0; pkgInc<packagesRetrieved.size(); pkgInc++ )

                filterItems[inc].CountofPackages = foundCount;

            } //else of else if (filterItems[inc].Code.equals("Rec")

        } //for (int inc=0; inc<NumOfFilters; inc++)


    } //public void refreshFilterItems()


    public void refreshRecentItems(){

      //  int ArraySize = masterAppItems.length;
        global = new GlobalHolder();

        AppItem[] RecentApps;

        GetAppList getAppListHandle;
        getAppListHandle = new GetAppList();

        RecentApps = new AppItem[10];
        RecentApps = getAppListHandle.getRecentApps();


                filterItems[0].filteredPkgs = RecentApps;
                filterItems[0].CountofPackages = getAppListHandle.recentAppCount;

                if (filterItems[0].CountofPackages > 2)
                { new SortApps().exchange_sort(filterItems[0].filteredPkgs,filterItems[0].CountofPackages); }


    } //public void refresRecentItems()



} //public class DrawFilterBox
