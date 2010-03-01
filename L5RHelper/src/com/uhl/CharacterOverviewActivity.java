package com.uhl;

import com.uhl.db.DBHelper;
import com.uhl.db.Profile;

import android.app.Activity;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.TextView;

public class CharacterOverviewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        profile = dbHelper.loadProfile(getIntent().getExtras().getInt("ID"));
        setContentView(R.layout.display_profile_data);
        DisplayData();        
      //  RegisterButtons();
    }
    
	private void DisplayData() {
	
		(this.<TextView>GetView(R.id.TextView01)).setText("id:" + String.valueOf(profile.getId()));
		(this.<TextView>GetView(R.id.TextView02)).setText("name:" + profile.getName());
		(this.<TextView>GetView(R.id.TextView03)).setText("dvid:" + String.valueOf(profile.getDefaultViewId()));
		(this.<TextView>GetView(R.id.TextView04)).setText("er:" + String.valueOf(profile.getEarthRing()));
		(this.<TextView>GetView(R.id.TextView05)).setText("wr:" + String.valueOf(profile.getWaterRing()));
		(this.<TextView>GetView(R.id.TextView06)).setText("fr:" + String.valueOf(profile.getFireRing()));
		(this.<TextView>GetView(R.id.TextView07)).setText("ar:" + String.valueOf(profile.getAirRing()));
		(this.<TextView>GetView(R.id.TextView08)).setText("vr:" + String.valueOf(profile.getVoidRing()));
		(this.<TextView>GetView(R.id.TextView09)).setText("ref:" + String.valueOf(profile.getReflexes()));
		(this.<TextView>GetView(R.id.TextView10)).setText("agi:" + String.valueOf(profile.getAgility()));
		(this.<TextView>GetView(R.id.TextView11)).setText("luck:" + String.valueOf(profile.getLuck()));
		(this.<TextView>GetView(R.id.TextView12)).setText("gp:" + String.valueOf(profile.getGp()));
		
	}
	
	@SuppressWarnings("unchecked")
	public <T extends View> T GetView(int id) {
		T result = null;
		try{			
			result = (T)findViewById(id);}
		catch(Exception e){			
		}
		return result;
	}

	private Profile profile;
	private DBHelper dbHelper;
}
