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

import com.uhl.db.DBHelper;
import com.uhl.db.Profile;

public class ManageTemplateView extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  dbHelper = new DBHelper(this);
	  profile = dbHelper.loadProfile(getIntent().getExtras().getInt("ID"));
	  
	  buildTemplateTable(profile.getId());
	  String[] names = new String[templates.keySet().size()];
	  templates.keySet().toArray(names);

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, names));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	    		int value = templates.get((String)((TextView) view).getText());	    		
	    		StartActivity(EditTemplateActivity.class, value, profile.getId() );
	    }
	  });
	}
	
	private void StartActivity(Class<?> classInput, Integer Id, Integer profileId) {
		Intent intent = new Intent(this, classInput);
		intent.putExtra("ID", Id);
		intent.putExtra("PROFILE_ID", profileId);
		this.startActivityForResult(intent, 0);
		
	}	
	
	private void buildTemplateTable(int profileId) {
		Cursor cursor = dbHelper.getTemplates(profileId);
		templates.put("New Template", -1);
		if(cursor.getCount() < 1){
			cursor.close();
			return;
		}
		do{
			templates.put(cursor.getString(1), cursor.getInt(0));
		}while(cursor.moveToNext());
		cursor.close();
	}

	private DBHelper dbHelper;
	private Hashtable<String, Integer> templates = new Hashtable<String, Integer>();
	private Profile profile;
	
}
