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
        registerButtons();
    }

	private void registerButtons() {
		(this.<Button>getView(R.id.create_new)).setOnClickListener(this);
		(this.<Button>getView(R.id.load_existing)).setOnClickListener(this);
	}


	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T result = null;
		try{			
			result = (T)findViewById(id);}
		catch(Exception e){			
		}
		return result;
	}


	@Override
	public void onClick(View e) {		
		Button button = this.<Button>getView(e.getId());		
		switch(button.getId()){
			case R.id.create_new:StartActivity(EditCharacterActivity.class);break; 
			case R.id.load_existing:StartActivity(LoadProfileView.class);break;
			default: break;
		}		
	}


	private void StartActivity(Class<?> classInput) {
		this.startActivity(new Intent(this, classInput));		
	}
	
}