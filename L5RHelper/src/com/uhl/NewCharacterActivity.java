package com.uhl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

public class NewCharacterActivity extends Activity implements OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_character);        
        RegisterButtons();
    }   


	private void RegisterButtons() {
		this.<Button>GetView(R.id.submit_button).setOnClickListener(this);		
	}
	
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
			case R.id.submit_button:SubmitPressed();
			default: break;
		}		
	}


	private void SubmitPressed() {
		if((this.<RadioButton>GetView(R.id.radio_melee)).isChecked()){
			SetupMeleeEntry();
		}else if ((this.<RadioButton>GetView(R.id.radio_caster)).isChecked()){
			setContentView(R.layout.character_entry_caster);
		}
	}


	private void SetupMeleeEntry() {
		setContentView(R.layout.character_entry_melee);
		(this.<Spinner>GetView(R.id.spin_earth)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_water)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_fire)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_air)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_void)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_reflexes)).setSelection(2);
		(this.<Spinner>GetView(R.id.spin_agility)).setSelection(2);		
	}


	private void StartActivity(Class<NewCharacterActivity> classInput) {
		this.startActivity(new Intent(this, classInput));		
	}

}
