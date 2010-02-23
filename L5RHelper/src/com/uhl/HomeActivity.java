package com.uhl;

import com.uhl.calc.Histogram;
import com.uhl.calc.Roll;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("test");
        Histogram hist = new Histogram(new Roll(10,5,0,0), this);
        int result = hist.getHighestTN(50);
        tv.setText("Hello, value:"+result);
        setContentView(tv);
    }
}