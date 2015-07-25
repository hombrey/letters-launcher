package com.archbrey.letters;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;

//import java.lang.reflect.Type;
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
            listRemoveItems();
        }//if (TypeOut.editMode==17)
        if (TypeOut.editMode==12) {
            TypeOut.typeoutView.append(getR.getString(R.string.tap_add));
            listAddItems();
        }//if (TypeOut.editMode==12)

       if  (DrawFilterBox.filterItems[filterPosition].filteredPkgs.length==0) TypeOut.editView.setVisibility(View.INVISIBLE);
       // TypeOut.editView.setText(" MOD ");

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
        LaunchpadActivity.appGridView.setOnItemClickListener(new ClickAddListener());

    } // private void listAddItems()

    private void listRemoveItems() {

        ArrayList<AppItem> removeList;
        AppItem[] removeArray;
        AppItem instanceAppItem;

        removeList = new ArrayList<AppItem>();
        for (int filterInc=0; filterInc<DrawFilterBox.filterItems[filterPosition].CountofPackages; filterInc++){
            instanceAppItem = DrawFilterBox.filterItems[filterPosition].filteredPkgs[filterInc];
            removeList.add(instanceAppItem);
        } //for (int filterInc=0; filterInc<DrawFilterBox.filterItems[filterPosition].CountofPackages; filterInc++)

        removeArray  = new AppItem[removeList.size()];
        for (int appInc=0; appInc<removeList.size(); appInc++) {
            removeArray[appInc] = removeList.get(appInc);
        } //for (int appInc=0; appInc<removeList.size(); appInc++)

        new DrawDrawerBox (mainContext, LaunchpadActivity.appGridView,removeArray);
        LaunchpadActivity.appGridView.setOnItemClickListener(new ClickRemoveListener());

    } //private void listAddItems()


    private class ClickAddListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            int filterPosition = FilterBoxTouchListener.filterPosition;
            DrawBox(mainContext);
            TypeOut.editView.setVisibility(View.VISIBLE);
            //DrawKeypadBox.keypadButton[KeyPosition].ShortcutPackage = allApps[position].pkgname;
            //DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel = allApps[position].label;
            //DrawBox(mainContext);

            //DBHelper dbHandler = new DBHelper(mainContext);
            //dbHandler.AssignShorcut(KeyPosition, allApps[position].pkgname);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)


    } //private class ClickAddListener implements AdapterView.OnItemClickListener


    private class ClickRemoveListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            int filterPosition = FilterBoxTouchListener.filterPosition;

            //code for debug
            DrawFilterBox.filterItems[filterPosition].filteredPkgs = new AppItem[0];

            if  (DrawFilterBox.filterItems[filterPosition].filteredPkgs.length==0) {
                TypeOut.editMode=12;
                TypeOut.editView.setVisibility(View.INVISIBLE);

                //code for debug
                DrawFilterBox.filterItems[filterPosition].filteredPkgs = new AppItem[1];
                DrawFilterBox.filterItems[filterPosition].CountofPackages = 1;
                DrawFilterBox.filterItems[filterPosition].filteredPkgs[0] = allApps[4];

                DrawBox(mainContext);
            } // if  (DrawFilterBox.filterItems[filterPosition].filteredPkgs.length==0)

            //DrawKeypadBox.keypadButton[KeyPosition].ShortcutPackage = allApps[position].pkgname;
            //DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel = allApps[position].label;
            //DrawBox(mainContext);

            //DBHelper dbHandler = new DBHelper(mainContext);
            //dbHandler.AssignShorcut(KeyPosition, allApps[position].pkgname);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)


    } //private class ClickAddListener implements AdapterView.OnItemClickListener

 }//public class FilterEdit
