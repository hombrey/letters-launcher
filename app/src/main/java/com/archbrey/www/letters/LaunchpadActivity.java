package com.archbrey.www.letters;

import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
//import android.view.View;
import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
import android.widget.Button;
//import android.widget.TextView;
import android.graphics.Color;
import android.util.TypedValue;
import android.content.res.Resources;
import android.view.Gravity;
//import android.widget.EditText;

public class LaunchpadActivity extends Activity {

    LinearLayout mainScreen;
    LinearLayout keypadBox;
    LinearLayout keypadTopRow;
    LinearLayout keypadMidRow;
    LinearLayout keypadBottomRow;
    LinearLayout keypadNumberRow;
    //LinearLayout typeoutBox;
    Button[] keypadKeys;
    String[] keypadAssign;
    String keypadDelAssign;
    String keypadMenuAssign;
    Button keypadDelKey;
    Button keypadMenuKey;
    //TextView typeoutText;
    //TextView typeoutText2;
    Resources r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launchpad);

        r = getResources();
    /*
        LinearLayout.LayoutParams mainScreenLayout =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);*/

       mainScreen = new LinearLayout(this);
       mainScreen.setOrientation(LinearLayout.VERTICAL);
       mainScreen.setGravity(Gravity.BOTTOM);

        assignKeys();

        LinearLayout.LayoutParams keypadBoxParams = new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                //keypadBoxParams.gravity=Gravity.CENTER_HORIZONTAL;

        drawKeypadBox();

        mainScreen.addView(keypadBox, keypadBoxParams);
        setContentView(mainScreen);

    }// protected void onCreate(Bundle savedInstanceState)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launchpad, menu);
        return true;
    }//public boolean onCreateOptionsMenu(Menu menu)

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } //public boolean onOptionsItemSelected(MenuItem item)


    void drawKeypadBox(){


        keypadKeys = new Button[36];
        int inc;
        for (inc=0; inc<=35; inc++) {
            keypadKeys[inc] = new Button(this);
            keypadKeys[inc].setText(keypadAssign[inc]);
            keypadKeys[inc].setTextColor(Color.WHITE);
        } //for (inc=0; inc<=35; inc++)

        keypadMenuKey = new Button(this);
        keypadMenuKey.setText(keypadMenuAssign);

        keypadDelKey = new Button(this);
        keypadDelKey.setText(keypadDelAssign);


        int keypad_key_height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 38,
                r.getDisplayMetrics());
        int keypad_key_width = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0,
                r.getDisplayMetrics());

        int midrow_padding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 19,
                r.getDisplayMetrics());

        LinearLayout.LayoutParams keypadKeysParams = new LinearLayout.LayoutParams (
                keypad_key_width,
                keypad_key_height);
        keypadKeysParams.setMargins(0,0,0,0);

        LinearLayout.LayoutParams keypadRowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        keypadKeysParams.weight=0.1f;

        keypadBox = new LinearLayout(this);
        keypadBox.setOrientation(LinearLayout.VERTICAL);
        keypadBox.setBackgroundColor(Color.BLUE);
        keypadBox.setGravity(Gravity.BOTTOM);

        //==========TOP ROW=========================================================================================
        keypadTopRow = new LinearLayout(this);
        keypadTopRow.setOrientation(LinearLayout.HORIZONTAL);
        //keypadTopRow.setBackgroundColor(Color.BLUE);
        keypadTopRow.setGravity(Gravity.CENTER);

        for (inc=10; inc<=19; inc++) {keypadTopRow.addView(keypadKeys[inc], keypadKeysParams);}
        //==========MID ROW=========================================================================================

        keypadMidRow = new LinearLayout(this);
        keypadMidRow.setOrientation(LinearLayout.HORIZONTAL);
        //keypadTopRow.setBackgroundColor(Color.BLUE);
        keypadMidRow.setGravity(Gravity.CENTER);
        keypadMidRow.setPadding(midrow_padding, 0, midrow_padding, 0);

        for (inc=20; inc<=28; inc++) {keypadMidRow.addView(keypadKeys[inc], keypadKeysParams);}

        //==========BOTTOM ROW=========================================================================================

        keypadBottomRow = new LinearLayout(this);
        keypadBottomRow.setOrientation(LinearLayout.HORIZONTAL);
        //keypadTopRow.setBackgroundColor(Color.BLUE);
        keypadBottomRow.setGravity(Gravity.CENTER);

        keypadBottomRow.addView(keypadMenuKey, keypadKeysParams);
        for (inc=29; inc<=35; inc++) {keypadBottomRow.addView(keypadKeys[inc], keypadKeysParams);}
        keypadBottomRow.addView(keypadDelKey, keypadKeysParams);

        //==========NUMBER ROW=========================================================================================

        keypadNumberRow = new LinearLayout(this);
        keypadNumberRow.setOrientation(LinearLayout.HORIZONTAL);
        //keypadTopRow.setBackgroundColor(Color.BLUE);
        keypadNumberRow.setGravity(Gravity.CENTER);

        for (inc=1; inc<=9; inc++) {keypadNumberRow.addView(keypadKeys[inc], keypadKeysParams);}
        keypadNumberRow.addView(keypadKeys[0], keypadKeysParams); //number '0' after 1 to 9

        //==========ASSEMBLE ALL ROWS ================================================================================

        keypadBox.addView(keypadTopRow, keypadRowParams);
        keypadBox.addView(keypadMidRow, keypadRowParams);
        keypadBox.addView(keypadBottomRow, keypadRowParams);
        keypadBox.addView(keypadNumberRow, keypadRowParams);

    } // void drawKeypadBox(void)

    void assignKeys(){

        keypadAssign = new String[36];

        keypadAssign[0]="0"; keypadAssign[1]="1";keypadAssign[2]="2";keypadAssign[3]="3";keypadAssign[4]="4";keypadAssign[5]="5";
        keypadAssign[6]="6";keypadAssign[7]="7";keypadAssign[8]="8";keypadAssign[9]="9";

        keypadAssign[10]="Q";keypadAssign[11]="W";keypadAssign[12]="E";keypadAssign[13]="R";keypadAssign[14]="T";keypadAssign[15]="Y";
        keypadAssign[16]="U";keypadAssign[17]="I";keypadAssign[18]="O";keypadAssign[19]="P";

        keypadAssign[20]="A";keypadAssign[21]="S";keypadAssign[22]="D";keypadAssign[23]="F";keypadAssign[24]="G";keypadAssign[25]="H";
        keypadAssign[26]="J";keypadAssign[27]="K";keypadAssign[28]="L";

        keypadAssign[29]="Z";keypadAssign[30]="X";keypadAssign[31]="C";keypadAssign[32]="V";keypadAssign[33]="B";keypadAssign[34]="N";
        keypadAssign[35]="M";

        keypadMenuAssign=String.valueOf(Character.toChars(8801));
        keypadDelAssign=String.valueOf(Character.toChars(8855));

    }//void assignKeys()

} //public class LaunchpadActivity
