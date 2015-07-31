package com.archbrey.letters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.archbrey.letters.Preferences.SettingsActivity;


public class TypeOut {

    public static RelativeLayout typeoutBox;

    public static TextView typeoutView;
    public static TextView editView;
    public static TextView findToggleView;

    private static Context mainContext;
    private static Resources rTypeout;

    private GlobalHolder global;


    public static int TextSize = 24;
    public static boolean findStatus;
    public static int editMode;

    private static KeypadShortcuts keypadShortcustHandle;
    private static FilterEdit filterEditHandle;

    public TypeOut() {

        findStatus = false;


    } //public TypeOut(

    public RelativeLayout getLayout() { return typeoutBox;
    } // public LinearLayout getLayout()

    public TextView getTypeoutView() { return typeoutView;
    } //public LinearLayout getLayout()

    public boolean getFindStatus() { return findStatus;
    } //public LinearLayout getLayout()

    public void setFindStatus (boolean getFindStatus) {

       // View drawerBox ;
        SideButton delButton;

        int findToggleTextSize = 24;
        global = new GlobalHolder();
       // drawerBox = global.getDrawerBox();
        delButton = global.getDelButton();

            findStatus=getFindStatus;

            if (!findStatus){
                findToggleView.setText(" find  ");
                findToggleTextSize = 15;
              //  drawerBox.setVisibility(View.INVISIBLE);
              //  typeoutBox.setVisibility(View.INVISIBLE);
                if (!LaunchpadActivity.isSetAsHome)
                    delButton.Key.setText(String.valueOf(Character.toChars(8593))); //"up" button
                else
                    delButton.Key.setText(String.valueOf(Character.toChars(8595)));//"down" button
            } // if (!findStatus)

        findToggleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, findToggleTextSize);

    } //public setFindStatus(boolean getFindStatus)

    public void toggleFindStatus() {

        View drawerBox ;
        SideButton delButton;

        int findToggleTextSize;
        global = new GlobalHolder();
        drawerBox = global.getDrawerBox();
        delButton = global.getDelButton();

        if (findStatus) {
            findStatus = false;
            findToggleView.setText(" find  ");
            findToggleTextSize = 15;
            drawerBox.setVisibility(View.INVISIBLE);
            typeoutBox.setVisibility(View.INVISIBLE);

            if (!LaunchpadActivity.isSetAsHome)
                delButton.Key.setText(String.valueOf(Character.toChars(8593))); //"up" button
            else
                delButton.Key.setText(String.valueOf(Character.toChars(8595)));//"down" button

        } //if (findStatus)
        else {
            findStatus = true;
            global.setFindString(""); //delete currently viewed letter before searching
            typeoutView.setText("");
            findToggleView.setText(" "); //x button
            findToggleView.append(String.valueOf(Character.toChars(215))); //x button
            findToggleView.append("  "); //x button
            findToggleTextSize = 24;
            delButton.Key.setText(String.valueOf(Character.toChars(8656)));//back button
            editView.setVisibility(View.GONE);
        } //else of if (findStatus==true)

        findToggleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, findToggleTextSize);

    } // public toggleFindStatus()


   public RelativeLayout DrawBox(RelativeLayout getTypeoutBox,Context c,Resources getR){

        typeoutBox = getTypeoutBox;
        mainContext = c;
        rTypeout = getR;

        int typeoutTextSize;
        int findToggleTextSize;

        int horizontal_margin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5,
                getR.getDisplayMetrics());

        int vertical_margin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 15,
                getR.getDisplayMetrics());

       int touchview_width = (int) TypedValue.applyDimension(
               TypedValue.COMPLEX_UNIT_DIP, 50,
               getR.getDisplayMetrics());


        findToggleTextSize = 15;
        typeoutView = new TextView(mainContext);
        typeoutView.setText(" ");
        typeoutView.setGravity(Gravity.CENTER_HORIZONTAL);
        typeoutView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TextSize);
        typeoutView.setTextColor(SettingsActivity.textColor);

        editView = new TextView(mainContext);
        editView.setText("  "); //spacer to make the tap target larger
        editView.append(String.valueOf(Character.toChars(177))); //plus minus button
        editView.append("  "); //x button
        editView.setGravity(Gravity.CENTER_HORIZONTAL);
        editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TextSize);
        editView.setTextColor(SettingsActivity.textColor);

        findToggleView = new TextView(mainContext);
        findToggleView.setText(" find  ");
        findToggleView.setGravity(Gravity.CENTER_HORIZONTAL);
        findToggleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, findToggleTextSize);
        findToggleView.setTextColor(SettingsActivity.textColor);


        RelativeLayout.LayoutParams typeoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, //width
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height

                typeoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                typeoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                typeoutParams.setMargins(0, vertical_margin, 0, vertical_margin);

       RelativeLayout.LayoutParams editViewParams = new RelativeLayout.LayoutParams(
                touchview_width, //width
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height

                editViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                editViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
             //   editViewParams.setMargins(0, 0, horizontal_margin, 0);

        RelativeLayout.LayoutParams findToggleParams = new RelativeLayout.LayoutParams(
                touchview_width, //width
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height
        findToggleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        findToggleParams.addRule(RelativeLayout.CENTER_VERTICAL);
      //  findToggleParams.setMargins(horizontal_margin, vertical_margin, 0, vertical_margin);

        typeoutBox = new RelativeLayout(mainContext);
        typeoutBox.setBackgroundColor(SettingsActivity.backerColor);

        typeoutBox.addView(editView, editViewParams);
        typeoutBox.addView(findToggleView, findToggleParams);
        typeoutBox.addView(typeoutView, typeoutParams);

       editView.setVisibility(View.GONE);
        return typeoutBox;

    } //public void DrawBox(LinearLayout getTypeoutBox,Context c,Resources getR)


    public void setListener() {



        findToggleView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) { //perform action of click

                        toggleFindStatus();

                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //findToggleView.Key.setOnClickListener


        editView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) { //perform action of click
                       //  SetAppList setApps;
                       //  setApps = new SetAppList();
                        //toggleFindStatus();
                        SelectMode();
                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //findToggleView.Key.setOnClickListener



        typeoutView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) { //perform action of click

                        PackageManager pmForListener;
                        pmForListener = global.getPackageManager();

                        if ((DrawKeypadBox.keypadButton[KeypadTouchListener.SelectedKeyButton].ShortcutPackage.length() > 1)  &&
                                editMode==1) { //check if keypad has assigned shortcut

                            Intent launchIntent = pmForListener.getLaunchIntentForPackage(DrawKeypadBox.keypadButton[KeypadTouchListener.SelectedKeyButton].ShortcutPackage);

                            global.getMainContext().startActivity(launchIntent);

                          //  AppItem launched;
                         //   launched = new AppItem();
                         //   launched.pkgname = DrawKeypadBox.keypadButton[KeypadTouchListener.SelectedKeyButton].ShortcutPackage;
                         //   launched.label = DrawKeypadBox.keypadButton[KeypadTouchListener.SelectedKeyButton].ShortcutLabel;
                         //   launched.name = "blank";
                         //   new GetAppList().addRecentApp(launched);

                        } //if(keypadButton[SelectedKeyButton].ShortcutPackage.length()>1)
                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //findToggleView.Key.setOnClickListener


    } //public void setListener()


    private void SelectMode() {

      //  AppItem allApps[];

        if (TypeOut.editMode<10) {

            LaunchpadActivity.drawerBox.setVisibility(View.VISIBLE);
            LaunchpadActivity.keypadBox.setVisibility(View.GONE);
            LaunchpadActivity.filterBox.setVisibility(View.INVISIBLE);
            TypeOut.findToggleView.setVisibility(View.GONE);

            if (TypeOut.editMode == 1) {
               // ShortcutSelect();
                editMode = 11;
                keypadShortcustHandle = new KeypadShortcuts();
                keypadShortcustHandle.DrawBox(mainContext);

            }//if (TypeOut.editMode==1)

            else if (TypeOut.editMode == 2) {
               // FilterItemsSelect();
                editMode = 12;
                filterEditHandle = new FilterEdit();
                filterEditHandle.DrawBox(mainContext);

            }//if (TypeOut.editMode==2)

        } //if (TypeOut.editMode<10)

        else {

            if (TypeOut.editMode == 11) {
                // ShortcutSelect();
                int KeyPosition = KeypadTouchListener.SelectedKeyButton;

                DrawKeypadBox.keypadButton[KeyPosition].ShortcutPackage = " ";
                DrawKeypadBox.keypadButton[KeyPosition].ShortcutLabel = " ";
                //need to delete database
                DBHelper dbHandler = new DBHelper(mainContext);
                dbHandler.DeleteShorcut(KeyPosition);

                keypadShortcustHandle.DrawBox(mainContext);
            }//if (TypeOut.editMode==11)

            if (TypeOut.editMode == 12) {
                editMode = 17;
                filterEditHandle = new FilterEdit();
                filterEditHandle.DrawBox(mainContext);
            }//if (TypeOut.editMode==12)
            else if (TypeOut.editMode == 17) {
                editMode = 12;
                filterEditHandle = new FilterEdit();
                filterEditHandle.DrawBox(mainContext);
            }//if (TypeOut.editMode==12)


        } //else of /if (TypeOut.editMode<10)

    } // public void SelectMode()





} //public class TypeOut
