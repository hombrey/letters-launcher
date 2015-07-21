package com.archbrey.letters;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;


public class KeypadShortcuts {

    private static AppItem[] allApps;
    private static GlobalHolder global;
    private static Context mainContext;

    public KeypadShortcuts(){

        global = new GlobalHolder();

    } //public KeypadShortcuts()

    public void DrawBox (Context getContext) {

        mainContext = getContext;
        int KeyPosition = KeypadTouchListener.SelectedKeyButton;

        TypeOut.editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        TypeOut.typeoutView.setText(DrawKeypadBox.keypadButton[KeyPosition].Letter);
        if (DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel.length()>1) {
                TypeOut.typeoutView.append(" - ");
                TypeOut.typeoutView.append(DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel);
            }//if (DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel.length()>1)
        else {TypeOut.typeoutView.append(" - Select");}

        TypeOut.editView.setText(String.valueOf(Character.toChars(215))); //x button
        allApps = global.getAllAppItems();
        new DrawDrawerBox (getContext, LaunchpadActivity.appGridView,allApps);

        LaunchpadActivity.appGridView.setOnItemClickListener(new ClickSelectListener());

    } //public GridView DrawBox (GridView getgridBox,Context c,Resources getR)


    private class ClickSelectListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            int KeyPosition = KeypadTouchListener.SelectedKeyButton;

            DrawKeypadBox.keypadButton[KeyPosition].ShortcutPackage = allApps[position].pkgname;
            DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel = allApps[position].label;
            DrawBox(mainContext);
            //TypeOut.typeoutView.append(" - ");
            //TypeOut.typeoutView.append(DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel);

            //view.setBackgroundColor(SettingsActivity.backSelectColor);

        }// public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)



    } //private class ClickSelectListener implements AdapterView.OnItemClickListener



} //public class KeypadShortcuts
