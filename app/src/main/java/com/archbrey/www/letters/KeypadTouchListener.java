package com.archbrey.www.letters;


import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class KeypadTouchListener  {

    private TextView typeoutView;

    private int keyWidth;
    private int keyHeight;

    private KeypadButton[] keypadButton ;
    private ButtonLocation[] buttonLocation;


    private class ButtonLocation {
        int X;
        int Y;
    }

    //public KeypadTouchListener(Button[] keypadKey, String[] keypadAssign, TextView textView) {
    public KeypadTouchListener(KeypadButton[] keypad, TextView textView) {
        int inc;
        typeoutView = textView;

        keypadButton = new KeypadButton[36];
        buttonLocation = new ButtonLocation[36];

        for (inc=0; inc<=35; inc++) {
            keypadButton[inc] = new KeypadButton();
            buttonLocation[inc] = new ButtonLocation();
            keypadButton[inc].Key = keypad[inc].Key;
            keypadButton[inc].Letter = keypad[inc].Letter;
        } //for (inc=0; inc<=35; inc++)

        setKeypadListener();

    } //public KeypadTouchListener(Button[] keypadKey, String[] keypadAssign, TextView textView)


    public void setKeypadListener() {

        int inc;
        View.OnTouchListener[] KeypadListener;
        KeypadListener = new View.OnTouchListener[36];

        for (inc=0; inc<=35; inc++) {
            keypadButton[inc].Key.setOnTouchListener(
                    KeypadListener[inc] = new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            // Button b = (Button)v;
                            // String TouchedLetter = b.getText().toString();
                            float currentX = event.getRawX();
                            float currentY = event.getRawY();
                            if (buttonLocation[1].Y == 0) //[perform only if not previously initialized
                                {
                                getkeypadLocations();
                                }
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

            keypadButton[inc].Key.setOnLongClickListener(
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


    private void getkeypadLocations(){

        int inc;
        int[] instanceLocation;
        instanceLocation = new int[2];


        for (inc=0; inc<=35; inc++) {

            keyWidth = keypadButton[inc].Key.getWidth();
            keyHeight = keypadButton[inc].Key.getHeight();

            keypadButton[inc].Key.getLocationOnScreen(instanceLocation);
            buttonLocation[inc].X=instanceLocation[0];
            buttonLocation[inc].Y=instanceLocation[1];

            typeoutView.setText("(");
            typeoutView.append(String.valueOf(instanceLocation[0]));
            typeoutView.append(",");
            typeoutView.append(String.valueOf(instanceLocation[1]));
            typeoutView.append(")");

        } //for (inc=10; inc<=19; inc++)


    } //void getkeypadLocations();


    private String determineLetter(float TouchX, float TouchY){


        String LetterFound="none";
        int inc;
        int IntTouchX=(int)TouchX;
        int IntTouchY=(int)TouchY;
        for (inc=0; inc<=35; inc++)

        {

            if( ( IntTouchX >= buttonLocation[inc].X) && (IntTouchX < (buttonLocation[inc].X+keyWidth) ) )
                if( ( IntTouchY >= buttonLocation[inc].Y) && (IntTouchY < (buttonLocation[inc].Y+keyHeight) ) )
                {LetterFound=keypadButton[inc].Letter;}

        } //for (inc=0; inc<=35; inc++)

        return LetterFound;

    } //string determineLetter(int TouchX, int TouchY)



}//public class KeypadTouchListener


