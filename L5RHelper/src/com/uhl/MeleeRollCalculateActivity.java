package com.uhl;

import com.uhl.calc.Raises;
import com.uhl.calc.Roll;
import com.uhl.db.DBHelper;
import com.uhl.db.Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MeleeRollCalculateActivity extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        profile = dbHelper.loadProfile(getIntent().getExtras().getInt("ID"));
        setContentView(R.layout.melee_roll_main_screen);
        Setup();        
        RegisterButtons();        
    }

	private DBHelper dbHelper;
    private Profile profile;
    private Roll roll;
    private int staticMod = 0;
    private AlertDialog.Builder resultBuilder;    

	private void RegisterButtons() {
		(this.<Button>GetView(R.id.calculate)).setOnClickListener(this);
	}    
	
	private void SetupResultDialog(int raises){
		resultBuilder = new AlertDialog.Builder(this);
		resultBuilder.setMessage("You can call "+raises+" raises.")
	       .setCancelable(false)
	       .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {	      		 
	           }
	       });	       
	}
	
    private void Setup() {
		roll = new Roll(profile.getAgility(), profile.getAgility(), 0, 0, 0);
		SetRollText();
		CheckBox luck = this.<CheckBox>GetView(R.id.use_luck);
		luck.setEnabled(profile.getLuck() == 1);
		
		this.<EditText>GetView(R.id.misc_mods).addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s) {
				int newValue;
				try{
					newValue = Integer.parseInt(s.toString());
					
				}catch(NumberFormatException e){
					newValue = 0;					
				}				
				roll.setStatMod(roll.getStatModRaw()-staticMod+newValue);
				staticMod = newValue;	
				SetRollText();
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		});
	}

    

	private void SetRollText() {
		this.<TextView>GetView(R.id.roll_text).setText(roll.toString());
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

	@Override
	public void onClick(View e) {
		Button button = this.<Button>GetView(e.getId());		
		switch(button.getId()){
			case R.id.calculate:Calculate();break;
			default: break;
		}
	}

	private void Calculate() {
		
		InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		EditText textbox = this.<EditText>GetView(R.id.misc_mods);
		imm.hideSoftInputFromWindow(textbox.getWindowToken(), 0);		
		
		int luck = this.<CheckBox>GetView(R.id.use_luck).isChecked()? 1 : 0;
		roll.setLuck(luck);	
		
		SetupResultDialog(Raises.calculateRaises(this, 15, roll, 60));		
		AlertDialog result = resultBuilder.create();
		
		result.show();
		
	}
	
	

}
