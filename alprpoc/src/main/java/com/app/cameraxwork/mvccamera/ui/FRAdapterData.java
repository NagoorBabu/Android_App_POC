/* Decompiler 5ms, total 271ms, lines 53 */
package com.app.cameraxwork.mvccamera.ui;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


import com.app.cameraxwork.mvccamera.util.DfcGlobalFunctions;

import java.util.List;

public class FRAdapterData extends ArrayAdapter<FSData> {
   public FRAdapterData(Context context, int resource, int textViewResourceId, List<FSData> objects) {
      super(context, resource, textViewResourceId, objects);
   }

   public FRAdapterData(Context context, int resource) {
      super(context, resource);
   }

   public View getView(int position, View convertView, ViewGroup parent) {
      TextView textView = (TextView)super.getView(position, convertView, parent);
      textView.setPadding(DfcGlobalFunctions.dpToPx(10), textView.getPaddingTop(), DfcGlobalFunctions.dpToPx(20), textView.getPaddingBottom());
      textView.setTextColor(ContextCompat.getColor(this.getContext(), android.R.color.white));
      textView.setGravity(17);
      FSData data = (FSData)this.getItem(position);
      textView.setText(data.getName());
      textView.setSingleLine();
      textView.setEllipsize(TruncateAt.END);
      textView.setTextSize(2, 14.0F);
      return textView;
   }

   public int getCount() {
      return super.getCount();
   }

   public View getDropDownView(int position, View convertView, ViewGroup parent) {
      TextView textView = (TextView)super.getDropDownView(position, convertView, parent);
      textView.setTextColor(ContextCompat.getColor(this.getContext(), android.R.color.black));
      FSData data = (FSData)this.getItem(position);
      textView.setText(data.getName());
      textView.setEllipsize(TruncateAt.END);
      textView.setSingleLine();
      textView.setGravity(17);
      textView.setTextSize(2, 14.0F);
      return textView;
   }
}
