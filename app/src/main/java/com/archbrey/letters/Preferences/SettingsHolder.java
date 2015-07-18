package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;

public class SettingsHolder {

    public Context SettingsContext;
    public Resources rSettings;

    public void setResources (Resources getR) {rSettings = getR;
    }//public void setResources

    public Resources getResources () {return rSettings;
    }//public void setDrawerBox (View getDrawerBox)


    public void setSettingsContext (Context getContext){ SettingsContext = getContext;
    } //public void getMainContext (Context getContext)

    public Context getSettingsContext (){ return SettingsContext;
    } //public void getMainContext (Context getContext)
}
