package com.archbrey.www.letters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.TextView;

public class AppDrawerAdapter extends BaseAdapter {

    Context getViewContext;
    AppItem[] appItemForAdapter;
    int drawerTextSize;

    public AppDrawerAdapter (Context c, AppItem passedAppItem[]){
        getViewContext = c;
        appItemForAdapter = passedAppItem;
    }


    @Override
    public int getCount() {

        return appItemForAdapter.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder{
        TextView text;
        // ImageView icon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        drawerTextSize = 17;
        ViewHolder viewHolder;
        LayoutInflater getViewInflater = (LayoutInflater) getViewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView==null){
            convertView = getViewInflater.inflate(R.layout.drawer_item, null);

            viewHolder = new ViewHolder();
            viewHolder.text= (TextView)convertView.findViewById(R.id.icon_text);
            // viewHolder.icon= (ImageView)convertView.findViewById(R.id.icon_image);

            convertView.setTag(viewHolder);
        } //if (convertView==null)
        else
            viewHolder = (ViewHolder) convertView.getTag();


        //  viewHolder.icon.setImageDrawable(appItemForAdapter[position].icon);
        viewHolder.text.setText(appItemForAdapter[position].label);
        //viewHolder.text.setTextSize(R.dimen.drawer_textSize);
        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, drawerTextSize);
        return convertView;

    } //public View getView(int position, View view, ViewGroup viewGroup)

} //public class AppDrawerAdapter extends BaseAdapter
