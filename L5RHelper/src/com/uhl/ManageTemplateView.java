package com.uhl;

import java.util.Hashtable;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
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

public class ManageTemplateView extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  dbHelper = DBServiceLocator.getDBHelper(this);
	  profile = dbHelper.loadProfile(getIntent().getExtras().getInt("ID"));
	  
	  initView();
	  
	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	    		String selection = (String)((TextView) view).getText();
	    		if(selection.equals(getString(R.string.new_template))){
	    			StartActivity(EditTemplateActivity.class, -1, profile.getId() );
	    		}else {
		    		int value = templates.get(selection);	    		
		    		StartActivity(EditTemplateActivity.class, value, profile.getId() );
	    		}
	    }
	  });
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		//for now just return to the list, call finish here if we want to return to character overview.
		initView();
	}
	
	private void initView() {
		buildTemplateTable(profile.getId());
	    String[] names = new String[templates.keySet().size()];
	    templates.keySet().toArray(names);
	    String[] listData = new String[names.length+1];
	    listData[0] = getString(R.string.new_template);
	    System.arraycopy(names, 0, listData, 1, names.length);    
	    
	    setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, listData));		
	}

	private void StartActivity(Class<?> classInput, Integer Id, Integer profileId) {
		Intent intent = new Intent(this, classInput);
		intent.putExtra("ID", Id);
		intent.putExtra("PROFILE_ID", profileId);
		this.startActivityForResult(intent, 0);
		
	}	
	
	private void buildTemplateTable(int profileId) {
		templates = new Hashtable<String, Integer>();
		Cursor cursor = dbHelper.getTemplateNames(profileId);		
		if(cursor.getCount() < 1){
			cursor.close();
			return;
		}
		do{
			templates.put(cursor.getString(1), cursor.getInt(0));
		}while(cursor.moveToNext());
		cursor.close();
	}

	private IDBHelper dbHelper;
	private Hashtable<String, Integer> templates;
	private Profile profile;
	
}
