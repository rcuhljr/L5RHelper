package com.uhl;

import java.util.Hashtable;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.uhl.db.DBServiceLocator;
import com.uhl.db.IDBHelper;
import com.uhl.db.Profile;

public class LoadProfileView extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  dbHelper = DBServiceLocator.getDBHelper(this);
	  
	  BuildUserTable();
	  String[] names = new String[profiles.keySet().size()];
	  profiles.keySet().toArray(names);

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, names));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	    		int value = profiles.get((String)((TextView) view).getText());
	    		if ( value == -1) return;
	    		StartActivity(ProfileOverviewActivity.class, value );
	    }
	  });
	}
	
	private void StartActivity(Class<?> classInput, Integer Id) {
		Intent intent = new Intent(this, classInput);
		intent.putExtra("ID", Id);
		this.startActivityForResult(intent, 0);
		
	}	

	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		//for now just return to the main activity, however we could stop here if they selected something to load a new character
		this.finish();
	}
	
	private void BuildUserTable() {
		Profile[] profileSet = dbHelper.getProfiles();
		if(profileSet.length < 1){
			profiles.put("No Profiles", -1);
			return;
		}
		for(Profile profile : profileSet){
			profiles.put(profile.getName(), profile.getId());			
		}
		
		
		
	}

	private IDBHelper dbHelper;
	private Hashtable<String, Integer> profiles = new Hashtable<String, Integer>();
	
}
