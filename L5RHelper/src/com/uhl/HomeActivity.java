package com.uhl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        RegisterButtons();
    }   


	private void RegisterButtons() {
		GetButton(R.id.create_new).setOnClickListener(this);
		GetButton(R.id.load_existing).setOnClickListener(this);
	}


	private Button GetButton(int id) {
		return (Button)findViewById(id);
	}


	@Override
	public void onClick(View e) {		
		Button button = GetButton(e.getId());		
		switch(button.getId()){
			case R.id.create_new:StartActivity(NewCharacterActivity.class);
			default: break;
		}		
	}


	private void StartActivity(Class<NewCharacterActivity> classInput) {
		this.startActivity(new Intent(this, classInput));		
	}
	
}