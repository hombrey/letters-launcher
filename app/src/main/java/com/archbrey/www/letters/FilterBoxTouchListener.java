package com.archbrey.www.letters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FilterBoxTouchListener {

    private TextView typeoutView;

    private int keyWidth;
    private int keyHeight;

    private int numofFilters;
    private FilterItem[] filterItems ;
    private ButtonLocation[] buttonLocation;
    private GlobalHolder global;
    private SettingsHolder savedSettings;
    private int filterPosition;

    private class ButtonLocation {
        int X;
        int Y;
    } //private class ButtonLocation


    public FilterBoxTouchListener (FilterItem[] getFilterItems, TextView getTextView) {

        typeoutView = getTextView;
        savedSettings = new SettingsHolder();

        savedSettings.setNumOfFilters(6);

        numofFilters =savedSettings.getNumofFilters();
        filterItems = new FilterItem[numofFilters];
        buttonLocation = new ButtonLocation[numofFilters];

        for (int inc=0; inc<numofFilters; inc++) {
            filterItems[inc] = new FilterItem();
            buttonLocation[inc] = new ButtonLocation();
            filterItems[inc] = getFilterItems[inc];
        } //for (int inc=0; inc<numofFilters; inc++ )

        setFilterBoxListener();

    } // public FilterBoxTouchListener (FilterItem[] getFilterItems, TextView getTextView)


    public void setFilterBoxListener() {

        View.OnTouchListener[] FilterItemListener;
        FilterItemListener = new View.OnTouchListener[numofFilters];

        for (int inc=0; inc<numofFilters; inc++) {

            filterItems[inc].button.setOnTouchListener(
                    FilterItemListener[inc] = new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {

                            float currentX = event.getRawX();
                            float currentY = event.getRawY();
                            String findString;
                            AppItem[] appItems;
                            PackageManager PkgMgr;

                            global = new GlobalHolder();
                            global.setFindString(""); //set the search string back to null whenever a filter box item is touched
                            appItems = new AppItem[global.getAppItemSize()];
                            appItems = global.getAppItem();

                            PkgMgr = global.getPackageManager();
                            String TouchedFilter = determineFilter(currentX, currentY);
                            findString = global.getFindString();

                            appItems = evaluateAction(filterPosition);

                            if (buttonLocation[1].Y == 0) //[perform only if not previously initialized
                            {
                                getFilterItemLocations();
                            }

                            int action = MotionEventCompat.getActionMasked(event);
                            switch (action) {
                                case (MotionEvent.ACTION_DOWN):
                                    typeoutView.setText(TouchedFilter);
                                    typeoutView.append(" ");
                                    typeoutView.append(String.valueOf(filterPosition));

                                    return false;
                                case (MotionEvent.ACTION_MOVE):
                                    typeoutView.setText(TouchedFilter);
                                    typeoutView.append(" ");
                                    typeoutView.append(String.valueOf(filterPosition));

                                    return false;
                                case (MotionEvent.ACTION_UP):
                                    typeoutView.setText(TouchedFilter);
                                    callAppListeners(PkgMgr, appItems);
                                    return false;
                                default:
                                    return true;
                            }//switch(action)
                        } //public boolean onTouch(View v, MotionEvent event)
                    } //new OnTouchListener()
            );//filterItems[inc].button.setOnTouchListener


        } //or (inc=0; inc<numofFilters; inc++)

    } //public void setFilterBoxListener()

    private void getFilterItemLocations() {

        int inc;
        int[] instanceLocation;
        instanceLocation = new int[2];

        for (inc=0; inc<numofFilters; inc++) {

            keyWidth = filterItems[inc].button.getWidth();
            keyHeight = filterItems[inc].button.getHeight();

            filterItems[inc].button.getLocationOnScreen(instanceLocation);
            buttonLocation[inc].X=instanceLocation[0];
            buttonLocation[inc].Y=instanceLocation[1];

        } //for (inc=0; inc<numofFilters; inc++)

    } //void getkeypadLocations();



    private String determineFilter(float TouchX, float TouchY){

        String FilterFound=" ";
        int inc;
        int IntTouchX=(int)TouchX;
        int IntTouchY=(int)TouchY;

        for (inc=0; inc<numofFilters; inc++)
        {

            if( ( IntTouchX >= buttonLocation[inc].X) && (IntTouchX < (buttonLocation[inc].X+keyWidth) ) )
                if( ( IntTouchY >= buttonLocation[inc].Y) && (IntTouchY < (buttonLocation[inc].Y+keyHeight) ) )
                {FilterFound=filterItems[inc].Code;
                 filterPosition = inc;}
        } //for (inc=0; inc<=35; inc++)

        return FilterFound;

    } //string determineLetter(int TouchX, int TouchY)

    private AppItem[] evaluateAction(int filterPosition) {

        AppItem[] resultItems;
        View drawerBox;
        RelativeLayout typeoutBox;

        drawerBox = global.getDrawerBox();
        typeoutBox = global.getTypeoutBox();
        drawerBox.setVisibility(View.VISIBLE);
        typeoutBox.setVisibility(View.VISIBLE);

        int ListLength =  filterItems[filterPosition].CountofPackages;
        resultItems = new AppItem[ListLength];

        for (int inc = 0; inc<ListLength; inc++){
            resultItems[inc] = new AppItem();
            resultItems[inc] = filterItems[filterPosition].filteredPkgs[inc];
        } //for (int inc = 0; inc<ListLength; inc++)

        new DrawDrawerBox(global.getMainContext(), global.getGridView(), resultItems);

        return resultItems;

    } //private AppItem[] evaluateAction(PackageManager PkgMgr, AppItem[] appItems, String searchString)

    private void callAppListeners(PackageManager pm, AppItem[] appItems) {

        Context clickListenerContext;
        GlobalHolder global;
        GridView drawerGrid;

        global = new GlobalHolder();
        clickListenerContext = global.getMainContext();
        drawerGrid = global.getGridView();

        AppDrawerAdapter drawerAdapterObject;

        drawerAdapterObject = new AppDrawerAdapter(clickListenerContext, appItems);
        drawerGrid.setAdapter(drawerAdapterObject);
        drawerGrid.setOnItemClickListener(new DrawerClickListener(clickListenerContext, appItems, pm));
        drawerGrid.setOnItemLongClickListener(new DrawerLongClickListener(clickListenerContext, appItems, pm));

    } // public void callListeners(PackageManager pm, AppItem[] appItems)

} //public class FilterBoxTouchListener
