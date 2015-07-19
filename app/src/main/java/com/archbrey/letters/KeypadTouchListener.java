package com.archbrey.letters;



import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class KeypadTouchListener  {

    private static PackageManager pmForListener;

    private TextView typeoutView;
    public static int SelectedKeyButton;

    private static int keyWidth;
    private static int keyHeight;

    private static KeypadButton[] keypadButton ;
    private static ButtonLocation[] buttonLocation;
    private static GlobalHolder global;
    private static LongTouchHolder longTouch;
    private static SideButton delButton;
    private static AppItem[] returnAppItems;
    private static AppItem[] appItems;
    private static TypeOut typeoutBoxHandle;

    private static class ButtonLocation {
        int X;
        int Y;
    }

    public KeypadTouchListener (TextView textView) {

        int inc;
        typeoutView = textView;
        global = new GlobalHolder();

        keypadButton = new KeypadButton[36];
        buttonLocation = new ButtonLocation[36];
        delButton = new SideButton();
        longTouch = new LongTouchHolder();
        typeoutBoxHandle = new TypeOut();

        pmForListener = global.getPackageManager();
        appItems = global.getAllAppItems();
        longTouch.reset();


        for (inc=0; inc<=35; inc++) {
            keypadButton[inc] = new KeypadButton();
            buttonLocation[inc] = new ButtonLocation();
            keypadButton[inc] = DrawKeypadBox.keypadButton[inc];
        } //for (inc=0; inc<=35; inc++)

        //delButton = DrawKeypadBox.delButton;
        delButton = global.getDelButton();

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
                            LaunchpadActivity.hideDrawerAllApps = true;

                            longTouch = new LongTouchHolder();
                            String TouchedLetter = determineLetter(currentX, currentY);

                            if (typeoutBoxHandle.getFindStatus()) {
                                findString = global.getFindString();
                                findString = findString + TouchedLetter;
                                } //if (typeoutBoxHandle.getFindStatus())
                            else
                                findString = TouchedLetter;

                            evaluateAction(appItems, findString);

                            if (findString.length() == 1) isLongPress(TouchedLetter);

                            if (buttonLocation[1].Y == 0) //[perform only if not previously initialized
                            {
                                getkeypadLocations();
                            }
                            typeoutView.setText(findString);

                            if (keypadButton[SelectedKeyButton].ShortcutPackage.length()>1) {
                                typeoutView.append(" - ");
                                typeoutView.append(keypadButton[SelectedKeyButton].ShortcutLabel);
                                if (longTouch.getStatus())
                                    global.getDrawerBox().setVisibility(View.INVISIBLE);
                            } //if (keypadButton[SelectedKeyButton].ShortcutPackage.length()>1))

                            int action = MotionEventCompat.getActionMasked(event);
                            switch (action) {
                                case (MotionEvent.ACTION_DOWN):
                                  //  typeoutView.setText(findString);
                                    //if (longTouch.getStatus()) typeoutView.append("long click");
                                    return false;
                                case (MotionEvent.ACTION_MOVE):
                                  //  typeoutView.setText(findString);
                                   // if (longTouch.getStatus()) typeoutView.append("long click");
                                    return false;
                                case (MotionEvent.ACTION_UP):
                                    typeoutView.setText(findString);
                                    global.setFindString(findString);

                                    if (longTouch.getStatus()) {
                                          launchShortcut();
                                          longTouch.reset();
                                        } // if (longTouch.getStatus())
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
                            String findString;
                            longTouch = new LongTouchHolder();
                            global = new GlobalHolder();

                            findString = global.getFindString();
                            findString = findString + longTouchedLetter;
                            if (findString.length()==1) {

                                typeoutView.setText(findString);
                                typeoutView.append(" long click");
                                longTouch.setStatus(true);

                            }//if (findString.length()==1)
                            return false;
                        } //public void OnClick(View v)
                    } //new Button.OnClickListener()

            ); //myButtonTrigger.setOnLongClickListener


        } //for (inc=0; inc<=35; inc++)

 //-----------------------------   DELETE KEY ------------------------------------------------------------
        delButton.Key.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) { //perform action of click
                     //   AppItem[] appItems;
                       // PackageManager PkgMgr;

                        global = new GlobalHolder();
                        String findString = global.getFindString();
                      //  PkgMgr= global.getPackageManager();
                       // appItems = global.getAppItem();

                        if (findString.length() > 0 && findString!=null) {
                            findString = findString.substring(0, findString.length() - 1);
                            callAppListeners(appItems);
                            global.setFindString(findString);
                        } //if (findString.length() > 0 && findString.charAt(findString.length()-1)=='x')
                        typeoutView.setText(findString);
                        evaluateAction(appItems, findString);
                    } //public void OnClick(View v)
                } //new Button.OnClickListener()
        ); //delButton.Key.setOnClickListener

        delButton.Key.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) { //perform action of click
                        //    AppItem[] appItems;
                        // PackageManager PkgMgr;

                        global = new GlobalHolder();
                        //PkgMgr = global.getPackageManager();
                        //    appItems = global.getAppItem();
                        global.setFindString("");
                        evaluateAction(appItems, "");
                        //callAppListeners(appItems);
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
                    {LetterFound=keypadButton[inc].Letter;
                      SelectedKeyButton=inc;
                    }

        } //for (inc=0; inc<=35; inc++)

        return LetterFound;

    } //string determineLetter(int TouchX, int TouchY)

    private boolean isLongPress (String getTouchedLetter) {

        if (!getTouchedLetter.equals(longTouch.getKeyString())){
            longTouch.setStartTime();
            longTouch.setKeyString(getTouchedLetter);
            longTouch.setStatus(false);
        } //if (searchString.equals(longTouch.getKeyString()))
        else {
            if (longTouch.getCurentTime()-longTouch.getStartTime()>=400) {
                longTouch.setStatus(true);
            } //(longTouch.getCurentTime()-longTouch.getStartTime()>=1000)
        } //else of if (!searchString.equals(longTouch.getKeyString()))

        return longTouch.getStatus();
    } //private boolean isLongPress(String getTouchedLetter)


    private AppItem[] evaluateAction(AppItem[] appItems, String searchString) {

        GetAppList getAppList;
        View drawerBox;
        RelativeLayout typeoutBox;
        getAppList = new GetAppList();
        int searchLength = searchString.length();

        int filteredSize;

        global = new GlobalHolder();
        longTouch = new LongTouchHolder();
        drawerBox = global.getDrawerBox();
        typeoutBox = global.getTypeoutBox();

        if (searchLength == 0)  {
            typeoutBoxHandle.setFindStatus(false); //stop search mode if length = 0;
            drawerBox.setVisibility(View.INVISIBLE);
            typeoutBox.setVisibility(View.INVISIBLE);
            TypeOut.editView.setVisibility(View.GONE);
        } //else of if (!getCurrentLetter.equals(" "))
        else if ( (searchLength == 1) && (!typeoutBoxHandle.getFindStatus()) ) {
            drawerBox.setVisibility(View.VISIBLE);
            typeoutBox.setVisibility(View.VISIBLE);
            filteredSize = getAppList.filterByFirstChar(searchString);
            TypeOut.editView.setVisibility(View.VISIBLE);
            TypeOut.editMode= 1;
            returnAppItems = new AppItem[filteredSize];
        } //if if (searchLength == 1)
        else if ( (searchLength > 1) || (typeoutBoxHandle.getFindStatus()) ) {
            filteredSize = getAppList.filterByString(searchString);
            TypeOut.editView.setVisibility(View.GONE);
            TypeOut.editMode= 0;
            returnAppItems = new AppItem[filteredSize];
        } //if (searchLength > 1)


        if (searchLength > 0) {
                returnAppItems = getAppList.getFilteredApps();
                new DrawDrawerBox(global.getMainContext(), global.getGridView(), returnAppItems);
                callAppListeners(returnAppItems);
                }


        return returnAppItems;
    }// private void evaluateAction(String getCurrentLetter)

    private void launchShortcut(){

      if (keypadButton[SelectedKeyButton].ShortcutPackage.length()>1) { //check if keypad has assigned shortcut

          Intent launchIntent = pmForListener.getLaunchIntentForPackage(keypadButton[SelectedKeyButton].ShortcutPackage);

          global.getMainContext().startActivity(launchIntent);

            AppItem launched;
            launched = new AppItem();
            launched.pkgname = keypadButton[SelectedKeyButton].ShortcutPackage;
            launched.label = keypadButton[SelectedKeyButton].ShortcutLabel;
            launched.name = "blank";
            new GetAppList().addRecentApp(launched);

      } //if(keypadButton[SelectedKeyButton].ShortcutPackage.length()>1)

    }//private void launchShortcut()

    private void callAppListeners(AppItem[] appItems) {

        Context clickListenerContext;
        GlobalHolder global;
        GridView drawerGrid;
        PackageManager pm;

        global = new GlobalHolder();
        clickListenerContext = global.getMainContext();
        drawerGrid = global.getGridView();
        pm=global.getPackageManager();

        AppDrawerAdapter drawerAdapterObject;

        drawerAdapterObject = new AppDrawerAdapter(clickListenerContext, appItems);
        drawerGrid.setAdapter(drawerAdapterObject);
        drawerGrid.setOnItemClickListener(new DrawerClickListener(clickListenerContext, appItems, pm));
        drawerGrid.setOnItemLongClickListener(new DrawerLongClickListener(clickListenerContext, appItems, pm));

    } // public void callListeners(PackageManager pm, AppItem[] appItems)


}//public class KeypadTouchListener


