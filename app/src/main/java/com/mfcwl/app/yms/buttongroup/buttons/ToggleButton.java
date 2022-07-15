package com.mfcwl.app.yms.buttongroup.buttons;

import android.widget.Checkable;

public interface ToggleButton extends Checkable {

    void setOnCheckedChangeListener(OnCheckedChangeListener listener);

}