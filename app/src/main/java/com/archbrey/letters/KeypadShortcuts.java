package com.archbrey.letters;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;


public class KeypadShortcuts {

    private static AppItem[] allApps;
    private static GlobalHolder global;
    private static Context mainContext;
    private static Resources rMain;

    public KeypadShortcuts(){

        global = new GlobalHolder();
        rMain = global.getResources();

    } //public KeypadShortcuts()

    public void DrawBox (Context getContext) {

        mainContext = getContext;
        int KeyPosition = KeypadTouchListener.SelectedKeyButton;

        TypeOut.editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        TypeOut.typeoutView.setText(DrawKeypadBox.keypadButton[KeyPosition].Letter);
        if (DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel.length()>1) {
                TypeOut.typeoutView.append(" - ");
                TypeOut.typeoutView.append(DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel);
                TypeOut.editView.setText("  "); //make x button larger
                TypeOut.editView.append(String.valueOf(Character.toChars(215))); //x button
                TypeOut.editView.append(" "); //make x button larger
            }//if (DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel.length()>1)
        else {
            TypeOut.typeoutView.setText(" - " + rMain.getString(R.string.unassigned));
            TypeOut.editView.setText(" "); //x button not needed if unassigned
        } //else of if (DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel.length()>1)

        allApps = global.getAllAppItems();
       // new DrawDrawerBox (getContext, LaunchpadActivity.appGridView,allApps);
        LaunchpadActivity.drawDrawerBox.DrawBox(allApps);

        LaunchpadActivity.appGridView.setOnItemClickListener(new ClickSelectListener());

    } //public GridView DrawBox (GridView getgridBox,Context c,Resources getR)


    private class ClickSelectListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            int KeyPosition = KeypadTouchListener.SelectedKeyButton;

            DrawKeypadBox.keypadButton[KeyPosition].ShortcutPackage = allApps[position].pkgname;
            DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel = allApps[position].label;
            DrawBox(mainContext);

            DBHelper dbHandler = new DBHelper(mainContext);
            dbHandler.AssignShorcut(KeyPosition, allApps[position].pkgname);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)


    } //private class ClickSelectListener implements AdapterView.OnItemClickListener


    public void RetrieveSavedShortcuts (Context getContext) {

        allApps = global.getAllAppItems();
        mainContext = getContext;
        DBHelper dbHandler = new DBHelper(mainContext);

     for (int inc=0; inc<=35; inc++) {

         String shortcutFound = dbHandler.RetrievePackage(inc);

         //assign " " by default and fill this up if shortcut exists
         DrawKeypadBox.keypadButton[inc].ShortcutPackage = " ";
         DrawKeypadBox.keypadButton[inc].ShortcutLabel = " ";
            for (int appInc=0; appInc<allApps.length; appInc++) {

                if (shortcutFound.equals(allApps[appInc].pkgname)  ){
                    DrawKeypadBox.keypadButton[inc].ShortcutLabel = allApps[appInc].label;
                    DrawKeypadBox.keypadButton[inc].ShortcutPackage = allApps[appInc].pkgname;
                    break;
                    } //if (DrawKeypadBox.keypadButton[inc].ShortcutPackage.equals(allApps[appInc].pkgname)  )
              /*  else //assign " " to keypad shortcut if package does not exist anymore
                    {
                       DrawKeypadBox.keypadButton[inc].ShortcutPackage = " ";
                       DrawKeypadBox.keypadButton[inc].ShortcutLabel = " ";
                     }*/

            } //for (int appInc=0; appInc<=35; appInc++)

        } //for (inc=0; inc<=35; inc++)


    } // public class RetrieveSavedShortcuts ()

} //public class KeypadShortcuts
