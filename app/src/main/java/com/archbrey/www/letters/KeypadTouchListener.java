package com.archbrey.www.letters;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class KeypadTouchListener  {

    private TextView typeoutView;

    private int keyWidth;
    private int keyHeight;

    private KeypadButton[] keypadButton ;
    private ButtonLocation[] buttonLocation;
    //private GetAppList getAppList;
    private GlobalHolder global;
    private SideButton delButton;

    private class ButtonLocation {
        int X;
        int Y;
    }

    public KeypadTouchListener(KeypadButton[] keypad, SideButton getdelButton,TextView textView) {

        int inc;
        typeoutView = textView;

        keypadButton = new KeypadButton[36];
        buttonLocation = new ButtonLocation[36];
        delButton = new SideButton();

        for (inc=0; inc<=35; inc++) {
            keypadButton[inc] = new KeypadButton();
            buttonLocation[inc] = new ButtonLocation();
           // keypadButton[inc].Key = keypad[inc].Key;
           // keypadButton[inc].Letter = keypad[inc].Letter;
            keypadButton[inc] = keypad[inc];
        } //for (inc=0; inc<=35; inc++)

        delButton = getdelButton;

       // getAppList = new GetAppList();
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
                            String findString;
                            AppItem[] appItems;
                            global = new GlobalHolder();
                            appItems = new AppItem[global.getAppItemSize()];
                            appItems = global.getAppItem();
                            PackageManager PkgMgr;
                            PkgMgr= global.getPackageManager();
                            findString=global.getFindString();

                            String TouchedLetter = determineLetter(currentX, currentY);
                            findString=findString+TouchedLetter;
                            appItems = evaluateAction(PkgMgr,appItems,TouchedLetter);

                            if (buttonLocation[1].Y == 0) //[perform only if not previously initialized
                                {
                                getkeypadLocations();
                                }

                            int action = MotionEventCompat.getActionMasked(event);
                            switch (action) {
                                case (MotionEvent.ACTION_DOWN):
                                    typeoutView.setText(TouchedLetter);
                                    return false;
                                case (MotionEvent.ACTION_MOVE):
                                    TouchedLetter = determineLetter(currentX, currentY);
                                    typeoutView.setText(TouchedLetter);
                                    return false;
                                case (MotionEvent.ACTION_UP):
                                    typeoutView.setText(TouchedLetter);
                                    typeoutView.append(" ");
                                    global.setFindString(findString);
                                    typeoutView.append(findString);
                                    //callListeners(PkgMgr,appItems);
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

 //-----------------------------   DELETE KEY ------------------------------------------------------------
        delButton.Key.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) { //perform action of click
                        GlobalHolder global;
                        global = new GlobalHolder();
                        String findString = global.getFindString();

                        if (findString.length() > 0 && findString!=null) {
                            findString = findString.substring(0, findString.length()-1);
                        } //if (findString.length() > 0 && findString.charAt(findString.length()-1)=='x')
                        typeoutView.setText(findString);
                        global.setFindString(findString);
                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //delButton.Key.setOnClickListener

        delButton.Key.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) { //perform action of click
                        GlobalHolder global;
                        global = new GlobalHolder();
                        global.setFindString("");

                        typeoutView.setText("");

                        return true;
                    } //public void OnClick(View v)
                } //new Button.OnClickListener()

        ); //myButtonTrigger.setOnLongClickListener



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

        String LetterFound=" ";
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

    private AppItem[] evaluateAction(PackageManager PkgMgr, AppItem[] appItems, String getCurrentLetter) {

        GetAppList getAppList;
        getAppList = new GetAppList();

        if (!getCurrentLetter.equals(" ")) {
            appItems = getAppList.filterByFirstChar(appItems, getCurrentLetter);
        } //if (!getCurrentLetter.equals(" "))
        else {
            appItems = getAppList.all_appItems(PkgMgr, appItems);
        } //else of if (!getCurrentLetter.equals(" "))

        new DrawDrawerBox(global.getMainContext(), global.getGridView(), appItems);

        return appItems;
    }// private void evaluateAction(String getCurrentLetter)

    /*
    private void callListeners(PackageManager pm, AppItem[] appItems) {

        Context clickListenerContext;
        GlobalHolder global;
        GridView drawerGrid;

        global = new GlobalHolder();
        clickListenerContext = global.getMainContext();
        drawerGrid = global.getGridView();

        AppDrawerAdapter drawerAdapterObject;

        drawerAdapterObject = new AppDrawerAdapter(clickListenerContext, appItems);
        drawerGrid.setAdapter(drawerAdapterObject);
        drawerGrid.setOnItemClickListener(new DrawerClickListener(clickListenerContext, appItems, pm));
        drawerGrid.setOnItemLongClickListener(new DrawerLongClickListener(clickListenerContext, appItems, pm));

    } // public void callListeners(PackageManager pm, AppItem[] appItems)*/


}//public class KeypadTouchListener


