package com.archbrey.letters;


//import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
//import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.archbrey.letters.Preferences.SettingsActivity;
//import android.widget.TextView;


public class DrawKeypadBox  {

    LinearLayout keypadTopRow;
    LinearLayout keypadMidRow;
    LinearLayout keypadBottomRow;
    LinearLayout keypadNumberRow;

    private static LinearLayout keypadBox;

    private Resources rDrawBox;

    public static KeypadButton[] keypadButton ;
    public static SideButton menuButton;
    private static SideButton delButton;
    Context LaunchpadContext;


    public DrawKeypadBox(LinearLayout mainkeypadBox,Context c, Resources r)
    {

        rDrawBox = r;
        LaunchpadContext = c;

        assignKeys();

        keypadBox = new LinearLayout(LaunchpadContext);
        keypadBox = mainkeypadBox;

        for (int inc=0; inc<=35; inc++) {
            keypadButton[inc].Key = new Button(LaunchpadContext);
        } //for (inc=0; inc<=35; inc++)

       // drawKeypadBox();

        GlobalHolder global;
        global = new GlobalHolder();
        global.setDelButton(delButton);

    } // public DrawKeypadBox(

    public  LinearLayout getLayout(){return keypadBox;
    }//public LinearLayout getLayout()

    public  KeypadButton[] getKeypadButton(){ return keypadButton;
    }//public  KeypadButton[] getKeypadButton

    public  SideButton getmenuButton(){return menuButton;
    }//public  SideButton getmenuButton()

    public  SideButton getdelButton(){return delButton;
    }//SideButton getdelButton()

    public void drawKeypadBox(){

        //keypadKeys = new Button[36];
        int inc;
        for (inc=0; inc<=35; inc++) {
           keypadButton[inc].Key.setText(keypadButton[inc].Letter);
           //keypadButton[inc].Key.setTextColor(rDrawBox.getColor(R.color.text_color));
           keypadButton[inc].Key.setTextColor(SettingsActivity.textColor);
           keypadButton[inc].Key.setBackgroundColor(SettingsActivity.backColor);
        } //for (inc=0; inc<=35; inc++)


        menuButton.Key.setText(menuButton.Ascii);
        delButton.Key.setText(delButton.Ascii);

        int keypad_key_width = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0,
                rDrawBox.getDisplayMetrics());
        int keypad_key_height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SettingsActivity.keyboardHeight,
                rDrawBox.getDisplayMetrics());

        int midrow_padding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 19,
                rDrawBox.getDisplayMetrics());

        LinearLayout.LayoutParams keypadKeysParams = new LinearLayout.LayoutParams (
                keypad_key_width,
                keypad_key_height);
        keypadKeysParams.setMargins(0,0,0,0);
        keypadKeysParams.weight=0.1f;

        LinearLayout.LayoutParams keypadRowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        keypadBox.setOrientation(LinearLayout.VERTICAL);
        //keypadBox.setBackgroundColor(Color.BLUE);
        keypadBox.setBackgroundColor(SettingsActivity.backColor);
        keypadBox.setGravity(Gravity.BOTTOM);

        //==========TOP ROW=========================================================================================
        keypadTopRow = new LinearLayout(LaunchpadContext);
        keypadTopRow.setOrientation(LinearLayout.HORIZONTAL);
        //keypadTopRow.setBackgroundColor(Color.BLUE);
        keypadTopRow.setGravity(Gravity.CENTER);

        for (inc=10; inc<=19; inc++) {keypadTopRow.addView(keypadButton[inc].Key, keypadKeysParams);}
        //==========MID ROW=========================================================================================

        keypadMidRow = new LinearLayout(LaunchpadContext);
        keypadMidRow.setOrientation(LinearLayout.HORIZONTAL);
        //keypadTopRow.setBackgroundColor(Color.BLUE);
        keypadMidRow.setGravity(Gravity.CENTER);
        keypadMidRow.setPadding(midrow_padding, 0, midrow_padding, 0);

        for (inc=20; inc<=28; inc++) {keypadMidRow.addView(keypadButton[inc].Key, keypadKeysParams);}

        //==========BOTTOM ROW=========================================================================================

        keypadBottomRow = new LinearLayout(LaunchpadContext);
        keypadBottomRow.setOrientation(LinearLayout.HORIZONTAL);
        keypadBottomRow.setGravity(Gravity.CENTER);

        keypadBottomRow.addView(menuButton.Key, keypadKeysParams);
        for (inc=29; inc<=35; inc++) {keypadBottomRow.addView(keypadButton[inc].Key, keypadKeysParams);}
        keypadBottomRow.addView(delButton.Key, keypadKeysParams);

        //==========NUMBER ROW=========================================================================================

        keypadNumberRow = new LinearLayout(LaunchpadContext);
        keypadNumberRow.setOrientation(LinearLayout.HORIZONTAL);
        //keypadTopRow.setBackgroundColor(Color.BLUE);
        keypadNumberRow.setGravity(Gravity.CENTER);

        for (inc=1; inc<=9; inc++) {keypadNumberRow.addView(keypadButton[inc].Key, keypadKeysParams);}
        keypadNumberRow.addView(keypadButton[0].Key, keypadKeysParams); //number '0' after 1 to 9

        //==========ASSEMBLE ALL ROWS ================================================================================


        keypadBox.addView(keypadTopRow, keypadRowParams);
        keypadBox.addView(keypadMidRow, keypadRowParams);
        keypadBox.addView(keypadBottomRow, keypadRowParams);
        keypadBox.addView(keypadNumberRow, keypadRowParams);


    } // void drawKeypadBox(void)

    public void removebox(){

        keypadBox.removeView(keypadTopRow);
        keypadBox.removeView(keypadMidRow);
        keypadBox.removeView(keypadBottomRow);
        keypadBox.removeView(keypadNumberRow);

    } // public void removebox()

    private void assignKeys(){

        keypadButton = new KeypadButton[36];
        menuButton = new SideButton();
        delButton = new SideButton();
        menuButton.Key = new Button(LaunchpadContext);
        delButton.Key = new Button(LaunchpadContext);
        //menuButton.Ascii = new String();
        //delButton.Ascii = new String();

        for (int inc=0; inc<=35; inc++) {
            keypadButton[inc] = new KeypadButton();
            keypadButton[inc].ShortcutPackage =" ";
            keypadButton[inc].ShortcutLabel = " ";

           // keypadButton[inc].Letter = new String();
        } //for (inc=0; inc<=35; inc++)

        keypadButton[0].Letter="0"; keypadButton[1].Letter="1";keypadButton[2].Letter="2";keypadButton[3].Letter="3";keypadButton[4].Letter="4";keypadButton[5].Letter="5";
        keypadButton[6].Letter="6";keypadButton[7].Letter="7";keypadButton[8].Letter="8";keypadButton[9].Letter="9";

        keypadButton[10].Letter="Q";keypadButton[11].Letter="W";keypadButton[12].Letter="E";keypadButton[13].Letter="R";keypadButton[14].Letter="T";keypadButton[15].Letter="Y";
        keypadButton[16].Letter="U";keypadButton[17].Letter="I";keypadButton[18].Letter="O";keypadButton[19].Letter="P";

        keypadButton[20].Letter="A";keypadButton[21].Letter="S";keypadButton[22].Letter="D";keypadButton[23].Letter="F";keypadButton[24].Letter="G";keypadButton[25].Letter="H";
        keypadButton[26].Letter="J";keypadButton[27].Letter="K";keypadButton[28].Letter="L";

        keypadButton[29].Letter="Z";keypadButton[30].Letter="X";keypadButton[31].Letter="C";keypadButton[32].Letter="V";keypadButton[33].Letter="B";keypadButton[34].Letter="N";
        keypadButton[35].Letter="M";

        menuButton.Ascii=String.valueOf(Character.toChars(926)); //"Xi symbol"
        //delButton.Ascii=String.valueOf(Character.toChars(8855));
        if (!LaunchpadActivity.isSetAsHome)
            delButton.Key.setText(String.valueOf(Character.toChars(8593))); //"up" button
        else
            delButton.Ascii=String.valueOf(Character.toChars(8595)); //"down" button

    }//void assignKeys()



} //public class DrawKeypadBox
