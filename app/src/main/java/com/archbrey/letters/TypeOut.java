package com.archbrey.letters;

import android.content.Context;
import android.content.res.Resources;
//import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TypeOut {

    private static RelativeLayout typeoutBox;

    private static TextView typeoutView;
    private static TextView editView;
    private static TextView findToggleView;

    private static Context mainContext;
    private static Resources rTypeout;

    private GlobalHolder global;


    public static boolean findStatus;

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

        View drawerBox ;
        SideButton delButton;

        int findToggleTextSize = 24;
        global = new GlobalHolder();
        drawerBox = global.getDrawerBox();
        delButton = global.getDelButton();

            findStatus=getFindStatus;

            if (!findStatus){
                findToggleView.setText("find");
                findToggleTextSize = 15;
                drawerBox.setVisibility(View.INVISIBLE);
                typeoutBox.setVisibility(View.INVISIBLE);
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
            findToggleView.setText("find");
            findToggleTextSize = 15;
            drawerBox.setVisibility(View.INVISIBLE);
            typeoutBox.setVisibility(View.INVISIBLE);
            delButton.Key.setText(String.valueOf(Character.toChars(8595)));//"down" button
        } //if (findStatus)
        else {
            findStatus = true;
            global.setFindString(""); //delete currently viewed letter before searching
            typeoutView.setText("");
            findToggleView.setText(String.valueOf(Character.toChars(215)));
            findToggleTextSize = 24;
            delButton.Key.setText(String.valueOf(Character.toChars(8656)));//back button
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

        typeoutTextSize = 24;
        findToggleTextSize = 15;
        typeoutView = new TextView(mainContext);
        typeoutView.setText(" ");
        typeoutView.setGravity(Gravity.CENTER_HORIZONTAL);
        typeoutView.setTextSize(TypedValue.COMPLEX_UNIT_SP, typeoutTextSize);
        typeoutView.setTextColor(LaunchpadActivity.textColor);

        editView = new TextView(mainContext);
        editView.setText(String.valueOf(Character.toChars(177)));
        editView.setGravity(Gravity.CENTER_HORIZONTAL);
        editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, typeoutTextSize);
        editView.setTextColor(LaunchpadActivity.textColor);

        findToggleView = new TextView(mainContext);
        findToggleView.setText("find");
        findToggleView.setGravity(Gravity.CENTER_HORIZONTAL);
        findToggleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, findToggleTextSize);
        findToggleView.setTextColor(LaunchpadActivity.textColor);


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
                editViewParams.setMargins(0, 0, horizontal_margin, 0);

        RelativeLayout.LayoutParams findToggleParams = new RelativeLayout.LayoutParams(
                touchview_width, //width
                RelativeLayout.LayoutParams.WRAP_CONTENT); //height
        findToggleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        findToggleParams.addRule(RelativeLayout.CENTER_VERTICAL);
        findToggleParams.setMargins(horizontal_margin, vertical_margin, 0, vertical_margin);

        typeoutBox = new RelativeLayout(mainContext);
        typeoutBox.setBackgroundColor(LaunchpadActivity.backerColor);

        typeoutBox.addView(editView, editViewParams);
        typeoutBox.addView(findToggleView, findToggleParams);
        typeoutBox.addView(typeoutView, typeoutParams);
        return typeoutBox;

    } //public void DrawBox(LinearLayout getTypeoutBox,Context c,Resources getR)


    public void setListener() {


        findToggleView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) { //perform action of click

                        toggleFindStatus();

                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //delButton.Key.setOnClickListener


    } //public void setListener()

} //public class TypeOut
