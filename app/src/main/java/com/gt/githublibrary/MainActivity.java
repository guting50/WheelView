package com.gt.githublibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.gt.wheelview.use.TimeSelectorUtils;

import java.util.Date;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date data = new Date();
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    data = sdf.parse("2020-1-3 12:15:00");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                new TimeSelectorUtils(MainActivity.this, data, new TimeSelectorUtils.YMDCallBack() {
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
