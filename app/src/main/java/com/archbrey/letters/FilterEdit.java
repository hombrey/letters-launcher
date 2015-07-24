package com.archbrey.letters;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class FilterEdit {


    private static AppItem[] allApps;
    private static GlobalHolder global;
    private static Context mainContext;
    private static Resources getR;
    private static int filterPosition;

    public FilterEdit(){

        global = new GlobalHolder();
        getR = global.getResources();

    } //public FilterEdit()


    public void DrawBox (Context getContext) {

        mainContext = getContext;
        filterPosition = FilterBoxTouchListener.filterPosition;


        TypeOut.editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        TypeOut.typeoutView.setText(DrawFilterBox.filterItems[filterPosition].Code);
        TypeOut.typeoutView.append(" - ");
        if (TypeOut.editMode==17) {
            TypeOut.typeoutView.append(getR.getString(R.string.tap_remove));
            //listRemoveItems();
        }//if (TypeOut.editMode==17)
        if (TypeOut.editMode==12) {
            TypeOut.typeoutView.append(getR.getString(R.string.tap_add));
            listAddItems();
        }//if (TypeOut.editMode==12)

        TypeOut.editView.setText(" MOD ");

    } // public void DrawBox (Context getContext)


    private void listAddItems() {

        allApps = global.getAllAppItems();

        ArrayList<AppItem> addList;
        AppItem[] addArray;
        AppItem instanceAppItem;

        boolean foundOnFilter;

        addList = new ArrayList<AppItem>();
        for (int appInc=0; appInc<allApps.length; appInc++) {

                foundOnFilter = false;
                for (int filterInc=0; filterInc<DrawFilterBox.filterItems[filterPosition].CountofPackages; filterInc++){

                    instanceAppItem = DrawFilterBox.filterItems[filterPosition].filteredPkgs[filterInc];
                    if (allApps[appInc].pkgname.equals(instanceAppItem.pkgname) ) {
                        foundOnFilter = true;
                    } //if (allApps[appInc].equals() )

                } //for (int filterInc=0; filterInc<DrawFilterBox.filterItems[filterPosition].CountofPackages; filterInc++)

            if(!foundOnFilter) {
                //add to addList
                addList.add(allApps[appInc]);
            }
        } //for (int appInc=0; appInc<=35; appInc++)

        addArray  = new AppItem[addList.size()];
        for (int appInc=0; appInc<addList.size(); appInc++) {
            addArray[appInc] = addList.get(appInc);
        }

        new DrawDrawerBox (mainContext, LaunchpadActivity.appGridView,addArray);

    } // private void listAddItems()



 }//public class FilterEdit
