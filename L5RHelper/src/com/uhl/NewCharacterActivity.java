package com.uhl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NewCharacterActivity extends Activity implements OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_character);        
        RegisterButtons();
    }   


	private void RegisterButtons() {
		GetButton(R.id.submit_button).setOnClickListener(this);		
	}
	
	private Button GetButton(int id) {
		return (Button)findViewById(id);
	}


	@Override
	public void onClick(View e) {		
		Button button = GetButton(e.getId());		
		switch(button.getId()){
			case R.id.submit_button:SubmitPressed();
			default: break;
		}		
	}


	private void SubmitPressed() {
		setContentView(R.layout.character_entry);
	}


	private void StartActivity(Class<NewCharacterActivity> classInput) {
		this.startActivity(new Intent(this, classInput));		
	}

}
