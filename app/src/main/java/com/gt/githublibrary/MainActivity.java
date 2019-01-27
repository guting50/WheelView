package com.gt.githublibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.gt.wheelview.use.TimeSelectorUtils;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimeSelectorUtils(MainActivity.this, new TimeSelectorUtils.YMDCallBack() {
                    @Override
                    public void setDate(PopupWindow popupWindow, String date) {
                        Toast.makeText(MainActivity.this, date, Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                    }
                }).getYmdPop().showAsDropDown(findViewById(R.id.rootView));
            }
        });
    }
}
