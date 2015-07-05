package com.archbrey.www.letters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;


public class DrawerLongClickListener implements OnItemLongClickListener  {

    Context longClickListenerContext;
    AppItem[] appItemForListener;

    public DrawerLongClickListener (Context c, AppItem[] appItem, PackageManager pm){

        longClickListenerContext = c;
        appItemForListener = appItem;

    }//public DrawerLongClicklistener (Context c, RelativeLayout mainView)

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View viewItem, int pos, long l) {

        //launch app manager
        //Intent intent = new Intent(Intent.ACTION_VIEW);
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", appItemForListener[pos].pkgname, null);
        intent.setData(uri);
        longClickListenerContext.startActivity(intent);

        return true;
    }
}