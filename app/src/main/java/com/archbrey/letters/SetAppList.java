package com.archbrey.letters;


import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.archbrey.letters.LaunchpadActivity;
import com.archbrey.letters.TypeOut;

public class SetAppList {

    public void SelectMode() {

      if (TypeOut.editMode<10) {

          LaunchpadActivity.keypadBox.setVisibility(View.GONE);
          LaunchpadActivity.filterBox.setVisibility(View.INVISIBLE);
          TypeOut.findToggleView.setVisibility(View.GONE);
          TypeOut.editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
          TypeOut.typeoutView.append(" - Select");
          TypeOut.editView.setText("Done");

          if (TypeOut.editMode == 1) {
              //perform actions
              ShortcutSelect();
              TypeOut.editMode = 11;

          }//if (TypeOut.editMode==1)
          else if (TypeOut.editMode == 2) {
              //perform actions
              FilterItemsSelect();
              TypeOut.editMode = 12;
          }//if (TypeOut.editMode==2)

      } //if (TypeOut.editMode<10)

    else {

          LaunchpadActivity.keypadBox.setVisibility(View.VISIBLE);
          LaunchpadActivity.filterBox.setVisibility(View.VISIBLE);
          TypeOut.findToggleView.setVisibility(View.VISIBLE);
          TypeOut.editView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TypeOut.TextSize);
          TypeOut.editView.setText(String.valueOf(Character.toChars(177)));
          LaunchpadActivity.drawerBox.setVisibility(View.INVISIBLE);
          TypeOut.typeoutBox.setVisibility(View.INVISIBLE);
          TypeOut.typeoutView.setText("");

      } //else of /if (TypeOut.editMode<10)

    } // public void SelectMode()


    private void ShortcutSelect() {


    } //private void ShortcutSelect ()


    private void FilterItemsSelect(){

    } //private void FilterItemsSelect()

} //public class SetAppList
