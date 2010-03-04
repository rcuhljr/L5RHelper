package com.uhl;

import com.uhl.db.DBHelper;
import com.uhl.db.Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CharacterOverviewActivity extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        profile = dbHelper.loadProfile(getIntent().getExtras().getInt("ID"));
        setContentView(R.layout.display_profile_data);
        DisplayData();        
        RegisterButtons();
        SetupDeleteDialog();
    }
    
    private AlertDialog.Builder deleteConfirmBuilder;
    
    private void SetupDeleteDialog() {
    	deleteConfirmBuilder = new AlertDialog.Builder(this);
    	deleteConfirmBuilder.setMessage("Are you sure you want to delete this character?")
	       .setCancelable(false)
	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	      		 dbHelper.deleteProfile(profile.getId()); 
	      		 CharacterOverviewActivity.this.setResult(Activity.RESULT_OK);  
	      		 CharacterOverviewActivity.this.finish();
	           }
	       })
	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	       });
	}

	
    
	private void RegisterButtons() {
		(this.<Button>GetView(R.id.return_main)).setOnClickListener(this);
		(this.<Button>GetView(R.id.delete_char)).setOnClickListener(this);
		(this.<Button>GetView(R.id.go_calculate)).setOnClickListener(this);
		(this.<Button>GetView(R.id.manage_character)).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View e) {		
		Button button = this.<Button>GetView(e.getId());		
		switch(button.getId()){
			case R.id.return_main:this.setResult(Activity.RESULT_OK); this.finish();break; 
			case R.id.delete_char:DeleteConfirm();break; 
			case R.id.go_calculate: StartActivityForResult(MeleeRollCalculateActivity.class, profile.getId()); break;
			case R.id.manage_character: StartActivityForResult(NewCharacterActivity.class, profile.getId()); break;
			case R.id.manage_templates: break;
			default: break;
		}		
	}	

	
	private void StartActivityForResult(Class<?> classInput, Integer Id) {
		Intent intent = new Intent(this, classInput);
		intent.putExtra("ID", Id);
		this.startActivityForResult(intent, 0);
		
	}

	private void DeleteConfirm() {
		AlertDialog aDialog = deleteConfirmBuilder.create(); 
		aDialog.setOwnerActivity(this); 
		aDialog.show();
	}



	private void DisplayData() {
		(this.<TextView>GetView(R.id.character_name)).setText(profile.getName());		
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
