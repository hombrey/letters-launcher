package com.archbrey.letters.Preferences;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.archbrey.letters.GlobalHolder;
//import com.archbrey.letters.Preferences.*;
import com.archbrey.letters.R;
//import com.archbrey.letters.LaunchpadActivity;


public class SettingsDrawerAdapter extends BaseAdapter  {

    private static Context getViewContext;
    private String[] ItemForAdapter;
   // private static int drawerTextSize;
    private static Resources rDrawer;
    private static com.archbrey.letters.Preferences.SettingsHolder holder;
    private static GlobalHolder global;

    public SettingsDrawerAdapter(Context c, String passedAppItem[]){
        getViewContext = c;
        ItemForAdapter = passedAppItem;
        holder = new com.archbrey.letters.Preferences.SettingsHolder();
        global = new GlobalHolder();
    }


    @Override
    public int getCount() {

        return ItemForAdapter.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        rDrawer = global.getResources();


        TextView viewHolder;
        LayoutInflater getViewInflater = (LayoutInflater) getViewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView==null){
            convertView = getViewInflater.inflate(R.layout.drawer_item, null);

            viewHolder = new TextView(getViewContext);
            viewHolder= (TextView)convertView.findViewById(R.id.icon_text);

            convertView.setTag(viewHolder);
        } //if (convertView==null)
        else
            viewHolder = (TextView) convertView.getTag();


        viewHolder.setText(ItemForAdapter[position]);
        viewHolder.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.drawerTextSize);
        viewHolder.setTextColor(SettingsActivity.textColor);

        return convertView;

    } //public View getView(int position, View view, ViewGroup viewGroup)



} //public class SettingsDrawerAdapter extends BaseAdapter
