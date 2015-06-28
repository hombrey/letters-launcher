package com.archbrey.www.letters;

import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;
import android.util.TypedValue;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.support.v4.view.MotionEventCompat;
//import android.util.Log;
//import android.widget.EditText;

public class LaunchpadActivity extends Activity {

    LinearLayout mainScreen;
    LinearLayout keypadBox;
    LinearLayout keypadTopRow;
    LinearLayout keypadMidRow;
    LinearLayout keypadBottomRow;
    LinearLayout keypadNumberRow;
    LinearLayout typeoutBox;
    Button[] keypadKeys;
    String[] keypadAssign;
    String keypadDelAssign;
    String keypadMenuAssign;
    Button keypadDelKey;
    Button keypadMenuKey;
    TextView typeoutView;

    int keyWidth;
    int keyHeight;
    int[] keypadKeyX;
    int[] keypadKeyY;

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

        LinearLayout.LayoutParams BoxParams = new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                //keypadBoxParams.gravity=Gravity.CENTER_HORIZONTAL;

        drawKeypadBox();
        drawTypeoutBox();
        setKeypadListener();

        mainScreen.addView(typeoutBox, BoxParams);
        mainScreen.addView(keypadBox, BoxParams);
        setContentView(mainScreen);

    }// protected void onCreate(Bundle savedInstanceState)

    @Override
    protected void onResume(){
        super.onResume();

        getkeypadLocations();
    } //protected void onResume()

    @Override
    protected void onStart(){
        super.onStart();

        //getkeypadLocations();
    } //protected void onResume()

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

    String determineLetter(float TouchX, float TouchY){


        String LetterFound="none";
        int inc;
        int IntTouchX=(int)TouchX;
        int IntTouchY=(int)TouchY;
        for (inc=0; inc<=35; inc++)

        {

            if( ( IntTouchX >= keypadKeyX[inc]) && (IntTouchX < (keypadKeyX[inc]+keyWidth) ) )
                if( ( IntTouchY >= keypadKeyY[inc]) && (IntTouchY < (keypadKeyY[inc]+keyHeight) ) )
                {LetterFound=keypadAssign[inc];}

        } //for (inc=0; inc<=35; inc++)

        return LetterFound;

    } //string determineLetter(int TouchX, int TouchY)

    void setKeypadListener() {

        int inc;
        OnTouchListener[] KeypadListener;
        KeypadListener = new OnTouchListener[36];

        for (inc=0; inc<=35; inc++) {
            keypadKeys[inc].setOnTouchListener(
                    KeypadListener[inc]= new OnTouchListener(){
                        //String TouchedLetter =keypadAssign[inc]; //does not work
                        public boolean onTouch(View v, MotionEvent event) {
                           // Button b = (Button)v;
                           // String TouchedLetter = b.getText().toString();
                            float currentX=event.getRawX();
                            float currentY=event.getRawY();
                            if (keypadKeyY[1] == 0) //[perform only if not previously initialized
                                {getkeypadLocations();}
                            String TouchedLetter = determineLetter(currentX, currentY);


                            int action = MotionEventCompat.getActionMasked(event);
                            switch (action) {
                                case (MotionEvent.ACTION_DOWN):
                                    typeoutView.setText(TouchedLetter);
                                    typeoutView.append(" DOWN");
                                    return false;
                                case (MotionEvent.ACTION_MOVE):

                                    TouchedLetter = determineLetter(currentX, currentY);
                                    typeoutView.setText(TouchedLetter);
                                  //  typeoutView.append(" ");
                                  //  typeoutView.append(TouchedLetter);
                                    typeoutView.append(" MOVE");

                                    return false;
                                case (MotionEvent.ACTION_UP):
                                    typeoutView.setText(TouchedLetter);
                                    typeoutView.append(" UP");
                                    return false;
                                default:
                                    return true;
                            }//switch(action)
                        } //public boolean onTouch(View v, MotionEvent event)
                    } //new OnTouchListener()
            );//keypadKeys[inc].setOnTouchListener

            keypadKeys[inc].setOnLongClickListener(
                    new Button.OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            Button b = (Button) v;
                            String longTouchedLetter = b.getText().toString();
                            typeoutView.setText(longTouchedLetter);
                            typeoutView.append("loooong press");
                            return false;
                        } //public void OnClick(View v)
                    } //new Button.OnClickListener()

            ); //myButtonTrigger.setOnLongClickListener


        } //for (inc=0; inc<=35; inc++)

    } //void setKeypadListener()


    void drawTypeoutBox(){

        typeoutView = new TextView(this);
        typeoutView.setText("Hello there");
        typeoutView.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams typeoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
            //typeoutParams.gravity=Gravity.CENTER;

        typeoutBox = new LinearLayout(this);
        typeoutBox.setOrientation(LinearLayout.HORIZONTAL);
        //keypadTopRow.setBackgroundColor(Color.BLUE);
        //typeoutBox.setGravity(Gravity.CENTER_HORIZONTAL);

        typeoutBox.addView(typeoutView, typeoutParams);

    } //drawTypeoutBox;


    void getkeypadLocations(){

        int inc;
        int[] instanceLocation;
        instanceLocation = new int[2];

        keypadKeyX = new int[36];
        keypadKeyY = new int[36];

        for (inc=0; inc<=35; inc++) {

            keyWidth = keypadKeys[inc].getWidth();
            keyHeight = keypadKeys[inc].getHeight();

            keypadKeys[inc].getLocationOnScreen(instanceLocation);
            keypadKeyX[inc]=instanceLocation[0];
            keypadKeyY[inc]=instanceLocation[1];

            typeoutView.setText("(");
            typeoutView.append(String.valueOf(instanceLocation[0]));
            typeoutView.append(",");
            typeoutView.append(String.valueOf(instanceLocation[1]));
            typeoutView.append(")");

        } //for (inc=10; inc<=19; inc++)


    } //void getkeypadLocations();


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
        keypadKeysParams.weight=0.1f;

        LinearLayout.LayoutParams keypadRowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


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
