package com.uhl;

import com.uhl.calc.Roll;
import com.uhl.db.DBHelper;
import com.uhl.db.Profile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RollCalculateActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        profile = dbHelper.loadProfile(getIntent().getExtras().getInt("ID"));
        setContentView(R.layout.roll_main_screen);
        Roll aRoll = new Roll(profile.getAgility(), profile.getAgility(), 0, 0, 0);
        TextView myText = ((TextView)findViewById(R.id.roll_text));
        myText.setText(aRoll.toString());
//        DisplayData();        
//        RegisterButtons();
//        SetupDeleteDialog();
    }
    
    private DBHelper dbHelper;
    private Profile profile;

}
