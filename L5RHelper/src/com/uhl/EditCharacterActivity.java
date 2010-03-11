package com.uhl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.uhl.db.DBServiceLocator;
import com.uhl.db.DefaultViews;
import com.uhl.db.IDBHelper;
import com.uhl.db.Profile;

public class EditCharacterActivity extends Activity implements OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_character);
        dbHelper = DBServiceLocator.getDBHelper(this);
        Bundle info = getIntent().getExtras();
        int id;
        if(info != null){
        	id = info.getInt("ID");
        }else { id = 0;}
        if(id != 0){
        	profile = dbHelper.loadProfile(getIntent().getExtras().getInt("ID"));
        	existingProfile = true;
        	configName();
        }else{
        	profile = new Profile(null, -1);
        }
        RegisterButtons();
        
        errorLabel = this.<TextView>GetView(R.id.error_message_create_char);
    }
           
	private void configName() {
		EditText nameBox = this.<EditText>GetView(R.id.char_name);
		nameBox.setText(profile.getName());
		if(profile.getDefaultViewId() == DefaultViews.melee.getId()){
			this.<RadioGroup>GetView(R.id.radio_group).check(R.id.radio_melee);
		}else{
			this.<RadioGroup>GetView(R.id.radio_group).check(R.id.radio_caster);
		}
		
	}

	private Profile profile;
	private IDBHelper dbHelper;
	private boolean existingProfile = false; 
	private TextView errorLabel;
	

	private void RegisterButtons() {
		this.<Button>GetView(R.id.submit_button).setOnClickListener(this);		
	}
	
	@SuppressWarnings("unchecked")
	private <T extends View> T GetView(int id) {
		try{
			return (T)findViewById(id);}
		catch(Exception e){
			return null;
		}
	}


	@Override
	public void onClick(View e) {		
		Button button = this.<Button>GetView(e.getId());		
		switch(button.getId()){
			case R.id.submit_button:SubmitPressed();break;
			case R.id.submit_caster_stats:
			case R.id.submit_melee_stats:SubmitToDB();break;
			default: break;
		}		
	}


	private void SubmitToDB() {
		int eRing = GetValue(R.id.spin_earth);
		int wRing = GetValue(R.id.spin_water);
		int fRing = GetValue(R.id.spin_fire);
		int aRing = GetValue(R.id.spin_air);
		int vRing = GetValue(R.id.spin_void);
		int ref = GetValue(R.id.spin_reflexes);
		int agi = GetValue(R.id.spin_agility);
		int luck = GetValue(R.id.spin_luck);
		int gp = GetValue(R.id.spin_gp);
		
		profile.SetStats(eRing, wRing, fRing, aRing, vRing, ref, agi, luck, gp);
		dbHelper.saveProfile(profile);
		//#todo transition to character loaded main screen.
		this.finish();
	}


	private int GetValue(int id) {
		int result = -1; //#todo find a better way to do this.
		Spinner spinner = this.<Spinner>GetView(id);
		if(spinner == null)
			return result;
		String value = (String)spinner.getSelectedItem();
		try {
			result = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			if(value.equals("Yes"))
				result = 1;
			if(value.equals("No"))
				result = 0;
		}
		return result;
	}


	private void SubmitPressed() {
		EditText nameBox = this.<EditText>GetView(R.id.char_name); 
		String name = nameBox.getText().toString();
		if(name == null || name .equals("")){
			errorLabel.setText(getString(R.string.error_no_name));
			return;
		}
		if(dbHelper.profileNameExists(name, profile.getId())){
			errorLabel.setText(getString(R.string.name_in_use));			
			return;
		
		}
		
		int defaultView = -1;
		int checkedButton = this.<RadioGroup>GetView(R.id.radio_group).getCheckedRadioButtonId();
		
		switch(checkedButton){
			case R.id.radio_melee:
				defaultView = DefaultViews.melee.getId();
				if(existingProfile){
					profile.setName(name); 
					profile.setDefaultViewId(defaultView);
				}else{ profile = new Profile(name, defaultView); }
				SetupMeleeEntry();
				break;
			case R.id.radio_caster:
				defaultView = DefaultViews.caster.getId();
				if(existingProfile){
					profile.setName(name); 
					profile.setDefaultViewId(defaultView);
				}else{ profile = new Profile(name, defaultView); }
				SetupCasterEntry();
				break;
			default:break;
		}
	}


	private void SetupCasterEntry() {
		setContentView(R.layout.character_entry_caster);		
		setSpinnerFromProfile(R.id.spin_earth, profile.getEarthRing());
		setSpinnerFromProfile(R.id.spin_water, profile.getWaterRing());
		setSpinnerFromProfile(R.id.spin_fire, profile.getFireRing());
		setSpinnerFromProfile(R.id.spin_air, profile.getAirRing());
		setSpinnerFromProfile(R.id.spin_void, profile.getVoidRing());
		setSpinnerFromProfile(R.id.spin_luck, profile.getLuck()+1);	
		
		this.<Button>GetView(R.id.submit_caster_stats).setOnClickListener(this);
		
	}

	private void setSpinnerFromProfile(int id, int value) {
		Spinner spinner = this.<Spinner>GetView(id);
		spinner.setSelection(value-1);		
	}

	private void SetupMeleeEntry() {		
		setContentView(R.layout.character_entry_melee);
		setSpinnerFromProfile(R.id.spin_earth, profile.getEarthRing());
		setSpinnerFromProfile(R.id.spin_water, profile.getWaterRing());
		setSpinnerFromProfile(R.id.spin_fire, profile.getFireRing());
		setSpinnerFromProfile(R.id.spin_air, profile.getAirRing());
		setSpinnerFromProfile(R.id.spin_void, profile.getVoidRing());
		setSpinnerFromProfile(R.id.spin_reflexes, profile.getReflexes());
		setSpinnerFromProfile(R.id.spin_agility, profile.getAgility());
		setSpinnerFromProfile(R.id.spin_luck, profile.getLuck()+1);	
		setSpinnerFromProfile(R.id.spin_gp, profile.getGp()+1);
			
		this.<Button>GetView(R.id.submit_melee_stats).setOnClickListener(this);
		
	}

}
