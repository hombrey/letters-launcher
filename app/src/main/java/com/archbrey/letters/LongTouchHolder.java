package com.archbrey.letters;


public class LongTouchHolder {

    private static String keyString;
    private static boolean status;

    public LongTouchHolder(){
    }

    public void setKeyString(String getKeyString){keyString = getKeyString;
    }

    public String getKeyString(){return keyString;
    }


    public void setStatus(boolean getStatus) {status = getStatus;
    }

    public boolean getStatus() {return status;
    }

    public void reset() {
        keyString="null";
        status = false;
    } //public void reset()

}
