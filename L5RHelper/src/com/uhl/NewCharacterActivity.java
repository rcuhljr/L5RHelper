package com.uhl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.uhl.db.DBHelper;
import com.uhl.db.DefaultViews;
import com.uhl.db.Profile;

public class NewCharacterActivity extends Activity implements OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.new_character);
        RegisterButtons();
    }
           
	private Profile profile;
	private DBHelper dbHelper;
	

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
		if(name == null || name .equals("") || name.equals(getString(R.string.error_no_name))){
			nameBox.setText(getString(R.string.error_no_name));
			return;
		}
		//#todo check if the name is used in the database.
		
		int defaultView = -1;
		int checkedButton = this.<RadioGroup>GetView(R.id.radio_group).getCheckedRadioButtonId();
		
		switch(checkedButton){
			case R.id.radio_melee:
				defaultView = DefaultViews.melee.getId();
				profile = new Profile(name, defaultView);
				SetupMeleeEntry();
				break;
			case R.id.radio_caster:
				defaultView = DefaultViews.caster.getId();
				profile = new Profile(name, defaultView);
				SetupCasterEntry();
				break;
			default:break;
		}
	}


	private void SetupCasterEntry() {
		HideKeyboard();
		setContentView(R.layout.character_entry_caster);
		
		(this.<Spinner>GetView(R.id.spin_earth)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_water)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_fire)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_air)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_void)).setSelection(2);
		this.<Button>GetView(R.id.submit_caster_stats).setOnClickListener(this);	
		
	}
	
	private void HideKeyboard(){
		InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		EditText textbox = this.<EditText>GetView(R.id.char_name);
		imm.hideSoftInputFromWindow(textbox.getWindowToken(), 0);		
	}

	private void SetupMeleeEntry() {
		HideKeyboard();
		setContentView(R.layout.character_entry_melee);
		(this.<Spinner>GetView(R.id.spin_earth)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_water)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_fire)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_air)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_void)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_reflexes)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_agility)).setSelection(2);	
		this.<Button>GetView(R.id.submit_melee_stats).setOnClickListener(this);
		
	}

}
